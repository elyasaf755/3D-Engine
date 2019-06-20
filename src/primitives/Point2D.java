package primitives;

public class Point2D {
    protected Coordinate _x;
    protected Coordinate _y;

    //Constructors

    public Point2D(Coordinate x, Coordinate y){
        this._x = new Coordinate(x);
        this._y = new Coordinate(y);
    }

    public Point2D(double x, double y){
        this._x = new Coordinate(x);
        this._y = new Coordinate(y);
    }

    public Point2D(Point2D point){
        _x = new Coordinate(point._x);
        _y = new Coordinate(point._y);
    }

    public Point2D(){
        _x = new Coordinate(0);
        _y = new Coordinate(0);
    }

    //Getters

    public Coordinate getX() {
        return new Coordinate(_x);
    }

    public Coordinate getY() {
        return new Coordinate(_y);
    }

    //Setters

    public void set_x(Coordinate x) {
        this._x.set_coord(x);
    }

    public void set_y(Coordinate y) {
        this._y.set_coord(y);
    }

    public void set_x(double x) {
        this._x.set_coord(x);
    }

    public void set_y(double y) {
        this._y.set_coord(y);
    }

    public void set_point(double x, double y){
        _x.set_coord(x);
        _y.set_coord(y);
    }

    public void set_point(Coordinate x, Coordinate y){
        _x.set_coord(x);
        _y.set_coord(y);
    }

    public void set_point(Point2D point){
        _x.set_coord(point.getX());
        _y.set_coord(point.getY());
    }

    //Methods

    public Point2D subtract(Point2D point){
        return new Point2D(_x.subtract(point._x), _y.subtract(point._y));
    }

    public Point2D add(Point2D point){
        return new Point2D(_x.add(point._x), _y.add(point._y));
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

        Point2D point2D = (Point2D)obj;

        return _x.equals(point2D._x) &&
                _y.equals(point2D._y);
    }

    @Override
    public String toString() {
        return "(" + _x.toString() + ", " + _y.toString() + ")";
    }

}
