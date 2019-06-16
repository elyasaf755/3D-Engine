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

    public static boolean isTriangle(Point3D p1, Point3D p2, Point3D p3){
        double d1 = p1.distance(p2);
        double d2 = p1.distance(p3);
        double d3 = p2.distance(p3);


        return  Util.uadd(d1, d2) > d3 &&
                Util.uadd(d1, d3) > d2 &&
                Util.uadd(d2, d3) > d1;
    }

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

        Point3D Pr = ray.get_point();

        if (planeIntersections.size() == 0){
            return planeIntersections;
        }

        Vector3D v1 = _point1.subtract(Pr);
        Vector3D v2 = _point2.subtract(Pr);
        Vector3D v3 = _point3.subtract(Pr);

        Vector3D n1 = v1.crossProduct(v2).normalized();
        Vector3D n2 = v2.crossProduct(v3).normalized();

        Vector3D n3;
        if (v3.normalized().equals(v1.normalized()))
            n3 = new Vector3D(Vector3D.ZERO);
        else
            n3 = v3.crossProduct(v1).normalized();

        ArrayList<GeoPoint> result = new ArrayList<>();

        Point3D p = planeIntersections.get(0).point;

        double scalar1;
        double scalar2;
        double scalar3;

        if (p.equals(Pr)){
            scalar1 = 0;
            scalar2 = 0;
            scalar3 = 0;
        }
        else{
            if (n1.equals(Vector3D.ZERO))
                scalar1 = 0;
            else
                scalar1 = p.subtract(Pr).dotProduct(n1);

            if (n2.equals(Vector3D.ZERO))
                scalar2 = 0;
            else
                scalar2 = p.subtract(Pr).dotProduct(n2);

            if (n3.equals(Vector3D.ZERO))
                scalar3 = 0;
            else
                scalar3 = p.subtract(Pr).dotProduct(n3);
        }

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

    @Override
    public void translate(double x, double y, double z) {
        super.translate(x, y, z);

        _point1.translate(x, y, z);
        _point2.translate(x, y, z);
        _point3.translate(x, y, z);
    }

    @Override
    public void rotate(double x, double y, double z) {
        super.rotate(x, y, z);

        _point1.rotate(x, y, z);
        _point2.rotate(x, y, z);
        _point3.rotate(x, y, z);
    }

    @Override
    public void scale(double x, double y, double z) {
        super.scale(x, y, z);

        _point1.scale(x, y, z);
        _point2.scale(x, y, z);
        _point3.scale(x, y, z);
    }

    public void scaleInPlace(double x, double y, double z){
        Point3D oldCentroid = this.getCentroid();

        this.scale(x, y, z);

        Point3D newCentroid = this.getCentroid();

        Vector3D direction = oldCentroid.subtract(newCentroid);

        this.translate(
                direction.getPoint().getX().getCoord(),
                direction.getPoint().getY().getCoord(),
                direction.getPoint().getZ().getCoord()
        );
    }

    @Override
    public void transform(Transform _transform) {
        super.transform(_transform);

        _point1.transform(_transform);
        _point2.transform(_transform);
        _point3.transform(_transform);
    }

    @Override
    public void transform(Vector3D translation, Vector3D rotation, Vector3D scale) {
        super.transform(translation, rotation, scale);

        _point1.transform(translation, rotation, scale);
        _point2.transform(translation, rotation, scale);
        _point3.transform(translation, rotation, scale);
    }

    //Overrides

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;

        if (obj == null)
            return false;

        if (!(obj instanceof Triangle))
            return false;

        Triangle triangle = (Triangle) obj;

        return   super.equals(obj) &&
                _point1.equals(triangle.get_point1()) &&
                _point2.equals(triangle.get_point2()) &&
                _point3.equals(triangle.get_point3());
    }
}
