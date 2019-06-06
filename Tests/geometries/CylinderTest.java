package geometries;

import org.junit.jupiter.api.Test;
import primitives.*;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class CylinderTest {
    //x^2+y^2=16
    Cylinder cylinder1 = new Cylinder(4, new Ray(new Point3D(), new Vector3D(0,0,1)));

    //y^2+z^2=16
    Cylinder cylinder2 = new Cylinder(4, new Ray(new Point3D(), new Vector3D(1,0,0)));

    //x^2+(z-6)^2=1
    Cylinder cylinder3 = new Cylinder(1, new Ray(new Point3D(0,0,6), new Vector3D(0,1,0)));

    Ray ray1 = new Ray(new Point3D(new Coordinate(1), new Coordinate(1), new Coordinate(1)), new Vector3D(new Coordinate(1), new Coordinate(0), new Coordinate(0)));
    Ray ray2 = new Ray(new Point3D(new Coordinate(0), new Coordinate(1), new Coordinate(1)), new Vector3D(new Coordinate(0), new Coordinate(1), new Coordinate(0)));

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
        ArrayList<Point3D> expected1 = new ArrayList<>();
        expected1.add(new Point3D(-4,0,0));
        expected1.add(new Point3D(4,0,0));
        Ray ray1 = new Ray(new Point3D(-5,0,0), new Vector3D(1,0,0));
        assertEquals(expected1, cylinder1.findIntersections(ray1));

        //ray in -x direction with intersections from outside of the cylinder
        ArrayList<Point3D> expected2 = new ArrayList<>();
        expected2.add(new Point3D(4,0,0));
        expected2.add(new Point3D(-4,0,0));
        Ray ray2 = new Ray(new Point3D(5,0,0), new Vector3D(-1,0,0));
        assertEquals(expected2, cylinder1.findIntersections(ray2));

        //ray in x direction without intersections from inside of the cylinder
        Ray ray3 = new Ray(new Point3D(5,0,0), new Vector3D(1,0,0));
        assertEquals(new ArrayList<>(), cylinder2.findIntersections(ray3));
    }
}