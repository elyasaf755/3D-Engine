package geometries;

import primitives.Color;
import primitives.Material;
import primitives.Point3D;
import primitives.Vector3D;


public abstract class IGeometry implements Intersectable {

    Material _material;
    Color _emmission;


    abstract Vector3D get_normal(Point3D point3D);
}
