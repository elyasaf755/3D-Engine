package geometries;

import com.sun.org.apache.xpath.internal.objects.DTMXRTreeFrag;
import primitives.*;

import java.rmi.MarshalException;
import java.util.ArrayList;

public class Torus extends RadialGeometry{
    private double _radiusTube;//the radius of the tube.
    private Ray _ray;

    //Radial Geometry's _radius is now the distance from the center of the hole to the center of the tube. i.e the Torus' radius.

    //Constructors

    public Torus(double radiusTor, double radiusTube){
        super(radiusTor);
        _radiusTube = radiusTube;
        _ray = new Ray(new Vector3D(0,0,1));
    }

    public Torus(double radiusTor, double radiusTube, Ray direction){
        super(radiusTor);
        _radiusTube = radiusTube;
        _ray = new Ray(direction);
    }

    public Torus(Torus torus){
        super(torus.get_radius());
        _radiusTube = torus.get_radiusTube();
        _ray = new Ray(torus.get_ray());
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
    }

    public void set_ray(Ray ray) {
        this._ray.set_point(ray.get_point());
        this._ray.set_direction(ray.get_direction());
    }

    public void set_torus(double radiusTor, double radiusTube, Ray ray){
        this.set_radius(radiusTor);
        this.set_radiusTube(radiusTube);
        this.set_ray(ray);
    }

    public void set_torus(Torus torus){
        this.set_radius(torus.get_radius());
        this.set_radiusTube(torus.get_radiusTube());
        this.set_ray(torus.get_ray());
    }

    public static Matrix3 transformToDefault(Torus torus, Ray ray){
        Point3D Pr = ray.get_point();
        Vector3D Vr = ray.get_direction();

        Point3D Pt = torus.get_ray().get_point();
        Vector3D Vt = torus.get_ray().get_direction();
        double radiusTor = torus.get_radius();
        double radiusTube = torus.get_radiusTube();

        Point3D PtT = new Point3D();
        Vector3D VtT = new Vector3D(0,0,1);
        Ray RtT = new Ray(PtT, VtT);

        if (Vt.equals(VtT)){
            if (Pt.equals(PtT)){
                return new Matrix3(Matrix3.IDENTITY);
            }

            Point3D PrT = Pr.subtract(Pt).getPoint();
            Vector3D VrT = Vr;

            ray.set_point(PrT);
            ray.set_direction(VrT);

            torus.set_torus(radiusTor, radiusTube, RtT);

            return new Matrix3(Matrix3.IDENTITY);
        }

        Matrix3 R = Transform.getRodriguesRotation(Vt, VtT);
        Matrix3 RInv = R.inversed();

        Point3D q = R.mult(Pt);

        Point3D PrT = R.mult(Pr).subtract(q).getPoint();
        Vector3D VrT = R.mult(Vr).normalized();

        Ray RT = new Ray(PrT, VrT);

        Torus TT = new Torus(radiusTor, radiusTube, RtT);

        ray.set_ray(RT);

        torus.set_torus(TT);

        return R;
    }

    //Methods

    @Override
    public Vector3D get_normal(Point3D point) {
        Ray Rt = this.get_ray();
        Point3D Pt = Rt.get_point();
        Vector3D Vt = Rt.get_direction();
        double radiusTor = this.get_radius();
        double radiusTube = this.get_radiusTube();

        Point3D PtT = new Point3D();
        Vector3D VtT = new Vector3D(0,0,1);
        Ray RtT = new Ray(PtT, VtT);
        Torus TT = new Torus(radiusTor, radiusTube, RtT);

        Matrix3 R = Transform.getRodriguesRotation(Vt, VtT);
        Matrix3 RInv = R.inversed();
        Point3D q = R.mult(Pt);

        Point3D O = TT.get_ray().get_point();
        Point3D P = R.mult(point).subtract(q).getPoint();
        Vector3D PT = new Vector3D(new Point3D(P.getX(), P.getY(), Coordinate.ZERO)).normalized();
        Point3D Q = PT.scale(radiusTor).getPoint();
        Vector3D N = P.subtract(Q).normalized();

        return RInv.mult(N);
    }

    public ArrayList<GeoPoint> findIntersectionsInZDirection(Ray ray){
        Point3D Pr = ray.get_point();
        Vector3D Vr = ray.get_direction();

        double xe = Vr.getPoint().getX().getCoord();
        double ye = Vr.getPoint().getY().getCoord();
        double ze = Vr.getPoint().getZ().getCoord();

        double xd = Pr.getX().getCoord();
        double yd = Pr.getY().getCoord();
        double zd = Pr.getZ().getCoord();

        double G = 4*_radius*_radius*(xe*xe + ye*ye);
        double H = 8*_radius*_radius*(xd*xe + yd*ye);
        double I = 4*_radius*_radius*(xd*xd + yd*yd);
        double J = xe*xe + ye*ye + ze*ze;
        double K = 2*(xd*xe + yd*ye + zd*ze);
        double L = xd*xd + yd*yd + zd*zd + _radius*_radius - _radiusTube*_radiusTube;

        double A = J*J;
        double B = 2*J*K;
        double C = 2*J*L + K*K - G;
        double D = 2*K*L - H;
        double E = L*L - I;

        double[] roots = Util.quarticRoots(A, B, C, D, E);

        ArrayList<GeoPoint> result = new ArrayList<>();

        for (double root : roots){
            if (Double.isNaN(root))
                continue;

            if (Util.equals(root, 0)){
                result.add(0, new GeoPoint(this, Pr));
            }
            else{
                result.add(0, new GeoPoint(this, Pr.add(Vr.scale(root))));
            }
        }

        return result;
    }

    @Override
    public ArrayList<GeoPoint> findIntersections(Ray ray){

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
            Vector3D VrT = Vr;
            Ray RT = new Ray(PrT, VrT);

            Torus torusT = new Torus(radiusTor, radiusTube, RtT);

            ArrayList<GeoPoint> intersectionsT = torusT.findIntersectionsInZDirection(RT);

            ArrayList<GeoPoint> result = new ArrayList<>();

            for(GeoPoint intersection : intersectionsT){
                Point3D point = new Point3D(intersection.point);
                result.add(new GeoPoint(this, point.add(Pt)));
            }

            return result;
        }

        Matrix3 R = Transform.getRodriguesRotation(Vt, VtT);
        Matrix3 RInv = R.inversed();

        Point3D q = R.mult(Pt);

        Point3D PrT = R.mult(Pr).subtract(q).getPoint();
        Vector3D VrT = R.mult(Vr).normalized();

        Ray RT = new Ray(PrT, VrT);

        Torus TT = new Torus(radiusTor, radiusTube, RtT);

        ArrayList<GeoPoint> intersectionsT = TT.findIntersectionsInZDirection(RT);

        ArrayList<GeoPoint> result = new ArrayList<>();

        for (GeoPoint intersection : intersectionsT){
            Point3D point = new Point3D(intersection.point);
            GeoPoint geoPoint = new GeoPoint(this, RInv.mult(point.add(q)));
            result.add(geoPoint);
        }

        return result;
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
