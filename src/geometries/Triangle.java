package geometries;

import primitives.*;

import java.util.ArrayList;

public class Triangle extends Plane implements FlatGeometry{
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

    //Methods

    public Point3D getCentroid(){
        double x1 = _point1.getX().getCoord();
        double x2 = _point2.getX().getCoord();
        double x3 = _point3.getX().getCoord();

        double y1 = _point1.getY().getCoord();
        double y2 = _point2.getY().getCoord();
        double y3 = _point3.getY().getCoord();

        double z1 = _point1.getZ().getCoord();
        double z2 = _point2.getZ().getCoord();
        double z3 = _point3.getZ().getCoord();

        return new Point3D((double)(x1 + x2 + x3)/3.0, (double)(y1 + y2 + y3)/3.0, (double)(z1 + z2 + z3)/3.0);
    }

    @Override
    public ArrayList<GeoPoint> findIntersections(Ray ray) {
        ArrayList<GeoPoint> planeIntersections = super.findIntersections(ray);

        if (planeIntersections.size() == 0){
            return planeIntersections;
        }

        Vector3D v1 = _point1.subtract(ray.get_point());
        Vector3D v2 = _point2.subtract(ray.get_point());
        Vector3D v3 = _point3.subtract(ray.get_point());

        Vector3D n1 = v1.crossProduct(v2).normalized();
        Vector3D n2 = v2.crossProduct(v3).normalized();
        Vector3D n3 = v3.crossProduct(v1).normalized();

        ArrayList<GeoPoint> result = new ArrayList<>();

        Point3D p = planeIntersections.get(0).point;

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
            result.add(new GeoPoint(this, p));
        }
        else if (!Util.isNegative(scalar1) &&
                    !Util.isNegative(scalar2) &&
                    !Util.isNegative(scalar3))
        {
            result.add(new GeoPoint(this, p));
        }

        return result;
    }

    //Overrides

    @Override
    public boolean equals(Object obj) {
        //TODO: CHECK
        if (this == obj)
            return true;

        if (obj == null)
            return false;

        if (!(obj instanceof Triangle))
            return false;
        Triangle triangle = (Triangle) obj;

        return super.equals(obj) &&
                _point1.equals(triangle.get_point1()) &&
                _point2.equals(triangle.get_point2()) &&
                _point3.equals(triangle.get_point3());
    }
}
