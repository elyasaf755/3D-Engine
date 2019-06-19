package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector3D;

import static geometries.Intersectable.GeoPoint;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ConeTest {

    @Test
    void get_normal() {
    }

    @Test
    void findIntersections() {
        Cone cone1 = new Cone(4, new Ray(new Point3D(), new Vector3D(0,0,1)), 30);
        Ray ray1  = new Ray(new Point3D(0, -20, 0), new Vector3D(0,0,1));
        ArrayList<GeoPoint> actual1 = cone1.findIntersections(ray1);
        ArrayList<GeoPoint> expected1 = new ArrayList<>();
        expected1.add(new GeoPoint(cone1, new Point3D(0, -20, 20)));
        assertEquals(expected1, actual1);

        Cone cone2 = new Cone(4, new Ray(new Point3D(0,0,1), new Vector3D(0,0,1)), 30);
        Ray ray2  = new Ray(new Point3D(0, -10, 1), new Vector3D(0,1,0));
        ArrayList<GeoPoint> actual2 = cone2.findIntersections(ray2);
        ArrayList<GeoPoint> expected2 = new ArrayList<>();
        expected2.add(new GeoPoint(cone2, new Point3D(0, 0, 1)));
        assertEquals(expected2, actual2);
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
}