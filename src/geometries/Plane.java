package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Util;
import primitives.Vector3D;

import java.util.ArrayList;

public class Plane extends Geometry  implements FlatGeometry{
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

    //Methods

    @Override
    public Vector3D get_normal(Point3D point3D) {
        return get_normal();
    }

    @Override
    public ArrayList<GeoPoint> findIntersections(Ray ray) {
        double denom = _normal.dotProduct(ray.get_direction());
        //if denom approaches 0
        if (Math.abs(denom) > 1e-6){
            if (_point.equals(ray.get_point()))
                return new ArrayList<>();

            double t = (_point.subtract(ray.get_point()).dotProduct(_normal)) / denom;

            if (t >= 0){
                ArrayList<GeoPoint> result = new ArrayList<>();
                result.add(new GeoPoint(this, ray.get_point().add(ray.get_direction().scale(t))));
                return result;
            }
        }

        return new ArrayList<>();
    }

    //Overrides


    @Override
    public boolean equals(Object obj) {
        //TODO: CHECK
        if (this == obj)
            return true;

        if (obj == null)
            return false;

        if (!(obj instanceof Plane))
            return false;

        Plane plane = (Plane) obj;

        return super.equals(obj) &&
                _point.equals(plane.get_point()) &&
                _normal.equals(plane.get_normal());
    }
}
