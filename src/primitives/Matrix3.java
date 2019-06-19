package primitives;

//TODO: TEST CLASS METHODS
public class Matrix3 {
    private double[][] _m;

    public static final double[][] IDENTITY = {
            {1,0,0},
            {0,1,0},
            {0,0,1},
    };

    //Constructors

    public Matrix3(){
        _m = new double[3][3];
    }

    public Matrix3(double[][] matrix) {
        int rows = matrix.length;
        int cols = matrix[0].length;

        if (rows != cols || rows != 3){
            throw new IllegalArgumentException("Cant load this matrix to a 3x3 matrix");
        }

        _m = new double[3][3];

        for (int i = 0; i < 3; ++i){
            for (int j = 0; j < 3; ++j){
                Coordinate temp = new Coordinate(matrix[i][j]);
                _m[i][j] = temp.getCoord();
            }
        }
    }

    public Matrix3(Matrix3 matrix) {
        double[][] temp = matrix.getMatrix();

        int rows = temp.length;
        int cols = temp[0].length;

        if (rows != cols || rows != 3){
            throw new IllegalArgumentException("Cant load this matrix to a 3x3 matrix");
        }

        _m = new double[3][3];

        for (int i = 0; i < 3; ++i){
            for (int j = 0; j < 3; ++j){
                _m[i][j] = temp[i][j];
            }
        }
    }

    //Getters

    public double[][] getMatrix() {
        double[][] result = new double[3][3];

        for (int i = 0; i < 3; ++i){
            for (int j = 0; j < 3; ++j){
                result[i][j] = _m[i][j];
            }
        }

        return result;
    }

    //Setters

    public void set_element(int row, int col, double value){
        Coordinate temp = new Coordinate(value);
        _m[row][col] = temp.getCoord();
    }

    //Methods

    public Matrix3 add(Matrix3 matrix) {
        Matrix3 result = new Matrix3();

        for (int i = 0; i < 3; ++i){
            for (int j = 0; j < 3; ++j){
                Coordinate temp = new Coordinate(_m[i][j] + matrix.getMatrix()[i][j]);
                result.set_element(i, j, temp.getCoord());
            }
        }

        return result;
    }

    public Matrix3 sub(Matrix3 matrix) {
        Matrix3 result = new Matrix3();

        for (int i = 0; i < 3; ++i){
            for (int j = 0; j < 3; ++j){
                Coordinate temp = new Coordinate(_m[i][j] - matrix.getMatrix()[i][j]);
                result.set_element(i, j, temp.getCoord());
            }
        }

        return result;
    }

    public Matrix3 mult(Matrix3 matrix) {
        double[][] A = this.getMatrix();
        double[][] B = this.getMatrix();

        double a00 = A[0][0]; double a01 = A[0][1]; double a02 = A[0][2];
        double a10 = A[1][0]; double a11 = A[1][1]; double a12 = A[1][2];
        double a20 = A[2][0]; double a21 = A[2][1]; double a22 = A[2][2];

        double b00 = B[0][0]; double b01 = B[0][1]; double b02 = B[0][2];
        double b10 = B[1][0]; double b11 = B[1][1]; double b12 = B[1][2];
        double b20 = B[2][0]; double b21 = B[2][1]; double b22 = B[2][2];

        double[][] result = {
                {a00*b00 + a01*b10 + a02*b20, a00*b01 + a01*b11 + a02*b21, a00*b02 + a01*b12 + a02*b22},
                {a10*b00 + a11*b10 + a12*b20, a10*b01 + a11*b11 + a12*b21, a10*b02 + a11*b12 + a12*b22},
                {a20*b00 + a21*b10 + a22*b20, a20*b01 + a21*b11 + a22*b21, a20*b02 + a21*b12 + a22*b22},
        };

        return new Matrix3(result);
    }

    public Vector3D mult(Vector3D vector){
        double[][] temp = this.getMatrix();

        double px = vector.getPoint().getX().getCoord();
        double py = vector.getPoint().getY().getCoord();
        double pz = vector.getPoint().getZ().getCoord();

        double a00 = temp[0][0]; double a01 = temp[0][1]; double a02 = temp[0][2];
        double a10 = temp[1][0]; double a11 = temp[1][1]; double a12 = temp[1][2];
        double a20 = temp[2][0]; double a21 = temp[2][1]; double a22 = temp[2][2];

        return new Vector3D(
                a00*px + a01*py + a02*pz,
                a10*px + a11*py + a12*pz,
                a20*px + a21*py + a22*pz
        );
    }

    public Point3D mult(Point3D point3D){
        double[][] temp = this.getMatrix();

        double px = point3D.getX().getCoord();
        double py = point3D.getY().getCoord();
        double pz = point3D.getZ().getCoord();

        double a00 = temp[0][0]; double a01 = temp[0][1]; double a02 = temp[0][2];
        double a10 = temp[1][0]; double a11 = temp[1][1]; double a12 = temp[1][2];
        double a20 = temp[2][0]; double a21 = temp[2][1]; double a22 = temp[2][2];

        return new Point3D(
                a00*px + a01*py + a02*pz,
                a10*px + a11*py + a12*pz,
                a20*px + a21*py + a22*pz
        );
    }

    public Matrix3 scale(double scalar){
        double[][] result = new double[3][3];

        for (int i = 0; i < 3; ++i){
            for (int j = 0; j < 3; ++j){
                result[i][j] = _m[i][j] * scalar;
            }
        }

        return new Matrix3(result);
    }

    public Matrix3 transpose(){
        Matrix result = new Matrix(this.getMatrix());

        return new Matrix3(result.transpose().get_matrix());
    }

    public Matrix3 inversed(){
        return new Matrix3(new Matrix(this.getMatrix()).inversedMatrix3X3().get_matrix());
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

        Matrix3 matrix = (Matrix3) obj;

        for (int i = 0; i < 3; ++i){
            for (int j = 0; j < 3; ++j){
                if (!Util.equals(getMatrix()[i][j], matrix.getMatrix()[i][j]))
                    return false;
            }
        }

        return true;
    }
}
