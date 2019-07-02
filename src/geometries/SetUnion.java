package geometries;

import primitives.*;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.ArrayList;

public class SetUnion extends GeometriesSet {
    static int counterIn = 0;
    static int counterOut = 0;
    static int total = 0;

    //Constructors

    public SetUnion(Geometry lhs, Geometry rhs){
        super(lhs, rhs);
    }

    //Methods

    @Override
    public Vector3D get_normal(Point3D point) {
        SetIntersection setIntersection = new SetIntersection(_lhs, _rhs);

        if (setIntersection.contains(point)){
            throw new IllegalArgumentException("This point is contained in the mergeWith, therefore is not on the union's surface");
        }

        if (this._lhs.surfaceContains(point)){
            return this._lhs.get_normal(point);
        }

        if (this._rhs.surfaceContains(point)){
            return this._rhs.get_normal(point);
        }

        throw new IllegalArgumentException("This point is not on the union's surface");
    }

    @Override
    public boolean contains(Point3D point) {
        if (this._lhs.contains(point) || this._rhs.contains(point)){
            return true;
        }

        return false;
    }

    @Override
    public boolean surfaceContains(Point3D point) {
        SetIntersection setIntersection = new SetIntersection(_lhs, _rhs);

        if (setIntersection.contains(point)){
            throw new IllegalArgumentException("This point is contained in the mergeWith, therefore is not on the union's surface");
        }

        if (this._lhs.surfaceContains(point)){
            return true;
        }

        if (this._rhs.surfaceContains(point)){
            return false;
        }

        throw new IllegalArgumentException("This point is not on the union's surface");
    }

    @Override
    public ArrayList<GeoPoint> findIntersections(Ray ray) {
        //TODO: TEST
        if (!intersects(ray)){
            return new ArrayList<>();
        }

        ArrayList<GeoPoint> intersections = new ArrayList<>();

        intersections.addAll(this._lhs.findIntersections(ray));
        intersections.addAll(this._rhs.findIntersections(ray));

        return intersections;
    }
}
