package elements;

import primitives.Matrix;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector3D;

public class Camera {
    private Point3D _position;
    private Vector3D _up;
    private Vector3D _to;
    private Vector3D _right;

    public Camera(Point3D position, Vector3D up, Vector3D to, Vector3D right){
        if (up.dotProduct(to) == 0 && up.dotProduct(right) == 0 && to.dotProduct(right) == 0){
            _position = new Point3D(position);
            _up = (new Vector3D(up)).normalized();
            _to = (new Vector3D(to)).normalized();
            _right = (new Vector3D(right)).normalized();
        }
        else{
            Matrix orthonormalBase = (new Matrix(up, to, right)).orthonormalized();
            _position = new Point3D(position);
            _up = (new Vector3D(up)).normalized();
            _to = Matrix.getColumnAsVector3(orthonormalBase, 1);
            _right = Matrix.getColumnAsVector3(orthonormalBase, 2);
        }
    }

    public Point3D get_position(){
        return new Point3D(_position);
    }

    public Vector3D get_up() {
        return new Vector3D(_up);
    }

    public Vector3D get_to() {
        return new Vector3D(_to);
    }

    public Vector3D get_right() {
        return new Vector3D(_right);
    }

    public Ray constructRayThroughPixel(int Nx, int Ny, int i, int j, double screenDistance, double screenWidth, double screenHeight){
        Point3D p0 = get_position();
        Vector3D up = get_up();
        Vector3D to = get_to();
        Vector3D right = get_right();

        //Image center point
        Point3D Pc = p0.add(to.scale(screenDistance));

        //Ratio (pixel height/width)
        double Ry = screenHeight / Ny;
        double Rx = screenWidth / Nx;

        //Center pixel
        double Xi = (i - Nx / 2)*Rx + Rx/2;
        double Yj = (j - Ny / 2)*Ry + Ry/2;
        Point3D p_ij = Pc.add((right.scale(Xi)).subtract(up.scale(Yj)));
        Vector3D v_ij = p_ij.subtract(p0);

        return new Ray(p0, v_ij.normalized());
    }

}
