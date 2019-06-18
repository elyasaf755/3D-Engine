package primitives;

import geometries.Triangle;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.MathContext;

import static org.junit.jupiter.api.Assertions.*;

class Matrix4Test {

    @Test
    void multVector3() {
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
    void multPoint3() {
        Matrix4 matrix = new Matrix4();
        matrix.setRow(0,1,0,0,0);
        matrix.setRow(1,0,1,0,0);
        matrix.setRow(2,0,0,1,0);
        matrix.setRow(3,0,0,0,1);

        Point3D point = new Point3D(1,2,3);

        Point3D result = matrix.mult(point);

        assertEquals(point, result);
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
        //scale Vector

        Matrix4 matrix = new Matrix4();

        matrix.initScale(5,2,0);

        Vector3D vector = new Vector3D(2,1,0);

        Vector3D result = matrix.mult(vector);

        assertEquals(new Vector3D(10,2,0), result);

        /*
        //scale Trianle

        Point3D p1 = new Point3D(-125, -225, -260);
        Point3D p2 = new Point3D(-225, -125, -260);
        Point3D p3 = new Point3D(-225, -225, -270);

        Triangle triangle = new Triangle(p1, p2, p3);
        Point3D centroid = triangle.getCentroid();

        matrix.initScale(5,5,5);

        Vector3D result1 = matrix.mult(new Vector3D(p1));
        Vector3D result2 = matrix.mult(new Vector3D(p2));
        Vector3D result3 = matrix.mult(new Vector3D(p3));

        Triangle triangledScaled = new Triangle(result1.getPoint(), result2.getPoint(), result3.getPoint());
        Point3D centroidScaled = triangledScaled.getCentroid();

        matrix = new Matrix4();
        matrix.initTranslation(centroid.subtract(centroidScaled));

        result1 = matrix.mult(result1);
        result2 = matrix.mult(result2);
        result3 = matrix.mult(result3);

        Triangle expected = new Triangle(
                new Point3D( 141 + 2/3.0,-358 - 1/3.0,-246-2/3.0),
                new Point3D(-358 + 1/3.0, 141 + 2/3.0,-246-2/3.0),
                new Point3D(-358 + 1/3.0,-358 - 1/3.0,-296-2/3.0)
        );

        Triangle actual = new Triangle(result1.getPoint(), result2.getPoint(), result3.getPoint());

        assertEquals(expected, actual);
         */
    }
}