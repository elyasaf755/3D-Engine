package primitives;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Vector3DTest {
    Vector3D _v1 = new Vector3D(new Point3D(1,0,0));
    Vector3D _v2 = new Vector3D(new Point3D(0,1,0));
    Vector3D _v3 = new Vector3D(new Point3D(0,0,1));
    Vector3D _v4 = new Vector3D(new Point3D(1,2,3));
    Vector3D _v5 = new Vector3D(new Point3D(99,23,5));

    @Test
    void getPoint() {
        assertEquals(new Point3D(1,0,0), _v1.getPoint());
    }

    @Test
    void subtract() {
        assertEquals(new Vector3D(1,-1,0), _v1.subtract(_v2));
    }

    @Test
    void add() {
        assertEquals(new Vector3D(1,1,0), _v1.add(_v2));

    }

    @Test
    void dotProduct() {
        assertEquals(0, _v1.dotProduct(_v2));
    }

    @Test
    void crossProduct() {
        assertEquals(new Vector3D(0,0,1), _v1.crossProduct(_v2));
    }

    @Test
    void length() {
        assertEquals(1, _v1.length());
    }

    @Test
    void scale() {
        assertEquals(new Vector3D(2,0,0), _v1.scale(2));
    }

    @Test
    void normalized() {
        assertEquals(1, _v1.normalized().length());
    }

    @Test
    void normalize() {
        Vector3D vector3= new Vector3D(_v1);
        vector3.normalize();

        assertEquals(1, vector3.length());
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

    @Test
    void translate() {
        Vector3D actual = new Vector3D(1,0,0);
        actual.translate(15,10,5);
        Vector3D expected = new Vector3D(1,0,0);
        assertEquals(expected, actual);//Vectors are not translated.
    }

    @Test
    void rotate() {
        Vector3D actual = new Vector3D(0,1,0);
        actual.rotate(90,0,0);
        Vector3D expected = new Vector3D(0,0,1);
        assertEquals(expected, actual);
    }

    @Test
    void scaleXYZ() {
        Vector3D actual = new Vector3D(0,1,0);
        actual.scale(15,10,5);
        Vector3D expected = new Vector3D(0,10,0);
        assertEquals(expected, actual);
    }

    @Test
    void transform() {
        Vector3D translation = new Vector3D(-120,0,0);
        Vector3D rotation = new Vector3D(0,180, 0);
        Vector3D scale = new Vector3D(5,10, 5);

        Transform transform = new Transform(translation, rotation, scale);

        Vector3D actual = new Vector3D(1,0,0);
        actual.transform(transform);
        Vector3D expected = new Vector3D(-5,0,0);
        assertEquals(expected, actual);
    }

    @Test
    void transformTRS() {
        Vector3D translation = new Vector3D(-120,0,0);
        Vector3D rotation = new Vector3D(0,180, 0);
        Vector3D scale = new Vector3D(5,10, 5);

        Vector3D actual = new Vector3D(1,0,0);
        actual.transform(translation, rotation, scale);
        Vector3D expected = new Vector3D(-5,0,0);
        assertEquals(expected, actual);
    }

    @Test
    void outterProduct() {
        Vector3D u = new Vector3D(3,4,1);
        Vector3D v = new Vector3D(3,7,5);

        double[][] matrix = {
                { 9, 21, 15},
                {12, 28, 20},
                { 3,  7,  5}
        };

        Matrix3 actual = u.outterProduct(v);
        Matrix3 expected = new Matrix3(matrix);

        assertEquals(expected, actual);
    }
}