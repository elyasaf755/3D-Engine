package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Util;
import primitives.Vector3D;

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
        if (t1 == t2){
            return intersections;
        }
        if (t2 > 0){
            intersections.add(new GeoPoint(this, new Point3D(ray.get_point().add(ray.get_direction().scale(t2)))));
        }

        return intersections;
    }

    //Overrides

    @Override
    public boolean equals(Object obj) {
        //TODO: Check
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
