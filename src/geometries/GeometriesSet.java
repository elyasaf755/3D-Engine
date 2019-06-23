package geometries;

import primitives.ITransformable;
import primitives.Point3D;
import primitives.Transform;
import primitives.Vector3D;

import java.util.ArrayList;

public abstract class GeometriesSet extends Geometry {
    protected Geometry _lhs;
    protected Geometry _rhs;

    //Constructors

    public GeometriesSet(Geometry lhs, Geometry rhs){
        _lhs = lhs;
        _rhs = rhs;
    }

    public GeometriesSet(GeometriesSet geometriesSet){
        _lhs = geometriesSet.get_lhs();
        _rhs = geometriesSet.get_rhs();
    }

    //Getters

    public Geometry get_lhs() {
        return _lhs;
    }

    public Geometry get_rhs() {
        return _rhs;
    }

    //Setters

    public void set_lhs(Geometry lhs) {
        _lhs = lhs;
    }

    public void set_rhs(Geometry rhs) {
        _rhs = rhs;
    }

    //Methods

    public static void removeDuplicates(ArrayList<GeoPoint> intersections){
        for(int i = 0; i < intersections.size(); ++i){
            for (int j = i + 1; j < intersections.size(); ++j){
                if (intersections.get(i).point.equals(intersections.get(j).point)){
                    intersections.remove(j);
                }
            }
        }
    }

    @Override
    public void translate(double x, double y, double z) {
        _lhs.translate(x, y, z);
        _rhs.translate(x, y, z);
    }

    @Override
    public void rotate(double x, double y, double z) {
        _lhs.rotate(x, y, z);
        _rhs.rotate(x, y, z);
    }

    @Override
    public void scale(double x, double y, double z) {
        _lhs.scale(x, y, z);
        _rhs.scale(x, y, z);
    }

    @Override
    public void transform(Transform _transform) {
        _lhs.transform(_transform);
        _rhs.transform(_transform);
    }

    @Override
    public void transform(Vector3D translation, Vector3D rotation, Vector3D scale) {
        _lhs.transform(translation, rotation, scale);
        _rhs.transform(translation, rotation, scale);
    }
}
