package renderer;

import elements.AmbientLight;
import elements.Camera;
import geometries.Sphere;
import geometries.Triangle;
import org.junit.jupiter.api.Test;
import primitives.Color;
import primitives.Point3D;
import primitives.Vector3D;
import scene.Scene;

class RenderTest {

    @Test
    void renderImage() {
        Scene scene = new Scene("renderTest");
        scene.set_background(new Color(75, 127,190));
        scene.set_ambientLight(new AmbientLight(new Color(255,255,255)));
        scene.set_camera(new Camera(new Point3D(0,0,0), new Vector3D(1,0,0), new Vector3D(0,0,1), new Vector3D(0,1,0)), 100);

        scene.set_background(new Color(java.awt.Color.BLACK));

        //Center sphere
        Sphere sphere = new Sphere(45, new Point3D(100, 0, 0));

        //Upper right
        Triangle triangle1 = new Triangle(
                new Point3D(100, -100, 100),
                new Point3D(100, -100, 0),
                new Point3D(100, 0, 100));

        //Upper left
        Triangle triangle2 = new Triangle(
                new Point3D(100, 100, 100),
                new Point3D(100, 0, 100),
                new Point3D(100, 100, 0));

        //Lower left
        Triangle triangle3 = new Triangle(
                new Point3D(100, 100, 0),
                new Point3D(100, 100, -100),
                new Point3D(100, 0, -100));

        //Lower right
        Triangle triangle4 = new Triangle(
                new Point3D(100, -100, 0),
                new Point3D(100, 0, -100),
                new Point3D(100, -100, -100));

        scene.addGeometries(sphere, triangle1, triangle2, triangle3, triangle4);

        ImageWriter imageWriter1 = new ImageWriter("1stRenderTest", 500, 500, 500, 500);

        Render render = new Render(scene, imageWriter1);

        render.renderImage();
        render.printGrid(50, java.awt.Color.WHITE);

        render.writeToImage();

        ImageWriter imageWriter2 = new ImageWriter("2ndRenderTest", 500, 500, 500, 500);

        render = new Render(scene, imageWriter2);

        sphere.set_emission(java.awt.Color.CYAN);
        triangle1.set_emission(java.awt.Color.RED);
        triangle2.set_emission(java.awt.Color.GREEN);
        triangle3.set_emission(java.awt.Color.BLUE);
        triangle4.set_emission(java.awt.Color.YELLOW);

        render.renderImage();

    }

    @Test
    void printGrid() {
        //TODO:Implement printGrid(int interval)
    }

    @Test
    void printGrid1() {
        //TODO:Implement printGrid(int interval, java.awt.Color color)
    }
}