package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector3D;

public class DirectionalLight extends Light implements LightSource {
    protected Vector3D _direction;

    //TODO: Test class functions.

    //Constructors

    public DirectionalLight(Vector3D direction){
        super();
        _direction = new Vector3D(direction);
    }

    public DirectionalLight(Color color, Vector3D direction){
        super(color);
        _direction = new Vector3D(direction);
    }

    public DirectionalLight(java.awt.Color color, Vector3D direction){
        super(color);
        _direction = new Vector3D(direction);
    }

    //Methods

    @Override
    public Color getIntensity(Point3D point) {
        return getIntensity();
    }

    @Override
    public Vector3D getLightDirectionTo(Point3D point) {
        return new Vector3D(_direction);
    }

    @Override
    public Color getIntensity() {
        return new Color(_color);
    }
}
