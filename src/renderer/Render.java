package renderer;

import elements.AmbientLight;
import elements.Camera;
import elements.Light;
import elements.LightSource;

import geometries.*;
import primitives.*;

import scene.Scene;
import sun.plugin2.gluegen.runtime.CPU;

import java.util.ArrayList;

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

    public void renderImage(){
        Camera camera = _scene.get_camera();

        for (int i = 0; i < _imageWriter.getWidth(); ++i){
            for (int j = 0; j < _imageWriter.getHeight(); ++j){
                ArrayList<Ray> rays = camera.constructRaysThroughPixel(
                        _imageWriter.getNx(), _imageWriter.getNy(),
                        i, j, _scene.get_screenDistance(),
                        _imageWriter.getWidth(), _imageWriter.getHeight()
                );
                Color color = new Color();
                for (Ray ray: rays) {
                    if (ray.get_direction().equals(new Vector3D(1, 0, 0))) {
                        int x = 5;
                    }

                    ArrayList<GeoPoint> intersectionPoints = _scene.get_geometries().findIntersections(ray);

                    if (intersectionPoints.isEmpty() == true)
                        _imageWriter.writePixel(i, j, _scene.get_background().getColor());
                    else {
                        GeoPoint closestPoint = getClosestPoint(intersectionPoints);

                        if (closestPoint.geometry.getClass() == Sphere.class) {
                            int x = 5;
                            ++x;
                        }

                        color.add(new Color(calcColor(closestPoint, new Ray(camera.get_origin(), closestPoint.point.subtract(camera.get_origin())))));
                    }
                }
                int length= rays.size();
                color= color.scale(1/length);

                _imageWriter.writePixel(i, j, color.getColor());

            }
        }
        /*Camera camera = _scene.get_camera();

        for (int i = 0; i < _imageWriter.getWidth(); ++i){
            for (int j = 0; j < _imageWriter.getHeight(); ++j){
                Ray ray = camera.constructRayThroughPixel(
                        _imageWriter.getNx(), _imageWriter.getNy(),
                        i, j, _scene.get_screenDistance(),
                        _imageWriter.getWidth(), _imageWriter.getHeight()
                );
                if (ray.get_direction().equals(new Vector3D(1,0,0))){
                    int x =5;
                }

                ArrayList<GeoPoint> intersectionPoints = _scene.get_geometries().findIntersections(ray);

                if (intersectionPoints.isEmpty() == true)
                    _imageWriter.writePixel(i, j,_scene.get_background().getColor());
                else{
                    GeoPoint closestPoint = getClosestPoint(intersectionPoints);

                    if (closestPoint.geometry.getClass() == Sphere.class){
                        int x = 5;
                        ++x;
                    }

                    Color color = new Color(calcColor(closestPoint, new Ray(camera.get_origin(), closestPoint.point.subtract(camera.get_origin()))));

                    _imageWriter.writePixel(i, j, color.getColor());
                }
            }
        }*/
    }

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
        Vector3D V = cameraDirection.scale(-1).normalized();
        Vector3D N = normal.normalized();
        Vector3D L = lightDirection.normalized();

        if (2 * L.dotProduct(N) == 0){
            double k = Ks *  Math.pow(V.dotProduct(L), nShininess);

            return lightColor.scale(Math.abs(k));
        }

        //Reflectance vector
        Vector3D R = L.subtract(N.scale(2 * L.dotProduct(N)));

        //TODO: If exception should be thrown at Vector3D's "subtract" function - del this;
        if (R == null){
            return lightColor.scale(0);
        }

        R.normalize();

        double k = Ks *  Math.pow(V.dotProduct(R), nShininess);

        return lightColor.scale(Math.abs(k));
    }

    private java.awt.Color calcColor(GeoPoint intersection, Ray ray) {
        return calcColor(intersection, ray, 0, 1.0).add(_scene.get_ambientLight().getIntensity()).getColor();
    }

    private Color calcColor(GeoPoint intersection, Ray ray, int level, double k){
        if (level == RECURSION_LEVEL || k < MIN_CALC_COLOR_K){
            return new Color(java.awt.Color.BLACK);
        }

        Color emission = intersection.geometry.get_emission();
        double Kd = intersection.geometry.get_material().get_Kd();
        double Ks = intersection.geometry.get_material().get_Ks();
        double Kr = intersection.geometry.get_material().get_Kr();
        double Kt = intersection.geometry.get_material().get_Kt();
        int shininess = intersection.geometry.get_material().get_nShininess();
        Vector3D normal = intersection.geometry.get_normal(intersection.point);
        Vector3D cameraDirection = intersection.point.subtract(_scene.get_camera().get_origin());

        Color result = emission;

        for (LightSource light : _scene.get_lights()){
            Vector3D lightDirection = light.getLightDirectionTo(intersection.point);

            //TODO: If exception should be thrown at PointLight's "getLightDirectionTo" or Point3D's subtract functions - del this;
            if (lightDirection == null || cameraDirection == null){
                continue;
            }

            if (normal.dotProduct(lightDirection) * normal.dotProduct(cameraDirection) > 0){
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
                reflectedColor = this.calcColor(reflectedPoint, reflectedRay, level + 1, k * Kr).scale(Kr);
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

    private GeoPoint getClosestPoint(ArrayList<GeoPoint> geoPoints){
        //TODO: implement for map
        if (geoPoints.size() == 0)
            return null;

        Point3D origin = _scene.get_camera().get_origin();
        double shortestDistance = 0;
        GeoPoint closestPoint = null;

        for(GeoPoint geoPoint : geoPoints){
            if (closestPoint == null && shortestDistance == 0){
                closestPoint = geoPoint;
                shortestDistance = origin.distance(geoPoint.point);
            }
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

    private double transparency(LightSource lightSource, GeoPoint intersection){
        Point3D intersectionPoint = new Point3D(intersection.point);
        Vector3D lightDirection = lightSource.getLightDirectionTo(intersectionPoint).scale(-1);
        Vector3D normal = intersection.geometry.get_normal(intersectionPoint);

        double epsilon = this.calcEpsilon(lightDirection, normal);
        Point3D shiftedPoint = intersectionPoint.add(normal.scale(epsilon));

        Ray lightRay = new Ray(shiftedPoint, lightDirection);

        ArrayList<GeoPoint> intersections = this.getSceneRayIntersections(lightRay);

        if (intersection.geometry instanceof FlatGeometry)
            intersections.remove(intersection);


        double ktr = 1;
        for (GeoPoint gp : intersections)
            ktr *= gp.geometry.get_material().get_Kt();
        return ktr;
    }

    private Ray constructReflectedRay(GeoPoint intersectionGeoPoint, Ray ray) {
        Vector3D normal = new Vector3D(intersectionGeoPoint.geometry.get_normal(intersectionGeoPoint.point));
        Point3D intersection = new Point3D(intersectionGeoPoint.point);
        Vector3D Vr = ray.get_direction();

        double scalar = 2 * ray.get_direction().dotProduct(normal);
        double epsilon = calcEpsilon(normal, normal);

        if (scalar == 0){
           return new Ray(intersection.add(normal.scale(epsilon)), ray.get_direction());
        }

        return new Ray(intersection.add(normal.scale(epsilon)), ray.get_direction().subtract(normal.scale(scalar)));
    }

    private Ray constructRefractedRay(GeoPoint intersectionGeoPoint, Ray ray) {
        Point3D P = intersectionGeoPoint.point;
        Vector3D normal = intersectionGeoPoint.geometry.get_normal(P);
        Vector3D Vr = ray.get_direction();

        double epsilon = calcEpsilon(Vr, normal);
        return new Ray(P.add(normal.scale(epsilon)), Vr);
    }

    private double calcEpsilon(Vector3D direction, Vector3D normal){
        return direction.dotProduct(normal) > 0 ? 0.001 : -0.001;
    }

    public void printGrid(int interval){
        for (int i = 0; i < _imageWriter.getWidth(); ++i){
            for (int j = 0; j < _imageWriter.getHeight(); ++j){
                if (i % interval == 0 || j % interval == 0){
                    _imageWriter.writePixel(i, j, java.awt.Color.WHITE);
                }
            }
        }
    }

    public void printGrid(int interval, java.awt.Color color){
        for (int i = 0; i < _imageWriter.getWidth(); ++i){
            for (int j = 0; j < _imageWriter.getHeight(); ++j){
                if (i % interval == 0 || j % interval == 0){
                    _imageWriter.writePixel(i, j, color);
                }
            }
        }
    }

    public void writeToImage(){
        _imageWriter.writeToimage();
    }

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
