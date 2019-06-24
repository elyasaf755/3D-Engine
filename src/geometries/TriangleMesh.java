package geometries;

import primitives.*;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.ArrayList;

public class TriangleMesh extends Geometry{

    static class IndexedTriangle{
        Triangle triangle;
        int index;

        IndexedTriangle(Triangle tri, int i){
            triangle = new Triangle(tri);
            index = i;
        }

        IndexedTriangle(IndexedTriangle iTriangle){
            triangle = iTriangle.triangle;
            index = iTriangle.index;
        }
    }

    // A list of all the vertex points used to define triangles.
    // A given point may be referenced by one or more triangles.
    ArrayList<Point3D> pointList;

    // A list of all the triangles, each of which refers
    // to 3 distinct points in pointList.
    ArrayList<IndexedTriangle> triangleList;

    //Constructors

    public TriangleMesh(){
        triangleList = new ArrayList<>();
    }

    //Methods

    public void addTriangle(Triangle triangle){
        int index = triangleList.size();
        triangleList.add(new IndexedTriangle(triangle, index));
    }

    @Override
    public Vector3D get_normal(Point3D point3D) {
        return null;
    }

    @Override
    public boolean contains(Point3D point) {
        throw new NotImplementedException();
    }

    @Override
    public boolean surfaceContains(Point3D point) {
        throw new NotImplementedException();
    }

    @Override
    public ArrayList<GeoPoint> findIntersections(Ray ray) {
        ArrayList<GeoPoint> result = new ArrayList<>();

        for (IndexedTriangle iTriangle : triangleList){
            result.addAll(iTriangle.triangle.findIntersections(ray));
        }

        if (result.size() != 0){
            int x = 5;
            ++x;
        }

        return result;
    }

    @Override
    public void translate(double x, double y, double z) {

    }

    @Override
    public void rotate(double x, double y, double z) {

    }

    @Override
    public void scale(double x, double y, double z) {

    }

    @Override
    public void scale(double scalar) {

    }

    @Override
    public void transform(Transform _transform) {

    }

    @Override
    public void transform(Vector3D translation, Vector3D rotation, Vector3D scale) {

    }
}
