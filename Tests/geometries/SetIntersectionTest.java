package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector3D;

import java.util.ArrayList;

import static geometries.Intersectable.GeoPoint;

import static org.junit.jupiter.api.Assertions.*;

class SetIntersectionTest {

    @Test
    void findIntersections() {
        Sphere sphere1 = new Sphere(4, new Point3D());
        Sphere sphere2 = new Sphere(4, new Point3D(8,0,0));
        Sphere sphere3 = new Sphere(4, new Point3D(4,4,0));
        SetIntersection setIntersection1 = new SetIntersection(sphere1, sphere2);
        Ray ray1 = new Ray(new Point3D(-6, 0, 0), new Vector3D(1,0,0));
        ArrayList<GeoPoint> actual1 = setIntersection1.findIntersections(ray1);
        ArrayList<GeoPoint> expected1 = new ArrayList<>();
        expected1.add(new GeoPoint(sphere1, new Point3D(4,0,0)));
        assertEquals(expected1, actual1);
    }
}