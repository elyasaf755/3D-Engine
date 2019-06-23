package geometries;

import primitives.Color;
import primitives.Material;
import primitives.Util;

public abstract class RadialGeometry extends Geometry {
    protected double _radius;

    //Constructors

    public RadialGeometry(double radius){
        _radius = radius;
    }

    public RadialGeometry(double radius, Color emission){
        super(emission);
        _radius = radius;
    }

    public RadialGeometry(double radius, Material material){
        super(material);
        _radius = radius;
    }

    public RadialGeometry(double radius, Color emission, Material material){
        super(emission, material);
        _radius = radius;
    }

    public RadialGeometry(RadialGeometry radialGeometry){
        _radius = radialGeometry._radius;
    }

    public RadialGeometry(RadialGeometry radialGeometry, Color emission){
        super(emission);

        _radius = radialGeometry._radius;
    }

    public RadialGeometry(RadialGeometry radialGeometry, Material material){
        super(material);

        _radius = radialGeometry._radius;
    }

    public RadialGeometry(RadialGeometry radialGeometry, Color emission, Material material){
        super(emission, material);

        _radius = radialGeometry._radius;
    }

    //Getters
    public double get_radius() {
        return _radius;
    }

    //Setters

    public void set_radius(double radius){
        _radius = radius;
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
                Util.equals(_radius, radialGeometry.get_radius());
    }
}
