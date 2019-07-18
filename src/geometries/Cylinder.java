package geometries;

import primitives.*;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.ArrayList;

public class Cylinder extends RadialGeometry{
    protected Ray _ray;

    private Matrix3 _R;//Default = Z axis
    private Matrix3 _RInv;
    private Point3D _q;

    private void initTransformFields(){
        _R = new Matrix3(Transform.getRodriguesRotation(_ray.get_direction(), Vector3D.zAxis));
        _RInv = new Matrix3(_R.inversed());
        _q = new Point3D(_R.mult(_ray.get_point()));
    }

    //Constructors
    public Cylinder(double radius, Ray ray) {
        super(radius);
        _ray = new Ray(ray);

        initTransformFields();
    }

    public Cylinder(double radius, Ray ray, Color emission) {
        super(radius, emission);
        _ray = new Ray(ray);

        initTransformFields();
    }

    public Cylinder(double radius, Ray ray, Material material) {
        super(radius, material);
        _ray = new Ray(ray);

        initTransformFields();
    }

    public Cylinder(double radius, Ray ray, Color emission, Material material) {
        super(radius, emission, material);
        _ray = new Ray(ray);

        initTransformFields();
    }

    public Cylinder(RadialGeometry radialGeometry, Ray ray) {
        super(radialGeometry);
        _ray = new Ray(ray);

        initTransformFields();
    }

    public Cylinder(RadialGeometry radialGeometry, Ray ray, Color emission) {
        super(radialGeometry, emission);
        _ray = new Ray(ray);

        initTransformFields();
    }

    public Cylinder(RadialGeometry radialGeometry, Ray ray, Material material) {
        super(radialGeometry, material);
        _ray = new Ray(ray);

        initTransformFields();
    }

    public Cylinder(RadialGeometry radialGeometry, Ray ray, Color emission, Material material) {
        super(radialGeometry, emission, material);
        _ray = new Ray(ray);

        initTransformFields();
    }

    public Cylinder(Cylinder cylinder){
        super(cylinder._radius, cylinder.get_emission(), cylinder.get_material());

        _ray = new Ray(cylinder._ray);

        initTransformFields();
    }

    //Getters
    public Ray get_ray() {
        return _ray;
    }

    //Methods
/*//DO NOT DELETE
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
    }*/

    @Override
    public Vector3D get_normal(Point3D point) {
        Point3D Pc = this._ray.get_point();
        Vector3D Vc = this._ray.get_direction();

        Point3D pointT = _R.mult(point).subtract(_q).getPoint();

        Point3D Q = new Point3D().add(Vector3D.zAxis.scaled(pointT.getZ().getCoord()));

        Vector3D normalT = pointT.subtract(Q);

        return _RInv.mult(normalT).normalized();
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
        double r = this._radius;

        Point3D pointT = _R.mult(point).subtract(_q).getPoint();

        double xe = pointT.getX().getCoord();
        double ye = pointT.getY().getCoord();

        return Util.equals(Util.uadd(xe*xe,ye*ye), r*r);
    }

    //TODO:
    @Override
    public void updateAABB() {
        throw new NotImplementedException();
    }

    @Override
    public Geometry clone() {
        return new Cylinder(this);
    }

    @Override
    public ArrayList<GeoPoint> findIntersections(Ray ray) {
        double r = this._radius;

        Point3D Pr = ray.get_point();
        Vector3D Vr = ray.get_direction();

        Point3D PrT = _R.mult(Pr).subtract(_q).getPoint();
        Vector3D VrT = _R.mult(Vr).normalized();

        double xe = PrT.getX().getCoord();
        double ye = PrT.getY().getCoord();

        double xd = VrT.getPoint().getX().getCoord();
        double yd = VrT.getPoint().getY().getCoord();

        double A = xd*xd + yd*yd;
        double B = 2*(xe*xd + ye*yd);
        double C = xe*xe + ye*ye - r*r;

        double delta = B*B-4*A*C;

        if (delta < 0 && !Util.equals(delta, 0)){
            return new ArrayList<>();
        }

        double[] roots = Util.quadraticRoots(A, B, C);

        ArrayList<GeoPoint> result = new ArrayList<>();

        for(double root : roots){
            if (!Util.equals(root, 0) && root > 0){
                Point3D point = Pr.add(Vr.scaled(root));
                result.add(new GeoPoint(this, point));
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
    public void scale(double scalar){
        _radius = Util.uscale(_radius, scalar);
        _ray.scale(scalar);//TODO:TEST

        initTransformFields();
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
