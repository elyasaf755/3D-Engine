package renderer;

import com.sun.org.apache.regexp.internal.RE;
import elements.*;
import geometries.*;
import javafx.scene.control.Tab;
import org.junit.jupiter.api.Test;
import prefabs.Aquarium;
import prefabs.Bubble;
import prefabs.JCTLogo;
import prefabs.Table;
import primitives.*;
import scene.Scene;
import sun.print.SunPrinterJobService;

import static geometries.Intersectable.GeoPoint;

import java.text.ParsePosition;
import java.util.ArrayList;

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

        scene.addGeometries( sphere, triangle1, triangle2, triangle3, triangle4);

        ImageWriter imageWriter1 = new ImageWriter("1stRenderTest - Scene Intersections", 500, 500, 500, 500);

        Render render = new Render(scene, imageWriter1);

        render.renderImage();
        render.printGrid(50, java.awt.Color.WHITE);

        render.writeToImage();
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

        sphere.set_emission(new Color(32,56,240));
        triangle1.set_emission(java.awt.Color.RED);
        triangle2.set_emission(java.awt.Color.GREEN);
        triangle3.set_emission(java.awt.Color.BLUE);
        triangle4.set_emission(java.awt.Color.YELLOW);

        scene.addLights(new PointLight(new Color(java.awt.Color.red), new Point3D(35, 0, 0), 1, 0.0, 0.0));
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
        scene.addLights(new SpotLight(new Point3D(0,0,100), new Vector3D(1,0,-1),1, 0.0, 0.0));

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
        sphereScaled.scale(6);
        Transform transform = new Transform();
        transform.setTranslation(new Vector3D(200,0,0));
        sphere.translate(0,-50,0);

        sphereScaled.set_point(transform.getTransformation().mult(new Vector3D(sphereScaled.get_point())).getPoint());

        scene.addGeometries(sphere, sphereScaled);


        ImageWriter imageWriter = new ImageWriter("7thRenderTest - Sphere Scaling", 500, 500, 500, 500);

        scene.addLights(new DirectionalLight(new Vector3D(1,2,0)));
        Render render = new Render(scene, imageWriter);

        render.renderImage();
        render.writeToImage();
    }

    @Test//TEST 8 - Cylinders
    void renderImage8(){

        Scene scene = new Scene("tubeScene");
        scene.set_background(new Color(75, 127,190));
        scene.set_ambientLight(new AmbientLight(new Color(java.awt.Color.WHITE), 0.3));
        Vector3D zAxis = new Vector3D(0,0,1);
        Vector3D yAxis = new Vector3D(0,1,0);
        scene.set_camera(new Camera(new Point3D(0,0,-10000), zAxis, yAxis), 10000);
        scene.set_background(new Color(java.awt.Color.CYAN));
        scene.get_camera().rotate(5, 15, 0);

        scene.addLights(new DirectionalLight(new Vector3D(0,0,1)));

        ImageWriter imageWriter = new ImageWriter("8thRenderTest - Cylinders Test", 500, 500, 500, 500);
        Render render = new Render(scene, imageWriter);
        render.printAxises();
        render.renderImage();
        render.writeToImage();
    }

    @Test//TEST 9 - Reflection and Refraction
    void renderImage9() {

        Scene scene = new Scene("renderTest");
        scene.set_background(new Color(75, 127,190));
        scene.set_ambientLight(new AmbientLight(new Color(java.awt.Color.WHITE), 0.3));
        scene.set_camera(new Camera(new Point3D(-1000,0,0), new Vector3D(1,0,0), new Vector3D(0,0,1)), 1100);

        scene.get_camera().setAa(1);
        scene.set_background(new Color(java.awt.Color.BLACK));

        //Center sphere
        Sphere sphere = new Sphere(45, new Point3D(100, 0, 0));//middle bigger sphere
        sphere.set_emission(new Color(32,56,240));

        Sphere sphere1 = new Sphere(30, new Point3D(50, -30, 30));//upper left sphere
        sphere1.set_emission(new Color(200,56,40));
        sphere1.get_material().set_Kr(1);
        sphere1.get_material().set_Kt(0.7);

        Sphere sphere2 = new Sphere(30, new Point3D(50, 30, -30));//lower right sphere
        sphere2.set_emission(new Color(32,200,24));
        sphere2.get_material().set_Kt(1);

        //Upper right
        Triangle triangle1 = new Triangle(
                new Point3D(100, -100, 100),
                new Point3D(100, -100, 0),
                new Point3D(100, 0, 100)
        );

        triangle1.set_emission(java.awt.Color.RED);
        triangle1.get_material().set_Kr(0.5);

        //Upper left
        Triangle triangle2 = new Triangle(
                new Point3D(100, 100, 100),
                new Point3D(100, 0, 100),
                new Point3D(100, 100, 0)
        );

        triangle2.set_emission(java.awt.Color.GREEN);
        triangle2.get_material().set_Kt(0.5);

        //Lower left
        Triangle triangle3 = new Triangle(
                new Point3D(100, 100, 0),
                new Point3D(100, 100, -100),
                new Point3D(100, 0, -100)
        );

        triangle3.set_emission(java.awt.Color.BLUE);

        //Lower right
        Triangle triangle4 = new Triangle(
                new Point3D(100, -100, 0),
                new Point3D(100, 0, -100),
                new Point3D(100, -100, -100)
        );

        triangle4.set_emission(java.awt.Color.YELLOW);

        scene.addLights(new PointLight(new Color(java.awt.Color.red), new Point3D(35, 0, 0), 1, 0.0, 0.0),
                        new DirectionalLight(new Color(java.awt.Color.YELLOW), new Vector3D(1,-1,0))
        );

        scene.addGeometries(sphere, triangle1, triangle2, triangle3, triangle4,sphere1,sphere2);
        ImageWriter imageWriter = new ImageWriter("9thRenderTest - Reflection and Refraction", 500, 500, 500, 500);

        Render render = new Render(scene, imageWriter);

        render.renderImage();
        render.printGrid(50);
        render.writeToImage();
    }

    @Test//TEST 10 - Cone - New Geometry
    void renderImage10(){
        Scene scene = new Scene("Cone");
        scene.set_background(new Color(java.awt.Color.cyan));
        scene.set_camera(new Camera(new Point3D(0,0,-1100), new Vector3D(0,0,1), new Vector3D(0,1,0)), 1100);
        scene.set_ambientLight(new AmbientLight(new Color(java.awt.Color.WHITE), 0.3));
        scene.get_camera().rotate(5,15,0);
        scene.get_camera().setAa(1);

        Cone cone = new Cone(4, new Ray(new Point3D(0,0,0), new Vector3D(1,0,0)), 60);
        cone.set_emission(java.awt.Color.CYAN);

        scene.addGeometries(cone);
        scene.addLights(new DirectionalLight(new Vector3D(0,0,1)), new DirectionalLight(new Vector3D(-1,-1,0)));

        ImageWriter iw = new ImageWriter("10thRenderTest - Cone", 500, 500, 500, 500);
        Render render = new Render(scene, iw);
        render.printAxises();

        render.renderImage();
        render.writeToImage();
    }

    @Test//TEST 11 - Reflection and Refraction 2
    void renderImage11() {

        Scene scene = new Scene("renderTest");
        scene.set_background(new Color(75, 127,190));
        scene.set_ambientLight(new AmbientLight(new Color(java.awt.Color.WHITE), 0.3));
        Camera camera = new Camera(new Point3D(0,0,-1100), new Vector3D(0,0,1), new Vector3D(0,1,0));
        scene.set_camera(camera, 1000);
        camera.rotate(5,15,0);
        camera.setAa(1);

        scene.set_background(new Color(53, 215, 255));

        //Center sphere
        Sphere sphere1 = new Sphere(45, new Point3D(40, 0, -40));
        sphere1.set_emission(new Color(32,56,240));
        sphere1.get_material().set_Kt(0.2);
        sphere1.get_material().set_Kr(0.2);

        Sphere sphere2 = new Sphere(22.5, new Point3D(100, 45, -100));
        sphere2.set_emission(java.awt.Color.CYAN);
        sphere2.get_material().set_Kt(0.2);
        sphere2.get_material().set_Kr(0.2);

        Plane plane= new Plane(new Point3D(0,-50,0),new Vector3D(0,1,0));
        plane.set_emission(new Color(java.awt.Color.BLACK));
        plane.get_material().set_Kr(0.2);
        plane.get_material().set_Kt(0.2);


        scene.addLights(new PointLight(new Color(java.awt.Color.red), new Point3D(0, 0, 35), 1, 0.0, 0.0),
                new DirectionalLight(new Color(java.awt.Color.YELLOW), new Vector3D(0,-1,0)),
                new DirectionalLight(new Color(java.awt.Color.YELLOW), new Vector3D(-1,0,0))
        );

        SetUnion union = new SetUnion(sphere1, sphere2);

        scene.addGeometries(union, plane);
        ImageWriter imageWriter = new ImageWriter("11thRenderTest - Reflection and Refraction 2", 1000, 1000, 1000, 1000);

        Render render = new Render(scene, imageWriter);
        render.printAxises();

        render.renderImage();
        render.writeToImage();
    }

    @Test//TEST 12 - Torus - New Geometry
    void renderImage12(){
        Camera camera = new Camera(new Point3D(0,0,-1100), new Vector3D(0,0,1), new Vector3D(0,1,0));

        Scene scene = new Scene("Torus");
        scene.set_background(new Color(java.awt.Color.WHITE));
        scene.set_camera(camera, 1000);
        scene.set_ambientLight(new AmbientLight(new Color(java.awt.Color.WHITE), 0.1));
        camera.rotate(5,15,0);
        camera.setAa(1);

        Torus torus = new Torus(40, 20, new Ray(new Point3D(0,0,0), new Vector3D(0,0,1)));
        torus.set_emission(java.awt.Color.BLUE);
        torus.set_material(new Material(1,1,0.3,0.4,2));
        torus.rotate(45,45,45);

        scene.addGeometries(torus);
        scene.addLights(new DirectionalLight(new Vector3D(0,0,1)));


        ImageWriter iw = new ImageWriter("12thRenderTest - Torus", 500, 500, 500, 500);
        Render render = new Render(scene, iw);
        render.printAxises();

        render.renderImage();
        render.writeToImage();
    }

    @Test//TEST 13 - Triangle Mesh//TODO:FIX
    void renderImage13(){
        Scene scene = new Scene("renderTest");
        scene.set_background(new Color(75, 127,190));
        scene.set_ambientLight(new AmbientLight(new Color(java.awt.Color.WHITE), 0.3));
        scene.set_camera(new Camera(new Point3D(0,0,-1100), new Vector3D(0,0,1), new Vector3D(0,1,0)), 1000);
        scene.get_camera().rotate(5,15,0);

        scene.set_background(new Color(53, 215, 255));

        scene.addLights(
                new DirectionalLight(new Color(java.awt.Color.YELLOW), new Vector3D(0,-1,0))
        );

        TriangleMesh mesh = new TriangleMesh();
        Triangle t3 = new Triangle(new Point3D(0,0,0), new Point3D(0,100,0), new Point3D(0,0,100));
        Triangle t1 = new Triangle(new Point3D(100,0,0), new Point3D(0,100,0), new Point3D(0,0,100));
        Triangle t2 = new Triangle(new Point3D(0,0,0), new Point3D(100,0,0), new Point3D(0,0,100));
        Triangle t4 = new Triangle(new Point3D(0,0,0), new Point3D(0,0,100), new Point3D(100,0,0));
        t3.set_emission(java.awt.Color.green);
        t1.set_emission(java.awt.Color.RED);
        t2.set_emission(java.awt.Color.blue);
        t4.set_emission(java.awt.Color.MAGENTA);
        mesh.addTriangle(t1);
        mesh.addTriangle(t2);
        mesh.addTriangle(t3);
        mesh.addTriangle(t4);

        scene.addGeometries(mesh);
        scene.addLights(new DirectionalLight(new Vector3D(1,0,0)));


        ImageWriter iw = new ImageWriter("13thRenderTest - Mesh", 500, 500, 500, 500);
        Render render = new Render(scene, iw);
        render.printAxises();

        render.renderImage();
        render.writeToImage();

    }

    @Test//TEST 14 - Set Operations
    void renderImage14(){
        Scene scene = new Scene("renderTest");
        scene.set_background(new Color(75, 127,190));
        scene.set_ambientLight(new AmbientLight(new Color(java.awt.Color.WHITE), 0.3));
        scene.set_camera(new Camera(new Point3D(0,0,-1100), new Vector3D(0,0,1), new Vector3D(0,1,0)), 1000);
        scene.get_camera().rotate(5,15,0);

        scene.set_background(new Color(53, 215, 255));

        scene.addLights(
                new DirectionalLight(new Color(java.awt.Color.YELLOW), new Vector3D(0,-1,0))
        );

        Sphere sphere1 = new Sphere(50, new Point3D());
        Sphere sphere2 = new Sphere(50, new Point3D(50,0,0));
        SetIntersection setIntersection = new SetIntersection(sphere1, sphere2);
        setIntersection.translate(100,100,0);
        scene.addGeometries(setIntersection);

        sphere1.set_emission(java.awt.Color.MAGENTA);
        sphere2.set_emission(java.awt.Color.GREEN);
        scene.addLights(new DirectionalLight(new Vector3D(1,0,0)));

        Sphere sphere3 = new Sphere(sphere1);
        Sphere sphere4 = new Sphere(sphere2);
        SetUnion setUnion = new SetUnion(sphere3, sphere4);
        setUnion.translate(-150,0,0);
        scene.addGeometries(setUnion);

        Sphere sphere5 = new Sphere(50, new Point3D());
        Sphere sphere6 = new Sphere(50, new Point3D(50, 0, 0));
        sphere5.set_emission(java.awt.Color.MAGENTA);
        sphere6.set_emission(java.awt.Color.GREEN);
        SetDifference setDifference1 = new SetDifference(sphere5, sphere6);
        setDifference1.translate(-200,100,0);
        scene.addGeometries(setDifference1);

        Triangle triangle1 = new Triangle(new Point3D(-40,0,0), new Point3D(0,80,0), new Point3D(40,0,0), new Color(java.awt.Color.red));
        Sphere sphere7 = new Sphere(20, new Point3D());
        SetDifference setDifference2 = new SetDifference(triangle1, sphere7);
        setDifference2.translate(0,-100,0);
        scene.addGeometries(setDifference2);



        ImageWriter iw = new ImageWriter("14thRenderTest - Set Operations", 500, 500, 500, 500);
        Render render = new Render(scene, iw);
        //render.printAxises();

        render.renderImage();
        render.writeToImage();

    }

    @Test//TEST 15 - Playing With Triangles
    void renderImage15(){
        Scene scene = new Scene("renderTest");
        scene.set_background(new Color(75, 127,190));
        scene.set_ambientLight(new AmbientLight(new Color(java.awt.Color.WHITE), 0.3));
        scene.set_camera(new Camera(new Point3D(0,0,-1100), new Vector3D(0,0,1), new Vector3D(0,1,0)), 1000);
        scene.get_camera().rotate(5,15,0);
        scene.get_camera().scale(2,2,2);

        scene.set_background(new Color(53, 215, 255));

        scene.addLights(
                new DirectionalLight(new Color(java.awt.Color.YELLOW), new Vector3D(0,-1,0))
        );

        Vector3D xAxis = new Vector3D(50,0,0);
        Vector3D rot = new Vector3D(xAxis);
        double angle = 22.5;
        rot.rotate(0,0,angle);
        int size = (int)Math.floor(360 / 22.5);
        Geometry[] geometries = new Geometry[size];
        Triangle t1 = new Triangle(new Point3D(), xAxis, rot);
        Color color = new Color(java.awt.Color.GREEN);
        double sum = 0;
        int i = 0;
        while (sum < 360){
            Triangle t2 = new Triangle(t1);
            double c = 255 / (360/angle);
            color = color.add(new Color(c, c, c));
            t2.set_emission(color);
            t2.rotate(0,0, sum + 0.001);
            geometries[i] = t2;
            sum += angle;
            ++i;
        }

        Geometries geometries1 = new Geometries(geometries);
        ArrayList<Geometry> list = geometries1.get_GeometriesList();
        geometries1.translate(75,-45,-75);
        geometries1.rotate(45,0,0);
        for (int j = 0; j < size; ++j){
            geometries[j] = list.get(j);
        }

        scene.addGeometries(geometries);
        scene.addLights(new DirectionalLight(new Vector3D(1,0,0)));


        ImageWriter iw = new ImageWriter("15thRenderTest - Playing With Triangles", 500, 500, 500, 500);
        Render render = new Render(scene, iw);
        render.printAxises();

        render.renderImage();
        render.writeToImage();

    }

    @Test//TEST 16 - Cuboid - base for AABB
    void renderImage16(){
        Scene scene = new Scene("renderTest");
        scene.set_background(new Color(75, 127,190));
        scene.set_ambientLight(new AmbientLight(new Color(java.awt.Color.WHITE), 0.3));
        scene.set_camera(new Camera(new Point3D(0,0,-1100), new Vector3D(0,0,1), new Vector3D(0,1,0)), 1000);
        scene.get_camera().rotate(45,15,0);
        //scene.get_camera().setAa(2);


        scene.set_background(new Color(53, 215, 255));

        scene.addLights(
                new DirectionalLight(new Color(java.awt.Color.YELLOW), new Vector3D(0,-1,0))
        );

        //Cuboid cuboid1 = new Cuboid(60,60,90, new Ray(new Point3D(50,50,-50), new Vector3D(1,0,0)), new Color(java.awt.Color.white));
        Cuboid cuboid1 = new Cuboid(60,60,90, new Ray(new Point3D(0,0,0), new Vector3D(0,0,1)), new Color(java.awt.Color.white));
        cuboid1.setBackFaceColor(new Color(java.awt.Color.green));
        cuboid1.setUpFaceColor(new Color(java.awt.Color.red));
        cuboid1.setFrontFaceColor(new Color(java.awt.Color.blue));
        cuboid1.setRightFaceColor(new Color(java.awt.Color.magenta));
        cuboid1.setDownFaceColor(new Color(java.awt.Color.yellow));
        cuboid1.setLeftFaceColor(new Color(java.awt.Color.cyan));

        cuboid1.translate(0,0,0);

        scene.addGeometries(cuboid1);
        scene.addLights(new DirectionalLight(new Vector3D(1,0,0)));


        ImageWriter iw = new ImageWriter("16thRenderTest - Cuboid", 500, 500, 500, 500);
        Render render = new Render(scene, iw);
        render.printAxises();

        render.renderImage();
        render.writeToImage();

    }

    @Test//TEST 17 - Aquarium
    void renderImage17() {
        Scene scene = new Scene("renderTest");
        scene.set_background(new Color(53, 215, 255));
        scene.set_ambientLight(new AmbientLight(new Color(java.awt.Color.WHITE), 0.1));
        scene.set_camera(new Camera(new Point3D(0,0,-1100), new Vector3D(0,0,1), new Vector3D(0,1,0)), 1000);
        scene.get_camera().rotate(0,0,0);
        scene.get_camera().setAa(1);

        Aquarium aquarium = new Aquarium();
        aquarium.scale(3);
        aquarium.rotate(45,45,45);

        scene.addGeometries(aquarium);



        PointLight pLight = new PointLight(new Color(java.awt.Color.YELLOW), new Point3D(0, 0, 35), 1, 0.0, 0.0);
        DirectionalLight dLight = new DirectionalLight(new Color(100,100,100), new Vector3D(0,0,1));
        DirectionalLight dLight2 = new DirectionalLight(new Color(130,130,130), new Vector3D(0,0,-1));

        scene.addLights(
                dLight
        );


        ImageWriter iw = new ImageWriter("17thRenderTest - Aquarium", 1000, 1000, 1000, 1000);
        Render render = new Render(scene, iw);
        //render.printAxises();

        render.renderImage();
        render.writeToImage();

    }

    @Test//TEST 18 - Pyramid
    void renderImage18()throws InterruptedException{
        Scene scene = new Scene("renderTest");
        scene.set_background(new Color(53, 215, 255));
        scene.set_ambientLight(new AmbientLight(new Color(java.awt.Color.WHITE), 0.1));
        scene.set_camera(new Camera(new Point3D(0,0,-1100), new Vector3D(0,0,1), new Vector3D(0,1,0)), 1000);
        scene.get_camera().rotate(5,25,0);
        scene.get_camera().setAa(1);

        Plane p1 = new Plane(new Point3D(0,0,0), new Vector3D(0,1,0), new Color(java.awt.Color.darkGray));
        Triangle t1 = new Triangle(new Point3D(-20,0,-20), new Point3D(0,50,0), new Point3D(20,0,-20), new Color(java.awt.Color.RED));
        Triangle t2 = new Triangle(t1);
        t2.rotate(0,0,0);
        t2.set_emission(java.awt.Color.GREEN);

        Triangle t3 = new Triangle(t2);
        t3.rotate(0,90,0);
        t3.set_emission(java.awt.Color.BLUE);

        Triangle t4 = new Triangle(t3);
        t4.rotate(0,90,0);
        t4.set_emission(java.awt.Color.MAGENTA);

        SetUnion u1 = new SetUnion(t1, t2);
        SetUnion u2 = new SetUnion(u1, t3);
        SetUnion u3 = new SetUnion(u2, t4);

        Cuboid cuboid = new Cuboid(40,40,40, new Color(java.awt.Color.darkGray));

        SetIntersection floor = new SetIntersection(cuboid, p1);

        SetUnion pyramid = new SetUnion(floor, u3);

        t1.scale(2,2,2);
        t2.scale(2,2,2);
        t3.scale(2,2,2);
        t4.scale(2,2,2);
        floor.scale(2,2,2);

        pyramid.rotate(180,0,0);
        pyramid.scale(2, 2, 2);

        scene.addGeometries(pyramid);

        PointLight pLight = new PointLight(new Color(java.awt.Color.YELLOW), new Point3D(0, 0, 35), 1, 0.0, 0.0);
        DirectionalLight dLight = new DirectionalLight(new Color(java.awt.Color.YELLOW), new Vector3D(0,-1,0));

        scene.addLights(
                dLight
        );


        ImageWriter iw = new ImageWriter("18thRenderTest - Pyramid", 500, 500, 500, 500);
        Render render = new Render(scene, iw);
        //render.printAxises();

        render.renderImage();
        render.writeToImage();

    }

    @Test//TEST 19 - Table
    void renderImage19(){
        Scene scene = new Scene("renderTest");
        scene.set_background(new Color(53, 215, 255));
        scene.set_ambientLight(new AmbientLight(new Color(java.awt.Color.WHITE), 0.1));
        Camera camera = new Camera(new Point3D(0,0,-1100), new Vector3D(0,0,1), new Vector3D(0,1,0));
        scene.set_camera(camera, 1000);
        camera.rotate(45,25,0);
        camera.setAa(1);

        Table table = new Table();
        table.scale(3);

        scene.addGeometries(table);

        DirectionalLight dLight = new DirectionalLight(new Color(200,200,200), new Vector3D(0,0,1));

        scene.addLights(
            dLight
        );


        ImageWriter iw = new ImageWriter("19thRenderTest - Table", 1000, 1000, 1000, 1000);
        Render render = new Render(scene, iw);
        //render.printAxises();

        render.renderImage();
        render.writeToImage();
    }

    @Test//TEST 20 - JCT Logo
    void renderImage20(){
        //from left to right
        //R: 27 G: 126 B: 169    Left Most triangle
        //R: 35 G: 43 B: 106
        //R: 68 G: 39 B: 105
        //R: 109 G: 43 B: 91   Right Most


        Scene scene = new Scene("renderTest");
        scene.set_background(new Color(53, 215, 255));
        scene.set_ambientLight(new AmbientLight(new Color(java.awt.Color.WHITE), 0.2));
        scene.set_camera(new Camera(new Point3D(0,0,-1100), new Vector3D(0,0,1), new Vector3D(0,1,0)), 1000);
        //scene.get_camera().rotate(5,15,0);
        scene.get_camera().setAa(1);

        JCTLogo logo = new JCTLogo();
        logo.scale(2);
        logo.rotate(45,45,45);
        logo.translate(45,45,45);

        scene.addGeometries(logo);

        PointLight pLight = new PointLight(new Color(java.awt.Color.white), new Point3D(0, 0, -20), 1, 0.0, 0.0);
        DirectionalLight dLight = new DirectionalLight(new Color(java.awt.Color.WHITE), new Vector3D(0,0,1));
        SpotLight sLight = new SpotLight(java.awt.Color.white, new Point3D(0,0,-100), new Vector3D(0,0,1), 1,0.01,0);

        scene.addLights(
            sLight
        );


        ImageWriter iw = new ImageWriter("20thRenderTest - JCT Logo", 1000, 1000, 1000, 1000);
        Render render = new Render(scene, iw);
        //render.printAxises();

        render.renderImage();
        render.writeToImage();
    }

    @Test//TEST 21 - Rectangle
    void renderImage21(){

        Scene scene = new Scene("renderTest");
        scene.set_background(new Color(53, 215, 255));
        scene.set_ambientLight(new AmbientLight(new Color(java.awt.Color.WHITE), 0.2));
        scene.set_camera(new Camera(new Point3D(0,0,-1100), new Vector3D(0,0,1), new Vector3D(0,1,0)), 1000);
        //scene.get_camera().rotate(5,15,0);
        scene.get_camera().setAa(2);

        Rectangle rectangle = new Rectangle(40, 80, new Ray(new Vector3D(0,0,1)));
        rectangle.set_emission(java.awt.Color.green);

        scene.addGeometries(rectangle);

        PointLight pLight = new PointLight(new Color(java.awt.Color.white), new Point3D(0, 0, -7.5), 1, 0.0, 0.0);
        DirectionalLight dLight = new DirectionalLight(new Color(java.awt.Color.WHITE), new Vector3D(0,0,1));

        scene.addLights(
                pLight
        );


        ImageWriter iw = new ImageWriter("21thRenderTest - Rectangle", 500, 500, 500, 500);
        Render render = new Render(scene, iw);
        //render.printAxises();

        render.renderImage();
        render.writeToImage();
    }

    @Test//TEST 22 - Final Scene
    void renderImage22(){

        //Scene definitions

        Scene scene = new Scene("renderTest");
        scene.set_background(new Color(53, 215, 255));
        scene.set_ambientLight(new AmbientLight(new Color(java.awt.Color.WHITE), 0.1));
        scene.set_camera(new Camera(new Point3D(0,0,-1100), new Vector3D(0,0,1), new Vector3D(0,1,0)), 1000);
        scene.get_camera().rotate(45,0,0);
        scene.get_camera().setAa(1);



        //Geometries

        Aquarium aquarium = new Aquarium();
        aquarium.scale(0.8);
        aquarium.rotate(0,0,0);
        aquarium.translate(0,100,0);


        Table table = new Table();
        table.scale(2);
        table.rotate(0,0,0);
        table.translate(0,0,0);
        table.set_material(new Material(0.5,0.5,0.1,0,3));

        scene.addGeometries(table, aquarium);


        //Lights

        PointLight pLight = new PointLight(new Color(java.awt.Color.YELLOW), new Point3D(0, 0, 35), 1, 0.0, 0.0);
        DirectionalLight dLight = new DirectionalLight(new Color(100,100,100), new Vector3D(0,0,1));
        DirectionalLight dLight2 = new DirectionalLight(new Color(130,130,130), new Vector3D(0,0,-1));



        scene.addLights(
                dLight
        );



        //Render

        ImageWriter iw = new ImageWriter("22ndRenderTest - Final Scene", 1000, 1000, 1000, 1000);
        Render render = new Render(scene, iw);
        //render.printAxises();

        render.renderImage();
        render.writeToImage();
    }

    @Test//TEST 23 - AABB TEST
    void renderImage23(){

        //Scene definitions

        Scene scene = new Scene("renderTest");
        scene.set_background(new Color(53, 215, 255));
        scene.set_ambientLight(new AmbientLight(new Color(java.awt.Color.WHITE), 0.1));
        scene.set_camera(new Camera(new Point3D(0,0,-1100), new Vector3D(0,0,1), new Vector3D(0,1,0)), 1000);
        scene.get_camera().rotate(0,0,0);
        scene.get_camera().setAa(1);



        //Geometries


        //Triangle - working

        Triangle t1 = new Triangle(new Point3D(50,0,0), new Point3D(-50,0,0), new Point3D(0,50,0));
        t1.scale(2);
        t1.rotate(0,0,45);
        t1.translate(-400,400,0);
        t1.set_emission(java.awt.Color.GREEN);

        //SPHERES - working

        //REGULAR - 3.26 s
        //AABB: 2.7 s
        //AABB with unions: 2.374
        Sphere temp = new Sphere(50, new Point3D(-400,0,0));
        temp.set_emission(java.awt.Color.green);
        Sphere temp2 = new Sphere(temp);
        temp2.scale(1.1);
        temp.translate(100,0,0);
        SetUnion spheres = new SetUnion(temp, temp2);
        for (int i = 0; i < 10; ++i){

            Sphere sphere = new Sphere(temp);
            sphere.set_emission(java.awt.Color.green);
            sphere.scale(1.1);
            sphere.translate(100, 0, 0);

            spheres = new SetUnion(spheres, sphere);

            temp = new Sphere(sphere);
        }


        //****NOT WORKING****(base rotation not working also
        //CUBOID
        Cuboid cuboid = new Cuboid(40,40,40);
        cuboid.set_emission(java.awt.Color.green);
        cuboid.scale(4);
        cuboid.rotate(0,0,0);


        //TORUS - WORKING

        //REGULAR - 5.86
        //AABB - 2.42
        Torus torus = new Torus(50,20);
        torus.set_emission(java.awt.Color.green);
        torus.rotate(45,0,0);//min: -70,-70,-63.6  max - 170,205,63
        torus.translate(100,100,100);//min - same, max - 170,205,63
        torus.scale(3);//min - same, max - 370,487,63
        torus.scale(0.3);

        //Working, AABB not suitable for infinite geometries
        Cylinder cylinder = new Cylinder(20, new Ray(new Vector3D(0,0,1)));
        cylinder.set_emission(java.awt.Color.green);
        cylinder.rotate(45,0,0);

        //TUBE

        //REGULAR - 3.707 s
        //WITH AABB - 2.36 s
        Tube tube = new Tube(20, new Ray(new Vector3D(0,1,0)), 20);
        tube.set_emission(java.awt.Color.green);
        tube.rotate(45,45,0);
        tube.scale(4);

        //Rectangle - WORKING

        //Regular - 3.294
        //AABB - 1.591 s
        Rectangle rectangle = new Rectangle(60, 20, new Ray(new Vector3D(0,0,1)));
        rectangle.set_emission(java.awt.Color.green);
        rectangle.scale(3);
        rectangle.rotate(0,0,0);
        rectangle.translate(50,50,50);

        //Aquarium - WORKING
        //From pure regular to PURE AABB: nothing 2.8 s --> spheres 2.753 s --> GeoSet 2.524 s --> SetInt --> 2.407 s --> Aquarium 2.216 s

        Aquarium aquarium = new Aquarium();
        aquarium.scale(0.1);
        aquarium.rotate(45,0,45);
        aquarium.translate(100,100,100);

        //Table - not working. see at camera rotation (45,0,0)
        //Table wont rotate correctly
        Table table = new Table();
        table.scale(3);
        table.rotate(0,45,0);

        //JCT - working
        //regular and aabb are the same.

        JCTLogo jctLogo = new JCTLogo();
        jctLogo.scale(2);
        jctLogo.rotate(45,45,45);
        jctLogo.translate(100,100,100);

        Plane plane= new Plane(new Point3D(0,-50,0),new Vector3D(0,1,0));
        plane.set_emission(new Color(java.awt.Color.BLACK));
        plane.get_material().set_Kr(0.2);
        plane.get_material().set_Kt(0.2);


        SetUnion union = new SetUnion(plane, torus);

        scene.addGeometries(union);



        //scene.addGeometries(aquarium, table);



        //Lights

        DirectionalLight dLight = new DirectionalLight(new Color(255,255,255), new Vector3D(0,0,1));



        scene.addLights(
                dLight
        );



        //Render

        ImageWriter iw = new ImageWriter("23rdRenderTest - AABB TEST", 1000, 1000, 1000, 1000);
        Render render = new Render(scene, iw);
        //render.printAxises();

        render.renderImage();
        render.writeToImage();
    }
}