package geometries;

import primitives.*;

import java.util.ArrayList;

public class Cone extends RadialGeometry {

    Point3D _origin;
    Vector3D _direction;
    double _height;

    //Constructors

    public Cone(double radius){
        super(radius);
        _origin = new Point3D();
        _direction = new Vector3D(0,1,0);
        _height = Double.POSITIVE_INFINITY;
    }

    public Cone(double radius, double height){
        super(radius);
        _origin = new Point3D();
        _direction = new Vector3D(0,1,0);
        _height = height;
    }

    public Cone(double radius, Point3D origin){
        super(radius);
        _origin = new Point3D();
        _direction = new Vector3D(0,1,0);
        _height = Double.POSITIVE_INFINITY;
    }

    public Cone(double radius, Point3D origin, double height){
        super(radius);
        _origin = new Point3D();
        _direction = new Vector3D(0,1,0);
        _height = height;
    }

    public Cone(double radius, Vector3D direction){
        super(radius);
        _origin = new Point3D();
        _direction = direction;
        _height = Double.POSITIVE_INFINITY;
    }

    public Cone(double radius, Vector3D direction, double height){
        super(radius);
        _origin = new Point3D();
        _direction = direction;
        _height = height;
    }

    public Cone(double radius, Point3D origin, Vector3D direction){
        super(radius);
        _origin = new Point3D();
        _direction = direction;
        _height = Double.POSITIVE_INFINITY;
    }

    public Cone(double radius, Point3D origin, Vector3D direction, double height){
        super(radius);
        _origin = new Point3D();
        _direction = direction;
        _height = height;
    }

    //Methods

    @Override
    public Vector3D get_normal(Point3D point3D) {
        double a = point3D.getX().getCoord();
        double b = point3D.getY().getCoord();
        double c = point3D.getZ().getCoord();

        return new Vector3D(-2*a, -2*b, 2*c).normalized();
    }

    @Override
    public ArrayList<GeoPoint> findIntersections(Ray ray) {
        Point3D Pr = ray.get_point();
        Vector3D Vr = ray.get_direction();
        Point3D Pc = this.get_origin();
        Vector3D Vc = this.get_direction();
        double r = this.get_radius();
        double h = this.get_height();
        Point3D PcT = new Point3D();
        Vector3D VcT = new Vector3D(0,0,1);

        if (Vc.equals(VcT)){
            return findIntersectionsInZDirection(ray);
        }

        Cone CT = new Cone(r, PcT, VcT, h);


        Matrix3 R = Transform.getRodriguesRotation(Vc, VcT);
        Matrix3 RInv = R.inversed();


        return null;
    }

    public ArrayList<GeoPoint> findIntersectionsInZDirection(Ray ray) {
        Point3D Pr = ray.get_point();
        Vector3D Vr = ray.get_direction();
        Point3D Pc = this.get_origin();
        Vector3D Vc = this.get_direction();

        double xe = Pr.getX().getCoord();
        double ye = Pr.getY().getCoord();
        double ze = Pr.getZ().getCoord();

        double xd = Vr.getPoint().getX().getCoord();
        double yd = Vr.getPoint().getY().getCoord();
        double zd = Vr.getPoint().getZ().getCoord();

        double A = xd*xd+yd*yd-zd*zd;
        double B = 2*xe*xd+2*ye*yd-2*ze*zd;
        double C = xe*xe+ye*ye-ze*ze;

        double[] roots = Util.quadraticRoots(A, B, C);

        ArrayList<GeoPoint> result = new ArrayList<>();

        for (double root : roots){
            if (Double.isNaN(root))
                continue;

            if (Util.equals(root, 0)) {
                result.add(new GeoPoint(this, Pr));
            }
            else if (root > 0){
                result.add(new GeoPoint(this, Pr.add(Vr.scale(root))));
            }
        }

        if (this.get_height() == Double.POSITIVE_INFINITY)
            return result;

        else
            return null;
    }

    //Getters

    public Point3D get_origin() {
        return _origin;
    }

    public Vector3D get_direction() {
        return _direction;
    }

    public double get_height() {
        return _height;
    }

    @Override
    public void translate(double x, double y, double z) {

    }

    @Override
    public void rotate(double x, double y, double z) {

    }

    @Override
    public void scale(double x, double y, double z) {

    }

    @Override
    public void transform(Transform _transform) {

    }

    @Override
    public void transform(Vector3D translation, Vector3D rotation, Vector3D scale) {

    }
}
