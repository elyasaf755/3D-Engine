package geometries;

import org.junit.jupiter.api.Test;
import primitives.Coordinate;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector3D;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class SphereTest {

    Sphere sphere = new Sphere(4, new Point3D(new Coordinate(1), new Coordinate(1), new Coordinate(1)));
    Sphere sphere2 = new Sphere(4, new Point3D(new Coordinate(0), new Coordinate(0), new Coordinate(0)));

    Ray ray = new Ray(new Point3D(new Coordinate(0), new Coordinate(0), new Coordinate(0)), new Vector3D(new Coordinate(0), new Coordinate(0), new Coordinate(1)));
    Ray ray2 = new Ray(new Point3D(new Coordinate(0), new Coordinate(0), new Coordinate(4)), new Vector3D(new Coordinate(0), new Coordinate(0), new Coordinate(1)));
    Ray ray3 = new Ray(new Point3D(new Coordinate(0), new Coordinate(0), new Coordinate(-4)), new Vector3D(new Coordinate(0), new Coordinate(0), new Coordinate(1)));
    Ray ray4 = new Ray(new Point3D(new Coordinate(0), new Coordinate(0), new Coordinate(-6)), new Vector3D(new Coordinate(0), new Coordinate(0), new Coordinate(1)));
    Ray ray5 = new Ray(new Point3D(new Coordinate(0), new Coordinate(0), new Coordinate(6)), new Vector3D(new Coordinate(0), new Coordinate(0), new Coordinate(1)));
    Ray ray6 = new Ray(new Point3D(new Coordinate(0), new Coordinate(-4), new Coordinate(0)), new Vector3D(new Coordinate(1), new Coordinate(1), new Coordinate(0)));
    Ray ray7 = new Ray(new Point3D(new Coordinate(0), new Coordinate(-4), new Coordinate(0)), new Vector3D(new Coordinate(-1), new Coordinate(-1), new Coordinate(0)));
    Ray ray8 = new Ray(new Point3D(new Coordinate(4), new Coordinate(0), new Coordinate(0)), new Vector3D(new Coordinate(0), new Coordinate(1), new Coordinate(0)));
    Ray ray9 = new Ray(new Point3D(new Coordinate(4), new Coordinate(-1), new Coordinate(0)), new Vector3D(new Coordinate(0), new Coordinate(1), new Coordinate(0)));
    Ray ray10 = new Ray(new Point3D(new Coordinate(4), new Coordinate(1), new Coordinate(0)), new Vector3D(new Coordinate(0), new Coordinate(1), new Coordinate(0)));
    Ray ray11 = new Ray(new Point3D(new Coordinate(5), new Coordinate(1), new Coordinate(0)), new Vector3D(new Coordinate(0), new Coordinate(1), new Coordinate(0)));

    @Test
    void get_point() {
        assertEquals(new Point3D(new Coordinate(1), new Coordinate(1), new Coordinate(1)), sphere.get_point());
    }

    @Test
    void rayIntersectionPoints() {
        ArrayList<Point3D> intercestions = new ArrayList<Point3D>();
        intercestions.add(new Point3D(new Coordinate(0), new Coordinate(0), new Coordinate(4)));

        assertEquals(intercestions, sphere2.findIntersections(ray));//from center to outter shell

        assertEquals(new ArrayList<Point3D>(), sphere2.findIntersections(ray2));//from outter shell towards out

        assertEquals(intercestions, sphere2.findIntersections(ray3));//from outter shell towards in through center

        ArrayList<Point3D> intercestions4 = new ArrayList<Point3D>();
        intercestions4.add(new Point3D(new Coordinate(0), new Coordinate(0), new Coordinate(-4)));
        intercestions4.add(new Point3D(new Coordinate(0), new Coordinate(0), new Coordinate(4)));

        assertEquals(intercestions4, sphere2.findIntersections(ray4));//from out towards in through center

        assertEquals(new ArrayList<Point3D>(), sphere2.findIntersections(ray5));//from out towards out

        ArrayList<Point3D> intercestions6 = new ArrayList<Point3D>();
        intercestions6.add(new Point3D(new Coordinate(4), new Coordinate(0), new Coordinate(0)));
        assertEquals(intercestions6, sphere2.findIntersections(ray6));

        assertEquals(new ArrayList<Point3D>(), sphere2.findIntersections(ray7));

        assertEquals(new ArrayList<Point3D>(), sphere2.findIntersections(ray8));

        assertEquals(intercestions6, sphere2.findIntersections(ray9));

        assertEquals(new ArrayList<Point3D>(), sphere2.findIntersections(ray10));

        assertEquals(new ArrayList<Point3D>(), sphere2.findIntersections(ray11));


    }

    @Test
    void get_normal() {
        assertEquals(new Vector3D(new Coordinate(1),new Coordinate(0),new Coordinate(0)),sphere2.get_normal(new Point3D(new Coordinate(4),new Coordinate(0),new Coordinate(0))));
    }
}