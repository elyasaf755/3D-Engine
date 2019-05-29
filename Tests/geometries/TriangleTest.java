package geometries;

import org.junit.jupiter.api.Test;
import primitives.*;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class TriangleTest {
    //Z = 0 plane
    Triangle t1 = new Triangle(new Point3D(new Coordinate(1), new Coordinate(0), new Coordinate(0)),
            new Point3D(new Coordinate(0), new Coordinate(1), new Coordinate(0)),
            new Point3D(new Coordinate(0), new Coordinate(0), new Coordinate(0)));

    //Y = 0 plane
    Triangle t2 = new Triangle(new Point3D(new Coordinate(1), new Coordinate(0), new Coordinate(0)),
            new Point3D(new Coordinate(0), new Coordinate(0), new Coordinate(0)),
            new Point3D(new Coordinate(0), new Coordinate(0), new Coordinate(1)));

    //X = 0 plane
    Triangle t3 = new Triangle(new Point3D(new Coordinate(0), new Coordinate(0), new Coordinate(0)),
            new Point3D(new Coordinate(0), new Coordinate(1), new Coordinate(0)),
            new Point3D(new Coordinate(0), new Coordinate(0), new Coordinate(1)));

    Triangle t4 = new Triangle(new Point3D(1,0,0), new Point3D(1,2,0), new Point3D(1,2,2));

    @Test
    void get_point1() {
        assertEquals(new Point3D(new Coordinate(1), new Coordinate(0), new Coordinate(0)), t1.get_point1());
    }

    @Test
    void get_point2() {
        assertEquals(new Point3D(new Coordinate(0), new Coordinate(1), new Coordinate(0)), t1.get_point2());
    }

    @Test
    void get_point3() {
        assertEquals(new Point3D(new Coordinate(0), new Coordinate(0), new Coordinate(0)), t1.get_point3());
    }

    @Test
    void findIntersections() {
        //ray origin before triangle (no need more tests since it's kindda inheritted from plane interesections and we already tested that).
        Ray ray1 = new Ray(new Point3D(0, 1.5, 1), new Vector3D(1, 0, 0));
        ArrayList<Point3D> expected1 = new ArrayList<Point3D>();
        expected1.add(new Point3D(1, 1.5, 1));
        assertEquals(expected1, t4.findIntersections(ray1));

    }
}