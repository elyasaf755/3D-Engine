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
                    _imageWriter.writePixel(i, j, calcColor(closestPoint));
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

    private java.awt.Color calcColor(GeoPoint intersection){
        Color emission = intersection.geometry.get_emission();
        Color ambient = _scene.get_ambientLight().getIntensity();
        double Kd = intersection.geometry.get_material().get_Kd();
        double Ks = intersection.geometry.get_material().get_Ks();
        int shininess = intersection.geometry.get_material().get_nShininess();
        Vector3D normal = intersection.geometry.get_normal(intersection.point);
        Vector3D cameraDirection = intersection.point.subtract(_scene.get_camera().get_origin());

        Color result = emission.add(ambient);

        for (LightSource light : _scene.get_lights()){
            if (!occluded(light, intersection)){
                Vector3D lightDirection = light.getLightDirectionTo(intersection.point);

                //TODO: If exception should be thrown at PointLight's "getLightDirectionTo" or Point3D's subtract functions - del this;
                if (lightDirection == null || cameraDirection == null){
                    continue;
                }

                //Fixing wrong illumination when the camera direction is in the opposite direction of the light. when both dot products have same sign.
                if (normal.dotProduct(lightDirection) * normal.dotProduct(cameraDirection) > 0){
                    Color intensity = light.getIntensity(intersection.point);

                    result = result.add(calcDiffusive(Kd, normal, lightDirection, intensity));
                    result = result.add(calcSpecular(Ks, normal, lightDirection, cameraDirection, shininess, intensity));
                }
            }
        }

        return result.getColor();
    }

    private GeoPoint getClosestPoint(ArrayList<GeoPoint> geoPoints){
        if (geoPoints.size() == 0)
            return null;

        Point3D origin = _scene.get_camera().get_origin();
        double shortestDistance = 0;
        GeoPoint closestPoint = null;

        for(GeoPoint geoPoint : geoPoints){
            /*
            Point3D point = geoPoint.point;
            Transform transform = new Transform();
            transform.setProjection(70, 500, 500, 1000, 100);
            Matrix pt = new Matrix(transform.getProjectedTransformation().getMatrix());
            double x = point.getX().getCoord();
            double y = point.getY().getCoord();
            double z = point.getZ().getCoord();
            double[][] ma = {
                    {x},
                    {y},
                    {z},
                    {1}
            };
            Matrix m = new Matrix(ma);
            Matrix tmp = pt.mult(m);
            double Z = tmp.get_element(3,0);
            x = m.get_element(0,0) / Z;
            y = m.get_element(1,0) / Z;
            z = m.get_element(2,0) / Z;

            point = new Point3D(x, y, z);
             */

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

    private boolean occluded(LightSource lightSource, GeoPoint intersection){
        Point3D intersectionPoint = new Point3D(intersection.point);
        Vector3D lightDirection = lightSource.getLightDirectionTo(intersectionPoint);
        lightDirection = lightDirection.scale(-1);

        Vector3D epsVector = new Vector3D(intersection.geometry.get_normal(intersectionPoint));
        epsVector = epsVector.scale(2);

        intersectionPoint = intersectionPoint.add(epsVector);

        Ray lightRay = new Ray(intersectionPoint, lightDirection);

        ArrayList<GeoPoint> intersections = getSceneRayIntersections(lightRay);

        if (intersection.geometry instanceof FlatGeometry)
            intersections.remove(intersection);

        return !intersections.isEmpty();
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
