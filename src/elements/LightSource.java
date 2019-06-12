package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector3D;

public interface LightSource {
    Color getIntensity(Point3D point);
    Vector3D getLightDirectionTo(Point3D point);
}
