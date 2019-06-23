package primitives;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TransformTest {
    Transform transform = new Transform();

    @Test
    void getTranslation() {
    }

    @Test
    void getRotation() {
    }

    @Test
    void getScale() {
    }

    @Test
    void setTranslation() {
    }

    @Test
    void setTranslation1() {
    }

    @Test
    void setRotation() {
    }

    @Test
    void setRotation1() {
    }

    @Test
    void setScale() {
    }

    @Test
    void setScale1() {
    }

    @Test
    void getTransformation() {
        Transform transform1 = new Transform(new Vector3D(2,0,0), new Vector3D(0,180,0), new Vector3D(5,2,0));

        Matrix4 matrix = new Matrix4();

        Vector3D vector1 = new Vector3D(2,0,0);

        Vector3D actual1 = transform1.getTransformation().mult(vector1);

        assertEquals(new Vector3D(-8,0,0), actual1);

        Vector3D expected2 = new Vector3D(2,3,4);
        Vector3D actual2 = transform.getTransformation().mult(expected2);

        assertEquals(expected2, actual2);


    }

    @Test
    void getRodriguesRotation() {
        Vector3D v1 = new Vector3D(1,0,0);
        Vector3D v2 = new Vector3D(0,0,1);

        Matrix3 H = Transform.getRodriguesRotation(v1, v2);

        Vector3D v3 = H.inversed().mult(H.mult(v1));

        assertEquals(v1, v3);

        Vector3D source = new Vector3D(1,2,3);
        Vector3D destination = new Vector3D(0,0,1);

        Matrix3 R = Transform.getRodriguesRotation(source, destination);

        Vector3D expected = R.mult(source).normalized();

        assertEquals(destination, expected);
    }

    @Test
    void getHouseholderMatrix() {
    }

    @Test
    void rotatedVectorAround() {
        Vector3D source1 = new Vector3D(1,0,0).normalized();
        Vector3D axis1 = new Vector3D(0,0,1);
        Vector3D actual1 = Transform.rotatedVectorAround(source1, axis1, Math.toRadians(90));
        Vector3D expected1 = new Vector3D(0,1,0);
        assertEquals(expected1, actual1);

        Vector3D source2 = new Vector3D(1,1,0).normalized();
        Vector3D axis2 = new Vector3D(0,0,1);
        Vector3D actual2 = Transform.rotatedVectorAround(source2, axis2, Math.toRadians(45));
        Vector3D expected2 = new Vector3D(0,1,0);
        assertEquals(expected2, actual2);
    }
}