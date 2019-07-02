package elements;

import primitives.Color;
import primitives.Point3D;

public abstract class Light {
    protected Color _color;


    //Constructors

    public Light(){
        _color = new Color(java.awt.Color.WHITE);
    }

    public Light(Color color){
        _color = new Color(color);
    }

    public Light(java.awt.Color color){
        _color = new Color(color);
    }

    //Methods

    //return the color of the light
    public abstract Color getIntensity();
}
