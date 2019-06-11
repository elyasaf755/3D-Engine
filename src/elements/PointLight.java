package elements;

import primitives.Point3D;
import primitives.Vector3D;

public class PointLight extends Light implements LightSource{
    Point3D _origin;
    double _Kc, _Kl, _Kq;

    //TODO: Implement class

    //Constructors

    //Methods

    @Override
    public Vector3D getL(Point3D point) {
        //TODO: Implement
        return null;
    }

    @Override
    public Vector3D getD(Point3D point) {
        //TODO: Implement
        return null;
    }

}
