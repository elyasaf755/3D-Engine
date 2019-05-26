package geometries;

import org.junit.jupiter.api.Test;
import primitives.Coordinate;
import primitives.Point3D;

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
        //TODO: Implement
    }
}