package geometries;

import javafx.scene.transform.MatrixType;
import primitives.*;

import java.nio.channels.Pipe;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.SplittableRandom;

public class Cylinder extends RadialGeometry{
    protected Ray _ray;

    //Constructors
    public Cylinder(double radius, Ray ray) {
        super(radius);
        _ray = new Ray(ray);
    }

    public Cylinder(RadialGeometry radialGeometry, Ray ray) {
        super(radialGeometry);
        _ray = new Ray(ray);
    }

    public Cylinder(Cylinder cylinder){
        super(cylinder._radius);

        _ray = new Ray(cylinder._ray);
    }

    //Getters
    public Ray get_ray() {
        return _ray;
    }

    //Overrides
    @Override
    public Vector3D get_normal(Point3D point3D) {
        Vector3D subPoints = point3D.subtract(_ray.get_point());
        double projection = _ray.get_direction().dotProduct(subPoints);//projection on the direction vector

        if (projection == 0){
            return point3D.subtract(_ray.get_point()).normalized();
        }

        Vector3D v = _ray.get_direction().scale(projection);
        Point3D p = _ray.get_point().add(v);
        Vector3D result = point3D.subtract(p).normalized();

        return result;
        //return point3D.subtract(_ray.get_point().add(_ray.get_direction().scale(_ray.get_direction().dotProduct(point3D.subtract(_ray.get_point()))))).normalized();
    }

    //Cylinder in the Z direction.
    @Override
    public ArrayList<Point3D> findIntersections(Ray ray) {
        ArrayList<Point3D> result = new ArrayList<>();

        Vector3D A1 = ray.get_direction().subtract(_ray.get_direction().scale(ray.get_direction().dotProduct(_ray.get_direction())));
        double A = A1.squared();
        Vector3D dp = ray.get_point().subtract(_ray.get_point());
        Vector3D B1 = dp.subtract(_ray.get_direction().scale(dp.dotProduct(_ray.get_direction())));
        double B = Util.uscale(2, A1.dotProduct(B1));
        double C = Util.usubtract(B1.squared(), Util.squared(_radius));

        double[] roots = Util.quadraticRoots(A, B, C);

        for (double root : roots){
            result.add(ray.get_point().add(ray.get_direction().scale(root)));
        }

        return result;
    }

}
