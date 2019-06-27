package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector3D;

import static org.junit.jupiter.api.Assertions.*;

class AABBTest {

    @Test
    void get_min() {
    }

    @Test
    void get_max() {
    }

    @Test
    void overlaps() {
        Point3D min1 = new Point3D(-2,-2,-2);
        Point3D max1 = new Point3D(2,2,2);
        AABB aabb1 = new AABB(min1, max1);

        Point3D min2 = new Point3D(-4,-4,-4);
        Point3D max2 = new Point3D(0,0,0);
        AABB aabb2 = new AABB(min2, max2);

        assertTrue(aabb1.overlaps(aabb2));

        Point3D min3 = new Point3D(-6,-6,-6);
        Point3D max3 = new Point3D(-2,-2,-2);
        AABB aabb3 = new AABB(min3, max3);

        assertTrue(aabb1.overlaps(aabb3));

        Point3D min4 = new Point3D(-7,-7,-7);
        Point3D max4 = new Point3D(-3,-3,-3);
        AABB aabb4 = new AABB(min4, max4);

        assertFalse(aabb1.overlaps(aabb4));
    }

    @Test
    void contains() {
        Point3D min1 = new Point3D(-2,-2,-2);
        Point3D max1 = new Point3D(2,2,2);
        AABB aabb1 = new AABB(min1, max1);

        Point3D min2 = new Point3D(-1,-1,-1);
        Point3D max2 = new Point3D(1,1,1);
        AABB aabb2 = new AABB(min2, max2);

        assertTrue(aabb1.contains(aabb2));
        assertFalse(aabb2.contains(aabb1));
    }

    @Test
    void mergeWith() {
        Point3D min1 = new Point3D(-2,-2,-2);
        Point3D max1 = new Point3D(2,2,2);
        AABB aabb1 = new AABB(min1, max1);

        Point3D min2 = new Point3D(-7,-7,-7);
        Point3D max2 = new Point3D(-3,-3,-3);
        AABB aabb2 = new AABB(min2, max2);

        AABB actual1 = aabb1.mergeWith(aabb2);
        AABB expected1 = new AABB(min2, max1);
        assertEquals(actual1, expected1);
    }

    @Test
    void intersects() {
        Point3D min1 = new Point3D(-2,-2,-2);
        Point3D max1 = new Point3D(2,2,2);
        AABB aabb1 = new AABB(min1, max1);
        Ray ray1 = new Ray(new Vector3D(1,0,0));
        assertTrue(aabb1.intersects(ray1));

        Ray ray2 = new Ray(new Point3D(-2,-2,-2), new Vector3D(0,1,0));
        assertTrue(aabb1.intersects(ray2));

        Ray ray3 = new Ray(new Point3D(0,0,-3), new Vector3D(0,1,0));
        assertFalse(aabb1.intersects(ray3));
    }

    @Test
    void getWidth() {
    }

    @Test
    void getHeight() {
    }

    @Test
    void getDepth() {
    }


}