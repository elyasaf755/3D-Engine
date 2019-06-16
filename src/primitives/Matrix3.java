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
        Matrix lhs = new Matrix(_m);
        Matrix rhs = new Matrix(matrix.getMatrix());

        Matrix result = lhs.mult(rhs);


        return new Matrix3(result.get_matrix());
    }

    public Vector3D mult(Vector3D vector){
        Matrix lhs = new Matrix(this.getMatrix());
        Matrix rhs = new Matrix(vector);

        Matrix result = lhs.mult(rhs);

        return new Vector3D(
                (double)result.get_element(0,0),
                (double)result.get_element(1,0),
                (double)result.get_element(2,0)
        );
    }

    public Point3D mult(Point3D point3D){
        Matrix lhs = new Matrix(this.getMatrix());
        Matrix rhs = new Matrix(3,1);

        rhs.set_element(0,0, point3D.getX().getCoord());
        rhs.set_element(1,0, point3D.getY().getCoord());
        rhs.set_element(2,0, point3D.getZ().getCoord());

        Matrix result = lhs.mult(rhs);

        return new Point3D(
                (double)result.get_element(0,0),
                (double)result.get_element(1,0),
                (double)result.get_element(2,0)
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

    public Matrix3 inverse(){
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
