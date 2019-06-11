package renderer;

import geometries.Geometry;
import geometries.Sphere;
import geometries.Triangle;
import primitives.Point3D;
import primitives.Ray;
import scene.Scene;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

                ArrayList<GeoPoint> intersectionPoints = _scene.get_geometries().getSceneRayIntersections(ray);

                if (intersectionPoints.isEmpty() == true)
                    _imageWriter.writePixel(i, j,_scene.get_background().getColor());
                else{
                    GeoPoint closestPoint = getClosestPoint(intersectionPoints);
                    _imageWriter.writePixel(i, j, calcColor(closestPoint));
                    //_imageWriter.writePixel(i, j, Color.BLUE);TODO: for del
                }
            }
        }
    }

    private Color calcColor(GeoPoint geoPoint){
        //TODO: Implement
        return geoPoint.geometry.get_emission().getColor();
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
