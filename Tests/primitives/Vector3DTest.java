package primitives;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Vector3DTest {
    Vector3D _v1 = new Vector3D(new Point3D(new Coordinate(1), new Coordinate(0), new Coordinate(0)));
    Vector3D _v2 = new Vector3D(new Point3D(new Coordinate(0), new Coordinate(1), new Coordinate(0)));
    Vector3D _v3 = new Vector3D(new Point3D(new Coordinate(0), new Coordinate(0), new Coordinate(1)));
    Vector3D _v4 = new Vector3D(new Point3D(new Coordinate(1), new Coordinate(2), new Coordinate(3)));
    Vector3D _v5 = new Vector3D(new Point3D(new Coordinate(99), new Coordinate(23), new Coordinate(5)));

    @Test
    void getPoint() {
        assertEquals(new Point3D(new Coordinate(1), new Coordinate(0), new Coordinate(0)), _v1.getPoint());
    }

    @Test
    void subtract() {
        assertEquals(new Vector3D(new Coordinate(1), new Coordinate(-1), new Coordinate(0)), _v1.subtract(_v2));
    }

    @Test
    void add() {
        assertEquals(new Vector3D(new Coordinate(1), new Coordinate(1), new Coordinate(0)), _v1.add(_v2));

    }

    @Test
    void dotProduct() {
        assertEquals(0d, _v1.dotProduct(_v2));
    }

    @Test
    void crossProduct() {
        assertEquals(new Vector3D(new Coordinate(0), new Coordinate(0), new Coordinate(1)), _v1.crossProduct(_v2));
    }

    @Test
    void length() {
        assertEquals(1d, _v1.length());
    }

    @Test
    void scale() {
        assertEquals(new Vector3D(new Coordinate(2), new Coordinate(0), new Coordinate(0)), _v1.scale(2));
    }

    @Test
    void normalized() {
        assertEquals(1d, _v1.normalized().length());
    }

    @Test
    void normalize() {
        Vector3D vector3= new Vector3D(_v1);
        vector3.normalize();

        assertEquals(1d, vector3.length());
    }

    @Test
    void equals() {
        assertEquals(false, _v1.equals(_v2));
        assertEquals(true, _v1.equals(_v1));
    }

    @Test
    void toStringTest() {
        assertEquals("t(1.0, 0.0, 0.0)", _v1.toString());
    }

}