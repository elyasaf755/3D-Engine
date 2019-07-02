package geometries;

import primitives.*;

import java.util.ArrayList;

public class Rectangle extends Geometry {

    private double _width;//X Axis - Width: +Right, -Left
    private double _height;//Y Axis - Height: +Up, -Down

    private Ray _ray;

    private Matrix3 _R;//Default = Z axis
    private Matrix3 _RInv;
    private Point3D _q;

    private void initTransformFields(){
        _R = new Matrix3(Transform.getRodriguesRotation(_ray.get_direction(), Vector3D.zAxis));
        _RInv = new Matrix3(_R.inversed());
        _q = new Point3D(_R.mult(_ray.get_point()));
    }

    private void init(){
        initTransformFields();

        //TODO: TEST
        updateAABB();
    }

    //Constructors

    public Rectangle(){
        _width = 40;
        _height = 80;

        _ray = new Ray(new Vector3D(0,0,1));

        init();
    }

    public Rectangle(double width, double height, Ray orientation){
        _width = width;
        _height = height;

        _ray = new Ray(orientation);

        init();
    }

    @Override
    public Vector3D get_normal(Point3D point) {
        return _ray.get_direction();
    }

    @Override
    public boolean contains(Point3D point) {
        return surfaceContains(point);
    }

    @Override
    public boolean surfaceContains(Point3D point) {
        //when rectangle direction is Z
        double x = _width / 2.0;//X Axis - Width: +Right, -Left
        double y = _height / 2.0;//Y Axis - Height: +Up, -Down

        Point3D Prec = this._ray.get_point();
        Vector3D Vrec = this._ray.get_direction();

        Point3D pointT = _R.mult(point);

        if (pointT.equals(_q)){
            pointT = new Point3D();
        }
        else{
            pointT = pointT.subtract(_q).getPoint();
        }

        double px = Math.abs(pointT.getX().getCoord());
        double py = Math.abs(pointT.getY().getCoord());
        double pz = Math.abs(pointT.getZ().getCoord());

        return  (Util.equals(px, x) || px < x) &&
                (Util.equals(py, y) || py < y) &&
                Util.equals(pz, 0);
    }

    //TODO:
    @Override
    public void updateAABB() {
        //when rectangle direction is Z
        double x = _width / 2.0;//X Axis - Width: +Right, -Left
        double y = _height / 2.0;//Y Axis - Height: +Up, -Down

        Point3D ruT = new Point3D(x,y,0);
        Point3D luT = new Point3D(-x,y,0);
        Point3D rdT = new Point3D(x,-y,0);
        Point3D ldT = new Point3D(-x,-y,0);

        Point3D ru = _RInv.mult(ruT).add(_q);
        Point3D lu = _RInv.mult(luT).add(_q);
        Point3D rd = _RInv.mult(rdT).add(_q);
        Point3D ld = _RInv.mult(ldT).add(_q);

        set_min(ru.min(lu,rd,ld));
        set_max(ru.max(lu,rd,ld));
    }

    @Override
    public ArrayList<GeoPoint> findIntersections(Ray ray) {
        //TODO:TEST
        if(!intersects(ray)){
            return new ArrayList<>();
        }

        Plane plane = new Plane(_ray.get_point(), _ray.get_direction());

        ArrayList<GeoPoint> intersections = plane.findIntersections(ray);

        ArrayList<GeoPoint> result = new ArrayList<>();

        for (GeoPoint intersection : intersections){
            if (surfaceContains(intersection.point)){
                result.add(new GeoPoint(this, intersection.point));
            }
        }

        return result;
    }

    @Override
    public void translate(double x, double y, double z) {
        _ray.translate(x, y, z);

        //TODO: TEST
        updateAABB();
    }

    @Override
    public void rotate(double x, double y, double z) {
        _ray.rotate(x, y, z);

        //TODO: TEST
        updateAABB();
    }

    @Override
    public void scale(double x, double y, double z) {
        _ray.scale(x, y, z);
        _width = _width * x;
        _height = _height * y;

        //TODO: TEST
        updateAABB();
    }

    @Override
    public void scale(double scalar) {
        _ray.scale(scalar);
        _width = _width * scalar;
        _height = _height * scalar;

        //TODO: TEST
        updateAABB();
    }

    @Override
    public void transform(Transform _transform) {
        _ray.transform(_transform);
        _width = _width * _transform.getScale().getPoint().getX().getCoord();
        _height = _height * _transform.getScale().getPoint().getY().getCoord();

        //TODO: TEST
        updateAABB();
    }

    @Override
    public void transform(Vector3D translation, Vector3D rotation, Vector3D scale) {
        _ray.transform(translation, rotation, scale);
        _width = _width * scale.getPoint().getX().getCoord();
        _height = _height * scale.getPoint().getY().getCoord();

        //TODO: TEST
        updateAABB();
    }
}
