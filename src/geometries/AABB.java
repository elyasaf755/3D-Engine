package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Util;
import primitives.Vector3D;

import java.util.ArrayList;

public class AABB {
    protected Point3D _min;
    protected Point3D _max;

    private Point3D[] _bounds;


    double _surfaceArea;

    //Constructors

    public AABB(){
        _bounds = new Point3D[2];

        setInfi();

        _surfaceArea = Double.POSITIVE_INFINITY;
    }

    public AABB(Point3D min, Point3D max){
        _min = new Point3D(min);
        _max = new Point3D(max);

        _bounds = new Point3D[2];
        _bounds[0] = _min;
        _bounds[1] = _max;

        _surfaceArea = calculateSurfaceArea();
    }

    //Getters

    public Point3D get_min() {
        return new Point3D(_min);
    }

    public Point3D get_max() {
        return new Point3D(_max);
    }

    //Setters

    protected void set_min(Point3D min){
        _min = new Point3D(min);
        _bounds[0] = _min;


        calculateSurfaceArea();
    }

    protected void set_max(Point3D max){
        _max = new Point3D(max);
        _bounds[1] = _max;

        calculateSurfaceArea();
    }

    //Methods

    public boolean overlaps(AABB other){
        return _max.getX().getCoord() >= other.get_max().getX().getCoord() &&
                _min.getX().getCoord() <= other.get_max().getX().getCoord() &&
                _max.getY().getCoord() >= other.get_min().getY().getCoord() &&
                _min.getY().getCoord() <= other.get_max().getY().getCoord() &&
                _max.getZ().getCoord() >= other.get_min().getZ().getCoord() &&
                _min.getZ().getCoord() <= other.get_max().getZ().getCoord();
    }

    public boolean contains(AABB other) {
        return other.get_max().getX().getCoord() >= _min.getX().getCoord() &&
                other.get_max().getX().getCoord() <= _max.getX().getCoord() &&
                other.get_min().getY().getCoord() >= _min.getY().getCoord() &&
                other.get_max().getY().getCoord() <= _max.getY().getCoord() &&
                other.get_min().getZ().getCoord() >= _min.getZ().getCoord() &&
                other.get_max().getZ().getCoord() <= _max.getZ().getCoord();
    }

    public AABB mergeWith(AABB other) {
        Point3D min = new Point3D(
                Math.min(_min.getX().getCoord(), other.get_min().getX().getCoord()),
                Math.min(_min.getY().getCoord(), other.get_min().getY().getCoord()),
                Math.min(_min.getZ().getCoord(), other.get_min().getZ().getCoord())
        );

        Point3D max = new Point3D(
                Math.max(_max.getX().getCoord(), other.get_max().getX().getCoord()),
                Math.max(_max.getY().getCoord(), other.get_max().getY().getCoord()),
                Math.max(_max.getZ().getCoord(), other.get_max().getZ().getCoord())
        );

        return new AABB(min, max);
    }

    public boolean intersects(Ray ray){
        double minx = _min.getX().getCoord();
        double miny = _min.getY().getCoord();
        double minz = _min.getZ().getCoord();

        double maxx = _max.getX().getCoord();
        double maxy = _max.getY().getCoord();
        double maxz = _max.getZ().getCoord();

        double Prx = ray.get_point().getX().getCoord();
        double Pry = ray.get_point().getY().getCoord();
        double Prz = ray.get_point().getZ().getCoord();

        double Vrx = ray.get_direction().getPoint().getX().getCoord();
        double Vry = ray.get_direction().getPoint().getY().getCoord();
        double Vrz = ray.get_direction().getPoint().getZ().getCoord();

        double tmin = (minx - Prx) / Vrx;
        double tmax = (maxx - Prx) / Vrx;

        if (tmin > tmax) {
            double temp = tmin;
            tmin = tmax;
            tmax = temp;
        }

        double tymin = (miny - Pry) / Vry;
        double tymax = (maxy - Pry) / Vry;

        if (tymin > tymax) {
            double temp = tymin;
            tymin = tymax;
            tymax = temp;
        }

        if ((tmin > tymax) || (tymin > tmax))
            return false;

        if (tymin > tmin)
            tmin = tymin;

        if (tymax < tmax)
            tmax = tymax;

        double tzmin = (minz - Prz) / Vrz;
        double tzmax = (maxz - Prz) / Vrz;

        if (tzmin > tzmax) {
            double temp = tzmin;
            tzmin = tzmax;
            tzmax = temp;
        }

        if ((tmin > tzmax) || (tzmin > tmax))
            return false;

        if (tzmin > tmin)
            tmin = tzmin;

        if (tzmax < tmax)
            tmax = tzmax;

        return true;

    }

    protected ArrayList<Point3D> AABBintersections(Ray ray){
        double minx = _min.getX().getCoord();
        double miny = _min.getY().getCoord();
        double minz = _min.getZ().getCoord();

        double maxx = _max.getX().getCoord();
        double maxy = _max.getY().getCoord();
        double maxz = _max.getZ().getCoord();

        Point3D Pr = ray.get_point();
        Vector3D Vr = ray.get_direction();

        double Prx = Pr.getX().getCoord();
        double Pry = Pr.getY().getCoord();
        double Prz = Pr.getZ().getCoord();

        double Vrx = Vr.getPoint().getX().getCoord();
        double Vry = Vr.getPoint().getY().getCoord();
        double Vrz = Vr.getPoint().getZ().getCoord();

        double tmin = (minx - Prx) / Vrx;
        double tmax = (maxx - Prx) / Vrx;

        if (tmin > tmax) {
            double temp = tmin;
            tmin = tmax;
            tmax = temp;
        }

        double tymin = (miny - Pry) / Vry;
        double tymax = (maxy - Pry) / Vry;

        if (tymin > tymax) {
            double temp = tymin;
            tymin = tymax;
            tymax = temp;
        }

        if ((tmin > tymax) || (tymin > tmax))
            return new ArrayList<>();

        if (tymin > tmin)
            tmin = tymin;

        if (tymax < tmax)
            tmax = tymax;

        double tzmin = (minz - Prz) / Vrz;
        double tzmax = (maxz - Prz) / Vrz;

        if (tzmin > tzmax) {
            double temp = tzmin;
            tzmin = tzmax;
            tzmax = temp;
        }

        if ((tmin > tzmax) || (tzmin > tmax))
            return new ArrayList<>();

        if (tzmin > tmin)
            tmin = tzmin;

        if (tzmax < tmax)
            tmax = tzmax;

        ArrayList<Point3D> result = new ArrayList<>();

        if (Util.isZero(tmin)){
            result.add(Pr);
        }
        else{
            result.add(Pr.add(Vr.scaled(tmin)));
        }

        if (Util.isZero(tmax)){
            result.add(Pr);
        }
        else{
            result.add(Pr.add(Vr.scaled(tmax)));
        }


        return result;
    }

    protected Vector3D AABBgetNormal(Point3D point){
        double x = point.getX().getCoord();
        double y = point.getY().getCoord();
        double z = point.getZ().getCoord();

        double w = getAABBWidth() / 2;
        double h = getAABBWidth() / 2;
        double l = getAABBLength() / 2;

        Plane[] faces = {
                new Plane(new Point3D(w,0,0), Vector3D.xAxis),
                new Plane(new Point3D(-w,0,0), Vector3D.xAxis.scaled(-1)),
                new Plane(new Point3D(0,h,0), Vector3D.yAxis),
                new Plane(new Point3D(0,-h,0), Vector3D.yAxis.scaled(-1)),
                new Plane(new Point3D(0,0,z), Vector3D.zAxis),
                new Plane(new Point3D(0,0,-z), Vector3D.zAxis.scaled(-1))
        };

        for (Plane face : faces){
            if (face.surfaceContains(point)){
                return face.get_normal();
            }
        }

        throw new IllegalArgumentException("This point is not on the AABB's surface");
    }

    /*public boolean intersects(Ray ray){
        double minx = _min.getX().getCoord();
        double miny = _min.getY().getCoord();
        double minz = _min.getZ().getCoord();

        double maxx = _max.getX().getCoord();
        double maxy = _max.getY().getCoord();
        double maxz = _max.getZ().getCoord();

        double Prx = ray.get_point().getX().getCoord();
        double Pry = ray.get_point().getY().getCoord();
        double Prz = ray.get_point().getZ().getCoord();

        double Vrx = ray.get_direction().getPoint().getX().getCoord();
        double Vry = ray.get_direction().getPoint().getY().getCoord();
        double Vrz = ray.get_direction().getPoint().getZ().getCoord();

        double iVrx = ray.get_invDirection().getPoint().getX().getCoord();
        double iVry = ray.get_invDirection().getPoint().getY().getCoord();
        double iVrz = ray.get_invDirection().getPoint().getZ().getCoord();
        
        int[] sign = ray.getSign();
        
        double tmin, tmax, tymin, tymax, tzmin, tzmax;

        tmin = (_bounds[sign[0]].getX().getCoord() - Prx) * iVrx;
        tmax = (_bounds[1-sign[0]].getX().getCoord() - Prx) * iVrx;
        tymin = (_bounds[sign[1]].getY().getCoord() - Pry) * iVry;
        tymax = (_bounds[1-sign[1]].getY().getCoord() - Pry) * iVry;

        if ((tmin > tymax) || (tymin > tmax))
            return false;

        if (tymin > tmin)
            tmin = tymin;
        if (tymax < tmax)
            tmax = tymax;

        tzmin = (_bounds[sign[2]].getZ().getCoord() - Prz) * iVrz;
        tzmax = (_bounds[1-sign[2]].getZ().getCoord() - Prz) * iVrz;

        if ((tmin > tzmax) || (tzmin > tmax))
            return false;

        if (tzmin > tmin)
            tmin = tzmin;

        if (tzmax < tmax)
            tmax = tzmax;

        return true;
    }*/

    public double getAABBWidth() { return _max.getX().subtract(_min.getX()).getCoord(); }
    public double getAABBHeight() { return _max.getY().subtract(_min.getY()).getCoord(); }
    public double getAABBLength() { return _max.getZ().subtract(_min.getZ()).getCoord(); }

    protected double calculateSurfaceArea() {
        return 2.0f * (getAABBWidth() * getAABBHeight() + getAABBWidth()* getAABBLength() + getAABBHeight()* getAABBLength());
    }

    protected void setInfi(){
        double pInfi = Double.POSITIVE_INFINITY;
        double nInfi = Double.NEGATIVE_INFINITY;

        _min = new Point3D(nInfi,nInfi,nInfi);
        _max = new Point3D(pInfi,pInfi,pInfi);
    }

    //Overrides

    @Override
    public String toString() {
        return "Min: " + _min.toString() +" Max: " + _max.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;

        if (obj == null)
            return false;

        if (getClass() != obj.getClass())
            return false;

        AABB other = (AABB)obj;

        return _min.equals(other.get_min()) &&
                _max.equals(other.get_max());
    }
}
