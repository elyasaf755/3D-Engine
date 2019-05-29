package geometries;


import org.junit.jupiter.api.Test;
import primitives.Coordinate;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector3D;

import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

class GeometriesTest {
    Geometries geometries=new Geometries();
    Sphere sphere = new Sphere(4, new Point3D(new Coordinate(0), new Coordinate(0), new Coordinate(1)));
    Sphere sphere2 = new Sphere(4, new Point3D(new Coordinate(0), new Coordinate(0), new Coordinate(0)));
    Ray ray = new Ray(new Point3D(new Coordinate(0), new Coordinate(0), new Coordinate(-6)), new Vector3D(new Coordinate(0), new Coordinate(0), new Coordinate(1)));
    ArrayList<Point3D> intercestions = new ArrayList<Point3D>();


    @org.junit.jupiter.api.Test
    void add_geometry() {
    }

    @org.junit.jupiter.api.Test
    void findIntersections() {
        geometries.add_geometry(sphere, sphere2);
        intercestions.add(new Point3D(new Coordinate(0), new Coordinate(0), new Coordinate(4)));
        intercestions.add(new Point3D(new Coordinate(0), new Coordinate(0), new Coordinate(5)));
        assertEquals(intercestions, geometries.findIntersections(ray));
    }
}