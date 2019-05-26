package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector3D;

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

    //Overrides

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
}
