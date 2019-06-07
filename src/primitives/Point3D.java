package primitives;

import static java.lang.Math.sqrt;

public class Point3D extends Point2D{
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

    //TODO: Check
    public void add(Point3D point, Vector3D vector) {

        this._x = new Coordinate(Util.uadd(vector.getPoint().getX().getCoord(), point.getX().getCoord()));
        this._y = new Coordinate(Util.uadd(vector.getPoint().getY().getCoord(), point.getY().getCoord()));
        this._z = new Coordinate(Util.uadd(vector.getPoint().getZ().getCoord(), point.getZ().getCoord()));

    }

    public double distanceSquared(Point3D point3D){
        Point3D result = subtract(point3D)._point;

        return result._x.multiply(result._x)._coord + result._y.multiply(result._y)._coord + result._z.multiply(result._z)._coord;
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

        return _x.equals(point3D._x) &&
                _y.equals(point3D._y) &&
                _z.equals(point3D._z);
    }

    @Override
    public String toString() {
        return "(" + _x.toString() + ", " +_y.toString() + ", " + _z.toString() + ")";
    }

}
