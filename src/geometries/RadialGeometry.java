package geometries;

public class RadialGeometry {
    protected double _radius;

    //Constructors
    public RadialGeometry(double radius){
        _radius = radius;
    }

    public RadialGeometry(RadialGeometry radialGeometry){
        _radius = radialGeometry._radius;
    }

    //Getters
    public double get_radius() {
        return _radius;
    }

}
