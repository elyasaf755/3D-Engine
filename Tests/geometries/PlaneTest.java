package geometries;

import org.junit.jupiter.api.Test;
import primitives.*;

import geometries.Intersectable.GeoPoint;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class PlaneTest {
    Plane plane1 = new Plane(new Point3D(1,0,0),
                             new Point3D(0,1,0),
                             new Point3D(0,0,1)
    );

    Plane plane2 = new Plane(
            new Point3D(1, 0, 0),
            new Point3D(1, 2, 1),
            new Point3D(1, 2, 2)
    );

    @Test
    void get_point() {
        assertEquals(new Point3D(new Coordinate(1), new Coordinate(0), new Coordinate(0)), plane1.get_point());
    }

    @Test
    void get_normal() {
        assertEquals(new Vector3D(new Point3D(new Coordinate(1), new Coordinate(1), new Coordinate(1))).normalized(), plane1.get_normal());
    }

    @Test
    void findIntersections() {
        //orthogonal and ray origin is before the plane
        Ray ray1 = new Ray(new Vector3D(1,0,0));
        ArrayList<GeoPoint> expected1 = new ArrayList<>();
        expected1.add(new GeoPoint(plane2, new Point3D(1, 0, 0)));
        assertEquals(expected1, plane2.findIntersections(ray1));


        //orthogonal and ray origin is in the plane
        Ray ray2 = new Ray(new Point3D(1,0,0), new Vector3D(1,0,0));
        assertEquals(new ArrayList<GeoPoint>(), plane2.findIntersections(ray2));

        //orthogonal and ray origin is after the plane
        Ray ray3 = new Ray(new Point3D(2,0,0), new Vector3D(1,0,0));
        assertEquals(new ArrayList<GeoPoint>(), plane2.findIntersections(ray3));

        //parllel and ray not included
        Ray ray4 = new Ray(new Vector3D(0,0,1));
        assertEquals(new ArrayList<GeoPoint>(), plane2.findIntersections(ray4));

        //parllel and ray included
        Ray ray5 = new Ray(new Point3D(1,0,0), new Vector3D(0,0,1));
        assertEquals(new ArrayList<GeoPoint>(), plane2.findIntersections(ray5));

    }

    @Test
    void distance() {
        //Parallel Planes
        //2x + 4y - 4z = 6
        Plane plane1 = new Plane(new Point3D(0,1.5,0), new Vector3D(2,4,-4));
        //x + 2y - 2z = -9
        Plane plane2 = new Plane(new Point3D(0,0,4.5), new Vector3D(1,2,-2));

        double expected = 4;
        double actual = plane1.distance(plane2);

        assertEquals(expected, actual);

        //Non-Paralel Planes
        //x=0
        Plane plane3 = new Plane(new Point3D(0,0,0), new Vector3D(1,0,0));
        double actua2 = plane1.distance(plane3);
        assertEquals(0, actua2);
    }

    @Test
    void translate() {
        Plane actual = new Plane(new Point3D(0,0,0), new Vector3D(1,0,0));
        actual.translate(5,0,0);
        Plane expected = new Plane(new Point3D(5,0,0), new Vector3D(1,0,0));
        assertEquals(expected, actual);
    }

    @Test
    void rotate() {
        Plane actual = new Plane(new Point3D(0,0,0), new Vector3D(1,0,0));
        actual.rotate(0,90,0);
        Plane expected = new Plane(new Point3D(0,0,0), new Vector3D(0,0,1));
        assertEquals(expected, actual);
    }

    @Test
    void scale() {
        Plane actual = new Plane(new Point3D(0,0,0), new Vector3D(1,0,0));
        actual.scale(90,90,90);
        Plane expected = new Plane(new Point3D(0,0,0), new Vector3D(1,0,0));
        assertEquals(expected, actual);
    }

    @Test
    void transform() {
        Vector3D translation = new Vector3D(10,0,0);
        Vector3D rotation = new Vector3D(0,90, 0);
        Vector3D scale = new Vector3D(5,10, 5);

        Transform transform = new Transform(translation, rotation, scale);

        Plane actual = new Plane(new Point3D(0,0,0), new Vector3D(1,0,0));
        actual.transform(transform);
        Plane expected = new Plane(new Point3D(10,0,0), new Vector3D(0,0,1));
        assertEquals(expected, actual);
    }

    @Test
    void transformTRS() {
        Vector3D translation = new Vector3D(10,0,0);
        Vector3D rotation = new Vector3D(0,90, 0);
        Vector3D scale = new Vector3D(5,10, 5);

        Plane actual = new Plane(new Point3D(0,0,0), new Vector3D(1,0,0));
        actual.transform(translation, rotation, scale);
        Plane expected = new Plane(new Point3D(10,0,0), new Vector3D(0,0,1));
        assertEquals(expected, actual);
    }

    @Test
    void contains() {
        Plane plane1 = new Plane(new Point3D(0,0,1), new Vector3D(0,0,1));
        Point3D point1 = new Point3D(1,1,1);
        assertEquals(true, plane1.contains(point1));
        assertEquals(false, plane1.contains(new Point3D(0,0,0)));
    }
}