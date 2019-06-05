package geometries;

import org.junit.jupiter.api.Test;
import primitives.Coordinate;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector3D;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class TubeTest {
    Tube tube = new Tube(4, new Ray(new Point3D(new Coordinate(1), new Coordinate(1), new Coordinate(1)), new Vector3D(new Coordinate(1), new Coordinate(0), new Coordinate(0))), 4);

    @Test
    void get_height() {
        assertEquals(4, tube._height);
    }

    @Test
    void get_normal() {
        // Point3D p1 = new Point3D(new Coordinate(1), new Coordinate(5),new Coordinate(1));
        Point3D p2 = new Point3D(new Coordinate(2), new Coordinate(5),new Coordinate(1));
        Point3D p20 = new Point3D(new Coordinate(2), new Coordinate(1),new Coordinate(1));
        Point3D p3 = new Point3D(new Coordinate(1), new Coordinate(1),new Coordinate(1));
        Point3D p4 = new Point3D(new Coordinate(5), new Coordinate(1),new Coordinate(1));
        Point3D p5 = new Point3D(new Coordinate(1), new Coordinate(2),new Coordinate(3));

        //assertEquals(/p1.subtract(tube._ray.get_point()).normalized(),tube.get_normal(p1));
        assertEquals(p2.subtract(p20).normalized(),tube.get_normal(p2));
        assertEquals(tube._ray.get_direction().scale(-1),tube.get_normal(p3));
        assertEquals(tube._ray.get_direction(),tube.get_normal(p4));
        assertEquals(tube._ray.get_direction().scale(-1),tube.get_normal(p5));
    }

}