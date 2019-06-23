package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector3D;
import static geometries.Intersectable.GeoPoint;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class GeometriesTest {
    //x^2 + y^2 + z^2 = 16
    Sphere sphere1 = new Sphere(4, new Point3D(0,0,0));

    //(x-8)^2 + y^2 + z^2 = 16
    Sphere sphere2 = new Sphere(4, new Point3D(8,0,0));

    //(x-9)^2 + y^2 + z^2 = 16
    Sphere sphere3 = new Sphere(4, new Point3D(9,0,0));

    @Test
    void add_geometries() {
        Geometries geometries = new Geometries();
        geometries.add_geometries(sphere1, sphere2);
        assertEquals(2, geometries.get_GeometriesList().size());
        assertEquals(sphere1, geometries.get_GeometriesList().get(0));
        assertEquals(sphere2, geometries.get_GeometriesList().get(1));
    }

    @Test
    void findIntersections() {
        Ray ray = new Ray(new Point3D(-6,0,0), new Vector3D(1,0,0));

        //the 2 geometries have a point of contact
        Geometries geometries1 = new Geometries();
        geometries1.add_geometries(sphere1, sphere2);
        ArrayList<GeoPoint> expected1 = new ArrayList<>();
        expected1.add(new GeoPoint(sphere1, new Point3D(-4,0,0)));//sphere1
        expected1.add(new GeoPoint(sphere1, new Point3D(4,0,0)));//sphere1
        expected1.add(new GeoPoint(sphere2, new Point3D(4,0,0)));//sphere2
        expected1.add(new GeoPoint(sphere2, new Point3D(12,0,0)));//sphere2
        ArrayList<GeoPoint> actual1 = geometries1.findIntersections(ray);
        assertEquals(expected1, actual1);


        //the 2 geometries doesnt have a point of contact
        Geometries geometries2 = new Geometries();
        geometries2.add_geometries(sphere1, sphere3);
        ArrayList<GeoPoint> expected2 = new ArrayList<>();
        expected2.add(new GeoPoint(sphere1, new Point3D(-4,0,0)));//sphere1
        expected2.add(new GeoPoint(sphere1, new Point3D(4,0,0)));//sphere1
        expected2.add(new GeoPoint(sphere3, new Point3D(5,0,0)));//sphere3
        expected2.add(new GeoPoint(sphere3, new Point3D(13,0,0)));//sphere3
        ArrayList<GeoPoint> actual2 = geometries2.findIntersections(ray);
        assertEquals(expected2, actual2);
    }

    @Test
    void equals1() {
        Geometries geometries1 = new Geometries();
        geometries1.add_geometries(sphere1, sphere2);

        Geometries geometries2 = new Geometries();
        geometries2.add_geometries(sphere1,sphere2,sphere3);

        Geometries geometries3 = new Geometries();
        geometries3.add_geometries(
                new Sphere(4, new Point3D(0,0,0)),
                new Sphere(4, new Point3D(8,0,0))
        );

        assertEquals(true,  geometries1.equals(geometries1));
        assertEquals(false, geometries1.equals(geometries2));
        assertEquals(true,  geometries1.equals(geometries3));
    }

    @Test
    void iterable(){
        Geometries geometries = new Geometries(sphere1, sphere2, sphere3);

        int i = 0;
        for (Geometry geometry : geometries){
            assertEquals(geometry, geometries.get_GeometriesList().get(i++));
        }
    }
}