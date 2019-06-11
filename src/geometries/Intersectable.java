package geometries;

import primitives.Point3D;
import primitives.Ray;

import java.util.ArrayList;

public interface Intersectable {
    static class GeoPoint{
        public Point3D point;
        public Geometry geometry;

        public GeoPoint(Geometry _geometry, Point3D _point){
            point = new Point3D(_point);
            geometry = _geometry;
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
    }

    ArrayList<GeoPoint> findIntersections(Ray ray);

}
