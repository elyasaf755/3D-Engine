package primitives;

import geometries.Triangle;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Matrix4Test {

    @Test
    void mult() {
        Matrix4 matrix = new Matrix4();
        matrix.setRow(0,1,0,0,0);
        matrix.setRow(1,0,1,0,0);
        matrix.setRow(2,0,0,1,0);
        matrix.setRow(3,0,0,0,1);

        Vector3D vector = new Vector3D(1,2,3);

        Vector3D result = matrix.mult(vector);

        assertEquals(vector, result);
    }

    @Test
    void initTranslation() {
        Matrix4 matrix = new Matrix4();
        matrix.initTranslation(5,0,0);

        Vector3D vector = new Vector3D(1,1,0);

        Vector3D result = matrix.mult(vector);

        assertEquals(new Vector3D(6,1,0), result);
    }

    @Test
    void initRotation() {
        Matrix4 matrix = new Matrix4();

        matrix.initRotation(0,180,0);
        Vector3D result = matrix.mult(new Vector3D(1,0,0));

        assertEquals(new Vector3D(-1,0,0), result);

        matrix.initRotation(0,0,90);
        result = matrix.mult(new Vector3D(1,0,0));

        assertEquals(new Vector3D(0,1,0), result);
    }

    @Test
    void initScale() {
        Matrix4 matrix = new Matrix4();

        matrix.initScale(5,2,0);

        Vector3D vector = new Vector3D(2,1,0);

        Vector3D result = matrix.mult(vector);

        assertEquals(new Vector3D(10,2,0), result);

        /*
        Triangle triangle = new Triangle(new Point3D(-125, -225, -260),  new Point3D(-225, -125, -260), new Point3D(-225, -225, -270));
        Point3D centroidOriginal = triangle.getCentroid();

        matrix.initScale(5,5,5);

        Vector3D result1 = matrix.mult(new Vector3D(triangle.get_point1()));
        Vector3D result2 = matrix.mult(new Vector3D(triangle.get_point2()));
        Vector3D result3 = matrix.mult(new Vector3D(triangle.get_point3()));

        Triangle triangledScaled = new Triangle(result1.getPoint(), result2.getPoint(), result3.getPoint());

        Point3D centroidScaled = triangledScaled.getCentroid();

        matrix.initTranslation(centroidOriginal.subtract(centroidScaled));

        result1 = matrix.mult(result1);
        result2 = matrix.mult(result2);
        result3 = matrix.mult(result3);

        assertEquals(new Vector3D(10,2,0), new Vector3D(1,0,0));
         */
    }
}