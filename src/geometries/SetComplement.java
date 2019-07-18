package geometries;

import primitives.*;

import java.util.ArrayList;

public class SetComplement extends Geometry {
    Geometry _geometry;

    //Constructors

    public SetComplement(Geometry geometry){
        _geometry = geometry.clone();

        //TODO:TEST
        updateAABB();
    }

    //Getters

    public Geometry get_geometry() {
        return _geometry;
    }

    //Setters

    public void set_geometry(Geometry _geometry) {
        this._geometry = _geometry;

        //TODO:TEST
        updateAABB();
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

    //TODO: TEST
    @Override
    public void updateAABB() {
        _geometry.updateAABB();
    }

    @Override
    public Geometry clone() {
        return new SetComplement(this);
    }

    @Override
    public void translate(double x, double y, double z) {
        _geometry.translate(x, y, z);

        //TODO:TEST
        updateAABB();
    }

    @Override
    public void rotate(double x, double y, double z) {
        _geometry.rotate(x, y, z);

        //TODO:TEST
        updateAABB();
    }

    @Override
    public void scale(double x, double y, double z) {
        _geometry.scale(x, y, z);

        //TODO:TEST
        updateAABB();
    }

    @Override
    public void scale(double scalar) {
        _geometry.scale(scalar);

        //TODO:TEST
        updateAABB();
    }

    @Override
    public void transform(Transform _transform) {
        _geometry.transform(_transform);

        //TODO:TEST
        updateAABB();
    }

    @Override
    public void transform(Vector3D translation, Vector3D rotation, Vector3D scale) {
        _geometry.transform(translation, rotation, scale);

        //TODO:TEST
        updateAABB();
    }
}
