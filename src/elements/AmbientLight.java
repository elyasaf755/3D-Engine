package elements;

import primitives.Color;

public class AmbientLight {
    private Color _color;
    private double _kA;

    public AmbientLight(){
        _color = new Color(java.awt.Color.WHITE);
        _kA = 0.3;
    }

    public AmbientLight(Color color){
        _color = color;
        _kA = 0.3;
    }

    public AmbientLight(Color color, double kA){
        _color = color;
        _kA = kA;
    }


    public Color getIntensity(){
        //TODO: Implement
        return null;
    }
}
