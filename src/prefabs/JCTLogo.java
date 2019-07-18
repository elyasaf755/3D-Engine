package prefabs;

import geometries.Geometry;
import geometries.Plane;
import geometries.SetUnion;
import geometries.Triangle;
import primitives.*;

import java.util.ArrayList;

public class JCTLogo extends Geometry {
    private Ray _orientation;

    private SetUnion _triangles;
    private Plane _surface;//TODO: DEL?

    //Triangles RGB from left to right
    //R: 27 G: 126 B: 169    Left Most triangle
    //R: 35 G: 43 B: 106
    //R: 68 G: 39 B: 105
    //R: 109 G: 43 B: 91   Right Most

    //Constructors

    public JCTLogo(){
        _orientation = new Ray(Vector3D.zAxis);

        Vector3D zAxis = new Vector3D(0,0,1);

        double radius = 50;
        double angle = Math.toRadians(60);

        Vector3D v1 = new Vector3D(0,-1,0).scaled(100);
        v1.rotate(0,0,-120);
        Vector3D v2 = Transform.rotatedVectorAround(v1, zAxis, angle);
        Vector3D v3 = Transform.rotatedVectorAround(v2, zAxis, angle);
        Vector3D v4 = Transform.rotatedVectorAround(v3, zAxis, angle);
        Vector3D v5 = Transform.rotatedVectorAround(v4, zAxis, angle);

        Triangle t1 = new Triangle(new Point3D(), v1, v2);
        Triangle t2 = new Triangle(new Point3D(), v2, v3);
        Triangle t3 = new Triangle(new Point3D(), v3, v4);
        Triangle t4 = new Triangle(new Point3D(), v4, v5);

        t1.set_emission(new Color(27,126,169));
        t4.set_emission(new Color(109,43,91));
        t3.set_emission(new Color(68,39,105));
        t2.set_emission(new Color(35,43,106));



        _triangles = new SetUnion(t1, t2);
        _triangles = new SetUnion(_triangles, t3);
        _triangles = new SetUnion(_triangles, t4);

        _surface = new Plane(_orientation.get_point(), _orientation.get_direction());

        //TODO: TEST
        updateAABB();
    }

    public JCTLogo(Ray orientation, double scale){
        _orientation = new Ray(orientation);

        _orientation = new Ray(new Vector3D(0,0,1));

        Vector3D zAxis = new Vector3D(0,0,1);

        double radius = 50;
        double angle = Math.toRadians(60);
        Vector3D v1 = new Vector3D(0,-1,0).scaled(50);
        v1.rotate(0,0,-120);
        Vector3D v2 = Transform.rotatedVectorAround(v1, zAxis, angle);

        Triangle t1 = new Triangle(new Point3D(), v1, v2);
        Triangle t2 = new Triangle(t1);
        t2.rotate(0,0,60);
        Triangle t3 = new Triangle(t2);
        t3.rotate(0,0,60);
        Triangle t4 = new Triangle(t3);
        t4.rotate(0,0,60);

        t1.set_emission(new Color(109,43,91));
        t2.set_emission(new Color(68,39,105));
        t3.set_emission(new Color(35,43,106));
        t4.set_emission(new Color(27,126,169));

        t1.scale(scale);
        t2.scale(scale);
        t3.scale(scale);
        t4.scale(scale);

        _triangles = new SetUnion(t1, t2);
        _triangles = new SetUnion(_triangles, t3);
        _triangles = new SetUnion(_triangles, t4);

        _surface = new Plane(_orientation.get_point(), _orientation.get_direction());

        Point3D Pr = orientation.get_point();
        Vector3D Vr = orientation.get_direction();

        Matrix3 R = Transform.getRodriguesRotation(Vr, zAxis);

        Point3D q = R.mult(Pr);

        //TODO: TEST
        updateAABB();

    }

    public JCTLogo(JCTLogo other){
        _orientation = new Ray(other._orientation);
        _triangles = new SetUnion(other._triangles);
        _surface = new Plane(other._surface);
    }

    //Methods

    @Override
    public Vector3D get_normal(Point3D point) {
        return _triangles.get_normal(point);
    }

    @Override
    public boolean contains(Point3D point) {
        return _triangles.contains(point);
    }

    @Override
    public boolean surfaceContains(Point3D point) {
        return _triangles.surfaceContains(point);
    }

    @Override
    public void updateAABB() {
        _triangles.updateAABB();

        _min = _triangles.get_min();
        _max = _triangles.get_max();
    }

    @Override
    public Geometry clone() {
        return new JCTLogo(this);
    }

    @Override
    public ArrayList<GeoPoint> findIntersections(Ray ray) {
        //TODO: TEST
        if (!intersects(ray)){
            return new ArrayList<>();
        }

        return _triangles.findIntersections(ray);
    }

    @Override
    public void translate(double x, double y, double z) {
        _triangles.translate(x, y, z);

        //TODO: TEST
        updateAABB();
    }

    @Override
    public void rotate(double x, double y, double z) {
        _triangles.rotate(x, y, z);

        //TODO: TEST
        updateAABB();
    }

    @Override
    public void scale(double x, double y, double z) {
        _triangles.scale(x, y, z);

        //TODO: TEST
        updateAABB();
    }

    @Override
    public void scale(double scalar) {
        _triangles.scale(scalar);

        //TODO: TEST
        updateAABB();
    }

    @Override
    public void transform(Transform _transform) {
        _triangles.transform(_transform);

        //TODO: TEST
        updateAABB();
    }

    @Override
    public void transform(Vector3D translation, Vector3D rotation, Vector3D scale) {
        _triangles.transform(translation, rotation, scale);

        //TODO: TEST
        updateAABB();
    }
}
