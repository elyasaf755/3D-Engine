package primitives;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Point3DTest {
    Point3D p1 = new Point3D(new Coordinate(1), new Coordinate(1), new Coordinate(1));
    Point3D p2 = new Point3D(new Coordinate(2), new Coordinate(2), new Coordinate(2));

    @Test
    void getZ() {
        assertEquals(new Coordinate(1), p1.getZ());
        assertNotEquals(new Coordinate(2), p1.getZ());
    }

    @Test
    void subtract() {
        Vector3D vector3 = p1.subtract(p2);

        assertEquals(vector3, new Vector3D(new Point3D(new Coordinate(-1), new Coordinate(-1), new Coordinate(-1))));
        assertNotEquals(vector3, new Vector3D(new Point3D(new Coordinate(-1), new Coordinate(-1), new Coordinate(-2))));
    }

    @Test
    void add() {
        Point3D point = p1.add(new Vector3D(p2));

        assertEquals(point, new Point3D(new Coordinate(3), new Coordinate(3), new Coordinate(3)));
        assertNotEquals(point, new Point3D(new Coordinate(-1), new Coordinate(-1), new Coordinate(-2)));
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
        boolean flag = p1.equals(new Point3D(new Coordinate(1), new Coordinate(1), new Coordinate(1)));

        assertEquals(true, flag);
        assertNotEquals(false, flag);
    }

    @Test
    void toStringTest() {
        assertEquals("(1.0, 1.0, 1.0)", p1.toString());
        assertNotEquals("(-1.0, -1.0, -1.0)", p1.toString());
    }
}