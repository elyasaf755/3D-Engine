package geometries;

import primitives.*;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.ArrayList;

public class Cone extends RadialGeometry{

    private Ray _ray;
    private double _height;

    private Matrix3 _R;//Default = Z axis
    private Matrix3 _RInv;
    private Point3D _q;

    private void initTransformFields(){
        _R = new Matrix3(Transform.getRodriguesRotation(_ray.get_direction(), Vector3D.zAxis));
        _RInv = new Matrix3(_R.inversed());
        _q = new Point3D(_R.mult(_ray.get_point()));
    }

    //Constructors

    public Cone(double radius){
        super(radius);
        _ray = new Ray(new Vector3D(0,0,1));
        _height = 30;

        initTransformFields();
    }

    public Cone(double radius, double height){
        super(radius);
        _ray = new Ray(new Vector3D(0,0,1));
        _height = height;

        initTransformFields();
    }

    public Cone(double radius, Ray ray){
        super(radius);
        _ray = new Ray(ray);
        _height = 30;

        initTransformFields();
    }

    public Cone(double radius, Ray ray, double height){
        super(radius);
        _ray = new Ray(ray);
        _height = height;

        initTransformFields();
    }

    public Cone(Cone other){
        super(other.get_radius());

        _ray = new Ray(other.get_ray());
        _height = other.get_height();
    }

    //Getters

    public Ray get_ray() {
        return new Ray(_ray);
    }

    public double get_height() {
        return _height;
    }

    //Setters

    public void set_ray(Ray ray) {
        this._ray = new Ray(ray);

        initTransformFields();
    }

    public void set_height(double height) {
        this._height = height;
    }

    public void setInfinite(){
        _height = Double.POSITIVE_INFINITY;
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
    public boolean contains(Point3D point) {
        throw new NotImplementedException();
    }

    @Override
    public boolean surfaceContains(Point3D point) {
        throw new NotImplementedException();
    }

    @Override
    public void updateAABB() {
        throw new NotImplementedException();
    }

    @Override
    public Geometry clone() {
        return new Cone(this);
    }

    @Override
    public ArrayList<GeoPoint> findIntersections(Ray ray) {
        Point3D Pc = this.get_ray().get_point();
        Vector3D Vc = this.get_ray().get_direction();
        double r = this.get_radius();
        double h = this.get_height();
        Point3D PcT = new Point3D();
        Vector3D VcT = new Vector3D(0,0,1);

        if (Vc.equals(VcT)){
            if (Pc.equals(PcT)){
                return findIntersectionsInZDirection(ray);
            }
            else{
                Point3D Pr = ray.get_point();
                Vector3D Vr = ray.get_direction();

                Cone CT = new Cone(r, new Ray(PcT, VcT), h);

                Point3D PrT = Pr.subtract(Pc).getPoint();
                Vector3D VrT = Vr;
                Ray RT = new Ray(PrT, VrT);

                ArrayList<GeoPoint> intersectionsT = CT.findIntersectionsInZDirection(RT);

                ArrayList<GeoPoint> result = new ArrayList<>();
                for(GeoPoint geoPointT : intersectionsT){
                    Point3D point = new Point3D(geoPointT.point);
                    GeoPoint geoPoint = new GeoPoint(this, point.add(Pc));
                    result.add(geoPoint);
                }

                return result;
            }
        }

        Cone CT = new Cone(r, new Ray(PcT, VcT), h);

        Point3D Pr = ray.get_point();
        Vector3D Vr = ray.get_direction();

        Point3D PrT = _R.mult(Pr).subtract(_q).getPoint();
        Vector3D VrT = _R.mult(Vr).normalized();
        Ray RT = new Ray(PrT, VrT);

        ArrayList<GeoPoint> intersectionsT = CT.findIntersectionsInZDirection(RT);

        ArrayList<GeoPoint> result = new ArrayList<>();
        for(GeoPoint geoPointT : intersectionsT){
            Point3D point = new Point3D(geoPointT.point);
            GeoPoint geoPoint = new GeoPoint(this, _RInv.mult(point.add(_q)));
            result.add(geoPoint);
        }

        return result;
    }

    public ArrayList<GeoPoint> findIntersectionsInZDirection(Ray ray) {
        Point3D Pr = ray.get_point();
        Vector3D Vr = ray.get_direction();
        Point3D Pc = this.get_ray().get_point();
        Vector3D Vc = this.get_ray().get_direction();
        double h = this.get_height();

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

        double zMin = Pc.getZ().getCoord();
        double zMax = Pc.getZ().add(Vc.getPoint().getZ().scale(h)).getCoord();

        for (double root : roots){
            if (Double.isNaN(root))
                continue;

            if (Util.equals(root, 0)) {
                Point3D point = new Point3D(0, 0, Pr.getZ().getCoord());
                result.add(new GeoPoint(this, point));
            }
            else if (root > 0){
                Point3D point = Pr.add(Vr.scaled(root));

                if (h != Double.POSITIVE_INFINITY){
                    double z = point.getZ().getCoord();

                    if (z < zMax && z > zMin){
                        result.add(new GeoPoint(this, point));
                    }
                }
                else{
                    result.add(new GeoPoint(this, point));
                }
            }
        }

        return result;
    }

    @Override
    public void translate(double x, double y, double z) {
        _ray.translate(x, y, z);

        initTransformFields();
    }

    @Override
    public void rotate(double x, double y, double z) {
        _ray.rotate(x, y, z);

        initTransformFields();
    }

    @Override
    public void scale(double x, double y, double z) {
        _ray.scale(x, y, z);

        initTransformFields();
    }

    @Override
    public void scale(double scalar) {
        _ray.scale(scalar);

        initTransformFields();
    }

    @Override
    public void transform(Transform _transform) {
        _ray.transform(_transform);

        initTransformFields();
    }

    @Override
    public void transform(Vector3D translation, Vector3D rotation, Vector3D scale) {
        _ray.transform(translation, rotation, scale);

        initTransformFields();
    }
}
