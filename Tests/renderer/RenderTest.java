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
        Scene scene = new Scene("renderTest1");
        scene.set_background(new Color(75, 127,190));
        scene.set_ambientLight(new AmbientLight(new Color(255.0,255.0,255.0)));
        scene.set_camera(new Camera(new Point3D(0,0,0), new Vector3D(1,0,0), new Vector3D(0,0,1), new Vector3D(0,1,0)), 100);

        scene.set_background(new Color(java.awt.Color.BLACK));

        scene.addGeometries(new Sphere(45, new Point3D(100, 0, 0)));


        /*
        //lower left triangle
        Triangle triangle1 = new Triangle(
                new Point3D(98, 0, -49),
                new Point3D(0, 98, -49),
                new Point3D(98, 98, -49));

         */

        //Upper right
        Triangle triangle1 = new Triangle(
                new Point3D(100, -250, 250),
                new Point3D(100, -250, 0),
                new Point3D(100, 0, 250));

        //Upper left
        Triangle triangle2 = new Triangle(
                new Point3D(100, 250, 250),
                new Point3D(100, 0, 250),
                new Point3D(100, 250, 0));

        Triangle triangle3 = new Triangle(
                new Point3D(100, 0, 0),
                new Point3D(100, 10, 10),
                new Point3D(100, 0, 10));

/*
        //unknown
        Triangle triangle3 = new Triangle(
                new Point3D(200, 0, -49),
                new Point3D(0, -200, -49),
                new Point3D(200, -200, -49));


        //upper right triangle
        Triangle triangle4 = new Triangle(
                new Point3D(-98, 0, -49),
                new Point3D(0, -98, -49),
                new Point3D(-98, -98, -49));
*/
        //scene.addGeometries(triangle1, triangle2, triangle3, triangle4);
        scene.addGeometries(triangle1, triangle2, triangle3);

        ImageWriter imageWriter = new ImageWriter("1stRenderTest", 500, 500, 500, 500);

        Render render = new Render(scene, imageWriter);

        render.renderImage();
        render.printGrid(50, java.awt.Color.WHITE);


        render.writeToImage();
    }

    @Test
    void printGrid() {
    }

    @Test
    void printGrid1() {
    }
}