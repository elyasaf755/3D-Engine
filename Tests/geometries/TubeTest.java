package geometries;

import org.junit.jupiter.api.Test;
import primitives.Coordinate;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector3D;

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

        //assertEquals(/p1.subtract(tube._ray.get_point()).normalized(),tube.get_normal(p1));
        assertEquals(p2.subtract(p20).normalized(),tube.get_normal(p2));
        assertEquals(tube._ray.get_direction().scale(-1),tube.get_normal(p3));
        assertEquals(tube._ray.get_direction(),tube.get_normal(p4));
        assertEquals(tube._ray.get_direction().scale(-1),tube.get_normal(p5));
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
}