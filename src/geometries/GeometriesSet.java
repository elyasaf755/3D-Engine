package geometries;

import primitives.*;

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

    @Override
    public void set_emission(Color emission) {
        _lhs.set_emission(emission);
        _rhs.set_emission(emission);
    }

    @Override
    public void set_emission(java.awt.Color emission) {
        _lhs.set_emission(emission);
        _rhs.set_emission(emission);
    }

    @Override
    public void set_material(Material material) {
        _lhs.set_material(material);
        _rhs.set_material(material);
    }

    //Methods


    @Override
    public ArrayList<GeoPoint> findIntersections(Ray ray) {
        ArrayList<GeoPoint> result = _lhs.findIntersections(ray);
        result.addAll(_rhs.findIntersections(ray));

        return result;
    }

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
    public void scale(double scalar) {
        _lhs.scale(scalar);
        _rhs.scale(scalar);
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
