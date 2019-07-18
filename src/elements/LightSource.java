package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector3D;

/**
 * every light source has to implement this interface
 */
public interface LightSource {

    /**
     * get the color in the point
     * @param point
     * @return color from this light source in the point
     */
    Color getIntensity(Point3D point);

    /**
     * get direction of the light in the point
     * @param point
     * @return direction of the light in this point
     */
    Vector3D getLightDirectionTo(Point3D point);

    /**
     * get the distance of the light source from the given point
     * @param point
     * @return the distance of the light source
     */
    double getDistanceFrom(Point3D point);
}
