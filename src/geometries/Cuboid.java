package geometries;

import primitives.*;

import java.util.ArrayList;

public class Cuboid extends Geometry {
    private Ray _ray;//Defining length

    private double _width;//X Axis - Width: +Right, -Left
    private double _height;//Y Axis - Height: +Up, -Down
    private double _length;//Z Axis - Length: +Front, -Back

    private Plane[] _faces;
    private Point3D[] _vertices;

    private Matrix3 _R;//Default = Z axis
    private Matrix3 _RInv;
    private Point3D _q;

    //Initializers

    private void init(){
        initTransformFields();
        initVertices();
    }

    private void initTransformFields(){
        _R = new Matrix3(Transform.getRodriguesRotation(_ray.get_direction(), Vector3D.zAxis));
        _RInv = new Matrix3(_R.inversed());
        _q = new Point3D(_R.mult(_ray.get_point()));
    }

    private void initVertices(){
        _vertices = new Point3D[8];

        double x = _width / 2;//X Axis - Width: +Right, -Left
        double y = _height / 2;//Y Axis - Height: +Up, -Down
        double z = _length / 2;//Z Axis - Length: +Front, -Back

        //R = Right, L = Left, U = Up, D = Down, F = Front, B = Back
        _vertices[0] = new Point3D(x,y,z);//RUF
        _vertices[1] = new Point3D(x,y,-z);//RUB
        _vertices[2] = new Point3D(x,-y,z);//RDF
        _vertices[3] = new Point3D(x,-y,-z);//RDB
        _vertices[4] = new Point3D(-x,y,z);//LUF
        _vertices[5] = new Point3D(-x,y,-z);//LUB
        _vertices[6] = new Point3D(-x,-y,z);//LDF
        _vertices[7] = new Point3D(-x,-y,-z);//LDB

        for (int i = 0; i < 8; ++i){
            _vertices[i] = _RInv.mult(_vertices[i]).add(_q);
        }
    }

    private void initPlanes(double width, double height, double length){
        _faces = new Plane[6];

        //when cube direction is Z (length)
        double x = width / 2.0;//X Axis - Width: +Right, -Left
        double y = height / 2.0;//Y Axis - Height: +Up, -Down
        double z = length / 2.0;//Z Axis - Length: +Front, -Back

        Vector3D xAxis = new Vector3D(1,0,0);//Width: +Right, -Left
        Vector3D yAxis = new Vector3D(0,1,0);//Height: +Up, -Down
        Vector3D zAxis = new Vector3D(0,0,1);//Length: +Front, -Back

        Vector3D front = zAxis.scaled(z);
        Vector3D back = front.scaled(-1);
        Vector3D right = xAxis.scaled(x);
        Vector3D left = right.scaled(-1);
        Vector3D up = yAxis.scaled(y);
        Vector3D down = up.scaled(-1);

        _faces[0] = new Plane(front.getPoint(), front);
        _faces[1] = new Plane(back.getPoint(), back);
        _faces[2] = new Plane(right.getPoint(), right);
        _faces[3] = new Plane(left.getPoint(), left);
        _faces[4] = new Plane(up.getPoint(), up);
        _faces[5] = new Plane(down.getPoint(), down);
    }

    private void initPlanes(double width, double height, double length, Ray ray){
        _faces = new Plane[6];

        //when cube direction is Z
        double x = width / 2.0;//X Axis - Width: +Right, -Left
        double y = height / 2.0;//Y Axis - Height: +Up, -Down
        double z = length / 2.0;//Z Axis - Length: +Front, -Back

        Vector3D xAxis = new Vector3D(1,0,0);//Width: +Right, -Left
        Vector3D yAxis = new Vector3D(0,1,0);//Height: +Up, -Down
        Vector3D zAxis = new Vector3D(0,0,1);//Length: +Front, -Back

        Point3D Pc = ray.get_point();

        Vector3D frontNormal = _RInv.mult(zAxis);
        Vector3D backNormal = frontNormal.scaled(-1);
        Vector3D rightNormal = _RInv.mult(xAxis);
        Vector3D leftNormal = rightNormal.scaled(-1);
        Vector3D upNormal = _RInv.mult(yAxis);
        Vector3D downNormal = upNormal.scaled(-1);

        Point3D frontOrigin = Pc.add(frontNormal.scaled(z));
        Point3D backOrigin = Pc.add(frontNormal.scaled(-z));
        Point3D rightOrigin = Pc.add(rightNormal.scaled(x));
        Point3D leftOrigin = Pc.add(rightNormal.scaled(-x));
        Point3D upOrigin = Pc.add(upNormal.scaled(y));
        Point3D downOrigin = Pc.add(upNormal.scaled(-y));

        _faces[0] = new Plane(frontOrigin, frontNormal);
        _faces[1] = new Plane(backOrigin, backNormal);
        _faces[2] = new Plane(rightOrigin, rightNormal);
        _faces[3] = new Plane(leftOrigin, leftNormal);
        _faces[4] = new Plane(upOrigin, upNormal);
        _faces[5] = new Plane(downOrigin, downNormal);

        return;
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

    private void initFaces(double width, double height, double length){
        if (width < 0 || length < 0 || height < 0){
            throw new IllegalArgumentException("Cubod's dimensions can't be negative");
        }

        initPlanes(width, height, length);
    }

    private void initFaces(double width, double height, double length, Color emission){
        if (width < 0 || length < 0 || height < 0){
            throw new IllegalArgumentException("Cubod's dimensions can't be negative");
        }

        initPlanes(width, height, length);
        initEmission(emission);
    }

    private void initFaces(double width, double height, double length, Material material){
        if (width < 0 || length < 0 || height < 0){
            throw new IllegalArgumentException("Cubod's dimensions can't be negative");
        }

        initPlanes(width, height, length);
        initMaterial(material);
    }

    private void initFaces(double width, double height, double length, Color emission, Material material){
        if (width < 0 || length < 0 || height < 0){
            throw new IllegalArgumentException("Cubod's dimensions can't be negative");
        }

        initPlanes(width, height, length);
        initEmission(emission);
        initMaterial(material);
    }

    private void initFaces(double width, double height, double length, Ray ray){
        if (width < 0 || length < 0 || height < 0){
            throw new IllegalArgumentException("Cubod's dimensions can't be negative");
        }

        initPlanes(width, height, length, ray);
    }

    private void initFaces(double width, double height, double length, Ray ray, Color emission){
        if (width < 0 || length < 0 || height < 0){
            throw new IllegalArgumentException("Cubod's dimensions can't be negative");
        }

        initPlanes(width, height, length, ray);
        initEmission(emission);
    }

    private void initFaces(double width, double height, double length, Ray ray, Material material){
        if (width < 0 || length < 0 || height < 0){
            throw new IllegalArgumentException("Cubod's dimensions can't be negative");
        }

        initPlanes(width, height, length, ray);
        initMaterial(material);
    }

    private void initFaces(double width, double height, double length, Ray ray, Color emission, Material material){
        if (width < 0 || length < 0 || height < 0){
            throw new IllegalArgumentException("Cubod's dimensions can't be negative");
        }

        initPlanes(width, height, length, ray);
        initEmission(emission);
        initMaterial(material);
    }

    //Constructors

    public Cuboid(double width, double height, double length){
        _ray = new Ray(new Vector3D(0,0,1));

        _width = width;
        _length = length;
        _height = height;

        initTransformFields();

        initFaces(width, height, length);

        //TODO:
        updateAABB();
    }

    public Cuboid(double width, double height, double length, Ray ray){
        _ray = new Ray(ray);

        _width = width;
        _length = length;
        _height = height;

        initTransformFields();

        initFaces(width, height, length, ray);

        //TODO:
        updateAABB();
    }

    public Cuboid(double width, double height, double length, Color emission){
        super(emission);

        _ray = new Ray(new Vector3D(0,0,1));

        _width = width;
        _length = length;
        _height = height;

        initTransformFields();

        initFaces(width, height, length, emission);

        //TODO:
        updateAABB();
    }

    public Cuboid(double width, double height, double length, Ray ray, Color emission){
        super(emission);

        _ray = new Ray(ray);
        _width = width;
        _length = length;
        _height = height;

        initTransformFields();

        initFaces(width, height, length, ray, emission);

        //TODO:
        updateAABB();
    }

    public Cuboid(double width, double height, double length, Material material){
        super(material);

        _ray = new Ray(new Vector3D(0,0,1));
        _width = width;
        _length = length;
        _height = height;

        initTransformFields();

        initFaces(width, height, length, material);

        //TODO:
        updateAABB();
    }

    public Cuboid(double width, double height, double length, Ray ray, Material material){
        super(material);

        _ray = new Ray(ray);
        _width = width;
        _length = length;
        _height = height;

        initTransformFields();

        initFaces(width, height, length, ray, material);

        //TODO:
        updateAABB();
    }

    public Cuboid(double width, double height, double length, Color emission, Material material){
        super(emission, material);

        _ray = new Ray(new Vector3D(0,0,1));
        _width = width;
        _length = length;
        _height = height;

        initTransformFields();

        initFaces(width, height, length, emission, material);

        //TODO:
        updateAABB();
    }

    public Cuboid(double width, double height, double length, Ray ray, Color emission, Material material){
        super(emission, material);

        _ray = new Ray(ray);
        _width = width;
        _length = length;
        _height = height;

        initTransformFields();

        initFaces(width, height, length, ray, emission, material);

        //TODO:
        updateAABB();
    }

    public Cuboid(Cuboid other){
        super(other.get_emission(), other.get_material());

        _ray = new Ray(other.get_ray());
        _width = other.get_width();
        _length = other.get_length();
        _height = other.get_height();

        initTransformFields();

        initFaces(other.get_width(), other.get_height(), other.get_length(), other.get_ray(), other.get_emission(), other.get_material());

        //TODO:
        updateAABB();
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

        initTransformFields();

        //TODO:
        updateAABB();
    }

    public void set_width(double _width) {
        this._width = _width;

        //TODO:
        updateAABB();
    }

    public void set_length(double _length) {
        this._length = _length;

        //TODO:
        updateAABB();
    }

    public void set_height(double _height) {
        this._height = _height;

        //TODO:
        updateAABB();
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

    @Override
    public void set_emission(Color emission) {
        for(Plane face : _faces){
            face.set_emission(emission);
        }
    }

    @Override
    public void set_emission(java.awt.Color emission) {
        for(Plane face : _faces){
            face.set_emission(emission);
        }
    }

    @Override
    public void set_material(Material material) {
        for(Plane face : _faces){
            face.set_material(material);
        }
    }

    //Methods

    //TODO: TEST
    @Override
    public void updateAABB() {
        init();

        set_min(Point3D.staticMin(_vertices));
        set_max(Point3D.staticMax(_vertices));
    }

    @Override
    public Geometry clone() {
        return new Cuboid(this);
    }

    @Override
    public Vector3D get_normal(Point3D point) {
        for (Plane face : this._faces){
            if (face.surfaceContains(point)){
                return face.get_normal();
            }
        }

        throw new IllegalArgumentException("This point is not on the cuboid's surface");
    }

    @Override
    public boolean contains(Point3D point) {

        //when cube direction is Z
        double x = _width / 2.0;//X Axis - Width: +Right, -Left
        double y = _height / 2.0;//Y Axis - Height: +Up, -Down
        double z = _length / 2.0;//Z Axis - Length: +Front, -Back

        Point3D Pc = this._ray.get_point();
        Vector3D Vc = this._ray.get_direction();
        Point3D PcT = new Point3D();
        Vector3D VcT = new Vector3D(0,0,1);

        if (Vc.equals(VcT)){
            if (Pc.equals(PcT)){
                double px = Math.abs(point.getX().getCoord());
                double py = Math.abs(point.getY().getCoord());
                double pz = Math.abs(point.getZ().getCoord());

                return  (Util.equals(px, x) || px < x) &&
                        (Util.equals(py, y) || py < y) &&
                        (Util.equals(pz, z) || pz < z);
            }

            Point3D pointT;

            if (point.equals(Pc)){
                pointT = new Point3D();
            }
            else{
                pointT = point.subtract(Pc).getPoint();
            }

            double px = Math.abs(pointT.getX().getCoord());
            double py = Math.abs(pointT.getY().getCoord());
            double pz = Math.abs(pointT.getZ().getCoord());

            return  (Util.equals(px, x) || px < x) &&
                    (Util.equals(py, y) || py < y) &&
                    (Util.equals(pz, z) || pz < z);
        }

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
                (Util.equals(pz, z) || pz < z);
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

    @Override
    public ArrayList<GeoPoint> findIntersections(Ray ray) {
        //TODO:TEST
        if(!intersects(ray)){
            return new ArrayList<>();
        }

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

    @Override
    public void translate(double x, double y, double z) {
        this._ray.translate(x, y, z);

        for (Plane face : this._faces){
            face.translate(x, y, z);
        }

        for (Point3D vertex : _vertices){
            vertex.translate(x,y,z);
        }

        //TODO: TEST
        updateAABB();
    }

    @Override
    public void rotate(double x, double y, double z) {
        this._ray.rotate(x, y, z);

        initTransformFields();

        initFaces(_width, _height, _length, _ray, _emission, _material);

        //TODO:
        updateAABB();

        /*for (Plane face : this._faces){
            face.rotate(x, y, z);
        }

        for (Point3D vertex : _vertices){
            vertex.rotate(x,y,z);
        }

        //TODO: TEST
        updateAABB();*/
    }

    @Override
    public void scale(double x, double y, double z) {
        this._ray.scale(x, y, z);

        _width = _width * x;
        _height = _height * y;
        _length = _length * z;

        for (Plane face : this._faces){
            face.scale(x, y, z);
        }

        for (Point3D vertex : _vertices){
            vertex.scale(x,y,z);
        }

        //TODO: TEST
        updateAABB();
    }

    @Override
    public void scale(double scalar) {
        this._ray.scale(scalar);

        _width = _width * scalar;
        _length = _length * scalar;
        _height = _height * scalar;

        for (Plane face : this._faces){
            face.scale(scalar);
        }

        for (Point3D vertex : _vertices){
            vertex.scale(scalar);
        }

        //TODO: TEST
        updateAABB();
    }

    @Override
    public void transform(Transform _transform) {
        this._ray.transform(_transform);

        for (Plane face : this._faces){
            face.transform(_transform);
        }

        for (Point3D vertex : _vertices){
            vertex.transform(_transform);
        }

        //TODO: TEST
        updateAABB();
    }

    @Override
    public void transform(Vector3D translation, Vector3D rotation, Vector3D scale) {
        this._ray.transform(translation, rotation, scale);

        for (Plane face : this._faces){
            face.transform(translation, rotation, scale);
        }

        for (Point3D vertex : _vertices){
            vertex.transform(translation, rotation, scale);
        }

        //TODO: TEST
        updateAABB();
    }
}