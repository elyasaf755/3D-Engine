package geometries;

import primitives.*;

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
        if (!Triangle.isTriangle(p1, p2, p3))
            throw new IllegalArgumentException("These points can't form a triangle/plane!");

        Vector3D v1 = new Vector3D(p2.subtract(p1));
        Vector3D v2 = new Vector3D(p3.subtract(p1));

        _point = new Point3D(p1);
        _normal = (v1.crossProduct(v2)).normalized();
    }

    public Plane(Point3D point3D, Vector3D normal, Color emission){
        super(emission);
        _point = new Point3D(point3D);
        _normal = (new Vector3D(normal)).normalized();
    }

    public Plane(Point3D point3D, Vector3D normal, Material material){
        super(material);
        _point = new Point3D(point3D);
        _normal = (new Vector3D(normal)).normalized();
    }

    public Plane(Point3D point3D, Vector3D normal, Color emission, Material material){
        super(emission, material);
        _point = new Point3D(point3D);
        _normal = (new Vector3D(normal)).normalized();
    }

    public Plane(Point3D p1, Point3D p2, Point3D p3, Color emission){
        super(emission);
        if (!Triangle.isTriangle(p1, p2, p3))
            throw new IllegalArgumentException("These points can't form a triangle/plane!");

        Vector3D v1 = new Vector3D(p2.subtract(p1));
        Vector3D v2 = new Vector3D(p3.subtract(p1));

        _point = new Point3D(p1);
        _normal = (v1.crossProduct(v2)).normalized();
    }

    public Plane(Point3D p1, Point3D p2, Point3D p3, Material material){
        super(material);
        if (!Triangle.isTriangle(p1, p2, p3))
            throw new IllegalArgumentException("These points can't form a triangle/plane!");

        Vector3D v1 = new Vector3D(p2.subtract(p1));
        Vector3D v2 = new Vector3D(p3.subtract(p1));

        _point = new Point3D(p1);
        _normal = (v1.crossProduct(v2)).normalized();
    }

    public Plane(Point3D p1, Point3D p2, Point3D p3, Color emission, Material material){
        super(emission, material);
        if (!Triangle.isTriangle(p1, p2, p3))
            throw new IllegalArgumentException("These points can't form a triangle/plane!");

        Vector3D v1 = new Vector3D(p2.subtract(p1));
        Vector3D v2 = new Vector3D(p3.subtract(p1));

        _point = new Point3D(p1);
        _normal = (v1.crossProduct(v2)).normalized();
    }

    public Plane(Plane plane){
        super(plane.get_emission(), plane.get_material());

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

    public double distance(Plane plane){
        if (!new Ray(_normal).isParallelTo(new Ray(plane.get_normal())))
            return 0;

        double A1 = _normal.getPoint().getX().getCoord();
        double B1 = _normal.getPoint().getY().getCoord();
        double C1 = _normal.getPoint().getZ().getCoord();
        double x1 = _point.getX().getCoord();
        double y1 = _point.getY().getCoord();
        double z1 = _point.getZ().getCoord();
        double D1 = A1*x1 + B1*y1 + C1*z1;

        double A2 = plane.get_normal().getPoint().getX().getCoord();
        double B2 = plane.get_normal().getPoint().getY().getCoord();
        double C2 = plane.get_normal().getPoint().getZ().getCoord();
        double x2 = plane.get_point().getX().getCoord();
        double y2 = plane.get_point().getY().getCoord();
        double z2 = plane.get_point().getZ().getCoord();
        double D2 = A2*x2 + B2*y2 + C2*z2;

        return  (Math.abs(D2-D1)) / (Math.sqrt(Math.pow(A1,2)+Math.pow(B1,2)+Math.pow(C1,2)));
    }

    @Override
    public Vector3D get_normal(Point3D point3D) {
        return get_normal();
    }

    @Override
    public boolean contains(Point3D point) {
        double A = _normal.getPoint().getX().getCoord();
        double B = _normal.getPoint().getY().getCoord();
        double C = _normal.getPoint().getZ().getCoord();

        double x = _point.getX().getCoord();
        double y = _point.getY().getCoord();
        double z = _point.getZ().getCoord();

        double D = A*x + B*y + C*z;

        x = point.getX().getCoord();
        y = point.getY().getCoord();
        z = point.getZ().getCoord();

        return Util.equals(A*x + B*y + C*z, D);
    }

    @Override
    public boolean surfaceContains(Point3D point) {
        return this.contains(point);
    }

    @Override
    public ArrayList<GeoPoint> findIntersections(Ray ray) {
        double denom = _normal.dotProduct(ray.get_direction());
        //if denom approaches 0
        //Math.abs(denom) > 1e-6
        if (!Util.isZero(Math.abs(denom))){
            if (_point.equals(ray.get_point()))
                return new ArrayList<>();

            double t = (_point.subtract(ray.get_point()).dotProduct(_normal)) / denom;

            ArrayList<GeoPoint> result = new ArrayList<>();

            if (Util.equals(t, 0)){
                result.add(new GeoPoint(this, ray.get_point()));
            }
            else if (t > 0){
                result.add(new GeoPoint(this, ray.get_point().add(ray.get_direction().scaled(t))));
            }

            return result;
        }

        return new ArrayList<>();
    }

    @Override
    public void translate(double x, double y, double z) {
        _point.translate(x, y, z);
        _normal.translate(x, y, z);
        _normal.normalize();
    }

    @Override
    public void rotate(double x, double y, double z) {
        _point.rotate(x, y, z);
        _normal.rotate(x, y, z);
        _normal.normalize();
    }

    @Override
    public void scale(double x, double y, double z) {
        _point.scale(x,y,z);
    }

    @Override
    public void scale(double scalar) {
        _point.scale(scalar);
    }

    @Override
    public void transform(Transform _transform) {
        Transform temp = new Transform(_transform);

        _point.transform(_transform);

        temp.setTranslation(Vector3D.ZERO);//Vectors are not translated
        temp.setScale(1,1,1);//Planes are not scaled.

        _normal.transform(temp);
        _normal.normalize();
    }

    @Override
    public void transform(Vector3D translation, Vector3D rotation, Vector3D scale) {
        _point.transform(translation, rotation, scale);

        _normal.transform(Vector3D.ZERO, rotation , new Vector3D(1,1,1));//Vectors are not translated, planes are not scaled
        _normal.normalize();
    }

    //Overrides

    @Override
    public boolean equals(Object obj) {
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

    @Override
    public String toString() {
        return "Origin: " + _point.toString() + " Normal: " + _normal.toString();
    }
}
