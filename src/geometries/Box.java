package geometries;

import primitives.*;

import java.util.ArrayList;

public class Box extends Geometry{
    Ray _ray;

    double _x, _y, _z;//By default: x = half width, y = half height, z = half length

    private Matrix3 _R;//Default = Y axis
    private Matrix3 _RInv;
    private Point3D _q;

    //Inits

    private void initTransformFields(){
        _R = new Matrix3(Transform.getRodriguesRotation(_ray.get_direction(), Vector3D.yAxis));
        _RInv = new Matrix3(_R.inversed());
        _q = new Point3D(_R.mult(_ray.get_point()));
    }

    private void init(){
        initTransformFields();

        //TODO: TEST
        updateAABB();
    }

    //Constructors

    public Box(){
        _x = _y = _z = 20;

        _ray = new Ray(Vector3D.yAxis);

        init();
    }

    public Box(double halfWidth, double halfHeight, double halfLength){
        _x = halfWidth;
        _y = halfHeight;
        _z = halfLength;

        _ray = new Ray(Vector3D.yAxis);

        init();
    }

    public Box(double halfWidth, double halfHeight, double halfLength, Ray ray){
        _x = halfWidth;
        _y = halfHeight;
        _z = halfLength;

        _ray = new Ray(ray);

        init();
    }

    //Getters

    @Override
    public double getWidth() {
        return _x * 2;
    }

    @Override
    public double getHeight() {
        return _y * 2;
    }

    @Override
    public double getLength() {
        return _z * 2;
    }

    //Methods

    @Override
    public Vector3D get_normal(Point3D point) {

        Point3D pointT = _R.mult(point).subtract(_q).getPoint();

        Vector3D normalT = AABBgetNormal(pointT);

        return _RInv.mult(normalT);
    }

    @Override
    public void updateAABB() {
        double max = Math.max(_x, _y);
        max = Math.max(max, _z);

        Sphere sphere = new Sphere(max, _ray.get_point());

        set_min(sphere.get_min());
        set_max(sphere.get_max());
    }

    @Override
    public boolean contains(Point3D point) {
        return false;
    }

    @Override
    public boolean surfaceContains(Point3D point) {
        return false;
    }

    @Override
    public ArrayList<Intersectable.GeoPoint> findIntersections(Ray ray) {

        if (!intersects(ray)){
            return new ArrayList<>();
        }

        Point3D Pr = ray.get_point();
        Vector3D Vr = ray.get_direction();

        Point3D PrT = _R.mult(Pr).subtract(_q).getPoint();
        Vector3D VrT = _R.mult(Vr);
        Ray RT = new Ray(PrT, VrT);

        ArrayList<Point3D> intersectionsT = AABBintersections(RT);

        ArrayList<Intersectable.GeoPoint> result = new ArrayList<>();

        for (Point3D intersection : intersectionsT){
            result.add(new Intersectable.GeoPoint(this, _RInv.mult(intersection.add(_q))));
        }

        return result;
    }

    @Override
    public void translate(double x, double y, double z) {
        _min.translate(x, y, z);
        _max.translate(x, y, z);
    }

    @Override
    public void rotate(double x, double y, double z) {
        /*_min.rotate(x, y, z);
        _max.rotate(x, y, z);*/
    }

    @Override
    public void scale(double x, double y, double z) {
        _min.scale(x, y, z);
        _max.scale(x, y, z);
    }

    @Override
    public void scale(double scalar) {
        _min.scale(scalar);
        _max.scale(scalar);
    }

    @Override
    public void transform(Transform _transform) {
        _min.transform(_transform);
        _max.transform(_transform);
    }

    @Override
    public void transform(Vector3D translation, Vector3D rotation, Vector3D scale) {
        _min.transform(translation, rotation, scale);
        _max.transform(translation, rotation, scale);
    }
}
