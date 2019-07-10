package elements;

import primitives.Color;
import primitives.Point3D;

/**
 * Ambient Light of the scene
 */
public class AmbientLight extends Light{
    protected Color _color;
    protected double _kA;//intensity factor, between 0 and 1 !!! 1 = brightests, 0 = dimmest


    //Constructors

    /**
     * Constructor
     */
    public AmbientLight(){
        _color = new Color(java.awt.Color.WHITE);
        _kA = 0.3;
    }

    /**
     * Constructor
     * @param color of the light
     */
    public AmbientLight(Color color){
        _color = color;
        _kA = 0.3;
    }

    /**
     * Constructor
     * @param kA intensity factor
     */
    public AmbientLight(double kA){
        _color = new Color(java.awt.Color.WHITE);
        _kA = kA;
    }

    /**
     * Constructor
     * @param color of the light
     * @param kA intensity factor
     */
    public AmbientLight(Color color, double kA){
        _color = color;
        _kA = kA;
    }

    //Getters

    /**
     * getter (intensity)
     * @return color
     */
    public Color getIntensity(){
        return _color.scale(_kA);
    }

    /**
     *  getter (intensity factor ka)
     * @return ka (between 0 and 1 ! [1 = brightests, 0 = dimmest])
     */
    public double get_kA() {
        return _kA;
    }
}
