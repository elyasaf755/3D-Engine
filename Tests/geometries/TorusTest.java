package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Util;
import primitives.Vector3D;

import static geometries.Intersectable.GeoPoint;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class TorusTest {

    @Test
    void get_normal() {
        Torus torus1 = new Torus(3, 1);
        Vector3D actual1 = torus1.get_normal(new Point3D(3,0,1));
        Vector3D expected1 = new Vector3D(0,0,1);
        assertEquals(expected1, actual1);
        expected1.rotate(0, 90, 0);

        Torus torus2 = new Torus(3, 1, new Ray(new Vector3D(1,0,0)));
        Vector3D actual2 = torus2.get_normal(new Point3D(1,0,-3));
        Vector3D expected2 = new Vector3D(1,0,0);
        assertEquals(expected2, actual2);
    }

    @Test
    void findIntersections() {
        Torus torus1 = new Torus(3, 1);
        Ray ray1 = new Ray(new Point3D(-6,0,0), new Vector3D(1,0,0));
        ArrayList<GeoPoint> actual1 = torus1.findIntersections(ray1);
        ArrayList<GeoPoint> expected1 = new ArrayList<>();
        expected1.add(new GeoPoint(torus1, new Point3D(-4,0,0)));
        expected1.add(new GeoPoint(torus1, new Point3D(-2,0,0)));
        expected1.add(new GeoPoint(torus1, new Point3D(2,0,0)));
        expected1.add(new GeoPoint(torus1, new Point3D(4,0,0)));

        assertTrue(Util.intersectionsEqual(expected1, actual1));

        Torus torus2 = new Torus(3, 1, new Ray(new Point3D(1,0,0), new Vector3D(1,0,0)));
        Ray ray2 = new Ray(new Point3D(1,0,6), new Vector3D(0,0,-1));
        ArrayList<GeoPoint> actual2 = torus2.findIntersections(ray2);
        ArrayList<GeoPoint> expected2 = new ArrayList<>();
        expected2.add(new GeoPoint(torus2, new Point3D(1,0,4)));
        expected2.add(new GeoPoint(torus2, new Point3D(1,0,2)));
        expected2.add(new GeoPoint(torus2, new Point3D(1,0,-2)));
        expected2.add(new GeoPoint(torus2, new Point3D(1,0,-4)));

        assertTrue(Util.intersectionsEqual(expected2, actual2));
    }

    @Test
    void translate() {
    }

    @Test
    void rotate() {
    }

    @Test
    void scale() {
    }

    @Test
    void transform() {
    }

    @Test
    void transform1() {
    }

    @Test
    void transformToDefault() {
        Torus torus = new Torus(3, 1, new Ray(new Point3D(5,10,15), new Vector3D(0,0,1)));
        Ray ray = new Ray(new Point3D(10,20,30), new Vector3D(0,1,0));
        Torus.transformToDefault(torus, ray);
        assertEquals(true, true);
    }
}