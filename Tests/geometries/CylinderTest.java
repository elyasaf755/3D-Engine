package geometries;

import org.junit.jupiter.api.Test;
import primitives.Coordinate;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector3D;

import static org.junit.jupiter.api.Assertions.*;

class CylinderTest {
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
}