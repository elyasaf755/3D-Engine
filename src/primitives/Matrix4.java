package primitives;

public class Matrix4 {
    private double[][] _m;

    public static final Matrix4 IDENTITY = new Matrix4(new double[][]{
            {1,0,0,0},
            {0,1,0,0},
            {0,0,1,0},
            {0,0,0,1}
    });


    //Constructors

    public Matrix4(){
        _m = new double[4][4];
    }

    public Matrix4(double[][] matrix) {
        int rows = matrix.length;
        int cols = matrix[0].length;

        if (rows != cols || rows != 4){
            throw new IllegalArgumentException("Cant load this matrix to a 4x4 matrix");
        }

        _m = new double[4][4];

        for (int i = 0; i < 4; ++i){
            for (int j = 0; j < 4; ++j){
                _m[i][j] = matrix[i][j];
            }
        }
    }

    public Matrix4(Matrix4 matrix) {
        double[][] temp = matrix.getMatrix();

        int rows = temp.length;
        int cols = temp[0].length;

        if (rows != cols || rows != 4){
            throw new IllegalArgumentException("Cant load this matrix to a 4x4 matrix");
        }

        _m = new double[4][4];

        for (int i = 0; i < 4; ++i){
            for (int j = 0; j < 4; ++j){
                _m[i][j] = temp[i][j];
            }
        }
    }

    //Getters

    public double[][] getMatrix() {
        double[][] result = new double[4][4];

        for (int i = 0; i < 4; ++i){
            for (int j = 0; j < 4; ++j){
                result[i][j] = _m[i][j];
            }
        }

        return result;
    }

    //Setters

    public void setMatrix(double[][] matrix) {
        int rows = matrix.length;
        int cols = matrix[0].length;

        if (rows != cols || rows != 4){
            throw new IllegalArgumentException("Cant load this matrix to a 4x4 matrix");
        }

        for (int i = 0; i < 4; ++i){
            for (int j = 0; j < 4; ++j){
                _m[i][j] = matrix[i][j];
            }
        }
    }

    public void setMatrix(Matrix4 matrix) {
        double[][] temp = matrix.getMatrix();

        int rows = temp.length;
        int cols = temp[0].length;

        if (rows != cols || rows != 4){
            throw new IllegalArgumentException("Cant load this matrix to a 4x4 matrix");
        }

        for (int i = 0; i < 4; ++i){
            for (int j = 0; j < 4; ++j){
                _m[i][j] = temp[i][j];
            }
        }
    }

    //Methods

    public Matrix4 initIdentity(){

        setRow(0,1,0,0,0);
        setRow(1,0,1,0,0);
        setRow(2,0,0,1,0);
        setRow(3,0,0,0,1);

        return this;
    }

    public Matrix4 initTranslation(Vector3D vector){

        double x = vector.getPoint().getX().getCoord();
        double y = vector.getPoint().getY().getCoord();
        double z = vector.getPoint().getZ().getCoord();

        setRow(0,1,0,0,    x);
        setRow(1,0,1,0,    y);
        setRow(2,0,0,1,    z);
        setRow(3,0,0,0,1);

        return this;
    }

    public Matrix4 initTranslation(double x, double y, double z){

        setRow(0,1,0,0,    x);
        setRow(1,0,1,0,    y);
        setRow(2,0,0,1,    z);
        setRow(3,0,0,0,1);

        return this;
    }

    public Matrix4 initRotation(Vector3D vector){

        double x = vector.getPoint().getX().getCoord();
        double y = vector.getPoint().getY().getCoord();
        double z = vector.getPoint().getZ().getCoord();

        Matrix4 rx = new Matrix4();
        Matrix4 ry = new Matrix4();
        Matrix4 rz = new Matrix4();

        x = Math.toRadians(x);
        y = Math.toRadians(y);
        z = Math.toRadians(z);

        rx.setRow(0, 1, 0,        0,         0);
        rx.setRow(1, 0, Math.cos(x), -Math.sin(x), 0);
        rx.setRow(2, 0, Math.sin(x),  Math.cos(x), 0);
        rx.setRow(3, 0,0,         0,         1);

        ry.setRow(0, Math.cos(y),0, -Math.sin(y), 0);
        ry.setRow(1, 0,       1,  0,        0);
        ry.setRow(2, Math.sin(y),0,  Math.cos(y), 0);
        ry.setRow(3, 0,       0,  0,        1);

        rz.setRow(0, Math.cos(z), -Math.sin(z), 0, 0);
        rz.setRow(1, Math.sin(z), Math.cos(z),  0, 0);
        rz.setRow(2, 0,      0,           1,0);
        rz.setRow(3, 0,      0,           0,1);

        setMatrix(rz.mult(ry.mult(rx)));

        return this;
    }

    public Matrix4 initRotation(double x, double y, double z){
        Matrix4 rx = new Matrix4();
        Matrix4 ry = new Matrix4();
        Matrix4 rz = new Matrix4();

        x = Math.toRadians(x);
        y = Math.toRadians(y);
        z = Math.toRadians(z);

        rx.setRow(0, 1, 0,        0,         0);
        rx.setRow(1, 0, Math.cos(x), -Math.sin(x), 0);
        rx.setRow(2, 0, Math.sin(x),  Math.cos(x), 0);
        rx.setRow(3, 0,0,         0,         1);

        ry.setRow(0, Math.cos(y),0, -Math.sin(y), 0);
        ry.setRow(1, 0,       1,  0,        0);
        ry.setRow(2, Math.sin(y),0,  Math.cos(y), 0);
        ry.setRow(3, 0,       0,  0,        1);

        rz.setRow(0, Math.cos(z), -Math.sin(z), 0, 0);
        rz.setRow(1, Math.sin(z), Math.cos(z),  0, 0);
        rz.setRow(2, 0,      0,           1,0);
        rz.setRow(3, 0,      0,           0,1);

        setMatrix(rz.mult(ry.mult(rx)));

        return this;
    }

    public Matrix4 initScale(Vector3D vector){

        double x = vector.getPoint().getX().getCoord();
        double y = vector.getPoint().getY().getCoord();
        double z = vector.getPoint().getZ().getCoord();

        setRow(0,   x,0,0,0);
        setRow(1,0,   y,0,0);
        setRow(2,0,0,   z,0);
        setRow(3,0,0,0,1);

        return this;
    }

    public Matrix4 initScale(double x, double y, double z){

        setRow(0,   x,0,0,0);
        setRow(1,0,   y,0,0);
        setRow(2,0,0,   z,0);
        setRow(3,0,0,0,1);

        return this;
    }

    public void setColumn(int column, double x, double y, double z, double w){
        _m[0][column] = x;
        _m[1][column] = y;
        _m[2][column] = z;
        _m[3][column] = w;
    }

    public void setRow(int row, double x, double y, double z, double w){
        _m[row][0] = x;
        _m[row][1] = y;
        _m[row][2] = z;
        _m[row][3] = w;
    }

    public Matrix4 mult(Matrix4 matrix4) {
        Matrix lhs = new Matrix(_m);
        Matrix rhs = new Matrix(matrix4.getMatrix());

        Matrix result = lhs.mult(rhs);


        return new Matrix4(result.get_matrix());
    }

    public Vector3D mult (Vector3D vector){
        Matrix lhs = new Matrix(this.getMatrix());
        Matrix rhs = new Matrix(4,1);
        rhs.set_element(0,0, vector.getPoint().getX().getCoord());
        rhs.set_element(1,0, vector.getPoint().getY().getCoord());
        rhs.set_element(2,0, vector.getPoint().getZ().getCoord());
        rhs.set_element(3,0, 1);

        Matrix result = lhs.mult(rhs);

        return new Vector3D(
                (double)result.get_element(0,0),
                (double)result.get_element(1,0),
                (double)result.get_element(2,0)
        );
    }

    public Point3D mult (Point3D point3D){
        Matrix lhs = new Matrix(this.getMatrix());
        Matrix rhs = new Matrix(4,1);
        rhs.set_element(0,0, point3D.getX().getCoord());
        rhs.set_element(1,0, point3D.getY().getCoord());
        rhs.set_element(2,0, point3D.getZ().getCoord());
        rhs.set_element(3,0, 1);

        Matrix result = lhs.mult(rhs);

        return new Point3D(
                (double)result.get_element(0,0),
                (double)result.get_element(1,0),
                (double)result.get_element(2,0)
        );
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;

        if (obj == null)
            return false;

        if (getClass() != obj.getClass())
            return false;

        Matrix4 matrix = (Matrix4) obj;

        for (int i = 0; i < 4; ++i){
            for (int j = 0; j < 4; ++j){
                if (!Util.equals(getMatrix()[i][j], matrix.getMatrix()[i][j]))
                    return false;
            }
        }

        return true;
    }
}
