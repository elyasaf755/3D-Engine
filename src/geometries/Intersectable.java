package geometries;

import primitives.Point3D;
import primitives.Ray;

import java.util.ArrayList;

public interface Intersectable {
    ArrayList<Point3D> findIntersections(Ray ray);

}
