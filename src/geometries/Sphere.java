package geometries;

import primitives.*;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.ArrayList;

public class Sphere extends RadialGeometry {
    protected Point3D _point;

    //Constructors

    public Sphere(double radius, Point3D point) {
        super(radius);

        this._point = new Point3D(point);

        //TODO: TEST
        updateAABB();
    }

    public Sphere(double radius, Point3D point, Color emission) {
        super(radius, emission);

        this._point = new Point3D(point);

        //TODO: TEST
        updateAABB();
    }

    public Sphere(double radius, Point3D point, Material material) {
        super(radius, material);

        this._point = new Point3D(point);

        //TODO: TEST
        updateAABB();
    }

    public Sphere(double radius, Point3D point, Color emission, Material material) {
        super(radius, emission, material);

        this._point = new Point3D(point);

        //TODO: TEST
        updateAABB();
    }

    public Sphere(RadialGeometry radialGeometry, Point3D point) {
        super(radialGeometry);

        this._point = new Point3D(point);

        //TODO: TEST
        updateAABB();
    }

    public Sphere(RadialGeometry radialGeometry, Point3D point, Color emission) {
        super(radialGeometry, emission);

        this._point = new Point3D(point);

        //TODO: TEST
        updateAABB();
    }

    public Sphere(RadialGeometry radialGeometry, Point3D point, Material material) {
        super(radialGeometry, material);

        this._point = new Point3D(point);

        //TODO: TEST
        updateAABB();
    }

    public Sphere(RadialGeometry radialGeometry, Point3D point, Color emission, Material material) {
        super(radialGeometry, emission, material);

        this._point = new Point3D(point);

        //TODO: TEST
        updateAABB();
    }

    public Sphere(Sphere sphere){
        super(sphere.get_radius(), sphere.get_emission(), sphere.get_material());

        _point = new Point3D(sphere.get_point());

        //TODO: TEST
        updateAABB();
    }

    //Getters
    public Point3D get_point() {
        return new Point3D(_point);
    }

    //Setters

    public void set_point(Point3D origin){
        _point = new Point3D(origin);

        //TODO: TEST
        updateAABB();
    }

    //Methods

    @Override
    public Vector3D get_normal(Point3D point3D) {
        return (point3D.subtract(_point)).normalized();
    }

    @Override
    public boolean contains(Point3D point) {
        double px = _point.getX().getCoord();
        double py = _point.getY().getCoord();
        double pz = _point.getZ().getCoord();

        double x = point.getX().getCoord();
        double y = point.getY().getCoord();
        double z = point.getZ().getCoord();

        double d = ( x-px )*( x-px ) + (y-py)*(y-py) + (z-pz)*(z-pz);

        if (Util.equals(d, _radius*_radius))
            return true;

        if (d < _radius * _radius)
            return true;

        return false;
    }

    @Override
    public boolean surfaceContains(Point3D point){
        double d = point.distance(_point);

        if (Util.equals(d, _radius)){
            return true;
        }

        return false;
    }

    @Override
    public void updateAABB() {
        double r = _radius;
        set_min(_point.add(new Vector3D(-r,-r,-r)));
        set_max(_point.add(new Vector3D(r,r,r)));

    }

    @Override
    public Geometry clone() {
        return new Sphere(this);
    }

    //Course approach
    @Override
    public ArrayList<GeoPoint> findIntersections(Ray ray) {
        //TODO:TEST
        if(!intersects(ray)){
            return new ArrayList<>();
        }
        if (ray.get_point().equals(_point))
        {
            Point3D intercection = _point.add(ray.get_direction().scaled(_radius));
            ArrayList<GeoPoint> intercections = new ArrayList<>();
            intercections.add(new GeoPoint(this, intercection));
            return intercections;
        }

        Vector3D u = _point.subtract(ray.get_point());
        double tm = ray.get_direction().dotProduct(u);
        double d = Math.sqrt(Util.usubtract(u.lengthSquared(), Util.uscale(tm, tm)));

        if (d > _radius || d < 0)
            return new ArrayList<>();

        double th = Math.sqrt(Util.usubtract(Util.uscale(_radius, _radius),Util.uscale(d, d)));
        double t1 = Util.usubtract(tm, th);
        double t2 = Util.uadd(tm, th);

        ArrayList<GeoPoint> intersections = new ArrayList<>();

        if (Util.equals(t1, 0)){
            //AABBintersections.add(new GeoPoint(this, new Point3D(ray.get_point())));//TODO: uncomment?
        }
        else if (t1 > 0){
            intersections.add(new GeoPoint(this, new Point3D(ray.get_point().add(ray.get_direction().scaled(t1)))));
        }
        if (Util.equals(t1, t2)){
            return intersections;
        }

        if (Util.equals(t2, 0)){
            //AABBintersections.add(new GeoPoint(this, new Point3D(ray.get_point())));//TODO: uncomment?
        }
        else if (t2 > 0){
            intersections.add(new GeoPoint(this, new Point3D(ray.get_point().add(ray.get_direction().scaled(t2)))));
        }

        return intersections;
    }

    public double getVolume(){
        double r_3 = Util.uscale(Util.uscale(_radius, _radius), _radius);
        return Util.uscale(Util.uscale((3.0/4.0), Math.PI), r_3);
    }

    public void scaleVolume(double factor){
        _radius = Math.pow(Util.alignZero(Util.uscale(factor, getVolume()) / (Util.uscale((3.0/4.0), Math.PI))), 1.0/3.0);

        //TODO: TEST
        updateAABB();
    }

    @Override
    public void translate(double x, double y, double z) {
        _point.translate(x,y,z);

        //TODO: TEST
        updateAABB();
    }

    @Override
    public void rotate(double x, double y, double z) {
        _point.rotate(x, y, z);

        //TODO: TEST
        updateAABB();
    }

    @Override
    public void scale(double factor){
        _radius = Util.uscale(_radius, factor);
        _point.scale(factor);

        //TODO: TEST
        updateAABB();
    }

    @Override
    public void scale(double x, double y, double z) {
        throw new NotImplementedException();
    }

    @Override
    public void transform(Transform _transform) {
        throw new NotImplementedException();
        //TODO: Not Implemented
    }

    @Override
    public void transform(Vector3D translation, Vector3D rotation, Vector3D scale) {
        throw new NotImplementedException();
        //TODO: Not Implemented
    }

    //Overrides

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;

        if (obj == null)
            return false;

        if (!(obj instanceof Sphere))
            return false;

        Sphere sphere = (Sphere) obj;

        return super.equals(obj) && get_point().equals(sphere.get_point());
    }

}
