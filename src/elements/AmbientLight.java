package elements;

import primitives.Color;

public class AmbientLight {
    private Color _intensity;
    private double _kA;

    public AmbientLight(){
        _intensity = new Color(java.awt.Color.WHITE);
        _kA = 0.3;
    }

    public AmbientLight(Color color){
        _intensity = color;
        _kA = 0.3;
    }

    public AmbientLight(Color color, double kA){
        _intensity = color;
        _kA = kA;
    }


    public Color getIntensity(){
        //TODO: Implement
        return new Color(_intensity);
    }
}
