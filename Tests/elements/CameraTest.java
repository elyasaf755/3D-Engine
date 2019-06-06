package elements;

import org.junit.jupiter.api.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector3D;

import static org.junit.jupiter.api.Assertions.*;

class CameraTest {

    @Test
    void constructRayThroughPixel() {
        //3x3 view plane
        Vector3D direction = new Vector3D(1, 0, 0);
        Vector3D up = new Vector3D(0, 1, 0);
        Vector3D right = direction.crossProduct(up);

        Camera camera = new Camera(new Point3D(), direction, up , right);

        Ray actual = camera.constructRayThroughPixel(3, 3, 0, 0, 100, 150, 150);
        Ray expected = new Ray(new Vector3D(100, 25, -25).normalized());
        assertEquals(expected, actual);

        //Constructors check BTW
        camera = new Camera(direction, up, right);
        actual = camera.constructRayThroughPixel(3, 3, 0, 0, 100, 150, 150);
        assertEquals(expected, actual);

        camera = new Camera(new Point3D(), direction, up);
        actual = camera.constructRayThroughPixel(3, 3, 0, 0, 100, 150, 150);
        assertEquals(expected, actual);

        camera = new Camera(direction, up);
        actual = camera.constructRayThroughPixel(3, 3, 0, 0, 100, 150, 150);
        assertEquals(expected, actual);

        camera = new Camera(new Point3D(), direction);
        actual = camera.constructRayThroughPixel(3, 3, 0, 0, 100, 150, 150);
        assertEquals(expected, actual);

        camera = new Camera(direction);
        actual = camera.constructRayThroughPixel(3, 3, 0, 0, 100, 150, 150);
        assertEquals(expected, actual);

        /************************************************/

        Vector3D direction1 = new Vector3D(1, 0, 0);

        Camera camera1 = new Camera(direction1);

        Ray actual1 = camera1.constructRayThroughPixel(3, 3, 0, 0, 100, 150, 150);
        Ray expected1 = new Ray(new Vector3D(100, 25, -25).normalized());
        assertEquals(expected1, actual1);

        //Different pixel positions check
        actual1 = camera1.constructRayThroughPixel(3, 3, 0, 1, 100, 150, 150);
        expected1 = new Ray(new Vector3D(100, -25, -25).normalized());
        assertEquals(expected1, actual1);

        actual1 = camera1.constructRayThroughPixel(3, 3, 0, 2, 100, 150, 150);
        expected1 = new Ray(new Vector3D(100, -75, -25).normalized());
        assertEquals(expected1, actual1);

        actual1 = camera1.constructRayThroughPixel(3, 3, 1, 0, 100, 150, 150);
        expected1 = new Ray(new Vector3D(100, 25, 25).normalized());
        assertEquals(expected1, actual1);

        actual1 = camera1.constructRayThroughPixel(3, 3, 1, 1, 100, 150, 150);
        expected1 = new Ray(new Vector3D(100, -25, 25).normalized());
        assertEquals(expected1, actual1);

        actual1 = camera1.constructRayThroughPixel(3, 3, 1, 2, 100, 150, 150);
        expected1 = new Ray(new Vector3D(100, -75, 25).normalized());
        assertEquals(expected1, actual1);

        actual1 = camera1.constructRayThroughPixel(3, 3, 2, 0, 100, 150, 150);
        expected1 = new Ray(new Vector3D(100, 25, 75).normalized());
        assertEquals(expected1, actual1);

        actual1 = camera1.constructRayThroughPixel(3, 3, 2, 1, 100, 150, 150);
        expected1 = new Ray(new Vector3D(100, -25, 75).normalized());
        assertEquals(expected1, actual1);

        actual1 = camera1.constructRayThroughPixel(3, 3, 2, 2, 100, 150, 150);
        expected1 = new Ray(new Vector3D(100, -75, 75).normalized());
        assertEquals(expected1, actual1);

        /************************************************/

        //4x4 view plane
        Vector3D direction2 = new Vector3D(1, 0, 0);

        Camera camera2 = new Camera(new Point3D(), direction2);

        Ray actual2 = camera2.constructRayThroughPixel(4, 4, 0, 0, 100, 200, 200);
        Ray expected2 = new Ray(new Vector3D(100, 75, -75).normalized());
        assertEquals(expected2, actual2);

        //garbage
        camera2 = new Camera(direction2);
        actual2 = camera2.constructRayThroughPixel(3, 3, 0, 0, 100, 150, 150);
        expected2 = new Ray(new Vector3D(100, 25, -25).normalized());
        assertEquals(expected2, actual2);
    }
}