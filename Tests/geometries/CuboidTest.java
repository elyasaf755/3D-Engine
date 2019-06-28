package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector3D;

import static geometries.Intersectable.GeoPoint;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class CuboidTest {

    @Test
    void get_ray() {
    }

    @Test
    void get_width() {
    }

    @Test
    void get_length() {
    }

    @Test
    void get_height() {
    }

    @Test
    void get_normal() {
        //Cuboid oriented along the default z axis
        Cuboid cuboid1 = new Cuboid(4, 4, 4);
        Point3D point1 = new Point3D(0,0,2);
        Vector3D actual1 = cuboid1.get_normal(point1);
        Vector3D expected1 = new Vector3D(0,0,1);
        assertEquals(expected1, actual1);

        Cuboid cuboid2 = new Cuboid(4, 4, 4, new Ray(new Vector3D(0,1,0)));
        Point3D point2 = new Point3D(0,2,0);
        Vector3D actual2 = cuboid2.get_normal(point2);
        Vector3D expected2 = new Vector3D(0,1,0);
        assertEquals(expected2, actual2);
    }

    @Test
    void contains() {
        //Cuboid oriented along the default z axis
        Cuboid cuboid1 = new Cuboid(4, 4, 4);
        Point3D point1 = new Point3D(0,0,0);
        assertEquals(true, cuboid1.contains(point1));

        Point3D point2 = new Point3D(0,0,2);
        assertEquals(true, cuboid1.contains(point2));

        Point3D point3 = new Point3D(0,0,-2);
        assertEquals(true, cuboid1.contains(point3));

        Point3D point4 = new Point3D(0,2,0);
        assertEquals(true, cuboid1.contains(point4));

        Point3D point5 = new Point3D(0,-2,0);
        assertEquals(true, cuboid1.contains(point5));

        Point3D point6 = new Point3D(2,0,0);
        assertEquals(true, cuboid1.contains(point6));

        Point3D point7 = new Point3D(-2,0,0);
        assertEquals(true, cuboid1.contains(point7));

        Point3D point8 = new Point3D(-2,-2,-2);
        assertEquals(true, cuboid1.contains(point8));

        Point3D point9 = new Point3D(-2,-2,-3);
        assertEquals(false, cuboid1.contains(point9));


        //Arbitary orientation
        Cuboid cuboid2 = new Cuboid(4, 5, 7, new Ray(new Point3D(-2,-2,-2), new Vector3D(1,1,1)));
        Point3D point21 = new Point3D(-1,-1,-1);
        assertEquals(true, cuboid2.contains(point21));

        Cuboid cuboid3 = new Cuboid(4, 5, 6, new Ray(new Point3D(-2,-2,-2), new Vector3D(1,1,1)));
        Point3D point31 = new Point3D(0,0,0);
        assertEquals(false, cuboid3.contains(point31));

        Cuboid cuboid4 = new Cuboid(4, 5, 6, new Ray(new Point3D(0,0,0), new Vector3D(1,1,1)));
        Point3D point41 = new Point3D(2.5,2.5,2.5);
        assertEquals(false, cuboid4.contains(point41));

        Cuboid cuboid5 = new Cuboid(4, 5, 6, new Ray(new Point3D(-2,-2,-2), new Vector3D(0,0,1)));
        Point3D point51 = new Point3D(-2,-2,3.5);
        assertEquals(false, cuboid5.contains(point51));
    }

    @Test
    void surfaceContains() {
    }

    @Test
    void findIntersections() {
        //Aligned with Y axis, default.
        Cuboid cuboid1 = new Cuboid(4,4,4);
        Ray ray1 = new Ray(new Point3D(-3,0,0), new Vector3D(1,0,0));
        ArrayList<GeoPoint> actual1 = cuboid1.findIntersections(ray1);
        ArrayList<GeoPoint> expected1 = new ArrayList<>();
        expected1.add(new GeoPoint(cuboid1, new Point3D(2,0,0)));
        expected1.add(new GeoPoint(cuboid1, new Point3D(-2,0,0)));
        assertEquals(expected1.get(0).point, actual1.get(0).point);
        assertEquals(expected1.get(1).point, actual1.get(1).point);

        //Aligned with Y axis but have different dimensions
        Cuboid cuboid2 = new Cuboid(2,4,3);
        Ray ray2 = new Ray(new Point3D(0,100,0), new Vector3D(0,-1,0));
        ArrayList<GeoPoint> actual2 = cuboid2.findIntersections(ray2);
        ArrayList<GeoPoint> expected2 = new ArrayList<>();
        expected2.add(new GeoPoint(cuboid1, new Point3D(0,2,0)));
        assertEquals(expected2.get(0).point, actual2.get(0).point);

        Cuboid cuboid3 = new Cuboid(2,4,3, new Ray(new Point3D(1,1,1), new Vector3D(0,0,1)));
        Ray ray3 = new Ray(new Point3D(0,100,0), new Vector3D(0,-1,0));
        ArrayList<GeoPoint> actual3 = cuboid3.findIntersections(ray3);
        ArrayList<GeoPoint> expected3 = new ArrayList<>();
        expected3.add(new GeoPoint(cuboid1, new Point3D(0,3,0)));
        assertEquals(expected3.get(0).point, actual3.get(0).point);

        Ray ray = new Ray(new Point3D(0,0,-200), Vector3D.zAxis);
        ArrayList<GeoPoint> interesections = cuboid1.findIntersections(ray);
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