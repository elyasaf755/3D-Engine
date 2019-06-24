package geometries;

import primitives.*;

import java.util.ArrayList;

public class Cuboid extends Geometry {

    private Ray _ray;

    private double _width;//a - half width
    private double _length;//b - half length
    private double _height;//c - half height

    private Color[] _colors;

    public Cuboid(double width, double length, double height){
        _ray = new Ray(new Vector3D(0,0,1));

        _width = width;
        _length = length;
        _height = height;

        _colors = new Color[6];

        for (int i = 0; i < 6; ++i)
            _colors[i] = new Color(java.awt.Color.BLACK);
    }

    public Cuboid(double width, double length, double height, Ray ray){
        _ray = new Ray(ray);

        _width = width;
        _length = length;
        _height = height;

        _colors = new Color[6];

        for (int i = 0; i < 6; ++i)
            _colors[i] = new Color(java.awt.Color.BLACK);
    }

    public Cuboid(double width, double length, double height, Color emission){
        super(emission);

        _ray = new Ray(new Vector3D(0,0,1));

        _width = width;
        _length = length;
        _height = height;
        _colors = new Color[6];

        for (int i = 0; i < 6; ++i)
            _colors[i] = new Color(emission);
    }

    public Cuboid(double width, double length, double height, Color[] colors){

        _ray = new Ray(new Vector3D(0,0,1));

        _width = width;
        _length = length;
        _height = height;
        _colors = new Color[6];

        for (int i = 0; i < 6; ++i)
            _colors[i] = new Color(colors[i]);
    }

    public Cuboid(double width, double length, double height, Ray ray, Color emission){
        super(emission);

        _ray = new Ray(ray);
        _width = width;
        _length = length;
        _height = height;

        _colors = new Color[6];

        for (int i = 0; i < 6; ++i)
            _colors[i] = new Color(emission);
    }

    public Cuboid(double width, double length, double height, Ray ray, Color[] colors){

        _ray = new Ray(ray);
        _width = width;
        _length = length;
        _height = height;

        _colors = new Color[6];

        for (int i = 0; i < 6; ++i)
            _colors[i] = new Color(colors[i]);
    }

    public Cuboid(double width, double length, double height, Material material){
        super(material);

        _ray = new Ray(new Vector3D(0,0,1));
        _width = width;
        _length = length;
        _height = height;

        _colors = new Color[6];

        for (int i = 0; i < 6; ++i)
            _colors[i] = new Color(java.awt.Color.BLACK);
    }

    public Cuboid(double width, double length, double height, Ray ray, Material material){
        super(material);

        _ray = new Ray(ray);
        _width = width;
        _length = length;
        _height = height;

        _colors = new Color[6];

        for (int i = 0; i < 6; ++i)
            _colors[i] = new Color(java.awt.Color.BLACK);
    }

    public Cuboid(double width, double length, double height, Color emission, Material material){
        super(emission, material);

        _ray = new Ray(new Vector3D(0,0,1));
        _width = width;
        _length = length;
        _height = height;

        _colors = new Color[6];

        for (int i = 0; i < 6; ++i)
            _colors[i] = new Color(emission);
    }

    public Cuboid(double width, double length, double height, Color[] colors, Material material){
        super(material);

        _ray = new Ray(new Vector3D(0,0,1));
        _width = width;
        _length = length;
        _height = height;

        _colors = new Color[6];

        for (int i = 0; i < 6; ++i)
            _colors[i] = new Color(colors[i]);
    }

    public Cuboid(double width, double length, double height, Ray ray, Color emission, Material material){
        super(emission, material);

        _ray = new Ray(ray);
        _width = width;
        _length = length;
        _height = height;

        _colors = new Color[6];

        for (int i = 0; i < 6; ++i)
            _colors[i] = new Color(emission);
    }

    public Cuboid(double width, double length, double height, Ray ray, Color[] colors, Material material){
        super(material);

        _ray = new Ray(ray);
        _width = width;
        _length = length;
        _height = height;

        _colors = new Color[6];

        for (int i = 0; i < 6; ++i)
            _colors[i] = new Color(colors[i]);
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

    public Color[] get_colors(){
        Color[] colors = new Color[6];

        for (int i = 0; i < 6; ++i){
            colors[i] = new Color(_colors[i]);
        }

        return colors;
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

    public void set_colors(Color[] colors) {
        int minSize = Math.min(colors.length, _colors.length);

        for (int i = 0; i < minSize; ++i){
            _colors[i] = new Color(colors[i]);
        }
    }

    public void setRightFaceColor(Color color){
        _colors[0].setColor(color);
    }

    public void setLeftFaceColor(Color color){
        _colors[1].setColor(color);
    }

    public void setBackFaceColor(Color color){
        _colors[2].setColor(color);
    }

    public void setFrontFaceColor(Color color){
        _colors[3].setColor(color);
    }

    public void setUpFaceColor(Color color){
        _colors[4].setColor(color);
    }

    public void setDownFaceColor(Color color){
        _colors[5].setColor(color);
    }


    //Methods

    @Override
    public Vector3D get_normal(Point3D point) {
        double a = _width / 2.0;
        double b = _length / 2.0;
        double c = _height / 2.0;

        Vector3D xAxis = new Vector3D(1,0,0);
        Vector3D yAxis = new Vector3D(0,1,0);
        Vector3D zAxis = new Vector3D(0,0,1);

        Vector3D n1 = xAxis.scaled(a);
        Vector3D n2 = n1.scaled(-1);
        Vector3D n3 = yAxis.scaled(b);
        Vector3D n4 = n3.scaled(-1);
        Vector3D n5 = zAxis.scaled(c);
        Vector3D n6 = n5.scaled(-1);

        Plane p1 = new Plane(n1.getPoint(), n1, _colors[0]);//Right face
        Plane p2 = new Plane(n2.getPoint(), n2, _colors[1]);//Left face
        Plane p3 = new Plane(n3.getPoint(), n3, _colors[2]);//Back face
        Plane p4 = new Plane(n4.getPoint(), n4, _colors[3]);//Front face
        Plane p5 = new Plane(n5.getPoint(), n5, _colors[4]);//Up face
        Plane p6 = new Plane(n6.getPoint(), n6, _colors[5]);//Down face

        Plane[] faces = {p1, p2, p3, p4, p5, p6};

        for (Plane face : faces){
            if (face.contains(point))
                return face.get_normal();
        }

        throw new IllegalArgumentException("This point isnt coontained in any of the cuboid faces");
    }

    @Override
    public boolean contains(Point3D point) {

        double a = _width / 2.0;
        double b = _length / 2.0;
        double c = _height / 2.0;

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
                        (Util.equals(py, b) || py < b) &&
                        (Util.equals(pz, c) || pz < c);
            }

            Point3D pointT = point.subtract(Pc).getPoint();

            double px = Math.abs(pointT.getX().getCoord());
            double py = Math.abs(pointT.getY().getCoord());
            double pz = Math.abs(pointT.getZ().getCoord());

            return  (Util.equals(px, a) || px < a) &&
                    (Util.equals(py, b) || py < b) &&
                    (Util.equals(pz, c) || pz < c);
        }

        Matrix3 R = Transform.getRodriguesRotation(Vc, VcT);
        Matrix3 RInv = R.inversed();

        Point3D q = R.mult(Pc);

        Point3D pointT = R.mult(point).subtract(q).getPoint();

        double px = Math.abs(pointT.getX().getCoord());
        double py = Math.abs(pointT.getY().getCoord());
        double pz = Math.abs(pointT.getZ().getCoord());

        return  (Util.equals(px, a) || px < a) &&
                (Util.equals(py, b) || py < b) &&
                (Util.equals(pz, c) || pz < c);
    }

    private boolean containsInZDirection(Point3D point){
        double a = _width / 2.0;
        double b = _length / 2.0;
        double c = _height / 2.0;

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
                        (Util.equals(py, b) || py < b) &&
                        (Util.equals(pz, c) || pz < c);
            }

            Point3D pointT = point.subtract(Pc).getPoint();

            double px = Math.abs(pointT.getX().getCoord());
            double py = Math.abs(pointT.getY().getCoord());
            double pz = Math.abs(pointT.getZ().getCoord());

            return  (Util.equals(px, a) || px < a) &&
                    (Util.equals(py, b) || py < b) &&
                    (Util.equals(pz, c) || pz < c);
        }

        throw new IllegalArgumentException("Cuboid not aligned with Z axis");
    }

    @Override
    public boolean surfaceContains(Point3D point) {
        double a = _width / 2.0;
        double b = _length / 2.0;
        double c = _height / 2.0;

        Vector3D xAxis = new Vector3D(1,0,0);
        Vector3D yAxis = new Vector3D(0,1,0);
        Vector3D zAxis = new Vector3D(0,0,1);

        Vector3D n1 = xAxis.scaled(a);
        Vector3D n2 = n1.scaled(-1);
        Vector3D n3 = yAxis.scaled(b);
        Vector3D n4 = n3.scaled(-1);
        Vector3D n5 = zAxis.scaled(c);
        Vector3D n6 = n5.scaled(-1);

        Plane p1 = new Plane(n1.getPoint(), n1, _colors[0]);//Right face
        Plane p2 = new Plane(n2.getPoint(), n2, _colors[1]);//Left face
        Plane p3 = new Plane(n3.getPoint(), n3, _colors[2]);//Back face
        Plane p4 = new Plane(n4.getPoint(), n4, _colors[3]);//Front face
        Plane p5 = new Plane(n5.getPoint(), n5, _colors[4]);//Up face
        Plane p6 = new Plane(n6.getPoint(), n6, _colors[5]);//Down face

        Plane[] faces = {p1, p2, p3, p4, p5, p6};

        for (Plane face : faces){
            if (p1.contains(point))
                return true;
        }

        return false;
    }

    @Override
    public ArrayList<GeoPoint> findIntersections(Ray ray) {
        Point3D Pr = ray.get_point();
        Vector3D Vr = ray.get_direction();

        Point3D Pc = this._ray.get_point();
        Vector3D Vc = this._ray.get_direction();
        Point3D PcT = new Point3D();
        Vector3D VcT = new Vector3D(0,0,1);
        Ray RcT = new Ray(PcT, VcT);

        if (Vc.equals(VcT)){
            if (Pc.equals(PcT)){
                return this.findIntersectionsInZDirection(ray);
            }

            Point3D PrT = Pr.subtract(Pc).getPoint();
            Vector3D VrT = Vr;
            Ray RT = new Ray(PrT, VrT);

            Cuboid cuboidT = new Cuboid(this._width, this._length, this._height);

            ArrayList<GeoPoint> intersectionsT = cuboidT.findIntersectionsInZDirection(RT);

            ArrayList<GeoPoint> result = new ArrayList<>();

            for(GeoPoint intersection : intersectionsT){
                Point3D point = new Point3D(intersection.point);
                result.add(new GeoPoint(this, point.add(Pc)));
            }

            return result;

        }

        Matrix3 R = Transform.getRodriguesRotation(Vc, VcT);
        Matrix3 RInv = R.inversed();

        Point3D q = R.mult(Pc);

        Point3D PrT = R.mult(Pr).subtract(q).getPoint();
        Vector3D VrT = R.mult(Vr).normalized();
        Ray RT = new Ray(PrT, VrT);

        Cuboid CT = new Cuboid(this._width, this._length, this._height, RcT, this._colors, this._material);

        ArrayList<GeoPoint> intersectionsT = CT.findIntersectionsInZDirection(RT);

        ArrayList<GeoPoint> result = new ArrayList<>();

        for(GeoPoint intersection : intersectionsT){
            Point3D point = new Point3D(intersection.point);
            result.add(new GeoPoint(intersection.geometry, RInv.mult(point.add(Pc))));
        }

        return result;
    }

    private ArrayList<GeoPoint> findIntersectionsInZDirection(Ray ray){
        double a = _width / 2.0;
        double b = _length / 2.0;
        double c = _height / 2.0;

        Vector3D xAxis = new Vector3D(1,0,0);
        Vector3D yAxis = new Vector3D(0,1,0);
        Vector3D zAxis = new Vector3D(0,0,1);

        Vector3D n1 = xAxis.scaled(a);
        Vector3D n2 = xAxis.scaled(-a);
        Vector3D n3 = yAxis.scaled(b);
        Vector3D n4 = yAxis.scaled(-b);
        Vector3D n5 = zAxis.scaled(c);
        Vector3D n6 = zAxis.scaled(-c);

        Plane p1 = new Plane(n1.getPoint(), xAxis, _colors[0]);//Right face
        Plane p2 = new Plane(n2.getPoint(), xAxis, _colors[1]);//Left face
        Plane p3 = new Plane(n3.getPoint(), yAxis, _colors[2]);//Back face
        Plane p4 = new Plane(n4.getPoint(), yAxis, _colors[3]);//Front face
        Plane p5 = new Plane(n5.getPoint(), zAxis, _colors[4]);//Up face
        Plane p6 = new Plane(n6.getPoint(), zAxis, _colors[5]);//Down face

        Plane[] faces = {p1, p2, p3, p4, p5, p6};

        ArrayList<GeoPoint> result = new ArrayList<>();

        for (Plane face : faces){
            ArrayList<GeoPoint> intersections = face.findIntersections(ray);

            for (GeoPoint intersection : intersections){
                if (this.containsInZDirection(intersection.point))
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
