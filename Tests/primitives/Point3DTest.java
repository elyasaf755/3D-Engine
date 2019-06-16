package primitives;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Point3DTest {
    Point3D p1 = new Point3D(1,1,1);
    Point3D p2 = new Point3D(2,2,2);

    @Test
    void getZ() {
        assertEquals(new Coordinate(1), p1.getZ());
        assertNotEquals(new Coordinate(2), p1.getZ());
    }

    @Test
    void subtract() {
        Vector3D vector3 = p1.subtract(p2);

        assertEquals(vector3, new Vector3D(new Point3D(-1,-1,-1)));
        assertNotEquals(vector3, new Vector3D(new Point3D(-1,-1,-2)));
    }

    @Test
    void addV() {
        Point3D point = p1.add(new Vector3D(p2));

        assertEquals(point, new Point3D(3,3,3));
        assertNotEquals(point, new Point3D(-1,-1,-2));
    }

    @Test
    void distanceSquared() {
        double distanceSquared = p1.distanceSquared(p2);

        assertEquals(3, distanceSquared);
        assertNotEquals(4, distanceSquared);
    }

    @Test
    void distance() {
        double distance = p1.distance(p2);

        assertEquals(Math.sqrt(3), distance);
        assertNotEquals(3, distance);
    }

    @Test
    void equals() {
        boolean flag = p1.equals(new Point3D(1,1,1));

        assertEquals(true, flag);
        assertNotEquals(false, flag);
    }

    @Test
    void toStringTest() {
        assertEquals("(1.0, 1.0, 1.0)", p1.toString());
        assertNotEquals("(-1.0, -1.0, -1.0)", p1.toString());
    }

    @Test
    void translate() {
        Point3D actual = new Point3D();
        actual.translate(15,10,5);
        Point3D expected = new Point3D(15,10,5);
        assertEquals(expected, actual);
    }

    @Test
    void rotate() {
        Point3D actual = new Point3D(0,1,0);
        actual.rotate(90,0,0);
        Point3D expected = new Point3D(0,0,1);
        assertEquals(expected, actual);
    }

    @Test
    void scale() {
        Point3D actual = new Point3D(0,1,0);
        actual.scale(15,10,5);
        Point3D expected = new Point3D(0,10,0);
        assertEquals(expected, actual);
    }

    @Test
    void transform() {
        Vector3D translation = new Vector3D(-120,0,0);
        Vector3D rotation = new Vector3D(0,180, 0);
        Vector3D scale = new Vector3D(5,10, 5);

        Transform transform = new Transform(translation, rotation, scale);

        Point3D actual = new Point3D(1,0,0);
        actual.transform(transform);
        Point3D expected = new Point3D(-125,0,0);
        assertEquals(expected, actual);
    }

    @Test
    void transformTRS() {
        Vector3D translation = new Vector3D(-120,0,0);
        Vector3D rotation = new Vector3D(0,180, 0);
        Vector3D scale = new Vector3D(5,10, 5);

        Point3D actual = new Point3D(1,0,0);
        actual.transform(translation, rotation, scale);
        Point3D expected = new Point3D(-125,0,0);
        assertEquals(expected, actual);
    }

    @Test
    void addP() {
    }

    @Test
    void addPV() {
    }
}