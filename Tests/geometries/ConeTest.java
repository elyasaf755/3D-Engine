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
        Cone cone = new Cone(4, new Point3D(), new Vector3D(0,0,1));
        Ray ray  = new Ray(new Point3D(0, -10, 0), new Vector3D(0,1,0));
        ArrayList<GeoPoint> actual = cone.findIntersections(ray);
        ArrayList<GeoPoint> expected = new ArrayList<>();
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