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
}