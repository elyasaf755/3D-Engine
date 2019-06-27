package prefabs;

import geometries.*;
import primitives.*;

import java.util.ArrayList;

public class Aquarium extends Geometry {
    private Ray _orientation;

    SetUnion _aquarium;

    public Aquarium(){
        _orientation = new Ray(new Vector3D(0,1,0));

        Sphere lhsSphere = new Sphere(50, new Point3D(0,0,0), new Color(Color.GLASS), new Material(Material.GLASS));
        Sphere rhsSphere = new Sphere(50, new Point3D(0,50,0));
        SetDifference glassBowl = new SetDifference(lhsSphere, rhsSphere);

        Sphere waterSpherer = new Sphere(lhsSphere);
        Plane waterPlane = new Plane(new Point3D(0,0,0), new Vector3D(0,1,0));
        waterPlane.set_emission(Color.BLUE_WATER);
        waterPlane.set_material(new Material(0,0,0.1,0.7,0));
        SetIntersection water = new SetIntersection(waterSpherer, waterPlane);

        _aquarium = new SetUnion(glassBowl, water);

        Bubble bubble = new Bubble(3, new Point3D(0,0,-10));
        bubble.scale(0.9);
        Bubble bubble1 = new Bubble(bubble);
        bubble1.translate(10,10,10);
        Bubble bubble2 = new Bubble(bubble);
        bubble2.scale(0.8);
        bubble2.translate(10,-10,-10);
        Bubble bubble3 = new Bubble(bubble2);
        bubble3.scale(0.7);
        bubble3.translate(-10,-10,10);

        SetUnion bubbles = new SetUnion(bubble, bubble1);
        bubbles = new SetUnion(bubbles, bubble2);
        bubbles = new SetUnion(bubbles, bubble3);

        bubbles.translate(0,-10,0);

        _aquarium = new SetUnion(_aquarium, bubbles);
    }

    @Override
    public Vector3D get_normal(Point3D point) {
        return _aquarium.get_normal(point);
    }

    @Override
    public boolean contains(Point3D point) {
        return _aquarium.contains(point);
    }

    @Override
    public boolean surfaceContains(Point3D point) {
        return _aquarium.surfaceContains(point);
    }

    @Override
    public ArrayList<GeoPoint> findIntersections(Ray ray) {
        return _aquarium.findIntersections(ray);
    }

    @Override
    public void translate(double x, double y, double z) {
        _aquarium.translate(x, y, z);
    }

    @Override
    public void rotate(double x, double y, double z) {
        _aquarium.rotate(x, y, z);
    }

    @Override
    public void scale(double x, double y, double z) {
        _aquarium.scale(x, y, z);
    }

    @Override
    public void scale(double scalar) {
        _aquarium.scale(scalar);
    }

    @Override
    public void transform(Transform _transform) {
        _aquarium.transform(_transform);
    }

    @Override
    public void transform(Vector3D translation, Vector3D rotation, Vector3D scale) {
        _aquarium.transform(translation, rotation, scale);
    }
}
