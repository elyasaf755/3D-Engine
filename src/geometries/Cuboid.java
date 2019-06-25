package geometries;

import primitives.*;

import java.util.ArrayList;

public class Cuboid extends Geometry {

    private Ray _ray;

    private double _width;//a - half width
    private double _length;//b - half length
    private double _height;//c - half height

    Plane[] _faces;

    //DEFAULT : Z = LENGTH, Y = HEIGHT, X  = WIDTH

    //Initializers
    private void initPlanes(double width, double length, double height){
        _faces = new Plane[6];

        double a = width / 2.0;
        double b = length / 2.0;
        double c = height / 2.0;

        Vector3D front = new Vector3D(0,0,-1).scaled(b);
        Vector3D back = front.scaled(-1);
        Vector3D right = new Vector3D(1,0,0).scaled(a);
        Vector3D left = right.scaled(-1);
        Vector3D up = new Vector3D(0,1,0).scaled(c);
        Vector3D down = up.scaled(-1);

        _faces[0] = new Plane(front.getPoint(), front);
        _faces[1] = new Plane(back.getPoint(), back);
        _faces[2] = new Plane(right.getPoint(), right);
        _faces[3] = new Plane(left.getPoint(), left);
        _faces[4] = new Plane(up.getPoint(), up);
        _faces[5] = new Plane(down.getPoint(), down);
    }

    private void initPlanes(double width, double length, double height, Ray ray){
        _faces = new Plane[6];

        double a = width / 2.0;//x Axis when cube aligned with Y
        double b = length / 2.0;//z Axis
        double c = height / 2.0;//y Axis

        Vector3D frontNormalT = new Vector3D(0,0,-1).scaled(b);
        Vector3D rightNormalT = new Vector3D(1,0,0).scaled(a);
        Vector3D upNormalT    = new Vector3D(0,1,0).scaled(c);

        Point3D frontOriginT  = frontNormalT.scaled(-1).getPoint();
        Point3D backOriginT = frontNormalT.getPoint();
        Point3D rightOriginT = rightNormalT.getPoint();
        Point3D leftOriginT  = rightNormalT.scaled(-1).getPoint();
        Point3D upOriginT    = upNormalT.getPoint();
        Point3D downOriginT  = upNormalT.scaled(-1).getPoint();

        Point3D Pc = ray.get_point();
        Vector3D Vc = ray.get_direction();
        Vector3D VcT = new Vector3D(0,0,1);

        Matrix3 R = Transform.getRodriguesRotation(Vc, VcT);
        Matrix3 RInv = R.inversed();

        Point3D q = R.mult(Pc);

        Point3D frontOrigin = RInv.mult(frontOriginT.add(q));
        Point3D backOrigin = RInv.mult(backOriginT.add(q));
        Point3D rightOrigin = RInv.mult(rightOriginT.add(q));
        Point3D leftOrigin = RInv.mult(leftOriginT.add(q));
        Point3D upOrigin = RInv.mult(upOriginT.add(q));
        Point3D downOrigin = RInv.mult(downOriginT.add(q));

        Vector3D frontNormal = RInv.mult(frontNormalT);
        Vector3D backNormal = frontNormal.scaled(-1);
        Vector3D rightNormal = RInv.mult(rightNormalT);
        Vector3D leftNormal = rightNormal.scaled(-1);
        Vector3D upNormal = RInv.mult(upNormalT);
        Vector3D downNormal = upNormal.scaled(-1);

        _faces[0] = new Plane(frontOrigin, frontNormal);
        _faces[1] = new Plane(backOrigin, backNormal);
        _faces[2] = new Plane(rightOrigin, rightNormal);
        _faces[3] = new Plane(leftOrigin, leftNormal);
        _faces[4] = new Plane(upOrigin, upNormal);
        _faces[5] = new Plane(downOrigin, downNormal);
    }

    private void initEmission(Color emission){
        for (Plane face : _faces){
            face.set_emission(emission);
        }
    }

    private void initMaterial(Material material){
        for (Plane face : _faces){
            face.set_material(material);
        }
    }

    private void initFaces(double width, double length, double height){
        if (width < 0 || length < 0 || height < 0){
            throw new IllegalArgumentException("Cubod's dimensions can't be negative");
        }

        initPlanes(width, length, height);
    }

    private void initFaces(double width, double length, double height, Color emission){
        if (width < 0 || length < 0 || height < 0){
            throw new IllegalArgumentException("Cubod's dimensions can't be negative");
        }

        initPlanes(width, length, height);
        initEmission(emission);
    }

    private void initFaces(double width, double length, double height, Material material){
        if (width < 0 || length < 0 || height < 0){
            throw new IllegalArgumentException("Cubod's dimensions can't be negative");
        }

        initPlanes(width, length, height);
        initMaterial(material);
    }

    private void initFaces(double width, double length, double height, Color emission, Material material){
        if (width < 0 || length < 0 || height < 0){
            throw new IllegalArgumentException("Cubod's dimensions can't be negative");
        }

        initPlanes(width, length, height);
        initEmission(emission);
        initMaterial(material);
    }

    private void initFaces(double width, double length, double height, Ray ray){
        if (width < 0 || length < 0 || height < 0){
            throw new IllegalArgumentException("Cubod's dimensions can't be negative");
        }

        initPlanes(width, length, height, ray);
    }

    private void initFaces(double width, double length, double height, Ray ray, Color emission){
        if (width < 0 || length < 0 || height < 0){
            throw new IllegalArgumentException("Cubod's dimensions can't be negative");
        }

        initPlanes(width, length, height, ray);
        initEmission(emission);
    }

    private void initFaces(double width, double length, double height, Ray ray, Material material){
        if (width < 0 || length < 0 || height < 0){
            throw new IllegalArgumentException("Cubod's dimensions can't be negative");
        }

        initPlanes(width, length, height, ray);
        initMaterial(material);
    }

    private void initFaces(double width, double length, double height, Ray ray, Color emission, Material material){
        if (width < 0 || length < 0 || height < 0){
            throw new IllegalArgumentException("Cubod's dimensions can't be negative");
        }

        initPlanes(width, length, height, ray);
        initEmission(emission);
        initMaterial(material);
    }

    //Constructors

    public Cuboid(double width, double length, double height){
        _ray = new Ray(new Vector3D(0,0,1));

        _width = width;
        _length = length;
        _height = height;

        initFaces(width, length, height);
    }

    public Cuboid(double width, double length, double height, Ray ray){
        _ray = new Ray(ray);

        _width = width;
        _length = length;
        _height = height;

        initFaces(width, length, height, ray);
    }

    public Cuboid(double width, double length, double height, Color emission){
        super(emission);

        _ray = new Ray(new Vector3D(0,0,1));

        _width = width;
        _length = length;
        _height = height;

        initFaces(width, length, height, emission);
    }

    public Cuboid(double width, double length, double height, Ray ray, Color emission){
        super(emission);

        _ray = new Ray(ray);
        _width = width;
        _length = length;
        _height = height;

        initFaces(width, length, height, ray, emission);
    }

    public Cuboid(double width, double length, double height, Material material){
        super(material);

        _ray = new Ray(new Vector3D(0,0,1));
        _width = width;
        _length = length;
        _height = height;

        initFaces(width, length, height, material);
    }

    public Cuboid(double width, double length, double height, Ray ray, Material material){
        super(material);

        _ray = new Ray(ray);
        _width = width;
        _length = length;
        _height = height;

        initFaces(width, length, height, ray, material);
    }

    public Cuboid(double width, double length, double height, Color emission, Material material){
        super(emission, material);

        _ray = new Ray(new Vector3D(0,0,1));
        _width = width;
        _length = length;
        _height = height;

        initFaces(width, length, height, emission, material);
    }

    public Cuboid(double width, double length, double height, Ray ray, Color emission, Material material){
        super(emission, material);

        _ray = new Ray(ray);
        _width = width;
        _length = length;
        _height = height;

        initFaces(width, length, height, ray, emission, material);
    }

    //Getters

    public Ray get_ray() {
        return new Ray(_ray);
    }

    public double get_width() {
        return _width;
    }

    public double get_length() {
        return _length;
    }

    public double get_height() {
        return _height;
    }

    //Setters

    public void set_ray(Ray ray) {
        this._ray = new Ray(ray);
    }

    public void set_width(double _width) {
        this._width = _width;
    }

    public void set_length(double _length) {
        this._length = _length;
    }

    public void set_height(double _height) {
        this._height = _height;
    }

    public void setRightFaceColor(Color color){
        _faces[0].set_emission(color);
    }

    public void setLeftFaceColor(Color color){
        _faces[1].set_emission(color);
    }

    public void setBackFaceColor(Color color){
        _faces[2].set_emission(color);
    }

    public void setFrontFaceColor(Color color){
        _faces[3].set_emission(color);
    }

    public void setUpFaceColor(Color color){
        _faces[4].set_emission(color);
    }

    public void setDownFaceColor(Color color){
        _faces[5].set_emission(color);
    }

    //Methods

    //frontOrigin
    //backOrigin

    //backOriginT
    //frontOriginT


    @Override
    public Vector3D get_normal(Point3D point) {
        double a = _width / 2.0;
        double b = _length / 2.0;
        double c = _height / 2.0;

        Vector3D frontNormalT = new Vector3D(0,0,-1).scaled(b);
        Vector3D backNormalT = frontNormalT.scaled(-1);
        Vector3D rightNormalT = new Vector3D(1,0,0).scaled(a);
        Vector3D leftNormalT = rightNormalT.scaled(-1);
        Vector3D upNormalT    = new Vector3D(0,1,0).scaled(c);
        Vector3D downNormalT = upNormalT.scaled(-1);

        Point3D frontOriginT = frontNormalT.getPoint();
        Point3D backOriginT  = frontNormalT.scaled(-1).getPoint();
        Point3D rightOriginT = rightNormalT.getPoint();
        Point3D leftOriginT  = rightNormalT.scaled(-1).getPoint();
        Point3D upOriginT    = upNormalT.getPoint();
        Point3D downOriginT  = upNormalT.scaled(-1).getPoint();

        Plane[] planesT = {
                new Plane(frontOriginT, frontNormalT, _faces[0].get_emission(), _faces[0].get_material()),
                new Plane(backOriginT, backNormalT, _faces[1].get_emission(), _faces[1].get_material()),
                new Plane(rightOriginT, rightNormalT, _faces[2].get_emission(), _faces[2].get_material()),
                new Plane(leftOriginT, leftNormalT, _faces[3].get_emission(), _faces[3].get_material()),
                new Plane(upOriginT, upNormalT, _faces[4].get_emission(), _faces[4].get_material()),
                new Plane(downOriginT, downNormalT, _faces[5].get_emission(), _faces[5].get_material()),
        };

        Point3D Pc = this._ray.get_point();
        Vector3D Vc = this._ray.get_direction();
        Vector3D VcT = new Vector3D(0,0,1);

        Matrix3 R = Transform.getRodriguesRotation(Vc, VcT);
        Matrix3 RInv = R.inversed();

        Point3D q = R.mult(Pc);

        Point3D pointT = R.mult(point).subtract(q).getPoint();

        for (Plane planeT : planesT){
            if (planeT.surfaceContains(pointT)){
                return RInv.mult(planeT.get_normal());
            }
        }

        throw new IllegalArgumentException("This point is not on the cuboid's surface");
    }

    @Override
    public boolean contains(Point3D point) {

        double a = _width / 2.0;//x axis when cuboid aligned with Y axis
        double b = _length / 2.0;//z axis
        double c = _height / 2.0;//y axis

        Point3D Pc = this._ray.get_point();
        Vector3D Vc = this._ray.get_direction();
        Point3D PcT = new Point3D();
        Vector3D VcT = new Vector3D(0,0,1);

        if (Vc.equals(VcT)){
            if (Pc.equals(PcT)){
                double px = Math.abs(point.getX().getCoord());
                double py = Math.abs(point.getY().getCoord());
                double pz = Math.abs(point.getZ().getCoord());

                return  (Util.equals(px, a) || px < a) &&
                        (Util.equals(py, c) || py < c) &&
                        (Util.equals(pz, b) || pz < b);
            }

            Point3D pointT = point.subtract(Pc).getPoint();

            double px = Math.abs(pointT.getX().getCoord());
            double py = Math.abs(pointT.getY().getCoord());
            double pz = Math.abs(pointT.getZ().getCoord());

            return  (Util.equals(px, a) || px < a) &&
                    (Util.equals(py, c) || py < c) &&
                    (Util.equals(pz, b) || pz < b);
        }

        Matrix3 R = Transform.getRodriguesRotation(Vc, VcT);

        Point3D q = R.mult(Pc);

        Point3D pointT = R.mult(point).subtract(q).getPoint();

        double px = Math.abs(pointT.getX().getCoord());
        double py = Math.abs(pointT.getY().getCoord());
        double pz = Math.abs(pointT.getZ().getCoord());

        return  (Util.equals(px, a) || px < a) &&
                (Util.equals(py, c) || py < c) &&
                (Util.equals(pz, b) || pz < b);
    }

    private boolean containsInYDirection(Point3D point){

        if (!this._ray.get_direction().equals(new Vector3D(0,0,1)) ||
                !this._ray.get_point().equals(new Point3D()))
        {
            throw new IllegalArgumentException("cuboid not aligned with Y axis");
        }

        double a = _width / 2.0;
        double b = _length / 2.0;
        double c = _height / 2.0;

        double px = Math.abs(point.getX().getCoord());
        double py = Math.abs(point.getY().getCoord());
        double pz = Math.abs(point.getZ().getCoord());

        return  (Util.equals(px, a) || px < a) &&
                (Util.equals(py, c) || py < c) &&
                (Util.equals(pz, b) || pz < b);
    }

    @Override
    public boolean surfaceContains(Point3D point) {
        if (!this.contains(point)){
            return false;
        }

        for (Plane face : this._faces){
            if (face.surfaceContains(point)){
                return true;
            }
        }

        return false;
    }

    public boolean surfaceContainsInYDirection(Point3D point) {

        if (!this._ray.get_direction().equals(new Vector3D(0,0,1)) ||
                !this._ray.get_point().equals(new Point3D()))
        {
            throw new IllegalArgumentException("cuboid not aligned with Y axis");
        }

        if (!this.containsInYDirection(point)){
            return false;
        }

        for (Plane face : this._faces){
            if (face.surfaceContains(point)){
                return true;
            }
        }

        return false;
    }

    @Override
    public ArrayList<GeoPoint> findIntersections(Ray ray) {
        ArrayList<GeoPoint> result = new ArrayList<>();

        for (Plane face : this._faces){
            ArrayList<GeoPoint> intersections = face.findIntersections(ray);

            if (!intersections.isEmpty()){
                GeoPoint intersection = intersections.get(0);//Ray can intersect plane at maximum 1 point.

                if (this.surfaceContains(intersection.point)){
                    result.add(intersection);
                }
            }
        }

        return result;
    }

    private ArrayList<GeoPoint> findIntersectionsInYDirection(Ray ray){

        ArrayList<GeoPoint> result = new ArrayList<>();

        for (Plane face : this._faces){
            GeoPoint intersection = face.findIntersections(ray).get(0);//Ray can intersect plane at maximum 1 point.

            if (this.surfaceContains(intersection.point)){
                result.add(intersection);
            }
        }

        return result;
    }

    @Override
    public void translate(double x, double y, double z) {
        this._ray.translate(x, y, z);
    }

    @Override
    public void rotate(double x, double y, double z) {
        this._ray.rotate(x, y, z);
    }

    @Override
    public void scale(double x, double y, double z) {
        this._ray.scale(x, y, z);

        _width = _width * x;
        _length = _length * y;
        _height = _height * z;
    }

    @Override
    public void scale(double scalar) {
        this._ray.scale(scalar);

        _width = _width * scalar;
        _length = _length * scalar;
        _height = _height * scalar;
    }

    @Override
    public void transform(Transform _transform) {
        this._ray.transform(_transform);
    }

    @Override
    public void transform(Vector3D translation, Vector3D rotation, Vector3D scale) {
        this._ray.transform(translation, rotation, scale);
    }
}