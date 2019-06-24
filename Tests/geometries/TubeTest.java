package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector3D;

import static geometries.Intersectable.GeoPoint;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class TubeTest {
    Tube tube = new Tube(4, new Ray(new Point3D(1,1,1), new Vector3D(1,0,0)), 4);

    @Test
    void get_height() {
        assertEquals(4, tube._height);
    }

    @Test
    void get_normal() {
        // Point3D p1 = new Point3D(new Coordinate(1), new Coordinate(5),new Coordinate(1));
        Point3D p2 = new Point3D(2,5,1);
        Point3D p20 = new Point3D(2,1,1);
        Point3D p3 = new Point3D(1,1,1);
        Point3D p4 = new Point3D(5,1,1);
        Point3D p5 = new Point3D(1,2,3);

        //assertEquals(/p1.sub(tube._ray.get_point()).normalized(),tube.get_normal(p1));
        assertEquals(p2.subtract(p20).normalized(),tube.get_normal(p2));
        assertEquals(tube._ray.get_direction().scaled(-1),tube.get_normal(p3));
        assertEquals(tube._ray.get_direction(),tube.get_normal(p4));
        assertEquals(tube._ray.get_direction().scaled(-1),tube.get_normal(p5));
    }

    @Test
    void findIntersections() {
        Tube tube1 = new Tube(4, new Ray(new Vector3D(0,0,1)), 4);
        Ray ray1 = new Ray(new Point3D(0,0,-1), new Vector3D(0,0,1));
        ArrayList<GeoPoint>  expected1 = new ArrayList<>();
        expected1.add(new GeoPoint(tube1, new Point3D()));
        expected1.add(new GeoPoint(tube1, new Point3D(0,0,4)));
        ArrayList<GeoPoint> actual1 = tube1.findIntersections(ray1);
        assertEquals(expected1, actual1);

        Ray ray2 = new Ray(new Point3D(0,0,5), new Vector3D(0,0,-1));
        ArrayList<GeoPoint>  expected2 = new ArrayList<>();
        expected2.add(new GeoPoint(tube1, new Point3D(0,0,4)));
        expected2.add(new GeoPoint(tube1, new Point3D()));
        ArrayList<GeoPoint> actual2 = tube1.findIntersections(ray2);
        assertEquals(expected2, actual2);

        Tube tube3 = new Tube(4, new Ray(new Vector3D(1,0,0)), 4);
        Ray ray3 = new Ray(new Point3D(-1,0,0), new Vector3D(1,0,0));
        ArrayList<GeoPoint>  expected3 = new ArrayList<>();
        expected3.add(new GeoPoint(tube3, new Point3D()));
        expected3.add(new GeoPoint(tube3, new Point3D(4,0,0)));
        ArrayList<GeoPoint> actual3 = tube3.findIntersections(ray3);
        assertEquals(expected3, actual3);

        Ray ray4 = new Ray(new Point3D(5,0,0), new Vector3D(-1,0,0));
        ArrayList<GeoPoint>  expected4 = new ArrayList<>();
        expected4.add(new GeoPoint(tube3, new Point3D(4,0,0)));
        expected4.add(new GeoPoint(tube3, new Point3D()));
        ArrayList<GeoPoint> actual4 = tube3.findIntersections(ray4);
        assertEquals(expected4, actual4);
    }

    @Test
    void equals1() {
        Tube tube1 = new Tube(4, new Ray(new Point3D(1,1,1), new Vector3D(1,0,0)), 4);
        Tube tube2 = new Tube(8, new Ray(new Point3D(1,1,1), new Vector3D(1,0,0)), 4);

        assertEquals(true,  tube.equals(tube));
        assertEquals(false, tube.equals(tube2));
        assertEquals(true,  tube.equals(tube1));

    }

    @Test
    void translate() {
        Tube actual = new Tube(5, new Ray(new Point3D(), new Vector3D(0,0,1)), 10);
        actual.translate(5,0,0);
        Tube expected = new Tube(5, new Ray(new Point3D(5,0,0), new Vector3D(0,0,1)), 10);
        assertEquals(expected, actual);
    }

    @Test
    void rotate() {
        Tube actual = new Tube(5, new Ray(new Point3D(), new Vector3D(0,0,1)), 10);
        actual.rotate(-90,0,0);
        Tube expected = new Tube(5, new Ray(new Point3D(0,0,0), new Vector3D(0,1,0)), 10);
        assertEquals(expected, actual);
    }

    @Test
    void scale() {
        Tube actual = new Tube(5, new Ray(new Point3D(), new Vector3D(0,0,1)), 5);
        actual.scale(5);
        Tube expected = new Tube(25, new Ray(new Point3D(), new Vector3D(0,0,1)), 25);
    }

    @Test
    void transform() {
    }

    @Test
    void transformTRS() {
    }


    @Test
    void contains() {
        Tube tube1 = new Tube(4, new Ray(new Vector3D(0,0,1)), 4);
        Point3D point1 = new Point3D(0,0,5);
        assertEquals(false, tube1.contains(point1));

        Point3D point2 = new Point3D(0,0,4);
        assertEquals(true, tube1.contains(point2));

        Point3D point3 = new Point3D(0,0,3);
        assertEquals(true, tube1.contains(point3));

        Point3D point4 = new Point3D(0,0,0);
        assertEquals(true, tube1.contains(point3));

        Point3D point5 = new Point3D(0,0,-1);
        assertEquals(false, tube1.contains(point5));

        Tube tube2 = new Tube(4, new Point3D(-6,-6,-2), new Point3D(-2,-2,0));
        Point3D point20 = new Point3D(-7,-7,0);
        assertEquals(false, tube2.contains(point20));

        Point3D point21 = new Point3D(-7,-6,0);
        assertEquals(true, tube2.contains(point21));

        Point3D point22 = new Point3D(-6,-6,-2);
        assertEquals(true, tube2.contains(point22));

        Point3D point23 = new Point3D(-5,-5,0);
        assertEquals(true, tube2.contains(point23));

        Point3D point24 = new Point3D(-2,-2,0);
        assertEquals(true, tube2.contains(point24));

        Point3D point25 = new Point3D(0,-8,0);
        assertEquals(false, tube2.contains(point25));

    }
}