package geometries;

import org.junit.jupiter.api.Test;
import primitives.*;

import geometries.Intersectable.GeoPoint;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class TriangleTest {
    //Z = 0 plane
    Triangle t1 = new Triangle(
            new Point3D(1,0,0),
            new Point3D(0,1,0),
            new Point3D(0,0,0)
    );

    //Y = 0 plane
    Triangle t2 = new Triangle(
            new Point3D(1,0,0),
            new Point3D(0,0,0),
            new Point3D(0,0,1)
    );

    //X = 0 plane
    Triangle t3 = new Triangle(
            new Point3D(0,0,0),
            new Point3D(0,1,0),
            new Point3D(0,0,1)
    );

    Triangle t4 = new Triangle(new Point3D(1,0,0), new Point3D(1,2,0), new Point3D(1,2,2));

    @Test
    void get_point1() {
        assertEquals(new Point3D(1,0,0), t1.get_point1());
    }

    @Test
    void get_point2() {
        assertEquals(new Point3D(0,1,0), t1.get_point2());
    }

    @Test
    void get_point3() {
        assertEquals(new Point3D(), t1.get_point3());
    }

    @Test
    void findIntersections() {
        //ray origin before triangle (no need more tests since it's kindda inheritted from plane interesections and we already tested that).
        Ray ray1 = new Ray(new Point3D(0, 1.5, 1), new Vector3D(1, 0, 0));
        ArrayList<Intersectable.GeoPoint> expected1 = new ArrayList<>();
        expected1.add(new GeoPoint(t4, new Point3D(1, 1.5, 1)));
        assertEquals(expected1, t4.findIntersections(ray1));

    }

    @Test
    void getCentroid() {
        Triangle triangle = new Triangle(
                new Point3D(0,0,0),
                new Point3D(3,9,0),
                new Point3D(12,0,0)
        );

        Point3D expected = new Point3D(5,3,0);
        Point3D actual = triangle.getCentroid();

        assertEquals(expected, actual);
    }

    @Test
    void isTriangle() {
        assertEquals(true, Triangle.isTriangle(t1.get_point1(), t1.get_point2(), t1.get_point3()));
        assertEquals(false, Triangle.isTriangle(new Point3D(0,0,0), new Point3D(0,1,0), new Point3D(0,2,0)));
    }

    @Test
    void equals1() {
        Triangle triangle1 = new Triangle(
                new Point3D(1,0,0),
                new Point3D(0,1,0),
                new Point3D(0,0,0)
        );

        assertEquals(true,  t1.equals(t1));
        assertEquals(false, t1.equals(t2));
        assertEquals(true,  t1.equals(triangle1));
    }

    @Test
    void translate() {
        Triangle actual = new Triangle(
                new Point3D(0,0,0),
                new Point3D(0,1,0),
                new Point3D(0,0,1)
        );

        actual.translate(1,2,3);

        Triangle expected = new Triangle(
                new Point3D(1,2,3),
                new Point3D(1,3,3),
                new Point3D(1,2,4)
        );

        assertEquals(actual, expected);
    }

    @Test
    void rotate() {
        Triangle actual = new Triangle(
                new Point3D(0,0,0),
                new Point3D(0,1,0),
                new Point3D(0,0,1)
        );

        actual.rotate(0,90,0);

        Triangle expected = new Triangle(
                new Point3D(0,0,0),
                new Point3D(0,1,0),
                new Point3D(-1,0,0)
        );

        assertEquals(actual, expected);
    }

    @Test
    void scale() {
        Triangle actual = new Triangle(
                new Point3D(0,0,0),
                new Point3D(0,1,0),
                new Point3D(0,0,1)
        );

        actual.scale(5,10,15);

        Triangle expected = new Triangle(
                new Point3D(0,0,0),
                new Point3D(0,10,0),
                new Point3D(0,0,15)
        );

        assertEquals(actual, expected);
    }

    @Test
    void scaleInPlace() {
        Triangle actual = new Triangle(
                new Point3D(0,0,0),
                new Point3D(0,1,0),
                new Point3D(0,0,1)
        );

        actual.scaleInPlace(5,10,15);

        Triangle expected = new Triangle(
                new Point3D(0,-3, -4.0-2/3.0),
                new Point3D(0, 7, -4.0-2/3.0),
                new Point3D(0,-3, 10.0+1/3.0)
        );

        assertEquals(expected, actual);
    }

    @Test
    void transform() {
        Vector3D translation = new Vector3D(10,0,0);
        Vector3D rotation = new Vector3D(0,90, 0);
        Vector3D scale = new Vector3D(5,10, 5);

        Transform transform = new Transform(translation, rotation, scale);

        Triangle actual = new Triangle(
                new Point3D(0,0,0),
                new Point3D(0,1,0),
                new Point3D(0,0,1)
        );

        actual.transform(transform);

        Triangle expected = new Triangle(
                new Point3D(10,0,0),
                new Point3D(10,10,0),
                new Point3D(5,0,0)
        );

        assertEquals(expected, actual);
    }

    @Test
    void transformTRS() {
        Vector3D translation = new Vector3D(10,0,0);
        Vector3D rotation = new Vector3D(0,90, 0);
        Vector3D scale = new Vector3D(5,10, 5);

        Triangle actual = new Triangle(
                new Point3D(0,0,0),
                new Point3D(0,1,0),
                new Point3D(0,0,1)
        );

        actual.transform(translation, rotation, scale);

        Triangle expected = new Triangle(
                new Point3D(10,0,0),
                new Point3D(10,10,0),
                new Point3D(5,0,0)
        );

        assertEquals(expected, actual);
    }


}