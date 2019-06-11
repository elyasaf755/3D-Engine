package geometries;

public abstract class RadialGeometry extends Geometry {
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

    //Overrides

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;

        if (obj == null)
            return false;

        if (!(obj instanceof RadialGeometry))
            return false;

        RadialGeometry radialGeometry = (RadialGeometry) obj;

        return super.equals(obj) &&
                _radius == radialGeometry.get_radius();
    }
}
