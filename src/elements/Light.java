package elements;

import primitives.Color;
import primitives.Point3D;

public abstract class Light {
    protected Color _intensity;

    //TODO: Implement class

    //Constructors

    public Light(){
        _intensity = new Color(java.awt.Color.WHITE);
    }

    public Light(Color intensity){
        _intensity = new Color(intensity);
    }

    public Light(java.awt.Color intensity){
        _intensity = new Color(intensity);
    }

    //Methods

    public Color getIntensity(Point3D point){
        return new Color(_intensity);
    }
}
