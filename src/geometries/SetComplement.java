package geometries;

import primitives.*;

import java.util.ArrayList;

public class SetComplement extends Geometry {
    Geometry _geometry;

    //Constructors

    public SetComplement(Geometry geometry){
        _geometry = geometry;
    }

    //Getters

    public Geometry get_geometry() {
        return _geometry;
    }

    //Setters

    public void set_geometry(Geometry _geometry) {
        this._geometry = _geometry;
    }

    @Override
    public ArrayList<GeoPoint> findIntersections(Ray ray) {
        return new ArrayList<>();
    }

//Methods

    @Override
    public Vector3D get_normal(Point3D point3D) {
        return _geometry.get_normal(point3D);
    }

    @Override
    public boolean contains(Point3D point) {
        return !_geometry.contains(point);
    }

    @Override
    public boolean surfaceContains(Point3D point) {
        return !_geometry.surfaceContains(point);
    }

    @Override
    public void translate(double x, double y, double z) {
        _geometry.translate(x, y, z);
    }

    @Override
    public void rotate(double x, double y, double z) {
        _geometry.rotate(x, y, z);
    }

    @Override
    public void scale(double x, double y, double z) {
        _geometry.scale(x, y, z);
    }

    @Override
    public void scale(double scalar) {
        _geometry.scale(scalar);
    }

    @Override
    public void transform(Transform _transform) {
        _geometry.transform(_transform);
    }

    @Override
    public void transform(Vector3D translation, Vector3D rotation, Vector3D scale) {
        _geometry.transform(translation, rotation, scale);
    }
}
