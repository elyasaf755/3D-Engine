package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector3D;

import java.util.ArrayList;

public class Plane implements Intersectable{
    protected Point3D _point;
    protected Vector3D _normal;

    //Constructors
    public Plane(Point3D point3D, Vector3D normal){
        _point = new Point3D(point3D);
        _normal = (new Vector3D(normal)).normalized();
    }

    public Plane(Point3D p1, Point3D p2, Point3D p3){
        Vector3D v1 = new Vector3D(p2.subtract(p1));
        Vector3D v2 = new Vector3D(p3.subtract(p1));

        _point = new Point3D(p1);
        _normal = (v1.crossProduct(v2)).normalized();
    }

    public Plane(Plane plane){
        _point = new Point3D(plane._point);
        _normal = (new Vector3D(plane._normal)).normalized();
    }

    //Getters
    public Point3D get_point() {
        return new Point3D(_point);
    }

    public Vector3D get_normal() {
        return new Vector3D(_normal);
    }

    //TODO: IMPLEMENT
    @Override
    public ArrayList<Point3D> findIntersections(Ray ray) {
        return null;
    }
}
