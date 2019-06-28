package geometries;

import primitives.*;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.ArrayList;

public class SetIntersection extends GeometriesSet {

    //Constructors

    public SetIntersection(Geometry lhs, Geometry rhs){
        super(lhs, rhs);
    }

    //Methods

    @Override
    public ArrayList<GeoPoint> findIntersections(Ray ray) {
        //TODO: TEST
        if (!intersects(ray)){
            return new ArrayList<>();
        }
        ArrayList<GeoPoint> intersections = new ArrayList<>();

        intersections.addAll(this._lhs.findIntersections(ray));
        intersections.addAll(this._rhs.findIntersections(ray));

        ArrayList<GeoPoint> result = new ArrayList<>();

        for (GeoPoint intersection : intersections){
            if (this.contains(intersection.point)){
                result.add(intersection);
            }
        }

        GeometriesSet.removeDuplicates(result);

        return result;
    }

    @Override
    public Vector3D get_normal(Point3D point) {
        if (this.contains(point)){
            if (this._lhs.surfaceContains(point)){
                return this._lhs.get_normal(point);
            }

            if (this._rhs.surfaceContains(point)){
                return this._rhs.get_normal(point);
            }
        }

        throw new IllegalArgumentException("This point is not contained in both of the geometries' surfaces");
    }

    @Override
    public boolean surfaceContains(Point3D point) {
        if (this.contains(point)){
            if (this._lhs.surfaceContains(point)){
                return true;
            }

            if (this._rhs.surfaceContains(point)){
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean contains(Point3D point) {
        if (this._lhs.contains(point) && this._rhs.contains(point)){
            return true;
        }

        return false;
    }
}
