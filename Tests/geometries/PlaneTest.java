package geometries;

import org.junit.jupiter.api.Test;
import primitives.Coordinate;
import primitives.Point3D;
import primitives.Vector3D;

import static org.junit.jupiter.api.Assertions.*;

class PlaneTest {
    Plane plane1 = new Plane(new Point3D(new Coordinate(1), new Coordinate(0), new Coordinate(0)),
            new Point3D(new Coordinate(0), new Coordinate(1), new Coordinate(0)),
            new Point3D(new Coordinate(0), new Coordinate(0), new Coordinate(1)));
    @Test
    void get_point() {
        assertEquals(new Point3D(new Coordinate(1), new Coordinate(0), new Coordinate(0)), plane1.get_point());
    }

    @Test
    void get_normal() {
        assertEquals(new Vector3D(new Point3D(new Coordinate(1), new Coordinate(1), new Coordinate(1))).normalized(), plane1.get_normal());
    }
}