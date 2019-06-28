package geometries;

import primitives.*;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.ArrayList;
import java.util.Arrays;

public class Tube extends Cylinder {
    protected double _height;

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

    public Tube(double radius, Ray ray, double height){
        super(radius, ray);

        _height = height;

        init();
    }

    public Tube(double radius, Ray ray, double height, Color emission){
        super(radius, ray, emission);

        _height = height;

        init();
    }

    public Tube(double radius, Ray ray, double height, Material material){
        super(radius, ray, material);

        _height = height;

        init();
    }

    public Tube(double radius, Ray ray, double height, Color emission, Material material){
        super(radius, ray, emission, material);

        _height = height;

        init();
    }

    public Tube(double radius, Point3D point1, Point3D point2){
        super(radius, new Ray(point1, point2.subtract(point1)));

        _height = point2.subtract(point1).length();

        init();
    }

    public Tube(double radius, Point3D point1, Point3D point2, Color emission){
        super(radius, new Ray(point1, point2.subtract(point1)), emission);

        _height = point2.subtract(point1).length();

        init();
    }

    public Tube(double radius, Point3D point1, Point3D point2, Material material){
        super(radius, new Ray(point1, point2.subtract(point1)), material);

        _height = point2.subtract(point1).length();

        init();
    }

    public Tube(double radius, Point3D point1, Point3D point2, Color emission, Material material){
        super(radius, new Ray(point1, point2.subtract(point1)), emission, material);

        _height = point2.subtract(point1).length();

        init();
    }

    public Tube(RadialGeometry radialGeometry, Ray ray, double height){
        super(radialGeometry, ray);

        _height = height;

        init();
    }

    public Tube(RadialGeometry radialGeometry, Ray ray, double height, Color emission){
        super(radialGeometry, ray, emission);

        _height = height;

        init();
    }

    public Tube(RadialGeometry radialGeometry, Ray ray, double height, Material material){
        super(radialGeometry, ray, material);

        _height = height;

        init();
    }

    public Tube(RadialGeometry radialGeometry, Ray ray, double height, Color emission, Material material){
        super(radialGeometry, ray, emission, material);

        _height = height;

        init();
    }

    public Tube(Cylinder cylinder, double height){
        super(cylinder);

        _height = height;

        init();
    }

    public Tube(Tube tube){
        super(tube._radius, tube._ray, tube._emission, tube._material);

        _height = tube._height;

        init();
    }

    //Getters

    public double get_height() {
        return _height;
    }

    //Setters

    public void set_height(double _height) {
        this._height = _height;

        init();
    }

    //Methods

    @Override
    public Vector3D get_normal(Point3D point3D) {
        if (point3D.equals(_ray.get_point())){
            return _ray.get_direction().scaled(-1);
        }

        double height = _ray.get_direction().dotProduct(point3D.subtract(_ray.get_point()));

        if (height == _height)
            return _ray.get_direction();

        if (height == 0)
            return _ray.get_direction().scaled(-1);

        return super.get_normal(point3D);
    }

    @Override
    public ArrayList<GeoPoint> findIntersections(Ray ray) {
        //TODO:TEST
        if(!intersects(ray)){
            return new ArrayList<>();
        }

        ArrayList<GeoPoint> cIntersections = super.findIntersections(ray);

        Point3D Pr = ray.get_point();
        Vector3D Vr = ray.get_direction();

        Point3D PtLow = this.get_ray().get_point();
        Vector3D Vt = this.get_ray().get_direction();
        double r = this.get_radius();
        double h = this.get_height();
        Point3D PtUp = PtLow.add(Vt.scaled(h));

        Plane upperCap = new Plane(PtUp, Vt);
        Plane lowerCap = new Plane(PtLow, Vt);

        ArrayList<GeoPoint> candidates = new ArrayList<>();

        for (GeoPoint geoPoint : cIntersections){
            Point3D qi = geoPoint.point;

            if (Vt.dotProduct(qi.subtract(PtLow)) > 0 &&
                Vt.dotProduct(qi.subtract(PtUp)) < 0)
            {
                candidates.add(geoPoint);
            }
        }

        ArrayList<GeoPoint> pIntersectios = new ArrayList<>();

        pIntersectios.addAll(upperCap.findIntersections(ray));
        pIntersectios.addAll(lowerCap.findIntersections(ray));

        if (pIntersectios.size() == 2){
            Point3D q3 = pIntersectios.get(0).point;
            Point3D q4 = pIntersectios.get(1).point;

            if (q3.subtract(PtLow).squared() <= Util.squared(r) &&
                q4.subtract(PtUp).squared() <= Util.squared(r)){
                candidates.addAll(pIntersectios);
            }
        }
        else if (pIntersectios.size() == 1){
            Point3D q3 = pIntersectios.get(0).point;

            if (q3.subtract(PtLow).squared() <= Util.squared(r)){
                candidates.addAll(pIntersectios);
            }
        }

        double[] t = new double[candidates.size()];

        for (int i = 0; i < candidates.size(); ++i){
            Point3D qi = candidates.get(i).point;

            double px = Pr.getX().getCoord();
            double py = Pr.getY().getCoord();
            double pz = Pr.getZ().getCoord();

            double vx = Vr.getPoint().getX().getCoord();
            double vy = Vr.getPoint().getY().getCoord();
            double vz = Vr.getPoint().getZ().getCoord();

            double qx = qi.getX().getCoord();
            double qy = qi.getY().getCoord();
            double qz = qi.getZ().getCoord();

            if (!Util.equals(vx, 0))
                t[i] = Util.udiv(Util.usubtract(qx, px), vx);
            else if (!Util.equals(vy, 0))
                t[i] = Util.udiv(Util.usubtract(qy, py), vy);
            else if (!Util.equals(vz, 0))
                t[i] = Util.udiv(Util.usubtract(qz, pz), vz);
        }

        ArrayList<GeoPoint> result = new ArrayList<>();

        Arrays.sort(t);
        int i = 0;
        for (double d : t){
            if ( i < 2){
                result.add(new GeoPoint(this, Pr.add(Vr.scaled(d))));
                ++i;
            }
            else{
                break;
            }
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
    public void scale(double scalar){
        super.scale(scalar);
        _height = Util.uscale(_height, scalar);

        init();
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

    @Override
    public boolean contains(Point3D point) {
        Vector3D direction = _ray.get_direction();
        Point3D pt1 = this._ray.get_point();
        Point3D pt2 = pt1.add(direction.scaled(_height));

        Vector3D pt1p;

        if (point.equals(pt1)){
            pt1p = new Vector3D(Vector3D.ZERO);
        }
        else{
            pt1p = point.subtract(pt1);
        }

        double dot = pt1p.dotProduct(direction);

        if (Util.equals(dot, 0) || Util.equals(dot, _height)){
            return super.contains(point);
        }

        if (dot < 0 || dot > _height){
            return false;
        }

        return super.contains(point);
    }

    @Override
    public boolean surfaceContains(Point3D point) {
        throw new NotImplementedException();
    }

    //TODO:
    @Override
    public void updateAABB() {
        Point3D Pt = _ray.get_point();
        Vector3D Vt = _ray.get_direction();

        Sphere down = new Sphere(_radius, Pt);
        Sphere up = new Sphere(_radius, Pt.add(Vt.scaled(_height)));

        AABB temp = down.mergeWith(up);
        set_min(temp.get_min());
        set_max(temp.get_max());

        _min = temp.get_min();
        _max = temp.get_max();
    }

    //Overrides

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;

        if (obj == null)
            return false;

        if (!(obj instanceof Tube))
            return false;

        Tube tube = (Tube) obj;

        return super.equals(obj) &&
                _height == tube.get_height();
    }


}
