package elements;

import org.junit.jupiter.api.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector3D;

import static org.junit.jupiter.api.Assertions.*;

class CameraTest {

    @Test
    void constructRayThroughPixel() {
        Vector3D up = new Vector3D(0, 1, 0);
        Vector3D to = new Vector3D(0, 0, -1);
        Vector3D right = up.crossProduct(to);

        Camera c = new Camera(new Point3D(0,0,0), up, to, right);

        Ray ray = c.constructRayThroughPixel(3, 3, 3, 3, 100, 150, 150);
        Point3D centerPoint = new Point3D(-100, -100, -100);
        Vector3D direction = new Vector3D(-0.5773502691896257, -0.5773502691896257, -0.5773502691896257);

        //x is negative why?
        Ray answer = new Ray(centerPoint, direction);
        assertEquals(answer, ray);
        assertEquals(answer.get_point(), ray.get_point());
    }
}