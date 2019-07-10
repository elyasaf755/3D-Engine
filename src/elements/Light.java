package elements;

import primitives.Color;
import primitives.Point3D;

/**
 * light
 */
public abstract class Light {
    protected Color _color;


    //Constructors

    /**
     * default constructor
     */
    public Light(){
        _color = new Color(java.awt.Color.WHITE);
    }

    /**
     * constructor
     * @param color (from local class 'color')
     */
    public Light(Color color){
        _color = new Color(color);
    }

    /**
     * constructor
     * @param color (from java.awt.Color)
     */
    public Light(java.awt.Color color){
        _color = new Color(color);
    }

    //Methods

    /**
     * abstract
     * @return color of light
     */
    public abstract Color getIntensity();
}
