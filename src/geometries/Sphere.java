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
    }

    public Sphere(RadialGeometry radialGeometry, Point3D point) {
        super(radialGeometry);
        this._point = new Point3D(point);
    }

    public Sphere(Sphere sphere){
        super(sphere._radius);
        _point = new Point3D(sphere._point);
    }

    //Getters
    public Point3D get_point() {
        return new Point3D(_point);
    }

    //Setters

    public void set_point(Point3D origin){
        _point = new Point3D(origin);
    }

    //Methods

    @Override
    public Vector3D get_normal(Point3D point3D) {
        return (point3D.subtract(_point)).normalized();
    }

    @Override
    public ArrayList<GeoPoint> findIntersections(Ray ray) {
        if (ray.get_point().equals(_point))
        {
            Point3D intercection = _point.add(ray.get_direction().scale(_radius));
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

        if (t1 > 0){
            intersections.add(new GeoPoint(this, new Point3D(ray.get_point().add(ray.get_direction().scale(t1)))));
        }
        if (Util.equals(t1, t2)){
            return intersections;
        }
        if (t2 > 0){
            intersections.add(new GeoPoint(this, new Point3D(ray.get_point().add(ray.get_direction().scale(t2)))));
        }

        return intersections;
    }

    public double getVolume(){
        double r_3 = Util.uscale(Util.uscale(_radius, _radius), _radius);
        return Util.uscale(Util.uscale((3.0/4.0), Math.PI), r_3);
    }

    public void scaleVolume(double factor){
        _radius = Math.pow(Util.alignZero(Util.uscale(factor, getVolume()) / (Util.uscale((3.0/4.0), Math.PI))), 1.0/3.0);
    }


    @Override
    public void translate(double x, double y, double z) {
        _point.translate(x,y,z);
    }

    @Override
    public void rotate(double x, double y, double z) {
        return;//Spheres are not rotated
    }

    public void scale(double factor){
        _radius = Util.uscale(_radius, factor);
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
