package primitives;

import java.math.BigDecimal;

public class Ray {
    private Point3D _point;
    private Vector3D _direction;

    //Constructors
    public Ray(Point3D point, Vector3D direction){
        this._point = new Point3D(point);
        this._direction = (new Vector3D(direction)).normalized();
    }

    public Ray(Vector3D direction){
        _point = new Point3D();
        _direction = new Vector3D(direction);
    }

    public Ray(Ray ray){
        _point = new Point3D(ray.get_point());
        _direction = (new Vector3D(ray.get_direction())).normalized();
    }

    //Getters
    public Point3D get_point() {
        return new Point3D(_point);
    }

    public Vector3D get_direction() {
        return new Vector3D(_direction);
    }

    //Methods
    public double angleBetween_rad(Ray ray){
        BigDecimal numerator = new BigDecimal(this.get_direction().dotProduct(ray.get_direction()));
        BigDecimal denom = new BigDecimal(Util.uscale(this.get_direction().length(), ray.get_direction().length()));

        double cos = numerator.doubleValue() / denom.doubleValue();

        if (cos > 1)
            cos = 1;

        return Math.acos(cos);
    }

    public double angleBetween_deg(Ray ray){
        return Math.toDegrees(this.angleBetween_rad(ray));
    }

    public boolean isParallelTo(Ray ray){
        double angle = this.angleBetween_deg(ray);

        if (angle == 0 || angle == 180)
            return true;

        return false;
    }

    public Point3D findIntersection(Ray ray){
        if (this.isParallelTo(ray)){
            return null;
        }

        Point3D P1 = this.get_point();
        Vector3D V1 = this.get_direction();

        Vector3D V2 = ray.get_direction();
        Point3D P2 = ray.get_point();

        //if true, the cross pruduct would be the 0 vector.
        if (V1.equals(V2) || V1.equals(V2.scale(-1))){
            return null;
        }

        Vector3D lhs = V1.crossProduct(V2);
        //If they share a common point. AND P2-P1 would result in ZERO vector ("rhs" in the next statement)
        if (P1.equals(P2)){
            return P1;
        }

        Vector3D tmp = P2.subtract(P1);
        if (new Ray(tmp).isParallelTo(new Ray(V2))){
            return P1;
        }

        Vector3D rhs = tmp.crossProduct(V2);
        Ray lhsR = new Ray(lhs);
        Ray rhsR = new Ray(rhs);

        if (lhsR.isParallelTo(rhsR)){
            double t = Math.abs(lhs.length() / rhs.length());
            return P1.add(V1.scale(t));
        }

        return null;
    }

    //Overrides
    @Override
    public boolean equals(Object obj) {
        //TODO: CHECK
        if (this == obj)
            return true;

        if (obj == null)
            return false;

        if (getClass() != obj.getClass())
            return false;

        Ray ray = (Ray)obj;

        return _point.equals(ray.get_point()) &&
                _direction.equals(ray.get_direction());
    }

    @Override
    public String toString() {
        return _point.toString() + " + " + _direction.toString();
    }
}
