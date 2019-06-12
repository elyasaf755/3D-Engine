package elements;

import primitives.Color;
import primitives.Point3D;

public class AmbientLight extends Light{
    protected Color _color;
    protected double _kA;//intensity factor, between 0 and 1 !!! 1 = brightests, 0 = dimmest

    //TODO: Test class functions.

    //Constructors

    public AmbientLight(){
        _color = new Color(java.awt.Color.WHITE);
        _kA = 0.3;
    }

    public AmbientLight(Color color){
        _color = color;
        _kA = 0.3;
    }

    public AmbientLight(double kA){
        _color = new Color(java.awt.Color.WHITE);
        _kA = kA;
    }

    public AmbientLight(Color color, double kA){
        _color = color;
        _kA = kA;
    }

    //Getters

    public Color getIntensity(){
        return _color.scale(_kA);
    }

    public double get_kA() {
        return _kA;
    }
}
