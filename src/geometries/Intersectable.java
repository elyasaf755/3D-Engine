package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector3D;

import java.util.ArrayList;

public interface Intersectable {
    static class GeoPoint{//TODO: Add normal field? Change name to Intersection?
        public Point3D point;
        public Geometry geometry;

        public GeoPoint(Geometry _geometry, Point3D _point){
            point = new Point3D(_point);
            geometry = _geometry;
        }

        public GeoPoint(GeoPoint geoPoint){
            point = new Point3D(geoPoint.point);
            geometry = geoPoint.geometry;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;

            if (obj == null)
                return false;

            if (!(obj instanceof GeoPoint))
                return false;

            GeoPoint geoPoint = (GeoPoint) obj;

            return point.equals(geoPoint.point) &&
                    geometry.equals(geoPoint.geometry);
        }

        @Override
        public String toString() {
            return point.toString() + " " + geometry.toString();
        }
    }

    ArrayList<GeoPoint> findIntersections(Ray ray);

}
