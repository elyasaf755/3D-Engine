package renderer;

import elements.*;
import geometries.Sphere;
import geometries.Triangle;
import org.junit.jupiter.api.Test;
import primitives.Color;
import primitives.Point3D;
import primitives.Transform;
import primitives.Vector3D;
import scene.Scene;

class RenderTest {

    @Test//TEST 1 - Scene Intersections
    void renderImage1() {

        Scene scene = new Scene("renderTest");
        scene.set_background(new Color(75, 127,190));
        scene.set_ambientLight(new AmbientLight(new Color(java.awt.Color.WHITE), 0.3));
        scene.set_camera(new Camera(new Point3D(0,0,0), new Vector3D(1,0,0), new Vector3D(0,0,1)), 100);

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

        ImageWriter imageWriter1 = new ImageWriter("1stRenderTest - Scene Intersections", 500, 500, 500, 500);

        Render render = new Render(scene, imageWriter1);

        render.renderImage();
        render.printGrid(50, java.awt.Color.WHITE);

        render.writeToImage();

        //TEST 2 - with colors
        ImageWriter imageWriter2 = new ImageWriter("2ndRenderTest - Colors", 500, 500, 500, 500);

        render = new Render(scene, imageWriter2);

        sphere.set_emission(java.awt.Color.CYAN);
        triangle1.set_emission(java.awt.Color.RED);
        triangle2.set_emission(java.awt.Color.GREEN);
        triangle3.set_emission(java.awt.Color.BLUE);
        triangle4.set_emission(java.awt.Color.YELLOW);

        render.renderImage();
        render.printGrid(50);
        render.writeToImage();

        //TEST 3 - With point light
        ImageWriter imageWriter3 = new ImageWriter("3rdRenderTest - Point Light", 500, 500, 500, 500);
        //scene.addLights(new PointLight(new Color(java.awt.Color.WHITE), new Point3D(35, 0, 0), 1, 0, 0));

        render = new Render(scene, imageWriter3);

        render.renderImage();
        render.printGrid(50);
        render.writeToImage();

        //TEST 4 - With directional light
        ImageWriter imageWriter4 = new ImageWriter("4thRenderTest - Directional Light", 500, 500, 500, 500);
        //scene.addLights(new DirectionalLight(new Vector3D(0,-1,0)));

        render = new Render(scene, imageWriter4);

        render.renderImage();
        render.printGrid(50);
        render.writeToImage();

        //TODO: FIX: DirectionalLight and SpotLight always lit, even if they lit the opposite direction.
    }

    @Test//TEST 2 - With Colors
    void renderImage2() {

        Scene scene = new Scene("renderTest");
        scene.set_background(new Color(75, 127,190));
        scene.set_ambientLight(new AmbientLight(new Color(java.awt.Color.WHITE), 0.3));
        scene.set_camera(new Camera(new Point3D(0,0,0), new Vector3D(1,0,0), new Vector3D(0,0,1)), 100);

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


        ImageWriter imageWriter = new ImageWriter("2ndRenderTest - Colors", 500, 500, 500, 500);

        Render render = new Render(scene, imageWriter);

        sphere.set_emission(java.awt.Color.CYAN);
        triangle1.set_emission(java.awt.Color.RED);
        triangle2.set_emission(java.awt.Color.GREEN);
        triangle3.set_emission(java.awt.Color.BLUE);
        triangle4.set_emission(java.awt.Color.YELLOW);

        render.renderImage();
        render.printGrid(50);
        render.writeToImage();
    }

    @Test//TEST 3 - With Point Light
    void renderImage3() {

        Scene scene = new Scene("renderTest");
        scene.set_background(new Color(75, 127,190));
        scene.set_ambientLight(new AmbientLight(new Color(java.awt.Color.WHITE), 0.3));
        scene.set_camera(new Camera(new Point3D(0,0,0), new Vector3D(1,0,0), new Vector3D(0,0,1)), 100);

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

        sphere.set_emission(java.awt.Color.CYAN);
        triangle1.set_emission(java.awt.Color.RED);
        triangle2.set_emission(java.awt.Color.GREEN);
        triangle3.set_emission(java.awt.Color.BLUE);
        triangle4.set_emission(java.awt.Color.YELLOW);

        scene.addLights(new PointLight(new Color(java.awt.Color.WHITE), new Point3D(35, 0, 0), 1, 0, 0));
        scene.addGeometries(sphere, triangle1, triangle2, triangle3, triangle4);
        ImageWriter imageWriter = new ImageWriter("3rdRenderTest - Point Light", 500, 500, 500, 500);

        Render render = new Render(scene, imageWriter);

        render.renderImage();
        render.printGrid(50);
        render.writeToImage();
    }

    @Test//TEST 4 - With Directional Light
    void renderImage4() {

        Scene scene = new Scene("renderTest");
        scene.set_background(new Color(75, 127,190));
        scene.set_ambientLight(new AmbientLight(new Color(java.awt.Color.WHITE), 0.3));
        scene.set_camera(new Camera(new Point3D(0,0,0), new Vector3D(1,0,0), new Vector3D(0,0,1)), 100);

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

        sphere.set_emission(java.awt.Color.CYAN);
        triangle1.set_emission(java.awt.Color.RED);
        triangle2.set_emission(java.awt.Color.GREEN);
        triangle3.set_emission(java.awt.Color.BLUE);
        triangle4.set_emission(java.awt.Color.YELLOW);

        scene.addGeometries(sphere, triangle1, triangle2, triangle3, triangle4);

        ImageWriter imageWriter = new ImageWriter("4thRenderTest - Directional Light", 500, 500, 500, 500);
        scene.addLights(new DirectionalLight(new Vector3D(0,-1,0)));//TODO: BUG? Actually lit in the opposite direction.

        Render render = new Render(scene, imageWriter);

        render.renderImage();
        render.printGrid(50);
        render.writeToImage();
    }

    @Test//TEST 5 - With Spot Light
    void renderImage5() {

        Scene scene = new Scene("renderTest");
        scene.set_background(new Color(75, 127,190));
        scene.set_ambientLight(new AmbientLight(new Color(java.awt.Color.WHITE), 0.3));
        scene.set_camera(new Camera(new Point3D(0,0,0), new Vector3D(1,0,0), new Vector3D(0,0,1)), 100);

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

        sphere.set_emission(java.awt.Color.CYAN);
        triangle1.set_emission(java.awt.Color.RED);
        triangle2.set_emission(java.awt.Color.GREEN);
        triangle3.set_emission(java.awt.Color.BLUE);
        triangle4.set_emission(java.awt.Color.YELLOW);

        scene.addGeometries(sphere, triangle1, triangle2, triangle3, triangle4);
        scene.addLights(new SpotLight(new Point3D(30,0,100), new Vector3D(0,0,-1),1, 0.01, 0.01));

        ImageWriter imageWriter = new ImageWriter("5thRenderTest - Spot Light", 500, 500, 500, 500);

        Render render = new Render(scene, imageWriter);

        render.renderImage();
        render.printGrid(50);
        render.writeToImage();
    }

    @Test//TEST 6 - Shadow Test (Occludded)
    void renderImage6() {

        Scene scene = new Scene("renderTest");
        scene.set_background(new Color(75, 127,190));
        scene.set_ambientLight(new AmbientLight(new Color(java.awt.Color.WHITE), 0.3));
        scene.set_camera(new Camera(new Point3D(0,0,0), new Vector3D(1,0,0), new Vector3D(0,0,1)), 100);

        scene.set_background(new Color(java.awt.Color.BLACK));

        //Center sphere
        Sphere sphere = new Sphere(35, new Point3D(100, 0, 0));

        //Upper right
        Triangle triangle = new Triangle(
                new Point3D(25, -40, -15),
                new Point3D(25, -15, -40),
                new Point3D(25, -40, -40));

        scene.addGeometries(sphere, triangle);

        sphere.set_emission(java.awt.Color.magenta);
        triangle.set_emission(java.awt.Color.green);

        ImageWriter imageWriter = new ImageWriter("6thRenderTest - Shadow Test", 500, 500, 500, 500);

        Vector3D direction = sphere.get_point().subtract(new Point3D(25,-32,-32));

        scene.addLights(new DirectionalLight(direction));
        Render render = new Render(scene, imageWriter);

        render.renderImage();
        render.writeToImage();
    }

    @Test//TEST 7 - Scale Test
    void renderImage7(){
        Scene scene = new Scene("renderTest");
        scene.set_background(new Color(75, 127,190));
        scene.set_ambientLight(new AmbientLight(new Color(java.awt.Color.WHITE), 0.3));
        scene.set_camera(new Camera(new Point3D(0,0,0), new Vector3D(1,0,0), new Vector3D(0,0,1)), 100);

        scene.set_background(new Color(java.awt.Color.BLACK));

        //Center sphere
        Sphere sphere = new Sphere(35, new Point3D(100, 0, 0));
        sphere.set_emission(java.awt.Color.magenta);

        Sphere sphereScaled = new Sphere(sphere);
        sphereScaled.set_emission(java.awt.Color.green);
        sphereScaled.scale(70);
        Transform transform = new Transform();
        transform.setTranslation(new Vector3D(200,0,0));

        sphereScaled.set_point(transform.getTransformation().mult(new Vector3D(sphereScaled.get_point())).getPoint());

        scene.addGeometries(sphere, sphereScaled);


        ImageWriter imageWriter = new ImageWriter("7thRenderTest - Sphere Scaling", 500, 500, 500, 500);

        Vector3D direction = sphere.get_point().subtract(new Point3D(25,-32,-32));

        scene.addLights(new DirectionalLight(direction));
        Render render = new Render(scene, imageWriter);

        render.renderImage();
        render.writeToImage();
    }


}