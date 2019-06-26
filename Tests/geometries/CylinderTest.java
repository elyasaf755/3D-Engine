package geometries;

import org.junit.jupiter.api.Test;
import primitives.*;
import static geometries.Intersectable.GeoPoint;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class CylinderTest {
    //x^2+y^2=16
    Cylinder cylinder1 = new Cylinder(4, new Ray(new Point3D(), new Vector3D(0,0,1)));

    //y^2+z^2=16
    Cylinder cylinder2 = new Cylinder(4, new Ray(new Point3D(), new Vector3D(1,0,0)));

    //x^2+(z-6)^2=1
    Cylinder cylinder3 = new Cylinder(1, new Ray(new Point3D(0,0,6), new Vector3D(0,1,0)));

    Ray ray1 = new Ray(new Point3D(1,1,1), new Vector3D(1,0,0));
    Ray ray2 = new Ray(new Point3D(0,1,1), new Vector3D(0,1,0));

    @Test
    void get_point() {
        assertEquals(new Point3D(new Coordinate(1), new Coordinate(1), new Coordinate(1)), ray1.get_point());
    }

    @Test
    void get_direction() {
        assertEquals(new Vector3D(new Coordinate(1), new Coordinate(0), new Coordinate(0)), ray1.get_direction());
    }

    @Test
    void equals() {
        assertEquals(new Ray(new Point3D(new Coordinate(1), new Coordinate(1), new Coordinate(1)), new Vector3D(new Coordinate(1), new Coordinate(0), new Coordinate(0))), ray1);
        assertNotEquals(ray2, ray1);
    }

    @Test
    void toStringTest() {
        assertEquals("(1.0, 1.0, 1.0) + t(1.0, 0.0, 0.0)", ray1.toString());
    }

    @Test
    void findIntersections() {
        //ray in x direction with intersections from outside of the cylinder
        ArrayList<GeoPoint> expected1 = new ArrayList<>();
        expected1.add(new GeoPoint(cylinder1, new Point3D(-4,0,0)));
        expected1.add(new GeoPoint(cylinder1, new Point3D(4,0,0)));
        Ray r1 = new Ray(new Point3D(-5,0,0), new Vector3D(1,0,0));
        ArrayList<GeoPoint> actual1 = cylinder1.findIntersections(r1);
        assertEquals(expected1, actual1);

        //ray in -x direction with intersections from outside of the cylinder
        ArrayList<GeoPoint> expected2 = new ArrayList<>();
        expected2.add(new GeoPoint(cylinder1, new Point3D(4,0,0)));
        expected2.add(new GeoPoint(cylinder1, new Point3D(-4,0,0)));
        Ray r2 = new Ray(new Point3D(5,0,0), new Vector3D(-1,0,0));
        ArrayList<GeoPoint> actual2 = cylinder1.findIntersections(r2);
        assertEquals(expected2, actual2);

        //ray in x direction without intersections from inside of the cylinder
        Ray r3 = new Ray(new Point3D(5,0,0), new Vector3D(1,0,0));
        ArrayList<GeoPoint> actual3 = cylinder2.findIntersections(r3);
        assertEquals(new ArrayList<>(), actual3);

        Ray r4 = new Ray(new Point3D(0,-5,5), new Vector3D(0,1,0));
        ArrayList<GeoPoint> expected4 = new ArrayList<>();
        expected4.add(new GeoPoint(cylinder1, new Point3D(0, -4,5)));
        expected4.add(new GeoPoint(cylinder1, new Point3D(0, 4,5)));
        ArrayList<GeoPoint> actual4 = cylinder1.findIntersections(r4);
        assertEquals(expected4, actual4);
    }

    @Test
    void equals1() {
        assertEquals(true, cylinder1.equals(cylinder1));
        assertEquals(false, cylinder1.equals(cylinder2));
        assertEquals(true, cylinder1.equals(new Cylinder(4, new Ray(new Point3D(), new Vector3D(0,0,1)))));
    }

    @Test
    void translate() {
        Cylinder actual = new Cylinder(5, new Ray(new Point3D(), new Vector3D(0,0,1)));
        actual.translate(5,0,0);
        Cylinder expected = new Cylinder(5, new Ray(new Point3D(5,0,0), new Vector3D(0,0,1)));
        assertEquals(expected, actual);
    }

    @Test
    void rotate() {
        Cylinder actual = new Cylinder(5, new Ray(new Point3D(), new Vector3D(0,0,1)));
        actual.rotate(-90,0,0);
        Cylinder expected = new Cylinder(5, new Ray(new Point3D(0,0,0), new Vector3D(0,1,0)));
        assertEquals(expected, actual);
    }

    @Test
    void scale() {
        Cylinder actual = new Cylinder(5, new Ray(new Point3D(), new Vector3D(0,0,1)));
        actual.scale(5);
        Cylinder expected = new Cylinder(25, new Ray(new Point3D(), new Vector3D(0,0,1)));
    }

    @Test
    void transform() {
    }

    @Test
    void transformTRS() {
    }

    @Test
    void contains() {
        Cylinder cylinder1 = new Cylinder(4, new Ray(new Vector3D(0,0,1)));
        Point3D point1 = new Point3D(3,0,0);
        assertEquals(true, cylinder1.contains(point1));

        Point3D point2 = new Point3D(4,0,0);
        assertEquals(true, cylinder1.contains(point2));

        Point3D point3 = new Point3D(5,0,0);
        assertEquals(false, cylinder1.contains(point3));

        Cylinder cylinder2 = new Cylinder(4, new Ray(new Point3D(1,0,-1), new Vector3D(0,1,0)));
        Point3D point21 = new Point3D(1,-90,3);
        assertEquals(true, cylinder2.contains(point21));

        Point3D point22 = new Point3D(1,45,-1);
        assertEquals(true, cylinder2.contains(point22));

        Point3D point23 = new Point3D(1,0,4);
        assertEquals(false, cylinder2.contains(point23));

        Point3D point24 = new Point3D(-3,0,-1);
        assertEquals(true, cylinder2.contains(point24));

        Point3D point25 = new Point3D(-3,0,0);
        assertEquals(false, cylinder2.contains(point25));
    }

    @Test
    void surfaceContains() {
        Cylinder cylinder1 = new Cylinder(4, new Ray(new Vector3D(1,0,0)));
        Point3D point1 = new Point3D(0,4,0);
        assertTrue(cylinder1.surfaceContains(point1));

        Cylinder cylinder2 = new Cylinder(4, new Ray(new Point3D(), new Vector3D(0,0,1)));
        Point3D point2 = new Point3D(0, -4,5);
        assertTrue(cylinder2.surfaceContains(point2));

    }
}