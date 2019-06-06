package primitives;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RayTest {
    Ray ray1 = new Ray(new Point3D(new Coordinate(1), new Coordinate(1), new Coordinate(1)), new Vector3D(new Coordinate(1), new Coordinate(0), new Coordinate(0)));
    Ray ray2 = new Ray(new Point3D(new Coordinate(0), new Coordinate(1), new Coordinate(1)), new Vector3D(new Coordinate(0), new Coordinate(1), new Coordinate(0)));

    @Test
    void toStringTest() {
        assertEquals("(1.0, 1.0, 1.0) + t(1.0, 0.0, 0.0)", ray1.toString());
    }

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
    void angleBetween_deg() {
        Ray r1 = new Ray(new Vector3D(1,0,0));
        Ray r2 = new Ray(new Vector3D(0,1,0));
        assertEquals(90, r1.angleBetween_deg(r2));
        assertEquals(0, r1.angleBetween_deg(r1));
    }

    @Test
    void isParallelTo() {
        Ray r1 = new Ray(new Vector3D(1,1,1));
        Ray r2 = new Ray(new Point3D(1,0,0), new Vector3D(2,2,2));
        assertEquals(true, r1.isParallelTo(r2));

        Ray r3 = new Ray(new Vector3D(1, 0, 0));
        assertEquals(false, r3.isParallelTo(r1));
        assertEquals(true, r3.isParallelTo(r3));

        Ray r4 = new Ray(new Vector3D(-1, 0, 0));
        assertEquals(true, r4.isParallelTo(r3));
    }

    @Test
    void findIntersection() {
        Ray ray1 = new Ray(new Vector3D(1,0,0));
        Ray ray2 = new Ray(new Vector3D(0,1,0));
        assertEquals(new Point3D(), ray1.findIntersection(ray2));

        Ray ray3 = new Ray(new Point3D(0,1,0), new Vector3D(1,0,0));
        assertEquals(new Point3D(0,1,0), ray3.findIntersection(ray2));

        Ray ray4 = new Ray(new Point3D(-2,0,0), new Vector3D(1,0,0));
        Ray ray5 = new Ray(new Point3D(-2,-1,0), new Vector3D(1,1,0));
        assertEquals(new Point3D(-1,0,0), ray4.findIntersection(ray5));

        Ray ray6 = new Ray(new Point3D(1,0,0), new Vector3D(1,0,0));
        Ray ray7 = new Ray(new Point3D(-1,0,0), new Vector3D(-1,0,0));
        assertEquals(null, ray6.findIntersection(ray7));
    }
}