package geometries;

import primitives.*;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.ArrayList;

public class Cylinder extends RadialGeometry{
    protected Ray _ray;

    //Constructors
    public Cylinder(double radius, Ray ray) {
        super(radius);
        _ray = new Ray(ray);
    }

    public Cylinder(double radius, Ray ray, Color emission) {
        super(radius, emission);
        _ray = new Ray(ray);
    }

    public Cylinder(double radius, Ray ray, Material material) {
        super(radius, material);
        _ray = new Ray(ray);
    }

    public Cylinder(double radius, Ray ray, Color emission, Material material) {
        super(radius, emission, material);
        _ray = new Ray(ray);
    }

    public Cylinder(RadialGeometry radialGeometry, Ray ray) {
        super(radialGeometry);
        _ray = new Ray(ray);
    }

    public Cylinder(RadialGeometry radialGeometry, Ray ray, Color emission) {
        super(radialGeometry, emission);
        _ray = new Ray(ray);
    }

    public Cylinder(RadialGeometry radialGeometry, Ray ray, Material material) {
        super(radialGeometry, material);
        _ray = new Ray(ray);
    }

    public Cylinder(RadialGeometry radialGeometry, Ray ray, Color emission, Material material) {
        super(radialGeometry, emission, material);
        _ray = new Ray(ray);
    }

    public Cylinder(Cylinder cylinder){
        super(cylinder._radius, cylinder.get_emission(), cylinder.get_material());

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

        Vector3D v = _ray.get_direction().scaled(projection);
        Point3D p = _ray.get_point().add(v);
        Vector3D result = point3D.subtract(p).normalized();

        return result;
        //return point3D.sub(_ray.get_point().add(_ray.get_direction().scaled(_ray.get_direction().dotProduct(point3D.sub(_ray.get_point()))))).normalized();
    }

    @Override
    public boolean contains(Point3D point) {
        Point3D a = this._ray.get_point();
        Vector3D n = this._ray.get_direction();

        Vector3D pa;
        if (a.equals(point)){
            pa = new Vector3D(Vector3D.ZERO);
        }
        else{
            pa = a.subtract(point);
        }

        double dot = pa.dotProduct(n);

        double d;

        if (Util.equals(dot, 0)){
            d = pa.length();
        }
        else{
            Vector3D temp = n.scaled(dot);

            if (pa.equals(temp)){
                return true;
            }
            else{
                d = pa.subtract(temp).length();
            }

        }


        return d <= _radius;
    }

    @Override
    public boolean surfaceContains(Point3D point) {
        throw new NotImplementedException();
    }

    @Override
    public ArrayList<GeoPoint> findIntersections(Ray ray) {
        Vector3D Vc = this.get_ray().get_direction();
        Vector3D VcT = new Vector3D(0,0,1);

        if (Vc.equals(VcT)){
            return this.findIntersectionsInZDirection(ray);
        }

        Matrix3 R = Transform.getRodriguesRotation(Vc, VcT);
        Matrix3 RInv = R.inversed();

        Point3D q = R.mult(this.get_ray().get_point());

        Cylinder CT = new Cylinder(_radius, new Ray(VcT));

        Point3D Pr = ray.get_point();
        Vector3D Vr = ray.get_direction();

        Point3D PrT = R.mult(Pr).subtract(q).getPoint();
        Vector3D VrT = R.mult(Vr).normalized();
        Ray RT = new Ray(PrT, VrT);

        ArrayList<GeoPoint> intersectionsT = CT.findIntersectionsInZDirection(RT);

        ArrayList<GeoPoint> result = new ArrayList<>();
        for(GeoPoint geoPointT : intersectionsT){
            Point3D point = new Point3D(geoPointT.point);
            GeoPoint geoPoint = new GeoPoint(this, RInv.mult(point.add(q)));
            result.add(geoPoint);
        }

        return result;
    }

    private ArrayList<GeoPoint> findIntersectionsInZDirection(Ray ray){
        Point3D eye = ray.get_point();
        Vector3D direction = ray.get_direction();
        double xe = eye.getX().getCoord();
        double ye = eye.getY().getCoord();

        double xd = direction.getPoint().getX().getCoord();
        double yd = direction.getPoint().getY().getCoord();

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
                result.add(new GeoPoint(this, eye.add(direction.scaled(root))));
            }


        }

        return result;
    }

    @Override
    public void translate(double x, double y, double z) {
        _ray.translate(x, y, z);
    }

    @Override
    public void rotate(double x, double y, double z) {
        _ray.rotate(x, y, z);
    }

    @Override
    public void scale(double scalar){
        _radius = Util.uscale(_radius, scalar);
        _ray.scale(scalar);//TODO:TEST
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
