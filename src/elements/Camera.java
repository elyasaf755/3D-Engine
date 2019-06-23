package elements;

import primitives.*;

import java.util.ArrayList;

public class Camera implements ITransform{
    private Point3D _origin;
    private Vector3D _direction;
    private Vector3D _up;
    private Vector3D _right;

    //Constructors

    public Camera(Point3D origin, Vector3D direction, Vector3D up, Vector3D right){
        if (direction.dotProduct(up) == 0 && direction.dotProduct(right) == 0 && up.dotProduct(right) == 0){
            _origin = new Point3D(origin);
            _direction = (new Vector3D(direction)).normalized();
            _up = (new Vector3D(up)).normalized();
            _right = (new Vector3D(right)).normalized();
        }
        else{
            Matrix orthonormalBase = (new Matrix(direction, up, right)).orthonormalized();
            _origin = new Point3D(origin);
            _direction = Matrix.getColumnAsVector3(orthonormalBase, 0);
            _up = Matrix.getColumnAsVector3(orthonormalBase, 1);
            _right = Matrix.getColumnAsVector3(orthonormalBase, 2);
        }
    }

    public Camera(Vector3D direction, Vector3D up, Vector3D right){
        if (direction.dotProduct(up) == 0 && direction.dotProduct(right) == 0 && up.dotProduct(right) == 0){
            _origin = new Point3D();
            _direction = (new Vector3D(direction)).normalized();
            _up = (new Vector3D(up)).normalized();
            _right = (new Vector3D(right)).normalized();
        }
        else{
            Matrix orthonormalBase = (new Matrix(direction, up, right)).orthonormalized();
            _origin = new Point3D();
            _direction = Matrix.getColumnAsVector3(orthonormalBase, 0);
            _up = Matrix.getColumnAsVector3(orthonormalBase, 1);
            _right = Matrix.getColumnAsVector3(orthonormalBase, 2);
        }
    }

    public Camera(Point3D origin, Vector3D direction, Vector3D up){

        if (direction.dotProduct(up) == 0){
            _origin = new Point3D(origin);
            _direction = (new Vector3D(direction)).normalized();
            _up = (new Vector3D(up)).normalized();
            _right = (_direction.crossProduct(_up)).normalized();
        }
        else{
            Matrix orthonormalBase = (new Matrix(direction, up, direction.crossProduct(up))).orthonormalized();
            _origin = new Point3D(origin);
            _direction = Matrix.getColumnAsVector3(orthonormalBase, 0);
            _up = Matrix.getColumnAsVector3(orthonormalBase, 1);
            _right = Matrix.getColumnAsVector3(orthonormalBase, 2);
        }
    }

    public Camera(Vector3D direction, Vector3D up){

        if (direction.dotProduct(up) == 0){
            _origin = new Point3D();
            _direction = (new Vector3D(direction)).normalized();
            _up = (new Vector3D(up)).normalized();
            _right = (_direction.crossProduct(_up)).normalized();
        }
        else{
            Matrix orthonormalBase = (new Matrix(direction, up, direction.crossProduct(up))).orthonormalized();
            _origin = new Point3D();
            _direction = Matrix.getColumnAsVector3(orthonormalBase, 0);
            _up = Matrix.getColumnAsVector3(orthonormalBase, 1);
            _right = Matrix.getColumnAsVector3(orthonormalBase, 2);
        }
    }

    public Camera(Point3D origin, Vector3D direction){
        Matrix orthonormalBase = Matrix.orthonormalBasis3X3(direction);
        _origin = new Point3D(origin);
        _direction = Matrix.getColumnAsVector3(orthonormalBase, 0);
        _up = Matrix.getColumnAsVector3(orthonormalBase, 1);
        _right = Matrix.getColumnAsVector3(orthonormalBase, 2);
    }

    public Camera(Vector3D direction){
        Matrix orthonormalBase = Matrix.orthonormalBasis3X3(direction);
        _origin = new Point3D();
        _direction = Matrix.getColumnAsVector3(orthonormalBase, 0);
        _up = Matrix.getColumnAsVector3(orthonormalBase, 1);
        _right = Matrix.getColumnAsVector3(orthonormalBase, 2);
    }

    //Getters

    public Point3D get_origin(){
        return new Point3D(_origin);
    }

    public Vector3D get_up() {
        return new Vector3D(_up);
    }

    public Vector3D get_direction() {
        return new Vector3D(_direction);
    }

    public Vector3D get_right() {
        return new Vector3D(_right);
    }

    //Methods

    /**
    @param Nx Number of pixel in the width of the screen
    @param Ny Number of pixel in the height of the screen
    **/
    public Ray constructRayThroughPixel(int Nx, int Ny, int i, int j, double screenDistance, double screenWidth, double screenHeight){
        //Fix pixel locations

        i = Nx - i - 1;
        //j = Ny - j - 1;


        Point3D p0 = get_origin();
        Vector3D direction = get_direction();
        Vector3D up = get_up();
        Vector3D right = get_right();

        //Image center point
        Point3D Pc = p0.add(direction.scale(screenDistance));

        //Pixel ratios
        double Rx = screenWidth / Nx; //Pixel width
        double Ry = screenHeight / Ny; //Pixel height

        //Center pixel
        double Xi = (i - Nx / 2.0)*Rx + Rx / 2.0;
        double Yj = (j - Ny / 2.0)*Ry + Ry / 2.0;

        Point3D p_ij;
        if (Xi == 0 && Yj == 0){
            p_ij = new Point3D(Pc);
        }
        else if (Xi == 0){
            p_ij = new Point3D(Pc.add(Vector3D.ZERO.subtract(up.scale(Yj))));
        }
        else if (Yj == 0){
            p_ij = new Point3D(Pc.add((right.scale(Xi)).subtract(Vector3D.ZERO)));
        }
        else{
            p_ij = new Point3D(Pc.add((right.scale(Xi)).subtract(up.scale(Yj))));
        }

        Vector3D v_ij = p_ij.subtract(p0);

        return new Ray(p0, v_ij.normalized());
    }
    public ArrayList<Ray> constructRaysThroughPixel(int Nx, int Ny, int i, int j, double screenDistance, double screenWidth, double screenHeight){
        //Fix pixel locations

        i = Nx - i - 1;
        //j = Ny - j - 1;


        Point3D p0 = get_origin();
        Vector3D direction = get_direction();
        Vector3D up = get_up();
        Vector3D right = get_right();

        //Image center point
        Point3D Pc = p0.add(direction.scale(screenDistance));

        ArrayList<Point3D> points=new ArrayList<Point3D>();

        ArrayList<Ray> rays=new ArrayList<Ray>();


        //Pixel ratios
        double Rx = screenWidth / Nx; //Pixel width
        double Ry = screenHeight / Ny; //Pixel height

        //Center pixel
        double Xi = (i - Nx / 2.0)*Rx + Rx / 2.0;
        double Yj = (j - Ny / 2.0)*Ry + Ry / 2.0;

        Point3D p_ij;
        if (Xi == 0 && Yj == 0){
            p_ij = new Point3D(Pc);
        }
        else if (Xi == 0){
            p_ij = new Point3D(Pc.add(Vector3D.ZERO.subtract(up.scale(Yj))));
        }
        else if (Yj == 0){
            p_ij = new Point3D(Pc.add((right.scale(Xi)).subtract(Vector3D.ZERO)));
        }
        else{
            p_ij = new Point3D(Pc.add((right.scale(Xi)).subtract(up.scale(Yj))));
        }
        double quarter_Rx=Rx/4;
        double quarter_Ry=Ry/4;

        Point3D m_p_ij=new Point3D(p_ij.add(new Vector3D(up).scale(quarter_Ry)));
        Point3D r_p_ij=new Point3D(m_p_ij.add(new Vector3D(right).scale(quarter_Rx)));
        Point3D l_p_ij=new Point3D(m_p_ij.add(new Vector3D(right).scale(-quarter_Rx)));

        rays.add(new Ray(p0,new Vector3D((m_p_ij.subtract(p0)).normalized())));
        rays.add(new Ray(p0,new Vector3D((r_p_ij.subtract(p0)).normalized())));
        rays.add(new Ray(p0,new Vector3D((l_p_ij.subtract(p0)).normalized())));

        points.add(m_p_ij);
        points.add(r_p_ij);
        points.add(l_p_ij);

        Point3D p=new Point3D();

        for (Point3D point: points)
        {
            for(int k=1;k<3;k++)
            {
                p= (point.add(new Vector3D(up).scale(-quarter_Ry*k)));

                rays.add(new Ray(p0,new Vector3D((p.subtract(p0)).normalized())));
            }
        }

        return rays;
    }

    @Override
    public void translate(double x, double y, double z) {
        _origin.translate(x, y, z);
    }

    @Override
    public void rotate(double x, double y, double z) {
        _origin.rotate(x, y, z);
        _direction.rotate(x, y, z);
        _up.rotate(x, y, z);
        _right.rotate(x, y, z);
    }

    @Override
    public void scale(double x, double y, double z) {
        x = 1.0 / x;
        y = 1.0 / y;
        z = 1.0 / z;

        _origin.scale(x, y, z);
        _direction.scale(x, y, z);
        _up.scale(x, y, z);
        _right.scale(x, y, z);
    }

    @Override
    public void transform(Transform _transform) {
        _origin.transform(_transform);
        _direction.transform(_transform);
        _up.transform(_transform);
        _right.transform(_transform);
    }

    @Override
    public void transform(Vector3D translation, Vector3D rotation, Vector3D scale) {
        _origin.transform(translation, rotation, scale);
        _direction.transform(translation, rotation, scale);
        _up.transform(translation, rotation, scale);
        _right.transform(translation, rotation, scale);
    }
}
