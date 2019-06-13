package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Transform;
import primitives.Vector3D;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.ArrayList;
import java.util.Arrays;

public class Tube extends Cylinder {
    protected double _height;

    //Constructors

    public Tube(double radius, Ray ray, double height){
        super(radius, ray);

        _height = height;
    }

    public Tube(RadialGeometry radialGeometry, Ray ray, double height){
        super(radialGeometry, ray);

        _height = height;
    }

    public Tube(Cylinder cylinder, double height){
        super(cylinder);

        _height = height;
    }

    public Tube(Tube tube){
        super(tube._radius, tube._ray);

        _height = tube._height;
    }

    //Getters

    public double get_height() {
        return _height;
    }

    //Methods

    @Override
    public Vector3D get_normal(Point3D point3D) {
        if (point3D.equals(_ray.get_point())){
            return _ray.get_direction().scale(-1);
        }
        double height = _ray.get_direction().dotProduct(point3D.subtract(_ray.get_point()));
        if (height == _height)
            return _ray.get_direction();

        if (height == 0)
            return _ray.get_direction().scale(-1);

        return super.get_normal(point3D);
    }

    @Override
    public ArrayList<GeoPoint> findIntersections(Ray ray) {
        return super.findIntersections(ray);
    }

    @Override
    public void translate(double x, double y, double z) {
        _ray.translate(x, y, z);
    }

    @Override
    public void rotate(double x, double y, double z) {
        _ray.rotate(x, y, z);
    }

    @Override
    public void scale(double x, double y, double z) {
        //TODO: Implement
        throw new NotImplementedException();

    }

    @Override
    public void transform(Transform _transform) {
        //TODO: Implement
        throw new NotImplementedException();
    }

    @Override
    public void transform(Vector3D translation, Vector3D rotation, Vector3D scale) {
        //TODO: Implement
        throw new NotImplementedException();
    }

    //Overrides

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;

        if (obj == null)
            return false;

        if (!(obj instanceof Tube))
            return false;

        Tube tube = (Tube) obj;

        return super.equals(obj) &&
                _height == tube.get_height();
    }


}
