package primitives;

import java.math.BigDecimal;

public class Ray implements ITransformable {
    private Point3D _point;
    private Vector3D _direction;

    //Optimizing AABB
    private Vector3D _invDirection;
    private int[] sign;

    private void initPrivates(){
        this._invDirection = new Vector3D(
                1 / _direction.getPoint().getX().getCoord(),
                1 / _direction.getPoint().getY().getCoord(),
                1 / _direction.getPoint().getZ().getCoord()
        );

        sign = new int[3];

        sign[0] = _invDirection.getPoint().getX().getCoord() < 0 ? 1 : 0;
        sign[1] = _invDirection.getPoint().getY().getCoord() < 0 ? 1 : 0;
        sign[2] = _invDirection.getPoint().getZ().getCoord() < 0 ? 1 : 0;
    }

    //Constructors
    public Ray(Point3D point, Vector3D direction){
        this._point = new Point3D(point);
        this._direction = (new Vector3D(direction)).normalized();

        initPrivates();
    }

    public Ray(Vector3D direction){
        _point = new Point3D();
        _direction = new Vector3D(direction);

        initPrivates();
    }

    public Ray(Ray ray){
        _point = new Point3D(ray.get_point());
        _direction = (new Vector3D(ray.get_direction())).normalized();

        initPrivates();
    }

    //Getters

    public Point3D get_point() {
        return new Point3D(_point);
    }

    public Vector3D get_direction() {
        return new Vector3D(_direction);
    }

    public Vector3D get_invDirection() {
        return new Vector3D(_invDirection);
    }

    public int[] getSign() {
        return sign;
    }

    //Setters

    public void set_point(Point3D point) {
        Coordinate x  = point.getX();
        Coordinate y = point.getY();
        Coordinate z = point.getZ();

        this._point.set_x(x);
        this._point.set_y(y);
        this._point.set_z(z);
    }

    public void set_direction(Vector3D direction) {
        this._direction.set_point(direction.getPoint());

        initPrivates();
    }

    public void set_ray(Point3D point, Vector3D direction){
        _point.set_point(point);
        _direction.set_vector(direction);

        initPrivates();
    }

    public void set_ray(Ray ray){
        this._point.set_point(ray.get_point());
        this._direction.set_vector(ray.get_direction());

        initPrivates();
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

        if (Util.equals(angle, 0) || Util.equals(angle, 180))
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
        if (V1.equals(V2) || V1.equals(V2.scaled(-1))){
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
            return P1.add(V1.scaled(t));
        }

        return null;
    }

    @Override
    public void translate(double x, double y, double z) {
        _point.translate(x, y, z);
    }

    @Override
    public void rotate(double x, double y, double z) {
        _point.rotate(x, y, z);
        _direction.rotate(x, y, z);
        _direction.normalize();
    }

    @Override
    public void scale(double x, double y, double z) {
        _point.scale(x, y, z);
        _direction.scale(x, y, z);
        _direction.normalize();
    }

    @Override
    public void scale(double scalar) {
        _point.scale(scalar);
        _direction.scale(scalar);
        _direction.normalize();
    }

    @Override
    public void transform(Transform _transform) {
        _point.transform(_transform);
        _direction.transform(_transform);
        _direction.normalize();
    }

    @Override
    public void transform(Vector3D translation, Vector3D rotation, Vector3D scale) {
        _point.transform(translation, rotation, scale);
        _direction.transform(translation, rotation, scale);
        _direction.normalize();
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

        return _point.equals(ray.get_point()) &&
                _direction.equals(ray.get_direction());
    }

    @Override
    public String toString() {
        return _point.toString() + " + " + _direction.toString();
    }

}
