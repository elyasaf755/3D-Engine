package geometries;

import org.junit.jupiter.api.Test;
import primitives.Coordinate;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector3D;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class GeometriesTest {
    //x^2 + y^2 + z^2 = 16
    Sphere sphere1 = new Sphere(4, new Point3D(new Coordinate(0), new Coordinate(0), new Coordinate(0)));

    //(x-8)^2 + y^2 + z^2 = 16
    Sphere sphere2 = new Sphere(4, new Point3D(new Coordinate(8), new Coordinate(0), new Coordinate(0)));

    //(x-9)^2 + y^2 + z^2 = 16
    Sphere sphere3 = new Sphere(4, new Point3D(new Coordinate(9), new Coordinate(0), new Coordinate(0)));

    @Test
    void add_geometry() {
        Geometries geometries = new Geometries();
        geometries.add_geometry(sphere1, sphere2);
        assertEquals(2, geometries.get_GeometriesList().size());
        assertEquals(sphere1, geometries.get_GeometriesList().get(0));
        assertEquals(sphere2, geometries.get_GeometriesList().get(1));
    }

    @Test
    void findIntersections() {
        Ray ray = new Ray(new Point3D(new Coordinate(-6), new Coordinate(0), new Coordinate(0)), new Vector3D(new Coordinate(1), new Coordinate(0), new Coordinate(0)));

        //the 2 geometries have a point of contact
        Geometries geometries1 = new Geometries();
        geometries1.add_geometry(sphere1, sphere2);
        ArrayList<Point3D> intercestions1 = new ArrayList<Point3D>();
        intercestions1.add(new Point3D(new Coordinate(-4), new Coordinate(0), new Coordinate(0)));
        intercestions1.add(new Point3D(new Coordinate(4), new Coordinate(0), new Coordinate(0)));
        intercestions1.add(new Point3D(new Coordinate(12), new Coordinate(0), new Coordinate(0)));
        assertEquals(intercestions1, geometries1.findIntersections(ray));

        //the 2 geometries doesnt have a point of contact
        Geometries geometries2 = new Geometries();
        geometries2.add_geometry(sphere1, sphere3);
        ArrayList<Point3D> intercestions2 = new ArrayList<Point3D>();
        intercestions2.add(new Point3D(new Coordinate(-4), new Coordinate(0), new Coordinate(0)));
        intercestions2.add(new Point3D(new Coordinate(4), new Coordinate(0), new Coordinate(0)));
        intercestions2.add(new Point3D(new Coordinate(5), new Coordinate(0), new Coordinate(0)));
        intercestions2.add(new Point3D(new Coordinate(13), new Coordinate(0), new Coordinate(0)));
        assertEquals(intercestions2, geometries2.findIntersections(ray));
    }
}