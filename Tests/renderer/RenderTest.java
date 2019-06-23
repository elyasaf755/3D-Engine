package renderer;

import com.sun.org.apache.regexp.internal.RE;
import elements.*;
import geometries.*;
import org.junit.jupiter.api.Test;
import primitives.*;
import scene.Scene;
import sun.print.SunPrinterJobService;

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

    @Test//TEST 8 - Render Tube
    void renderImage8(){

        Scene scene = new Scene("tubeScene");
        scene.set_background(new Color(75, 127,190));
        scene.set_ambientLight(new AmbientLight(new Color(java.awt.Color.WHITE), 0.3));
        Vector3D zAxis = new Vector3D(0,0,1);
        Vector3D yAxis = new Vector3D(0,1,0);
        scene.set_camera(new Camera(new Point3D(0,0,-10000), zAxis, yAxis), 10000);
        scene.set_background(new Color(java.awt.Color.CYAN));
        scene.get_camera().rotate(5, 15, 0);

        Sphere sphere = new Sphere(5, new Point3D(0,0,0));
        sphere.set_emission(java.awt.Color.GREEN);

        scene.addGeometries(sphere);

        scene.addLights(new DirectionalLight(new Vector3D(0,0,1)));

        ImageWriter imageWriter = new ImageWriter("8thRenderTest - Tube Test", 500, 500, 500, 500);
        Render render = new Render(scene, imageWriter);
        render.printAxises();
        render.renderImage();
        render.writeToImage();
    }

    @Test//TEST 9 - reflection and refraction
    void renderImage9() {

        Scene scene = new Scene("renderTest");
        scene.set_background(new Color(75, 127,190));
        scene.set_ambientLight(new AmbientLight(new Color(java.awt.Color.WHITE), 0.3));
        scene.set_camera(new Camera(new Point3D(-1000,0,0), new Vector3D(1,0,0), new Vector3D(0,0,1)), 1100);

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
        scene.set_background(new Color(java.awt.Color.WHITE));
        scene.set_camera(new Camera(new Point3D(0,0,-1100), new Vector3D(0,0,1), new Vector3D(0,1,0)), 1100);
        scene.set_ambientLight(new AmbientLight(new Color(java.awt.Color.WHITE), 0.3));
        scene.get_camera().rotate(5,15,0);

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

    @Test//TEST 11 - reflection and refraction 2
    void renderImage11() {

        Scene scene = new Scene("renderTest");
        scene.set_background(new Color(75, 127,190));
        scene.set_ambientLight(new AmbientLight(new Color(java.awt.Color.WHITE), 0.3));
        scene.set_camera(new Camera(new Point3D(0,0,-1100), new Vector3D(0,0,1), new Vector3D(0,1,0)), 1000);
        scene.get_camera().rotate(5,15,0);

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

        scene.addGeometries(sphere1, sphere2 ,plane);
        ImageWriter imageWriter = new ImageWriter("11thRenderTest - Reflection and Refraction 2", 500, 500, 500, 500);

        Render render = new Render(scene, imageWriter);
        render.printAxises();

        render.renderImage();
        render.writeToImage();
    }

    @Test//TEST 12 - Torus - New Geometry
    void renderImage12(){
        Scene scene = new Scene("Torus");
        scene.set_background(new Color(java.awt.Color.WHITE));
        scene.set_camera(new Camera(new Point3D(0,0,-1100), new Vector3D(1,0,0), new Vector3D(0,0,1)), 1000);
        scene.set_ambientLight(new AmbientLight(new Color(java.awt.Color.WHITE), 0.3));
        //scene.get_camera().rotate(5,15,0);

        Torus torus = new Torus(3, 1, new Ray(new Point3D(0,0,0), new Vector3D(0,0,1)));
        torus.set_emission(java.awt.Color.BLUE);

        scene.addGeometries(torus);
        scene.addLights(new DirectionalLight(new Vector3D(1,0,0)));


        ImageWriter iw = new ImageWriter("12thRenderTest - Torus", 500, 500, 500, 500);
        Render render = new Render(scene, iw);
        //render.printAxises();

        render.renderImage();
        render.writeToImage();
    }

    @Test//TEST 13 - Triangle Mesh
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


}