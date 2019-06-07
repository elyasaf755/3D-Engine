package elements;

import primitives.*;

public class Camera {
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

}
