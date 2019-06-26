package prefabs;

import geometries.Geometry;
import geometries.Intersectable;
import geometries.Sphere;
import primitives.*;

import java.util.ArrayList;

public class Bubble extends Geometry {
    private Sphere _sphere;

    //Constructors

    public Bubble(){
        _sphere = new Sphere(5, new Point3D());

        _sphere.set_emission(new Color(32,56,240));
        _sphere.set_material(Material.GLASS);
        _sphere.get_material().set_Kt(0.4);
        _sphere.get_material().set_Kr(0.2);
    }

    public Bubble(double radius, Point3D origin){
        _sphere = new Sphere(radius, origin);

        _sphere.set_emission(new Color(32,56,240));
        _sphere.set_material(Material.GLASS);
        _sphere.get_material().set_Kt(0.4);
        _sphere.get_material().set_Kr(0.2);
    }

    public Bubble(Bubble bubble){
        _sphere = new Sphere(bubble.get_sphere());
    }

    //Getters

    public Sphere get_sphere() {
        return new Sphere(_sphere);
    }

    //Setters



    //Methods

    @Override
    public Vector3D get_normal(Point3D point) {
        return _sphere.get_normal(point);
    }

    @Override
    public boolean contains(Point3D point) {
        return _sphere.contains(point);
    }

    @Override
    public boolean surfaceContains(Point3D point) {
        return _sphere.surfaceContains(point);
    }

    @Override
    public ArrayList<GeoPoint> findIntersections(Ray ray) {
        return _sphere.findIntersections(ray);
    }

    @Override
    public void translate(double x, double y, double z) {
        _sphere.translate(x, y, z);
    }

    @Override
    public void rotate(double x, double y, double z) {
        _sphere.rotate(x, y, z);
    }

    @Override
    public void scale(double x, double y, double z) {
        _sphere.scale(x, y, z);
    }

    @Override
    public void scale(double scalar) {
        _sphere.scale(scalar);
    }

    @Override
    public void transform(Transform _transform) {
        _sphere.transform(_transform);
    }

    @Override
    public void transform(Vector3D translation, Vector3D rotation, Vector3D scale) {
        _sphere.transform(translation, rotation, scale);
    }
}
