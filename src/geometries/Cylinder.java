package geometries;

import javafx.scene.transform.MatrixType;
import primitives.*;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.nio.channels.Pipe;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.SplittableRandom;

public class Cylinder extends RadialGeometry{
    protected Ray _ray;

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

    //Methods
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

    @Override
    public ArrayList<GeoPoint> findIntersections(Ray ray) {
        Vector3D Vc = this.get_ray().get_direction();
        Vector3D VcT = new Vector3D(0,0,1);

        if (Vc.equals(VcT)){
            return this.findIntersectionsInZDirection(ray);
        }

        Matrix3 R = Transform.getRodriguesRotation(Vc, VcT);

        Point3D q = R.mult(this.get_ray().get_point());

        Cylinder CT = new Cylinder(_radius, new Ray(VcT));

        Point3D Pr = ray.get_point();
        Vector3D Vr = ray.get_direction();

        Point3D PrT = R.mult(Pr).subtract(R.mult(q)).getPoint();
        Vector3D VrT = R.mult(Vr).normalized();
        Ray RT = new Ray(PrT, VrT);

        ArrayList<GeoPoint> intersectionsT = CT.findIntersectionsInZDirection(RT);

        ArrayList<GeoPoint> result = new ArrayList<>();
        for(GeoPoint geoPointT : intersectionsT){
            Point3D point = new Point3D(geoPointT.point);
            GeoPoint geoPoint = new GeoPoint(this, R.inverse().mult(point.add(q)));
            result.add(geoPoint);
        }

        return result;
    }

    private ArrayList<GeoPoint> findIntersectionsInZDirection(Ray ray){
        Point3D eye = ray.get_point();
        Vector3D direction = ray.get_direction();
        double xe = eye.getX().getCoord();
        double ye = eye.getY().getCoord();
        double ze = eye.getZ().getCoord();

        double xd = direction.getPoint().getX().getCoord();
        double yd = direction.getPoint().getY().getCoord();
        double zd = direction.getPoint().getZ().getCoord();

        double r = this.get_radius();

        double A = xd*xd+yd*yd;
        double B = 2*xe*xd+2*ye*yd;
        double C = xe*xe+ye*ye-r*r;

        double[] roots = Util.quadraticRoots(A, B, C);

        ArrayList<GeoPoint> result = new ArrayList<>();

        for(double root : roots){
            if (Double.isNaN(root))
                continue;

            if (Util.equals(root, 0)){
                result.add(new GeoPoint(this, eye));
            }
            else if (root > 0){
                result.add(new GeoPoint(this, eye.add(direction.scale(root))));
            }


        }

        return result;
    }

    /*
    private ArrayList<GeoPoint> findIntersectionsInZDirection(Ray ray){
        Point3D Pc = this.get_ray().get_point();
        Vector3D Vc = this.get_ray().get_direction();
        double r = this.get_radius();

        Point3D Pr = ray.get_point();
        Vector3D Vr = ray.get_direction();

        double VrVc = Vr.dotProduct(Vc);
        Vector3D a;

        if (Util.equals(VrVc, 0)){
            a = new Vector3D(Vr);
        }
        else if (Vr.equals(Vc.scale(VrVc))){
            a = new Vector3D(Vector3D.ZERO);
        }
        else{
            a = Vr.subtract(Vc.scale(VrVc));
        }

        Vector3D deltaP;

        if (Pr.equals(Pc)){
            deltaP = new Vector3D(Vector3D.ZERO);
        }
        else{
            deltaP = Pr.subtract(Pc);
        }

        double deltaPVc = deltaP.dotProduct(Vc);
        Vector3D b;

        if (Util.equals(deltaPVc, 0)){
            b = new Vector3D(deltaP);
        }
        else if (deltaP.equals(Vc.scale(deltaPVc))){
            b = new Vector3D(Vector3D.ZERO);
        }
        else{
            b = deltaP.subtract(Vc.scale(deltaPVc));
        }

        double A = a.squared();
        double B = Util.uscale(a.dotProduct(b), 2);
        double C = Util.usubtract(b.squared(), Util.squared(r));

        double[] roots = Util.quadraticRoots(A, B, C);

        ArrayList<GeoPoint> result = new ArrayList<>();

        for (double root : roots){
            if (Double.isNaN(root))
                continue;

            if (Util.equals(root, 0)){
                result.add(new GeoPoint(this, Pr));
            }
            else if (root > 0){
                Point3D q = Pr.add(Vr.scale(root));
                result.add(new GeoPoint(this, q));
            }
        }

        return result;
    }
     */

    @Override
    public void translate(double x, double y, double z) {
        _ray.translate(x, y, z);
    }

    @Override
    public void rotate(double x, double y, double z) {
        _ray.rotate(x, y, z);
    }

    public void scale(double factor){
        _radius = Util.uscale(_radius, factor);
    }

    @Override
    public void scale(double x, double y, double z) {
        //TODO: Implement
        throw new NotImplementedException();
    }

    @Override
    public void transform(Transform _transform) {
        //TODO: Implement
        throw new NotImplementedException();
    }

    @Override
    public void transform(Vector3D translation, Vector3D rotation, Vector3D scale) {
        //TODO: Implement
        throw new NotImplementedException();
    }

    //Overrides

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;

        if (obj == null)
            return false;

        if (!(obj instanceof Cylinder))
            return false;

        Cylinder cylinder = (Cylinder) obj;

        return super.equals(obj) && _ray.equals(cylinder.get_ray());
    }


}
