package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector3D;

import java.util.ArrayList;

public class SetDifference extends GeometriesSet {

    public SetDifference(Geometry lhs, Geometry rhs){
        super(lhs, rhs);
        //TODO: DEL BELOW?
        _lhs = lhs;
        _rhs = rhs;
    }

    @Override
    public Vector3D get_normal(Point3D point3D) {
        if (_lhs.surfaceContains(point3D) && !_rhs.surfaceContains(point3D)){
            return _lhs.get_normal(point3D);
        }

        throw new IllegalArgumentException("the point is contained in rhs geometry's surface!");
    }

    @Override
    public boolean contains(Point3D point) {
        if (_lhs.contains(point) && !_rhs.contains(point)){
            return true;
        }

        return false;
    }

    @Override
    public boolean surfaceContains(Point3D point) {
        if (_lhs.surfaceContains(point) && !_rhs.surfaceContains(point)){
            return true;
        }

        return false;
    }

    @Override
    public ArrayList<GeoPoint> findIntersections(Ray ray) {
        //TODO: TEST
        if (!intersects(ray)){
            return new ArrayList<>();
        }
        ArrayList<GeoPoint> intersections = new ArrayList<>();
        intersections.addAll(_lhs.findIntersections(ray));
        intersections.addAll(_rhs.findIntersections(ray));

        ArrayList<GeoPoint> result = new ArrayList<>();

        for (GeoPoint intersection : intersections){
            if (_lhs.contains(intersection.point) && !_rhs.contains(intersection.point)){
                result.add(intersection);
            }
        }

        return result;
    }
}
