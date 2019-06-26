package primitives;

public class Vector3D implements ITransformable {
    protected Point3D _point;

    public final static Vector3D ZERO = new Vector3D();
    public final static Vector3D xAxis = new Vector3D(1,0,0);
    public final static Vector3D yAxis = new Vector3D(0,1,0);
    public final static Vector3D zAxis = new Vector3D(0,0,1);

    //Constructors
    public Vector3D(Coordinate x, Coordinate y, Coordinate z) {
        if (x.equals(Coordinate.ZERO) && y.equals(Coordinate.ZERO) && z.equals(Coordinate.ZERO)){
            throw new IllegalArgumentException("t(0,0,0) is not a vector");
        }

        _point = new Point3D(x,y,z);
    }

    public Vector3D(double x, double y, double z) {
        if (new Coordinate(x).equals(Coordinate.ZERO) && new Coordinate(y).equals(Coordinate.ZERO) && new Coordinate(z).equals(Coordinate.ZERO)){
            throw new IllegalArgumentException("t(0,0,0) is not a vector");
        }

        _point = new Point3D(x,y,z);
    }

    public Vector3D(Point3D point3D){
        if (point3D.getX().equals(Coordinate.ZERO) && point3D.getY().equals(Coordinate.ZERO) && point3D.getZ().equals(Coordinate.ZERO)){
            throw new IllegalArgumentException("t(0,0,0) is not a vector");
        }
        _point = new Point3D(point3D);
    }

    public Vector3D(Point3D start, Point3D end) {
        if (start.equals(end))
            throw new IllegalArgumentException("Can't sub 2 equal points. t(0,0,0) is not a vector");

        Vector3D result = end.subtract(start);

        _point = new Point3D(result.getPoint());
    }

    public Vector3D(Vector3D vector3D){
        _point = new Point3D(vector3D._point);
    }

    private Vector3D(){
        _point = new Point3D();
    }

    //Getters
    public Point3D getPoint() {
        return new Point3D(_point);
    }

    //Setters

    public void set_point(double x, double y, double z) {
        this._point.set_x(x);
        this._point.set_y(y);
        this._point.set_z(z);
    }

    public void set_point(Coordinate x, Coordinate y, Coordinate z) {
        this._point.set_x(x);
        this._point.set_y(y);
        this._point.set_z(z);
    }

    public void set_point(Point3D point) {
        this._point.set_point(point);
    }

    public void set_vector(Vector3D vector) {
        this._point.set_point(vector.getPoint());
    }

    //Methods

    public Vector3D subtract(Vector3D vector){
        if (this.equals(vector)){
            throw new IllegalArgumentException("Cant sub 2 equal vectors. t(0,0,0) is not a vector");
        }

        return new Vector3D(_point.subtract(vector._point));
    }

    public Vector3D add(Vector3D vector){
        if (this.equals(Vector3D.ZERO)){
            return new Vector3D(vector);
        }

        if (this.scaled(-1).equals(vector))
            throw new IllegalArgumentException("Can't add vectors with the negative directions. t(0,0,0) is not a vector");

        return new Vector3D(_point.add(vector));
    }

    public double dotProduct(Vector3D vector3D){
        Point3D point3D = vector3D._point;

        return point3D._x.multiply(_point._x).add(point3D._y.multiply(_point._y).add(point3D._z.multiply(_point._z))).getCoord();
    }

    /*
    public Vector3D crossProduct(Vector3D vector3D){
        double x1 = this._point.getX().getCoord();
        double y1 = this._point.getY().getCoord();
        double z1 = this._point.getZ().getCoord();

        double x2 = vector3D.getPoint().getX().getCoord();
        double y2 = vector3D.getPoint().getY().getCoord();
        double z2 = vector3D.getPoint().getZ().getCoord();

        return new Vector3D((y1*z2)-(z1*y2),
                (z1*x2)-(x1*z2),
                (x1*y2)-(y1*x2));
    }

     */

    public Vector3D crossProduct(Vector3D vector3D){
        Point3D rhs = vector3D._point;

        return new Vector3D(_point._y.multiply(rhs._z).subtract(_point._z.multiply(rhs._y)),
                _point._z.multiply(rhs._x).subtract(_point._x.multiply(rhs._z)),
                _point._x.multiply(rhs._y).subtract(_point._y.multiply(rhs._x)));
    }

    public Matrix3 outterProduct(Vector3D vector){
        double u[] = {
                this.getPoint().getX().getCoord(),
                this.getPoint().getY().getCoord(),
                this.getPoint().getZ().getCoord()
        };

        double v[] = {
                vector.getPoint().getX().getCoord(),
                vector.getPoint().getY().getCoord(),
                vector.getPoint().getZ().getCoord()
        };

        double[][] result = new double[3][3];

        for(int i = 0; i < 3; ++i){
            for (int j = 0; j < 3; ++j){
                Coordinate temp = new Coordinate(u[i]*v[j]);
                result[i][j] = temp.getCoord();
            }
        }

        return new Matrix3(result);
    }

    public double length(){

        return _point.distance(new Point3D(0, 0, 0));
    }

    public double lengthSquared(){
        return Util.uscale(length(), length());
    }

    public Vector3D scaled(double scalar){
        return new Vector3D(_point._x.scale(scalar),
                _point._y.scale(scalar),
                _point._z.scale(scalar));
    }

    public Vector3D normalized(){
        Vector3D vector3 = new Vector3D(this);

        vector3.normalize();

        return vector3;
    }


    public void normalize(){
        double length = length();

        if (length() == 0)
            _point._x = _point._y = _point._z = new Coordinate(0);

        _point._x = new Coordinate(_point._x.scale(1.0/length));
        _point._y = new Coordinate(_point._y.scale(1.0/length));
        _point._z = new Coordinate(_point._z.scale(1.0/length));
    }

    public double squared(){
        return this.dotProduct(this);
    }

    public double angleBetween_rad(Vector3D vector){
        return new Ray(this).angleBetween_rad(new Ray(vector));
        //TODO: TEST
    }

    public double angleBetween_deg(Vector3D vector){
        return Math.toDegrees(this.angleBetween_rad(vector));
        //TODO: TEST
    }

    public Vector3D projection(Vector3D projector){
        double lhs = this.dotProduct(projector);
        double rhs = this.dotProduct(this);

        if (Util.equals(rhs, 0)){
            throw new IllegalArgumentException("Can't devide by zero.");
        }

        if (Util.equals(lhs, 0)){
            return new Vector3D(Vector3D.ZERO);
        }

        return new Vector3D(this.scaled(Util.udiv(lhs, rhs)));
    }

    @Override
    public void translate(double x, double y, double z) {
        return;//Vectors are not translated.
    }

    @Override
    public void rotate(double x, double y, double z) {
        Transform transform = new Transform();
        transform.setRotation(x, y, z);

        Point3D result = transform.getTransformation().mult(new Vector3D(this)).getPoint();

        _point.set_point(result);
    }

    @Override
    public void scale(double x, double y, double z) {
        Transform transform = new Transform();
        transform.setScale(x, y, z);

        Point3D result = transform.getTransformation().mult(new Vector3D(this)).getPoint();

        _point = new Point3D(result);
    }

    @Override
    public void scale(double scalar) {
        Transform transform = new Transform();
        transform.setScale(scalar);

        Point3D result = transform.getTransformation().mult(new Vector3D(this)).getPoint();

        _point = new Point3D(result);
    }

    @Override
    public void transform(Transform _transform) {
        _transform.setTranslation(Vector3D.ZERO);//Vectors are not translated

        Vector3D result = _transform.getTransformation().mult(new Vector3D(this));

        _point = new Point3D(result.getPoint());
    }

    @Override
    public void transform(Vector3D translation, Vector3D rotation, Vector3D scale) {
        Transform transform = new Transform(Vector3D.ZERO, rotation, scale);//Vectors are not translated

        Point3D result = transform.getTransformation().mult(new Vector3D(this)).getPoint();

        _point = new Point3D(result);
    }

    //Overrides

    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;

        if (obj == null)
            return false;

        if (getClass() != obj.getClass())
            return false;

        Vector3D vector3D = (Vector3D)obj;

        return _point.equals(vector3D._point);
    }

    @Override
    public String toString() {
        return "t" + _point.toString();
    }
}
