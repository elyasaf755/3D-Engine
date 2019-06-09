package renderer;

import primitives.Point3D;
import primitives.Ray;
import scene.Scene;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

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
        //TODO: check
        for (int i = 0; i < _imageWriter.getWidth(); ++i){
            for (int j = 0; j < _imageWriter.getHeight(); ++j){
                Ray ray = _scene.get_camera().constructRayThroughPixel(
                        _imageWriter.getNx(), _imageWriter.getNy(),
                        i, j, _scene.get_screenDistance(),
                        _imageWriter.getWidth(), _imageWriter.getHeight()
                );

                ArrayList<Point3D> intersectionPoints = _scene.get_geometries().findIntersections(ray);

                if (intersectionPoints.isEmpty() == true)
                    _imageWriter.writePixel(i, j,_scene.get_background().getColor());
                else{
                    Point3D closestPoint = getClosestPoint(intersectionPoints);
                    //_imageWriter.writePixel(i, j, calcColor(closestPoint));
                    _imageWriter.writePixel(i, j, Color.BLUE);//TODO: fix
                }
            }
        }
    }

    private primitives.Color calcColor(Point3D point){
        //TODO: Implement
        return _scene.get_ambientLight().getIntensity();
    }

    private Point3D getClosestPoint(ArrayList<Point3D> points){
        //TODO: Check
        if (points.size() == 0)
            return null;

        Point3D origin = _scene.get_camera().get_origin();
        double shortestDistance = 0;
        Point3D closestPoint = null;

        for(Point3D point : points){
            if (closestPoint == null && shortestDistance == 0){
                closestPoint = point;
                shortestDistance = origin.distance(point);
            }
            else{
                double distance = origin.distance(point);
                if (distance < shortestDistance){
                    shortestDistance = distance;
                    closestPoint = point;
                }
            }
        }

        return closestPoint;
    }

    public void printGrid(int interval){
        //TODO: Check
        for (int i = 0; i < _imageWriter.getWidth(); ++i){
            for (int j = 0; j < _imageWriter.getHeight(); ++j){
                if (i % interval == 0 || j % interval == 0){
                    _imageWriter.writePixel(i, j, java.awt.Color.WHITE);
                }
            }
        }
    }

    public void printGrid(int interval, java.awt.Color color){
        //TODO: Check
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
