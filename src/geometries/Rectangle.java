package geometries;

import primitives.*;

import java.util.ArrayList;

public class Rectangle extends Geometry {

    private double _width;//X Axis - Width: +Right, -Left
    private double _height;//Y Axis - Height: +Up, -Down

    private Ray _orientation;

    //Constructors

    public Rectangle(){
        _width = 40;
        _height = 80;

        _orientation = new Ray(new Vector3D(0,0,1));
    }

    public Rectangle(double width, double height, Ray orientation){
        _width = width;
        _height = height;

        _orientation = new Ray(orientation);
    }

    @Override
    public Vector3D get_normal(Point3D point) {
        return _orientation.get_direction();
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

        Point3D Prec = this._orientation.get_point();
        Vector3D Vrec = this._orientation.get_direction();

        Matrix3 R = Transform.getRodriguesRotation(Vrec, Vector3D.zAxis);
        Matrix3 RInv = R.inversed();

        Point3D q = R.mult(Prec);

        Point3D pointT = R.mult(point);

        if (pointT.equals(q)){
            pointT = new Point3D();
        }
        else{
            pointT = pointT.subtract(q).getPoint();
        }

        double px = Math.abs(pointT.getX().getCoord());
        double py = Math.abs(pointT.getY().getCoord());
        double pz = Math.abs(pointT.getZ().getCoord());

        return  (Util.equals(px, x) || px < x) &&
                (Util.equals(py, y) || py < y) &&
                Util.equals(pz, 0);
    }

    @Override
    public ArrayList<GeoPoint> findIntersections(Ray ray) {
        Plane plane = new Plane(_orientation.get_point(), _orientation.get_direction());

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
        _orientation.translate(x, y, z);
    }

    @Override
    public void rotate(double x, double y, double z) {
        _orientation.rotate(x, y, z);
    }

    @Override
    public void scale(double x, double y, double z) {
        _orientation.scale(x, y, z);
        _width = _width * x;
        _height = _height * y;
    }

    @Override
    public void scale(double scalar) {
        _orientation.scale(scalar);
        _width = _width * scalar;
        _height = _height * scalar;
    }

    @Override
    public void transform(Transform _transform) {
        _orientation.transform(_transform);
        _width = _width * _transform.getScale().getPoint().getX().getCoord();
        _height = _height * _transform.getScale().getPoint().getY().getCoord();
    }

    @Override
    public void transform(Vector3D translation, Vector3D rotation, Vector3D scale) {
        _orientation.transform(translation, rotation, scale);
        _width = _width * scale.getPoint().getX().getCoord();
        _height = _height * scale.getPoint().getY().getCoord();
    }
}
