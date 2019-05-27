package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector3D;

import java.util.ArrayList;

public class Cylinder extends RadialGeometry implements IGeometry{
    protected Ray _ray;
    int x;

    //Constructors
    public Cylinder(double radius, Ray ray) {
        super(radius);
        _ray = new Ray(ray);
    }

    public Cylinder(RadialGeometry radialGeometry, Ray ray) {
        super(radialGeometry);
        _ray = new Ray(ray);
    }

    public Cylinder(Cylinder cylinder){
        super(cylinder._radius);

        _ray = new Ray(cylinder._ray);
    }

    //Getters
    public Ray get_ray() {
        return _ray;
    }

    //Overrides
    @Override
    public Vector3D get_normal(Point3D point3D) {
        Vector3D subPoints = point3D.subtract(_ray.get_point());
        double projection = _ray.get_direction().dotProduct(subPoints);//projection on the direction vector

        if (projection == 0){
            return point3D.subtract(_ray.get_point()).normalized();
        }

        Vector3D v = _ray.get_direction().scale(projection);
        Point3D p = _ray.get_point().add(v);
        Vector3D result = point3D.subtract(p).normalized();

        return result;
        //return point3D.subtract(_ray.get_point().add(_ray.get_direction().scale(_ray.get_direction().dotProduct(point3D.subtract(_ray.get_point()))))).normalized();
    }

    //Todo: Implement
    @Override
    public ArrayList<Point3D> findIntersections(Ray ray) {
        return null;
    }
}
/*****************TEST TEST TEST**************************/
