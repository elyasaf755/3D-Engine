package primitives;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RayTest {
    Ray ray1 = new Ray(new Point3D(1, 1, 1), new Vector3D(1, 0, 0));
    Ray ray2 = new Ray(new Point3D(0, 1, 1), new Vector3D(0, 1, 0));

    @Test
    void toStringTest() {
        assertEquals("(1.0, 1.0, 1.0) + t(1.0, 0.0, 0.0)", ray1.toString());
    }

    @Test
    void get_point() {
        assertEquals(new Point3D(1, 1, 1), ray1.get_point());
    }

    @Test
    void get_direction() {
        assertEquals(new Vector3D(1, 0, 0), ray1.get_direction());
    }

    @Test
    void equals() {
        assertEquals(new Ray(new Point3D(1, 1, 1), new Vector3D(1, 0, 0)), ray1);
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

    @Test
    void equals1() {
        assertEquals(true,ray1.equals(ray1));
        assertEquals(false, ray1.equals(ray2));
        assertEquals(true, ray1.equals(new Ray(new Point3D(1, 1, 1), new Vector3D(1, 0, 0))));
    }

    @Test
    void translate() {
        Ray actual = new Ray(new Point3D(), new Vector3D(1,0,0));
        actual.translate(10,0,0);
        Ray expected = new Ray(new Point3D(10,0,0), new Vector3D(1,0,0));
        assertEquals(expected, actual);
    }

    @Test
    void rotate() {
        Ray actual = new Ray(new Point3D(5,0,0), new Vector3D(1,0,0));
        actual.rotate(0,180,0);
        Ray expected = new Ray(new Point3D(-5,0,0), new Vector3D(-1,0,0));
        assertEquals(expected, actual);
    }

    @Test
    void scale() {
        Ray actual = new Ray(new Point3D(1,1,1), new Vector3D(1,2,3));
        actual.scale(5,10,20);
        Ray expected = new Ray(new Point3D(5,10,20), new Vector3D(5,20,60));
        assertEquals(expected, actual);
    }

    @Test
    void transform() {
        Vector3D translation = new Vector3D(-120,0,0);
        Vector3D rotation = new Vector3D(0,180, 0);
        Vector3D scale = new Vector3D(5,10, 5);

        Transform transform = new Transform(translation, rotation, scale);

        Ray actual = new Ray(new Point3D(1,0,0), new Vector3D(1,0,0));
        actual.transform(transform);
        Ray expected = new Ray(new Point3D(-125,0,0), new Vector3D(-125,0,0));
        assertEquals(expected, actual);
    }

    @Test
    void transformTRS() {
        Vector3D translation = new Vector3D(-120,0,0);
        Vector3D rotation = new Vector3D(0,180, 0);
        Vector3D scale = new Vector3D(5,10, 5);

        Ray actual = new Ray(new Point3D(1,0,0), new Vector3D(1,0,0));
        actual.transform(translation, rotation, scale);
        Ray expected = new Ray(new Point3D(-125,0,0), new Vector3D(-125,0,0));
        assertEquals(expected, actual);
    }
}