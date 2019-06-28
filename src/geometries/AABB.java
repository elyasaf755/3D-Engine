package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector3D;

public class AABB {
    private Point3D _min;
    private Point3D _max;

    double _surfaceArea;

    //Constructors

    public AABB(Point3D min, Point3D max){
        _min = new Point3D(min);
        _max = new Point3D(max);

        _surfaceArea = calculateSurfaceArea();
    }

    //Getters

    public Point3D get_min() {
        return new Point3D(_min);
    }

    public Point3D get_max() {
        return new Point3D(_max);
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

    public double getWidth() { return _max.getX().subtract(_min.getX()).getCoord(); }
    public double getHeight() { return _max.getY().subtract(_min.getY()).getCoord(); }
    public double getDepth() { return _max.getZ().subtract(_min.getZ()).getCoord(); }

    private double calculateSurfaceArea() {
        return 2.0f * (getWidth() * getHeight() + getWidth()*getDepth() + getHeight()*getDepth());
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
