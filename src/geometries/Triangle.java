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

    public Triangle(Point3D point1, Point3D point2, Point3D point3, Color emission){
        super(point1, point2, point3, emission);

        _point1 = new Point3D(point1);
        _point2 = new Point3D(point2);
        _point3 = new Point3D(point3);
    }

    public Triangle(Point3D point1, Point3D point2, Point3D point3, Material material){
        super(point1, point2, point3, material);

        _point1 = new Point3D(point1);
        _point2 = new Point3D(point2);
        _point3 = new Point3D(point3);
    }

    public Triangle(Point3D point1, Point3D point2, Point3D point3, Color emission, Material material){
        super(point1, point2, point3, emission, material);

        _point1 = new Point3D(point1);
        _point2 = new Point3D(point2);
        _point3 = new Point3D(point3);
    }

    public Triangle(Point3D origin, Vector3D v1, Vector3D v2){
        super(origin, origin.add(v1), origin.add(v2));

        _point1 = new Point3D(origin);
        _point2 = origin.add(v1);
        _point3 = origin.add(v2);
    }

    public Triangle(Triangle triangle){
        super(triangle._point1, triangle._point2, triangle._point3, triangle._emission, triangle._material);

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

    public void flipNormal(){
        Vector3D v1 = new Vector3D(_point2.subtract(_point1));
        Vector3D v2 = new Vector3D(_point3.subtract(_point1));

        _normal = (v2.crossProduct(v1)).normalized();
    }

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

    /*
    @Override
    public ArrayList<GeoPoint> findIntersections(Ray ray) {
        ArrayList<GeoPoint> intersections = super.findIntersections(ray);

        ArrayList<GeoPoint> result = new ArrayList<>();

        for (GeoPoint intersection : intersections){
            if (this.contains(intersection.point))
                result.add(intersection);
        }

        return result;
    }
    */

    @Override
    public ArrayList<GeoPoint> findIntersections(Ray ray) {
        ArrayList<GeoPoint> planeIntersections = super.findIntersections(ray);

        Point3D Pr = ray.get_point();

        if (planeIntersections.size() == 0){
            return planeIntersections;
        }

        Vector3D v1;
        Vector3D v2;
        Vector3D v3;

        if (_point1.equals(new Point3D())){
            v1 = new Vector3D(Pr).scaled(-1);
        }
        else{
            v1 = _point1.subtract(Pr);
        }

        if (_point2.equals(new Point3D())){
            v2 = new Vector3D(Pr).scaled(-1);
        }
        else{
            v2 = _point2.subtract(Pr);
        }

        if (_point3.equals(new Point3D())){
            v3 = new Vector3D(Pr).scaled(-1);
        }
        else{
            v3 = _point3.subtract(Pr);
        }

        v1.normalize();
        v2.normalize();
        v3.normalize();

        Vector3D n1;
        Vector3D n2;
        Vector3D n3;

        if (v1.equals(v2)){
            n1 = new Vector3D(Vector3D.ZERO);
        }
        else{
            n1 = v1.crossProduct(v2).normalized();
        }

        if (v2.equals(v3)){
            n2 = new Vector3D(Vector3D.ZERO);
        }
        else{
            n2 = v2.crossProduct(v3).normalized();
        }

        if (v3.equals(v1)){
            n3 = new Vector3D(Vector3D.ZERO);
        }
        else{
            n3 = v3.crossProduct(v1).normalized();
        }

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

    @Override
    public void scale(double scalar) {
        super.scale(scalar);

        _point1.scale(scalar);
        _point2.scale(scalar);
        _point3.scale(scalar);
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

    @Override
    public boolean contains(Point3D point) {
        if (_point1.equals(point) || _point2.equals(point) || _point3.equals(point))
            return true;

        Vector3D AB = _point2.subtract(_point1);
        Vector3D AC = _point3.subtract(_point1);
        //ABC area
        double ABC = AB.crossProduct(AC).length() / 2;

        Vector3D AP = point.subtract(_point1);

        Vector3D ABn = AB.normalized();
        Vector3D APn = AP.normalized();

        double ABP;
        if (ABn.equals(APn) || ABn.equals(APn.scaled(-1))){
            ABP = 0;
        }
        else{
            ABP = AB.crossProduct(AP).length() / 2;
        }

        Vector3D ACn = AC.normalized();
        double APC;
        if (APn.equals(ACn) || APn.equals(ACn.scaled(-1))){
            APC = 0;
        }
        else{
            APC = AP.crossProduct(AC).length() / 2;
        }

        Vector3D BP = point.subtract(_point2);
        Vector3D BC = _point3.subtract(_point2);
        Vector3D BPn = BP.normalized();
        Vector3D BCn = BC.normalized();

        double BPC;
        if (BPn.equals(BCn) || BPn.equals(BCn.scaled(-1))){
            BPC = 0;
        }
        else{
            BPC = BP.crossProduct(BC).length() / 2;
        }

        return Util.equals(ABC, ABP + APC + BPC);
    }

    @Override
    public boolean surfaceContains(Point3D point) {
        return this.contains(point);
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
