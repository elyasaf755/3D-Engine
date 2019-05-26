package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Util;
import primitives.Vector3D;

import java.util.ArrayList;

public class Sphere extends RadialGeometry implements IGeometry{
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

    //Methods

    //Overrides
    @Override
    public Vector3D get_normal(Point3D point3D) {
        return (point3D.subtract(_point)).normalized();
    }

    @Override
    public ArrayList<Point3D> findIntersections(Ray ray) {
        if (ray.get_point().equals(_point))
        {
            Point3D intercection = _point.add(ray.get_direction().scale(_radius));
            ArrayList<Point3D> intercections = new ArrayList<Point3D>();
            intercections.add(intercection);
            return intercections;
        }
        Vector3D u = _point.subtract(ray.get_point());
        double tm = ray.get_direction().dotProduct(u);
        double d = Math.sqrt(Util.usubtract(u.lengthSquared(), Util.uscale(tm, tm)));
        if (d > _radius || d < 0)
            return new ArrayList<Point3D>();
        double th = Math.sqrt(Util.usubtract(Util.uscale(_radius, _radius),Util.uscale(d, d)));
        double t1 = Util.usubtract(tm, th);
        double t2 = Util.uadd(tm, th);


        ArrayList<Point3D> intersections = new ArrayList<Point3D>();



        if (t1 > 0){
            intersections.add(new Point3D(ray.get_point().add(ray.get_direction().scale(t1))));
        }
        if (t1 == t2){
            return intersections;
        }
        if (t2 > 0){
            intersections.add(new Point3D(ray.get_point().add(ray.get_direction().scale(t2))));
        }

        return intersections;
    }
}
