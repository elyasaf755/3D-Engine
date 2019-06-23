package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector3D;

public class PointLight extends Light implements LightSource{
    protected Point3D _origin;
    protected double _Kc, _Kl, _Ke;//Attenuation factors: constant, linear, and exponent. brightests when kc+kl*d+ke*d^2 = 1

    //TODO: Test class functions

    //Constructors

    public PointLight(Point3D origin){
        super();
        _origin = new Point3D(origin);
        _Kc = 1;
        _Kl = 0;
        _Ke = 0;
    }

    public PointLight(Color color, Point3D origin){
        super(color);
        _origin = new Point3D(origin);
        _Kc = 1;
        _Kl = 0;
        _Ke = 0;
    }

    public PointLight(java.awt.Color color, Point3D origin){
        super(color);
        _origin = new Point3D(origin);
        _Kc = 1;
        _Kl = 0;
        _Ke = 0;
    }

    public PointLight(Point3D origin, double Kc, double Kl, double Kq){
        super();

        _origin = new Point3D(origin);
        _Kc = Kc;
        _Kl = Kl;
        _Ke = Kq;
    }

    public PointLight(Color color, Point3D origin, double Kc, double Kl, double Kq){
        super(color);

        _origin = new Point3D(origin);
        _Kc = Kc;
        _Kl = Kl;
        _Ke = Kq;
    }

    public PointLight(java.awt.Color color, Point3D origin, double Kc, double Kl, double Kq){
        super(color);

        _origin = new Point3D(origin);
        _Kc = Kc;
        _Kl = Kl;
        _Ke = Kq;
    }

    //Methods

    @Override
    public Color getIntensity() {
        return new Color(_color);
    }

    @Override
    public Color getIntensity(Point3D point) {
        //TODO: Is it done right? (is l0 = _color in the slides?)

        double distance = point.distance(_origin);

        double attenuation = _Kc + _Kl * distance + _Ke * distance * distance;

        return _color.reduce(attenuation);
    }

    @Override
    public Vector3D getLightDirectionTo(Point3D point) {
        //TODO: Q: THROW ZERO VECTOR EXCEPTION?
        if (_origin.equals(point)){
            return null;
        }

        return point.subtract(_origin);
    }

    @Override
    public double getDistanceFrom(Point3D point) {
        return this._origin.distance(point);
    }
}
