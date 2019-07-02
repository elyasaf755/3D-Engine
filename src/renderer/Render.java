package renderer;

import com.sun.jmx.snmp.tasks.Task;
import elements.Camera;
import elements.LightSource;

import geometries.*;
import primitives.*;

import scene.Scene;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.concurrent.*;

import static geometries.Intersectable.GeoPoint;

public class Render {
    private Scene _scene;
    private ImageWriter _imageWriter;
    private static final int RECURSION_LEVEL = 3;
    private static final double MIN_CALC_COLOR_K = 0.001;

    //Constructors

    public Render(Scene scene, ImageWriter imageWriter){
        _scene = scene;
        _imageWriter = imageWriter;
    }

    //Methods

    /*construct rays through every pixel and calculate its color*/
    public void renderImage(){
        Camera camera = _scene.get_camera();

        int cores = Runtime.getRuntime().availableProcessors();
        ExecutorService pool = Executors.newFixedThreadPool(cores);

        for (int i = 0; i < _imageWriter.getWidth(); ++i){
            final int iFinal = i;

            Runnable task = () -> {

                /*going over every pixel and construct rays through it*/
                for (int j = 0; j < _imageWriter.getHeight(); ++j){
                    ArrayList<Ray> rays = camera.constructRaysThroughPixel(
                            _imageWriter.getNx(), _imageWriter.getNy(),
                            iFinal, j, _scene.get_screenDistance(),
                            _imageWriter.getWidth(), _imageWriter.getHeight()
                    );

                    Color color = new Color();

                    /*calculate the color of every intersection point of every ray*/
                    for (Ray ray: rays) {

                        ArrayList<GeoPoint> intersectionPoints = _scene.get_geometries().findIntersections(ray);

                        //if there is no intersections for this ray
                        if (intersectionPoints.isEmpty() == true) {
                            color = color.add(_scene.get_background());
                        }
                        else {

                            GeoPoint closestPoint = getClosestPoint(intersectionPoints);

                            if (closestPoint.point.equals(camera.get_origin())){//if the point is "on" the camera
                                continue;
                            }

                            color = color.add(new Color(calcColor(closestPoint, new Ray(camera.get_origin(), closestPoint.point.subtract(camera.get_origin())))));
                        }
                    }

                    int length = rays.size();

                    /*the color of the pixel will be the average of all rays*/
                    color = color.scale(1.0/length);

                    _imageWriter.writePixel(iFinal, j, color.getColor());
                }

            };

            pool.execute(task);
        }

        pool.shutdown();
        try {
            while (!pool.awaitTermination(1, TimeUnit.HOURS));
        } catch (Exception ignored) {}
    }
//get intersections of the ray  Scene
    public ArrayList<GeoPoint> getSceneRayIntersections(Ray ray){
        return _scene.get_geometries().findIntersections(ray);
    }

    /**
     *
     * @param Kd Material's Kd (K_D variable in formula)
     * @param normal Normal to a geometry at an intersection point (N variable in formula)
     * @param lightDirection Direction vector from light to intersection point of a geometry (L variable in Diffusive formula)
     * @param lightColor Light's color (I_L variable in formula)
     * @return
     */
    private Color calcDiffusive(double Kd, Vector3D normal, Vector3D lightDirection, Color lightColor){
        double k = Kd * normal.normalized().dotProduct(lightDirection.normalized());

        return lightColor.scale(Math.abs(k));
    }

    /**
     *
     * @param Ks Material's Ks (K_S variable in formula)
     * @param cameraDirection Camera's direction (the negative direction of V in the formula)
     * @param lightDirection Light's direction to intersection point of a geometry (D variable in specular formula)
     * @param normal Normal to a geometry at an intersection point (N variable in formula)
     * @param nShininess Shininess factor (n variable in formula)
     * @param lightColor Light's color (I_L variable in formula)
     * @return
     */
    private Color calcSpecular(double Ks, Vector3D normal, Vector3D lightDirection, Vector3D cameraDirection, int nShininess, Color lightColor){
        Vector3D V = cameraDirection.scaled(-1).normalized();
        Vector3D N = normal.normalized();
        Vector3D L = lightDirection.normalized();

        if (2 * L.dotProduct(N) == 0){
            double k = Ks *  Math.pow(V.dotProduct(L), nShininess);

            return lightColor.scale(Math.abs(k));
        }

        //Reflectance vector
        Vector3D R = L.subtract(N.scaled(2 * L.dotProduct(N)));

        if (R == null){
            return lightColor.scale(0);
        }

        R.normalize();

        double k = Ks *  Math.pow(V.dotProduct(R), nShininess);

        return lightColor.scale(Math.abs(k));//return the specular color ratio to the whole color
    }

    /*this is the calcolor which called by the renderImage,
    and it calls the recorsive calcolor*/
    private java.awt.Color calcColor(GeoPoint intersection, Ray ray) {
        return calcColor(intersection, ray, 0, 1.0).add(_scene.get_ambientLight().getIntensity()).getColor();
    }

    /*calculate the color of the intersection point of the ray*/

    /**
     *
     * @param intersection the intersection point
     * @param ray the ray which intersect the geometry
     * @param level -of the recorsive calls (in reflection and refraction)
     * @param k- the percent of "weight" of this color on the final color
     * @return the color of the intersection point
     */
    private Color calcColor(GeoPoint intersection, Ray ray, int level, double k){
        if (level == RECURSION_LEVEL || k < MIN_CALC_COLOR_K){
            return new Color(java.awt.Color.BLACK);
        }

        Color emission = intersection.geometry.get_emission();
        double Kd = intersection.geometry.get_material().get_Kd();
        double Ks = intersection.geometry.get_material().get_Ks();
        double Kr = intersection.geometry.get_material().get_Kr();
        double Kt = intersection.geometry.get_material().get_Kt();
        int shininess = intersection.geometry.get_material().get_shininess();
        Vector3D normal = intersection.geometry.get_normal(intersection.point);
        Vector3D cameraDirection = intersection.point.subtract(_scene.get_camera().get_origin());

        Color result = emission;

        /*calculate the color from every light source*/
        for (LightSource light : _scene.get_lights()){
            Vector3D lightDirection = light.getLightDirectionTo(intersection.point);

            if (lightDirection == null || cameraDirection == null){
                continue;
            }

            if (normal.dotProduct(lightDirection) * normal.dotProduct(cameraDirection) > 0){//if the light is not from behind the geometry
                double ktr = transparency(light, intersection);
                if (ktr * k > MIN_CALC_COLOR_K) {
                    Color intensity = light.getIntensity(intersection.point).scale(ktr);

                    //Diffusive & Specular
                    result = result.add(calcDiffusive(Kd, normal, lightDirection, intensity));
                    result = result.add(calcSpecular(Ks, normal, lightDirection, cameraDirection, shininess, intensity));
                }
            }
        }

        //Reflection
        if (!Util.equals(Kr, 0)) {
            Ray reflectedRay = this.constructReflectedRay(intersection, ray);

            Color reflectedColor = new Color(java.awt.Color.BLACK);
            ArrayList<GeoPoint> reflectedIntersections = this.getSceneRayIntersections(reflectedRay);

            if (!reflectedIntersections.isEmpty()){
                GeoPoint reflectedPoint = this.getClosestPoint(reflectedIntersections);

                if (intersection.geometry != reflectedPoint.geometry){
                    reflectedColor = this.calcColor(reflectedPoint, reflectedRay, level + 1, k * Kr).scale(Kr);
                }

            }

            result = result.add(reflectedColor);
        }

        //Refraction
        if (!Util.equals(Kt, 0)) {
            Ray refractedRay = this.constructRefractedRay(intersection, ray);

            Color refractedColor = new Color(java.awt.Color.BLACK);
            ArrayList<GeoPoint> refractedIntersections = this.getSceneRayIntersections(refractedRay);

            if (!refractedIntersections.isEmpty()) {
                GeoPoint refractedPoint = this.getClosestPoint(refractedIntersections);

                refractedColor = this.calcColor(refractedPoint, refractedRay, level + 1, k * Kt).scale(Kt);
            }
            else{
                refractedColor = _scene.get_background();
            }

            result = result.add(refractedColor);
        }

        return result;
    }

    /**
     *
     * @param geoPoints list of all intersection (of specific ray)
     * @return the closest point to the camera
     */
    private GeoPoint getClosestPoint(ArrayList<GeoPoint> geoPoints){

        if (geoPoints.size() == 0)
            return null;

        Point3D origin = _scene.get_camera().get_origin();
        double shortestDistance = 0;
        GeoPoint closestPoint = null;


        for(GeoPoint geoPoint : geoPoints){
            //take the first point
            if (closestPoint == null && shortestDistance == 0){
                closestPoint = geoPoint;
                shortestDistance = origin.distance(geoPoint.point);
            }
            //take the point if its distance is shorter than closestPoint
            else{
                double distance = origin.distance(geoPoint.point);

                if (distance < shortestDistance){
                    shortestDistance = distance;
                    closestPoint = geoPoint;
                }
            }
        }

        return closestPoint;
    }

    /**
     * calculate the percent of the light which arrive to the intersection point from a light source
     * (check if the light is not hidden by another geometry (or partly hidden)
     * @param lightSource
     * @param intersection
     * @return the percents of the light which arrive to the intersection point
     */
    private double transparency(LightSource lightSource, GeoPoint intersection){
        Point3D intersectionPoint = new Point3D(intersection.point);
        Vector3D lightDirection = lightSource.getLightDirectionTo(intersectionPoint).scaled(-1);
        Vector3D normal = intersection.geometry.get_normal(intersectionPoint);

        //distance the origin of the ray (to the light source)  "epsilon' from the original intersection point
        double epsilon = this.calcEpsilon(lightDirection, normal);
        Point3D shiftedPoint = intersectionPoint.add(normal.scaled(epsilon));

        Ray lightRay = new Ray(shiftedPoint, lightDirection);// construct the ray

        ArrayList<GeoPoint> intersections = this.getSceneRayIntersections(lightRay);



        if (intersection.geometry instanceof FlatGeometry)
            intersections.remove(intersection);

        double ktr = 1;
        /*check for every geometry in the way of the ray if its transparent and how much*/
        for (GeoPoint gp : intersections)
            ktr *= gp.geometry.get_material().get_Kt();

        return ktr;
    }

    /**
     *  construct Reflected Ray from point
     * @param intersectionGeoPoint the point
     * @param ray the origin ray of the light
     * @return the reflection ray
     */
    private Ray constructReflectedRay(GeoPoint intersectionGeoPoint, Ray ray) {
        Vector3D normal = new Vector3D(intersectionGeoPoint.geometry.get_normal(intersectionGeoPoint.point));
        Point3D intersection = new Point3D(intersectionGeoPoint.point);
        Vector3D Vr = ray.get_direction();

        //distance the origin of the ray  "epsilon" from the original intersection point
        double scalar = 2 * ray.get_direction().dotProduct(normal);
        double epsilon = calcEpsilon(normal, normal);

        if (scalar == 0){
           return new Ray(intersection.add(normal.scaled(epsilon)), ray.get_direction());
        }

        return new Ray(intersection.add(normal.scaled(epsilon)), ray.get_direction().subtract(normal.scaled(scalar)));
    }

    /**
     *  construct Refracted Ray from point
     * @param intersectionGeoPoint the point
     * @param ray the origin ray of the light
     * @return the refraction ray
     */
    private Ray constructRefractedRay(GeoPoint intersectionGeoPoint, Ray ray) {
        Point3D P = intersectionGeoPoint.point;
        Vector3D normal = intersectionGeoPoint.geometry.get_normal(P);
        Vector3D Vr = ray.get_direction();

        //distance the origin of the ray  "epsilon" from the original intersection point
        double epsilon = calcEpsilon(Vr, normal);
        return new Ray(P.add(normal.scaled(epsilon)), Vr);
    }

    /**
     *
     * @param direction
     * @param normal
     * @return 'epsilon' between -0.001 to 0.001
     */
    private double calcEpsilon(Vector3D direction, Vector3D normal){
        return direction.dotProduct(normal) > 0 ? 0.001 : -0.001;
    }

    /**
     * print a net over the screen
     * @param interval- num of pixels in every hole in the grid
     */
    public void printGrid(int interval){
        for (int i = 0; i < _imageWriter.getWidth(); ++i){
            for (int j = 0; j < _imageWriter.getHeight(); ++j){
                if (i % interval == 0 || j % interval == 0){
                    _imageWriter.writePixel(i, j, java.awt.Color.WHITE);
                }
            }
        }
    }

    /**
     * print a net over the screen
     * @param interval  num of pixels in every hole in the grid
     * @param color og the grid
     */
    public void printGrid(int interval, java.awt.Color color){
        for (int i = 0; i < _imageWriter.getWidth(); ++i){
            for (int j = 0; j < _imageWriter.getHeight(); ++j){
                if (i % interval == 0 || j % interval == 0){
                    _imageWriter.writePixel(i, j, color);
                }
            }
        }
    }

    /**
     * make the picture from all the pixels
     */
    public void writeToImage(){
        _imageWriter.writeToimage();
    }

    /**
     * print Axises
     */
    public void printAxises(){
        Cylinder xAxis = new Cylinder(5,new Ray( new Vector3D(1,0,0)));
        Cylinder yAxis = new Cylinder(5,new Ray( new Vector3D(0,1,0)));
        Cylinder zAxis = new Cylinder(5,new Ray( new Vector3D(0,0,1)));

        xAxis.set_emission(java.awt.Color.RED);
        yAxis.set_emission(java.awt.Color.GREEN);
        zAxis.set_emission(java.awt.Color.BLUE);
        _scene.addGeometries(xAxis, yAxis, zAxis);
    }


}
