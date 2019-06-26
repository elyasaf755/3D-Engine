package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point3D;

import static org.junit.jupiter.api.Assertions.*;

class RectangleTest {

    @Test
    void get_normal() {
    }

    @Test
    void contains() {
    }

    @Test
    void surfaceContains() {
        //Default orientation
        Rectangle rectangle1 = new Rectangle();
        Point3D point1 = new Point3D(2,2,0);
        assertTrue(rectangle1.surfaceContains(point1));
    }

    @Test
    void findIntersections() {
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
    void scale1() {
    }

    @Test
    void transform() {
    }

    @Test
    void transform1() {
    }
}