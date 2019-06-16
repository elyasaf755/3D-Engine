package primitives;

import static java.lang.Math.sqrt;

public class Point3D extends Point2D implements ITransform{
    protected Coordinate _z;

    //Constructors
    public Point3D(Coordinate x, Coordinate y, Coordinate z){
        super(x, y);

        this._z = new Coordinate(z);
    }

    public Point3D(double x, double y, double z){
        super(x, y);

        this._z = new Coordinate(z);
    }

    public Point3D(Point3D point){
        super(point._x, point._y);

        _z = new Coordinate(point._z);
    }

    public Point3D(){
        super();

        _z = new Coordinate(0);
    }

    //Getters
    public Coordinate getZ() {
        return new Coordinate(_z);
    }

    //Methods
    public Vector3D subtract(Point3D point3d) {
        Point2D point2d = subtract(new Point2D(point3d._x, point3d._y));
        Coordinate z = _z.subtract(point3d._z);

        return new Vector3D(point2d._x, point2d._y, z);
    }

    public Point3D add(Vector3D vector3D){
        return new Point3D(_x.add(vector3D._point._x), _y.add(vector3D._point._y), _z.add(vector3D._point._z));
    }

    public Point3D add(Point3D point){
        if (point.equals(new Point3D())){
            return this.add(Vector3D.ZERO);
        }

        return this.add(new Vector3D(point));
    }

    public void add(Point3D point, Vector3D vector) {
        //TODO: Check
        this._x = new Coordinate(Util.uadd(vector.getPoint().getX().getCoord(), point.getX().getCoord()));
        this._y = new Coordinate(Util.uadd(vector.getPoint().getY().getCoord(), point.getY().getCoord()));
        this._z = new Coordinate(Util.uadd(vector.getPoint().getZ().getCoord(), point.getZ().getCoord()));

    }

    public double distanceSquared(Point3D point3D){
        Point3D result = this.subtract(point3D)._point;

        return Util.uadd(Util.uadd(result._x.multiply(result._x)._coord, result._y.multiply(result._y)._coord), result._z.multiply(result._z)._coord);
    }

    public double distance(Point3D point3D){
        return Util.alignZero(sqrt(distanceSquared(point3D)));
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

        Point3D point3D = (Point3D)obj;

        return  _x.equals(point3D._x) &&
                _y.equals(point3D._y) &&
                _z.equals(point3D._z);
    }

    @Override
    public String toString() {
        return "(" + _x.toString() + ", " +_y.toString() + ", " + _z.toString() + ")";
    }

    @Override
    public void translate(double x, double y, double z) {
        Transform transform = new Transform();
        transform.setTranslation(x, y, z);

        Point3D result = transform.getTransformation().mult(this);

        _x = result.getX();
        _y = result.getY();
        _z = result.getZ();
    }

    @Override
    public void rotate(double x, double y, double z) {
        Transform transform = new Transform();
        transform.setRotation(x, y, z);

        Point3D result = transform.getTransformation().mult(this);

        _x = result.getX();
        _y = result.getY();
        _z = result.getZ();
    }

    @Override
    public void scale(double x, double y, double z) {
        Transform transform = new Transform();
        transform.setScale(x, y, z);

        Point3D result = transform.getTransformation().mult(this);
        _x = result.getX();
        _y = result.getY();
        _z = result.getZ();
    }

    @Override
    public void transform(Transform _transform) {
        Point3D result = _transform.getTransformation().mult(this);

        _x = result.getX();
        _y = result.getY();
        _z = result.getZ();
    }

    @Override
    public void transform(Vector3D translation, Vector3D rotation, Vector3D scale) {
        Transform transform = new Transform(translation, rotation, scale);

        Point3D result = transform.getTransformation().mult(this);

        _x = result.getX();
        _y = result.getY();
        _z = result.getZ();
    }
}
