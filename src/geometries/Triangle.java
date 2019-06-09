package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Util;
import primitives.Vector3D;

import java.util.ArrayList;

public class Triangle extends Plane{
    protected Point3D _point1;
    protected Point3D _point2;
    protected Point3D _point3;

    //Constructors
    public Triangle(Point3D point1, Point3D point2, Point3D point3){
        super(point1, point2, point3);

        _point1 = new Point3D(point1);
        _point2 = new Point3D(point2);
        _point3 = new Point3D(point3);
    }

    public Triangle(Triangle triangle){
        super(triangle._point1, triangle._point2, triangle._point3);

        _point1 = new Point3D(triangle._point1);
        _point2 = new Point3D(triangle._point2);
        _point3 = new Point3D(triangle._point3);
    }

    //Getters
    public Point3D get_point1() {
        return new Point3D(_point1);
    }

    public Point3D get_point2() {
        return new Point3D(_point2);
    }

    public Point3D get_point3() {
        return new Point3D(_point3);
    }

    @Override
    public ArrayList<Point3D> findIntersections(Ray ray) {
        ArrayList<Point3D> planeIntersections = super.findIntersections(ray);

        if (planeIntersections.size() == 0){
            return planeIntersections;
        }

        Vector3D v1 = _point1.subtract(ray.get_point());
        Vector3D v2 = _point2.subtract(ray.get_point());
        Vector3D v3 = _point3.subtract(ray.get_point());

        Vector3D n1 = v1.crossProduct(v2).normalized();
        Vector3D n2 = v2.crossProduct(v3).normalized();
        Vector3D n3 = v3.crossProduct(v1).normalized();

        ArrayList<Point3D> result = new ArrayList<Point3D>();

        Point3D p = planeIntersections.get(0);

        double scalar1 = p.subtract(ray.get_point()).dotProduct(n1);
        double scalar2 = p.subtract(ray.get_point()).dotProduct(n2);
        double scalar3 = p.subtract(ray.get_point()).dotProduct(n3);
        if (Util.isZero(scalar1) == true || Util.isZero(scalar2) == true || Util.isZero(scalar3) == true){
            return result;
        }
        else if (Util.isNegative(scalar1) &&
                Util.isNegative(scalar2) &&
                Util.isNegative(scalar3))
        {
            result.add(p);
        }
        else if (!Util.isNegative(scalar1) &&
                    !Util.isNegative(scalar2) &&
                    !Util.isNegative(scalar3))
        {
            result.add(p);
        }

        return result;
    }

}
