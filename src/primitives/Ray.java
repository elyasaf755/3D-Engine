package primitives;

public class Ray {
    Point3D _point;
    Vector3D _direction;

    //Constructors
    public Ray(Point3D point, Vector3D direction){
        this._point = new Point3D(point);
        this._direction = (new Vector3D(direction)).normalized();
    }

    public Ray(Ray ray){
        _point = new Point3D(ray._point);
        _direction = (new Vector3D(ray._direction)).normalized();
    }

    //Getters
    public Point3D get_point() {
        return new Point3D(_point);
    }

    public Vector3D get_direction() {
        return new Vector3D(_direction);
    }

    //Overrides
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;

        if (obj == null)
            return false;

        if (getClass() != obj.getClass())
            return false;

        Ray ray = (Ray)obj;

        return _point.equals(ray._point) &&
                _direction.equals(ray._direction);
    }

    @Override
    public String toString() {
        return _point.toString() + " + " + _direction.toString();
    }

}
