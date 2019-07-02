package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector3D;

public class DirectionalLight extends Light implements LightSource {
    protected Vector3D _direction;//direction of light without source (like sun)


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

    //get the distance between the point to the source light. in this case- it defined to be infinity because it doesn't has any source
    @Override
    public double getDistanceFrom(Point3D point) {
        return Double.POSITIVE_INFINITY;
    }

    @Override
    public Color getIntensity() {
        return new Color(_color);
    }
}
