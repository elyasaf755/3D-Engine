package renderer;

import elements.LightSource;

import geometries.FlatGeometry;
import geometries.Geometry;
import geometries.Triangle;
import primitives.*;

import scene.Scene;

import java.util.ArrayList;

import static geometries.Intersectable.GeoPoint;

public class Render {
    private Scene _scene;
    private ImageWriter _imageWriter;
    private static final int RECURSION_LEVEL=3;
    private static final double MIN_CALC_COLOR_K = 0.001;


    //Constructors

    public Render(Scene scene, ImageWriter imageWriter){
        _scene = scene;
        _imageWriter = imageWriter;
    }

    //Methods

    public void renderImage(){
        for (int i = 0; i < _imageWriter.getWidth(); ++i){
            for (int j = 0; j < _imageWriter.getHeight(); ++j){
                Ray ray = _scene.get_camera().constructRayThroughPixel(
                        _imageWriter.getNx(), _imageWriter.getNy(),
                        i, j, _scene.get_screenDistance(),
                        _imageWriter.getWidth(), _imageWriter.getHeight()
                );

                ArrayList<GeoPoint> intersectionPoints = _scene.get_geometries().findIntersections(ray);

                if (intersectionPoints.isEmpty() == true)
                    _imageWriter.writePixel(i, j,_scene.get_background().getColor());
                else{
                    GeoPoint closestPoint = getClosestPoint(intersectionPoints);
                    Color colort=new Color( calcColor(closestPoint,new Ray(_scene.get_camera().get_origin(),closestPoint.point.subtract(_scene.get_camera().get_origin()))));
                    _imageWriter.writePixel(i, j, calcColor(closestPoint,new Ray(_scene.get_camera().get_origin(),closestPoint.point.subtract(_scene.get_camera().get_origin()))));
                    //_imageWriter.writePixel(i, j, Color.BLUE);TODO: for del
                }
            }
        }
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
        if (level == RECURSION_LEVEL || k < MIN_CALC_COLOR_K||intersection==null) {
            return new Color(0, 0, 0);
        }
        Color emission = intersection.geometry.get_emission();
        double Kd = intersection.geometry.get_material().get_Kd();
        double Ks = intersection.geometry.get_material().get_Ks();
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

                //Fixing wrong illumination when the camera direction is in the opposite direction of the light. when both dot products have same sign.
               double z=normal.dotProduct(lightDirection);
               double t=normal.dotProduct(cameraDirection);
                if (normal.dotProduct(lightDirection) * normal.dotProduct(cameraDirection) > 0){
                    //TODO: Add the following code into the if statement?
                    double ktr = transparency(light, intersection);
                    if (ktr * k > MIN_CALC_COLOR_K) {


                        Color intensity = light.getIntensity(intersection.point).scale(ktr);

                        result = result.add(calcDiffusive(Kd, normal, lightDirection, intensity));
                        result = result.add(calcSpecular(Ks, normal, lightDirection, cameraDirection, shininess, intensity));
                    }
                }

        }

        double kr = intersection.geometry.get_material().get_Kr();
        if (kr!=0) {

            Ray reflectedRay = constructReflectedRay(intersection, ray);
            ArrayList<GeoPoint> reflectionPoints = _scene.get_geometries().findIntersections(reflectedRay);
            if (!reflectionPoints.isEmpty()) {
                GeoPoint closestReflectedPoint = getClosestPoint(reflectionPoints);
                Color reflectedColor = new Color(calcColor(closestReflectedPoint, reflectedRay, level + 1, k * kr));
                Color reflectedLight = new Color(reflectedColor.scale(kr));
                result = result.add(reflectedLight);
            }
        }
        double kt = intersection.geometry.get_material().get_Kt();
        if (kt!=0) {
            Ray refractedRay = constructRefractedRay(intersection, ray);
            ArrayList<GeoPoint> refractionPoints = _scene.get_geometries().findIntersections(refractedRay);
            if (!refractionPoints.isEmpty()) {
                GeoPoint closestRefractedPoint = getClosestPoint(refractionPoints);

                Color refractedColor = new Color(calcColor(closestRefractedPoint, refractedRay, level + 1, k * kt));
                Color refractedLight = new Color(refractedColor.scale(kt));
                result = result.add(refractedLight);
            }
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


        Vector3D epsVector = new Vector3D(intersection.geometry.get_normal(intersectionPoint));
        epsVector = epsVector.scale(2);

        intersectionPoint = intersectionPoint.add(epsVector);

        Ray lightRay = new Ray(intersectionPoint, lightDirection);

        ArrayList<GeoPoint> intersections = getSceneRayIntersections(lightRay);

        if (intersection.geometry instanceof FlatGeometry)
            intersections.remove(intersection);

        //todo: to understand how it is work. i just copied it
        double ktr = 1;
        for (GeoPoint gp : intersections)
            ktr *= gp.geometry.get_material().get_Kt();
        return ktr;
    }

    private Ray constructReflectedRay(GeoPoint intersection, Ray ray)
    {
        Vector3D normal = new Vector3D(intersection.geometry.get_normal(intersection.point));
        Point3D point = new Point3D(intersection.point);
        Ray originRay = new Ray(ray);

       //todo: return null? if 'yes', to check if it is null in calcColor.
        if (originRay.get_direction().dotProduct(normal) == 0){
           return null;
        }

        Vector3D R = originRay.get_direction().subtract(normal.scale(2 * originRay.get_direction().dotProduct(normal)));
        return new Ray(point,R);
    }

    private Ray constructRefractedRay(GeoPoint intersection, Ray ray)
    {
        Vector3D normal = new Vector3D(intersection.geometry.get_normal(intersection.point));
        Point3D point = new Point3D(intersection.point);
        Ray originRay = new Ray(ray);

        //todo: return null? if 'yes', to check if it is null in calcColor.
        if (originRay.get_direction().dotProduct(normal) == 0){
            return null;
        }

        //todo: implement calculating R
        //Vector3D R = ;
        return new Ray(originRay);
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


}
