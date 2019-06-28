package geometries;

import primitives.*;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.xml.ws.soap.Addressing;
import java.util.ArrayList;

public class Torus extends RadialGeometry{
    private double _radiusTube;//the radius of the tube.
    private Ray _ray;

    //Radial Geometry's _radius is now the distance from the center of the hole to the center of the tube. i.e the Torus' radius.

    private Matrix3 _R;//Default = Z axis
    private Matrix3 _RInv;
    private Point3D _q;

    private void initTransformFields(){
        _R = new Matrix3(Transform.getRodriguesRotation(_ray.get_direction(), Vector3D.zAxis));
        _RInv = new Matrix3(_R.inversed());
        _q = new Point3D(_R.mult(_ray.get_point()));
    }

    private void init(){
        initTransformFields();

        //TODO: TEST
        updateAABB();
    }

    //Constructors

    public Torus(double radiusTor, double radiusTube){
        super(radiusTor);
        _radiusTube = radiusTube;
        _ray = new Ray(new Vector3D(0,0,1));

        init();
    }

    public Torus(double radiusTor, double radiusTube, Color emission){
        super(radiusTor, emission);
        _radiusTube = radiusTube;
        _ray = new Ray(new Vector3D(0,0,1));

        init();
    }

    public Torus(double radiusTor, double radiusTube, Material material){
        super(radiusTor, material);
        _radiusTube = radiusTube;
        _ray = new Ray(new Vector3D(0,0,1));

        init();
    }

    public Torus(double radiusTor, double radiusTube, Color emission, Material material){
        super(radiusTor, emission, material);
        _radiusTube = radiusTube;
        _ray = new Ray(new Vector3D(0,0,1));

        init();
    }

    public Torus(double radiusTor, double radiusTube, Ray direction){
        super(radiusTor);
        _radiusTube = radiusTube;
        _ray = new Ray(direction);

        init();
    }

    public Torus(double radiusTor, double radiusTube, Ray direction, Color emission){
        super(radiusTor, emission);
        _radiusTube = radiusTube;
        _ray = new Ray(direction);

        init();
    }

    public Torus(double radiusTor, double radiusTube, Ray direction, Material material){
        super(radiusTor, material);
        _radiusTube = radiusTube;
        _ray = new Ray(direction);

        init();
    }

    public Torus(double radiusTor, double radiusTube, Ray direction, Color emission, Material material){
        super(radiusTor, emission, material);
        _radiusTube = radiusTube;
        _ray = new Ray(direction);

        init();
    }

    public Torus(Torus torus){
        super(torus.get_radius(), torus.get_emission(), torus.get_material());
        _radiusTube = torus.get_radiusTube();
        _ray = new Ray(torus.get_ray());

        init();
    }

    //Getters

    public double get_radiusTube() {
        return _radiusTube;
    }

    public Ray get_ray() {
        return new Ray(_ray);
    }

    //Setters

    public void set_radiusTube(double radiusTube) {
        this._radiusTube = radiusTube;

        init();
    }

    public void set_ray(Ray ray) {
        this._ray.set_point(ray.get_point());
        this._ray.set_direction(ray.get_direction());

        init();
    }

    public void set_torus(double radiusTor, double radiusTube, Ray ray){
        this.set_radius(radiusTor);
        this.set_radiusTube(radiusTube);
        this.set_ray(ray);

        init();
    }

    public void set_torus(Torus torus){
        this.set_radius(torus.get_radius());
        this.set_radiusTube(torus.get_radiusTube());
        this.set_ray(torus.get_ray());

        init();
    }

    //Methods

    //TODO: TEST
    @Override
    public void updateAABB() {

        Sphere sphere = new Sphere(_radius + _radiusTube, _ray.get_point());
        set_min(sphere.get_min());
        set_max(sphere.get_max());
    }

    @Override
    public Vector3D get_normal(Point3D point) {
        Point3D Pt = this.get_ray().get_point();
        Vector3D Vt = this.get_ray().get_direction();
        double R = this._radius;
        double r = this._radiusTube;

        Point3D pointT = _R.mult(point).subtract(_q).getPoint();
        double x = pointT.getX().getCoord();
        double y = pointT.getY().getCoord();
        double z = pointT.getZ().getCoord();

        double a = 1.0 - (R / Math.sqrt(x*x + y*y));
        Vector3D NT = new Vector3D(a*x, a*y, z);

        return _RInv.mult(NT);
    }

    @Override
    public boolean contains(Point3D point) {
        throw new NotImplementedException();
    }

    @Override
    public boolean surfaceContains(Point3D point) {
        throw new NotImplementedException();
    }

    public ArrayList<GeoPoint> findIntersectionsInZDirection(Ray ray){
        double R = this._radius;
        double S = this._radiusTube;

        Point3D Pr = ray.get_point();
        Vector3D Vr = ray.get_direction();
        Vector3D Prv = new Vector3D(Pr);

        double dx = Vr.getPoint().getX().getCoord();
        double dy = Vr.getPoint().getY().getCoord();
        double dz = Vr.getPoint().getZ().getCoord();

        double ex = Pr.getX().getCoord();
        double ey = Pr.getY().getCoord();
        double ez = Pr.getZ().getCoord();

        double T = 4.0 * R * R;
        double G = T * (dx*dx + dy*dy);
        double H = 2.0 * T * (ex*dx + ey*dy);
        double I = T * (ex*ex + ey*ey);
        double J = Vr.lengthSquared();
        double K = 2.0 * Prv.dotProduct(Vr);
        double L = Prv.lengthSquared() + R*R - S*S;

        double[] roots = Util.realQuarticRoots(
                J*J,
                2.0*J*K,
                2.0*J*L + K*K - G,
                2.0*K*L - H,
                L*L - I
        );

        ArrayList<GeoPoint> result = new ArrayList<>();

        for (double root : roots)
        {
            result.add(new GeoPoint(this, Pr.add(Vr.scaled(root))));
        }

        return result;
    }

    @Override
    public ArrayList<GeoPoint> findIntersections(Ray ray){
        //TODO:TEST
        if(!intersects(ray)){
            return new ArrayList<>();
        }

        Point3D Pr = ray.get_point();
        Vector3D Vr = ray.get_direction();

        Point3D Pt = this.get_ray().get_point();
        Vector3D Vt = this.get_ray().get_direction();
        double radiusTor = this.get_radius();
        double radiusTube = this.get_radiusTube();

        Point3D PtT = new Point3D();
        Vector3D VtT = new Vector3D(0,0,1);
        Ray RtT = new Ray(PtT, VtT);

        if (Vt.equals(VtT)){
            if (Pt.equals(PtT)){
                return this.findIntersectionsInZDirection(ray);
            }

            Point3D PrT = Pr.subtract(Pt).getPoint();
            Ray RT = new Ray(PrT, Vr);

            Torus torusT = new Torus(radiusTor, radiusTube, RtT, this._emission, this._material);

            ArrayList<GeoPoint> intersectionsT = torusT.findIntersectionsInZDirection(RT);

            ArrayList<GeoPoint> result = new ArrayList<>();

            for(GeoPoint intersection : intersectionsT){
                Point3D point = new Point3D(intersection.point);
                result.add(new GeoPoint(this, point.add(Pt)));
            }

            return result;

        }

        Point3D PrT = _R.mult(Pr).subtract(_q).getPoint();
        Vector3D VrT = _R.mult(Vr).normalized();

        Ray RT = new Ray(PrT, VrT);

        Torus TT = new Torus(radiusTor, radiusTube, RtT, this._emission, this._material);

        ArrayList<GeoPoint> intersectionsT = TT.findIntersectionsInZDirection(RT);

        ArrayList<GeoPoint> result = new ArrayList<>();

        for (GeoPoint intersection : intersectionsT){
            Point3D point = new Point3D(intersection.point);
            GeoPoint geoPoint = new GeoPoint(this, _RInv.mult(point.add(_q)));
            result.add(geoPoint);
        }

        return result;
    }

    @Override
    public void translate(double x, double y, double z) {
        _ray.translate(x, y, z);

        init();
    }

    @Override
    public void rotate(double x, double y, double z) {
        _ray.rotate(x, y, z);

        init();
    }

    @Override
    public void scale(double x, double y, double z) {
        _ray.scale(x, y, z);

        init();
    }

    @Override
    public void scale(double scalar) {
        _ray.scale(scalar, scalar, scalar);

        init();
    }

    @Override
    public void transform(Transform _transform) {
        _ray.transform(_transform);

        init();
    }

    @Override
    public void transform(Vector3D translation, Vector3D rotation, Vector3D scale) {
        _ray.transform(translation, rotation, scale);

        init();
    }
}
