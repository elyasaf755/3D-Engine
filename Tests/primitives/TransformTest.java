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

        Vector3D vector = new Vector3D(2,0,0);

        Vector3D result = transform1.getTransformation().mult(vector);

        assertEquals(new Vector3D(-8,0,0), result);


    }
}