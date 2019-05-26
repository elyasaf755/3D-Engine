package geometries;

import primitives.Point3D;
import primitives.Vector3D;

public interface IGeometry extends Intersectable {
    Vector3D get_normal(Point3D point3D);
}
