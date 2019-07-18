package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector3D;

/**
 * point light with direction
 */
public class SpotLight extends PointLight{
    protected Vector3D _direction;


    //Constructors

    /**
     * Constructor
     * @param origin the place of the source light
     * @param direction of light
     */
    public SpotLight(Point3D origin, Vector3D direction){
        super(origin, 1, 0.1, 0.1);
        _direction = new Vector3D(direction);
    }

    /**
     * Constructor
     * @param color of light (from local class 'color')
     * @param origin the place of the source light
     * @param direction of light
     */
    public SpotLight(Color color, Point3D origin, Vector3D direction){
        super(color, origin, 1, 0.1, 0.1);
        _direction = new Vector3D(direction);
    }

    /**
     * Constructor
     * @param color of light (from java.awt.Color)
     * @param origin the place of the source light
     * @param direction of light
     */
    public SpotLight(java.awt.Color color, Point3D origin, Vector3D direction){
        super(color, origin, 1, 0.1, 0.1);
        _direction = new Vector3D(direction);
    }

    /**
     * Constructor
     * @param origin the place of the source light
     * @param direction of light
     * @param Kc Attenuation factor: constant
     * @param Kl Attenuation factor: linear (Relative to the distance)
     * @param Ke Attenuation factor: exponent (Relative to the distance)
     */
    public SpotLight(Point3D origin, Vector3D direction, double Kc, double Kl, double Ke){
        super(origin, Kc, Kl, Ke);
        _direction = new Vector3D(direction);
    }

    /**
     * Constructor
     * @param color of light (from local class 'color')
     * @param origin the place of the source light
     * @param direction of light
     * @param Kc Attenuation factor: constant
     * @param Kl Attenuation factor: linear (Relative to the distance)
     * @param Ke Attenuation factor: exponent (Relative to the distance)
     */
    public SpotLight(Color color, Point3D origin, Vector3D direction, double Kc, double Kl, double Ke){
        super(color, origin, Kc, Kl, Ke);
        _direction = new Vector3D(direction);
    }

    /**
     * Constructor
     * @param color of light (from java.awt.Color)
     * @param origin the place of the source light
     * @param direction of light
     * @param Kc Attenuation factor: constant
     * @param Kl Attenuation factor: linear (Relative to the distance)
     * @param Ke Attenuation factor: exponent (Relative to the distance)
     */
    public SpotLight(java.awt.Color color, Point3D origin, Vector3D direction, double Kc, double Kl, double Ke){
        super(color, origin, Kc, Kl, Ke);
        _direction = new Vector3D(direction);
    }

    //Methods


    @Override
    public Color getIntensity(Point3D point) {
        double distance = point.distance(_origin);

        double attenuation = _Kc + _Kl * distance + _Ke * distance * distance;

        return _color.scale(Math.abs(_direction.normalized().dotProduct(getLightDirectionTo(point).normalized()))).reduce(attenuation);
    }
}
