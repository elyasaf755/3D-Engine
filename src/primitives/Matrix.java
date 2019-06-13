package primitives;

import java.math.BigDecimal;
import java.math.MathContext;

public class Matrix {
    private double[][] _matrix;
    private int _numOfRows;
    private int _numOfCols;

    //Constructors

    public Matrix(int rows, int cols){
        _matrix = new double[rows][cols];
        _numOfRows = rows;
        _numOfCols = cols;

        for(int i = 0; i < rows; i++)
        {
            for(int j = 0; j < cols; j++)
            {
                _matrix[i][j] = 0;
            }
        }
    }

    public Matrix(double[][] matrix){
        _numOfRows = matrix.length;
        _numOfCols = matrix[0].length;
        _matrix = new double[_numOfRows][_numOfCols];

        for(int i = 0; i < _numOfRows; i++)
        {
            for(int j = 0; j < _numOfCols; j++)
            {
                Coordinate temp = new Coordinate(matrix[i][j]);
                _matrix[i][j] = temp.getCoord();
            }
        }
    }

    public Matrix(Matrix matrix){
        _numOfCols = matrix.getColumns();
        _numOfRows = matrix.getRows();
        _matrix = new double[_numOfRows][_numOfCols];

        for(int i = 0; i < _numOfRows; i++)
        {
            for(int j = 0; j < _numOfCols; j++)
            {
                _matrix[i][j] = matrix.get_element(i, j);
            }
        }
    }

    public Matrix(Vector3D v1, Vector3D v2, Vector3D v3){
        _matrix = new double[3][3];
        _numOfRows = 3;
        _numOfCols = 3;

        _matrix[0][0] = v1.getPoint().getX().getCoord();
        _matrix[1][0] = v1.getPoint().getY().getCoord();
        _matrix[2][0] = v1.getPoint().getZ().getCoord();
        _matrix[0][1] = v2.getPoint().getX().getCoord();
        _matrix[1][1] = v2.getPoint().getY().getCoord();
        _matrix[2][1] = v2.getPoint().getZ().getCoord();
        _matrix[0][2] = v3.getPoint().getX().getCoord();
        _matrix[1][2] = v3.getPoint().getY().getCoord();
        _matrix[2][2] = v3.getPoint().getZ().getCoord();
    }

    //Methods

    public int getRows(){return _numOfRows;}

    public int getColumns(){return _numOfCols;}

    public double[][] get_matrix() {
        double[][] result = new double[_numOfRows][_numOfCols];

        for (int i = 0; i < _numOfRows; ++i){
            for (int j = 0; j < _numOfCols; ++j){
                result[i][j] = _matrix[i][j];
            }
        }

        return result;
    }

    public double get_element(int row, int col){
        return _matrix[row][col];
    }

    public void set_element(int row, int col, double value){
        Coordinate temp = new Coordinate(value);
        _matrix[row][col] = temp.getCoord();
    }

    public void set_matrix(double[][] matrix){
        int rows = matrix.length;
        int cols = matrix[0].length;

        if (_numOfRows != rows || _numOfCols != cols){
            throw new IllegalArgumentException("Can't copy a matrix with different dimensions");
        }

        for (int i = 0; i < _numOfRows; ++i){
            for (int j = 0; j < _numOfCols; ++j){
                Coordinate temp = new Coordinate(matrix[i][j]);
                _matrix[i][j] = temp.getCoord();
            }
        }

    }

    public Matrix add(Matrix matrix) {
        if (matrix.getRows() != _numOfRows || matrix.getColumns() != _numOfCols){
            return null;
        }
        Matrix result = new Matrix(matrix.getRows(), matrix.getColumns());


        for (int i = 0; i < _numOfRows; ++i){
            for (int j = 0; j < _numOfCols; ++j){
                Coordinate temp = new Coordinate(_matrix[i][j] + matrix.get_matrix()[i][j]);
                result.set_element(i, j, temp.getCoord());
            }
        }

        return result;
    }

    public Matrix sub(Matrix matrix) {
        if (matrix.getRows() != _numOfRows || matrix.getColumns() != _numOfCols){
            return null;
        }
        Matrix result = new Matrix(matrix.getRows(), matrix.getColumns());


        for (int i = 0; i < _numOfRows; ++i){
            for (int j = 0; j > _numOfCols; ++i){
                Coordinate temp = new Coordinate(_matrix[i][j] - matrix.get_matrix()[i][j]);

                result.set_element(i, j, temp.getCoord());
            }
        }

        return result;
    }

    public Matrix mult(Matrix matrix) {
        if (_numOfCols != matrix.getRows())
            return null;

        Matrix result = new Matrix(_numOfRows, matrix.getColumns());


        for (int i = 0; i < _numOfRows; i++)
        {
            for (int j = 0; j < matrix.getColumns(); j++)
            {
                BigDecimal sum = new BigDecimal(0, MathContext.UNLIMITED);

                for (int k = 0; k < matrix.getRows(); k++)
                {
                    BigDecimal temp = new BigDecimal(_matrix[i][k]*matrix.get_element(k, j), MathContext.UNLIMITED);
                    sum = sum.add(temp);
                }

                Coordinate temp = new Coordinate(sum.doubleValue());

                result.set_element(i, j, temp.getCoord());
            }
        }

        return result;
    }

    public Vector3D mult(Vector3D vector) {
        if (_numOfCols != 3)
            return null;

        return new Vector3D(_matrix[0][0]*vector.getPoint().getX()._coord + _matrix[0][1]*vector.getPoint().getY()._coord + _matrix[0][2]*vector.getPoint().getZ()._coord,
                _matrix[1][0]*vector.getPoint().getX()._coord + _matrix[1][1]*vector.getPoint().getY()._coord + _matrix[1][2]*vector.getPoint().getZ()._coord,
                _matrix[2][0]*vector.getPoint().getX()._coord + _matrix[2][1]*vector.getPoint().getY()._coord + _matrix[2][2]*vector.getPoint().getZ()._coord);
    }

    public Matrix mult(double scalar){
        Matrix result = new Matrix(this);

        for (int i = 0; i < _numOfRows; ++i){
            for (int j = 0; j < _numOfCols; ++j){
                Coordinate temp = new Coordinate(result.get_element(i, j)*scalar);
                result.set_element(i, j, temp.getCoord());
            }
        }

        return result;
    }

    public Matrix multRow(int row, double scalar){
        Matrix result = new Matrix(_matrix);

        for (int j = 0; j < _numOfCols; ++j){
            Coordinate temp = new Coordinate(result.get_element(row, j)*scalar);
            result.set_element(row, j, temp.getCoord());
        }

        return result;
    }

    public Matrix multCol(int col, double scalar){
        Matrix result = new Matrix(_matrix);

        for (int i = 0; i < _numOfRows; ++i){
            Coordinate temp = new Coordinate(result.get_element(i, col)*scalar);
            result.set_element(i, col, temp.getCoord());
        }

        return result;
    }

    public double determinant(){
        return staticDeterminant(_matrix);
    }

    public static double staticDeterminant(double[][] matrix) {
        double temporary[][];
        double result = 0;

        if (matrix.length == 1) {
            result = matrix[0][0];
            return (result);
        }

        if (matrix.length == 2) {
            result = Util.usubtract(Util.uscale(matrix[0][0], matrix[1][1]), Util.uscale(matrix[0][1], matrix[1][0]));
            return (result);
        }

        for (int i = 0; i < matrix[0].length; i++) {
            temporary = new double[matrix.length - 1][matrix[0].length - 1];

            for (int j = 1; j < matrix.length; j++) {
                for (int k = 0; k < matrix[0].length; k++) {
                    if (k < i) {
                        temporary[j - 1][k] = matrix[j][k];
                    } else if (k > i) {
                        temporary[j - 1][k - 1] = matrix[j][k];
                    }
                }
            }

            result = Util.uadd(result, Util.uscale(matrix[0][i], Util.uscale(Math.pow (-1, (double) i), staticDeterminant(temporary))));
        }
        return (result);
    }

    public static double staticDeterminant(Matrix matrix) {
        double temporary[][];
        double result = 0;

        double[][] m = matrix.get_matrix();

        if (m.length == 1) {
            result = m[0][0];
            return (result);
        }

        if (m.length == 2) {
            result = Util.usubtract(Util.uscale(m[0][0], m[1][1]), Util.uscale(m[0][1], m[1][0]));
            return (result);
        }

        for (int i = 0; i < m[0].length; i++) {
            temporary = new double[m.length - 1][m[0].length - 1];

            for (int j = 1; j < m.length; j++) {
                for (int k = 0; k < m[0].length; k++) {
                    if (k < i) {
                        Coordinate temp = new Coordinate(m[j][k]);
                        temporary[j - 1][k] = temp.getCoord();
                    } else if (k > i) {
                        Coordinate temp = new Coordinate(m[j][k]);
                        temporary[j - 1][k - 1] = temp.getCoord();
                    }
                }
            }

            result = Util.uadd(result, Util.uscale(m[0][i], Util.uscale(Math.pow (-1, (double) i), staticDeterminant(temporary))));
        }

        return (result);
    }

    public static Matrix transpose(Matrix matrix) {
        Matrix result = new Matrix(matrix.getColumns(), matrix.getRows());
        for (int i = 0; i < matrix.getRows(); i++) {
            for (int j = 0; j < matrix.getColumns(); j++) {
                Coordinate temp = new Coordinate(matrix.get_element(i, j));
                result.set_element(j, i, temp.getCoord());
            }
        }
        return result;
    }

    public Matrix transpose(){
        return new Matrix(transpose(this));
    }

    public static void copyColumnToColumn(Matrix sourceMat, int sourceCol, Matrix destMat, int destCol){
        for (int i = 0; i < sourceMat.getRows(); ++i){
            destMat.set_element(i, destCol, sourceMat.get_element(i, sourceCol));
        }
    }

    public static void copyRowToRow(Matrix sourceMat, int sourceRow, Matrix destMat, int destRow){
        for (int j = 0; j < sourceMat.getRows(); ++j){
            destMat.set_element(destRow, j, sourceMat.get_element(sourceRow, j));
        }
    }

    public static void copyVector3ToColumn(Vector3D vector, Matrix matrix, int col){

        int rows = matrix.getRows();

        if (rows != 3)
            return;

        matrix.set_element(0, col, vector.getPoint().getX().getCoord());
        matrix.set_element(1, col, vector.getPoint().getY().getCoord());
        matrix.set_element(2, col, vector.getPoint().getZ().getCoord());
    }

    public static double[] getColumn(Matrix source, int colIndex){
        int rows = source.get_matrix().length;
        int cols = source.get_matrix()[0].length;
        double[] result = new double[rows];

        for (int i = 0; i < rows; ++i){
            result[i] = source.get_element(i, colIndex);
        }

        return result;
    }

    public static Vector3D getColumnAsVector3(Matrix source, int colIndex) {
        return new Vector3D(
                source.get_element(0, colIndex),
                source.get_element(1, colIndex),
                source.get_element(2, colIndex)
        );
    }

    public Matrix inversedMatrix3X3(){
        if (_numOfRows != _numOfCols || _numOfRows != 3)
            return null;

        Matrix temp = new Matrix(4,4);

        temp.set_element(0, 0, _matrix[1][1]);
        temp.set_element(0, 1, _matrix[1][2]);
        temp.set_element(0, 2, _matrix[1][0]);
        temp.set_element(0, 3, _matrix[1][1]);
        temp.set_element(1, 0, _matrix[2][1]);
        temp.set_element(1, 1, _matrix[2][2]);
        temp.set_element(1, 2, _matrix[2][0]);
        temp.set_element(1, 3, _matrix[2][1]);
        temp.set_element(2, 0, _matrix[0][1]);
        temp.set_element(2, 1, _matrix[0][2]);
        temp.set_element(2, 2, _matrix[0][0]);
        temp.set_element(2, 3, _matrix[0][1]);
        temp.set_element(3, 0, _matrix[1][1]);
        temp.set_element(3, 1, _matrix[1][2]);
        temp.set_element(3, 2, _matrix[1][0]);
        temp.set_element(3, 3, _matrix[1][1]);

        Matrix result = new Matrix(3,3);

        Coordinate coord;

        BigDecimal e00 = new BigDecimal(Util.usubtract(Util.uscale(temp.get_element(0,0), temp.get_element(1,1)), Util.uscale(temp.get_element(0,1), temp.get_element(1,0))), MathContext.UNLIMITED);
        coord = new Coordinate(e00.doubleValue());
        result.set_element(0,0, coord.getCoord());
        BigDecimal e01 = new BigDecimal(Util.usubtract(Util.uscale(temp.get_element(1,0), temp.get_element(2,1)), Util.uscale(temp.get_element(1,1), temp.get_element(2,0))), MathContext.UNLIMITED);
        coord = new Coordinate(e01.doubleValue());
        result.set_element(0,1, coord.getCoord());
        BigDecimal e02 = new BigDecimal(Util.usubtract(Util.uscale(temp.get_element(2,0), temp.get_element(3,1)), Util.uscale(temp.get_element(2,1), temp.get_element(3,0))), MathContext.UNLIMITED);
        coord = new Coordinate(e02.doubleValue());
        result.set_element(0,2, coord.getCoord());
        BigDecimal e10 = new BigDecimal(Util.usubtract(Util.uscale(temp.get_element(0,1), temp.get_element(1,2)), Util.uscale(temp.get_element(0,2), temp.get_element(1,1))), MathContext.UNLIMITED);
        coord = new Coordinate(e10.doubleValue());
        result.set_element(1,0, coord.getCoord());
        BigDecimal e11 = new BigDecimal(Util.usubtract(Util.uscale(temp.get_element(1,1), temp.get_element(2,2)), Util.uscale(temp.get_element(1,2), temp.get_element(2,1))), MathContext.UNLIMITED);
        coord = new Coordinate(e11.doubleValue());
        result.set_element(1,1, coord.getCoord());
        BigDecimal e12 = new BigDecimal(Util.usubtract(Util.uscale(temp.get_element(2,1), temp.get_element(3,2)), Util.uscale(temp.get_element(2,2), temp.get_element(3,1))), MathContext.UNLIMITED);
        coord = new Coordinate(e12.doubleValue());
        result.set_element(1,2, coord.getCoord());
        BigDecimal e20 = new BigDecimal(Util.usubtract(Util.uscale(temp.get_element(0,2), temp.get_element(1,3)), Util.uscale(temp.get_element(0,3), temp.get_element(1,2))), MathContext.UNLIMITED);
        coord = new Coordinate(e20.doubleValue());
        result.set_element(2,0, coord.getCoord());
        BigDecimal e21 = new BigDecimal(Util.usubtract(Util.uscale(temp.get_element(1,2), temp.get_element(2,3)), Util.uscale(temp.get_element(1,3), temp.get_element(2,2))), MathContext.UNLIMITED);
        coord = new Coordinate(e21.doubleValue());
        result.set_element(2,1, coord.getCoord());
        BigDecimal e22 = new BigDecimal(Util.usubtract(Util.uscale(temp.get_element(2,2), temp.get_element(3,3)), Util.uscale(temp.get_element(2,3), temp.get_element(3,2))), MathContext.UNLIMITED);
        coord = new Coordinate(e22.doubleValue());
        result.set_element(2,2, coord.getCoord());

        BigDecimal a = new BigDecimal(1, MathContext.UNLIMITED);
        BigDecimal b = new BigDecimal(determinant(), MathContext.UNLIMITED);
        BigDecimal c = new BigDecimal((a.divide(b)).doubleValue(), MathContext.UNLIMITED);

        return result.mult(c.doubleValue());
    }

    public void inverse3x3(){
        if (_numOfRows != _numOfCols || _numOfRows != 3)
            return;

        Matrix inversed = this.inversedMatrix3X3();

        _matrix[0][0] = inversed.get_element(0,0);
        _matrix[1][0] = inversed.get_element(1,0);
        _matrix[2][0] = inversed.get_element(2,0);
        _matrix[0][1] = inversed.get_element(0,1);
        _matrix[1][1] = inversed.get_element(1,1);
        _matrix[2][1] = inversed.get_element(2,1);
        _matrix[0][2] = inversed.get_element(0,2);
        _matrix[1][2] = inversed.get_element(1,2);
        _matrix[2][2] = inversed.get_element(2,2);
    }

    public void set_matrix_3x3(Vector3D v1, Vector3D v2, Vector3D v3){
        if (_numOfRows != 3 || _numOfCols != 3){
            return;
        }

        _matrix[0][0] = v1.getPoint().getX().getCoord();
        _matrix[1][0] = v1.getPoint().getY().getCoord();
        _matrix[2][0] = v1.getPoint().getZ().getCoord();
        _matrix[0][1] = v2.getPoint().getX().getCoord();
        _matrix[1][1] = v2.getPoint().getY().getCoord();
        _matrix[2][1] = v2.getPoint().getZ().getCoord();
        _matrix[0][2] = v3.getPoint().getX().getCoord();
        _matrix[1][2] = v3.getPoint().getY().getCoord();
        _matrix[2][2] = v3.getPoint().getZ().getCoord();
    }

    public static Matrix grahmSchmidt3X3(Matrix basis){
        Matrix result = new Matrix(3,3);
        int rows = basis.getRows();
        int cols = basis.getColumns();

        Vector3D V1 = Matrix.getColumnAsVector3(basis, 0);
        Vector3D V2 = Matrix.getColumnAsVector3(basis, 1);
        Vector3D V3 = Matrix.getColumnAsVector3(basis, 2);

        if (V1.dotProduct(V2) == 0 && V1.dotProduct(V3) == 0 && V2.dotProduct(V3) == 0){
            return basis;
        }

        Vector3D U1 = V1;
        Vector3D U2 = V2.subtract(U1.projection(V2));
        Vector3D U3 = (V3.subtract(U1.projection(V3))).subtract(U2.projection(V3));

        result.set_matrix_3x3(U1.normalized(), U2.normalized(), U3.normalized());

        return result;
    }

    /**
     get an orthonormalized 3x3 vector space basis
     @return Orthonormalized basis of this matrix representing a 3x3 vector space
     */
    public Matrix orthonormalized(){
        if (_numOfRows != _numOfCols || _numOfRows != 3)
            return null;

        return Matrix.grahmSchmidt3X3(this);
    }

    public void orthonormalize(){
        Matrix orthonormalizedMat = this.orthonormalized();

        _matrix[0][0] = orthonormalizedMat.get_element(0,0);
        _matrix[1][0] = orthonormalizedMat.get_element(1,0);
        _matrix[2][0] = orthonormalizedMat.get_element(2,0);
        _matrix[0][1] = orthonormalizedMat.get_element(0,1);
        _matrix[1][1] = orthonormalizedMat.get_element(1,1);
        _matrix[2][1] = orthonormalizedMat.get_element(2,1);
        _matrix[0][2] = orthonormalizedMat.get_element(0,2);
        _matrix[1][2] = orthonormalizedMat.get_element(1,2);
        _matrix[2][2] = orthonormalizedMat.get_element(2,2);
    }

    public static Matrix orthonormalBasis3X3(Vector3D vector){
        Matrix result = new Matrix(3,3);

        //Trivial solutions
        if (vector.normalized().equals(new Vector3D(1,0,0))){
            Vector3D v1 = new Vector3D(1,0,0);
            Vector3D v2 = new Vector3D(0,1,0);
            Vector3D v3 = new Vector3D(0,0,1);
            return new Matrix(v1, v2, v3);
        }
        if (vector.normalized().equals(new Vector3D(0,1,0))){
            Vector3D v1 = new Vector3D(1,0,0);
            Vector3D v2 = new Vector3D(0,1,0);
            Vector3D v3 = new Vector3D(0,0,1);
            return new Matrix(v1, v2, v3);
        }
        if (vector.normalized().equals(new Vector3D(0,0,1))){
            Vector3D v1 = new Vector3D(1,0,0);
            Vector3D v2 = new Vector3D(0,1,0);
            Vector3D v3 = new Vector3D(0,0,1);
            return new Matrix(v1, v2, v3);
        }

        Vector3D U1 = new Vector3D(vector);
        double x1 = U1.getPoint().getX().getCoord();
        double y1 = U1.getPoint().getY().getCoord();
        double z1 = U1.getPoint().getZ().getCoord();

        Vector3D U2 = new Vector3D(1,1,1);

        if (x1 != 0){
            U2.set_point(Util.usubtract(-y1,z1)/x1, 1, 1);
        }
        else if (y1 !=0){
            U2.set_point(1, Util.usubtract(-x1,z1)/y1, 1);
        }
        else if (z1 !=0){
            U2.set_point(1, 1, Util.usubtract(-x1,y1)/z1);
        }

        Vector3D U3 = U1.crossProduct(U2);

        U1.normalize();
        U2.normalize();
        U3.normalize();

        Matrix.copyVector3ToColumn(U1, result, 0);
        Matrix.copyVector3ToColumn(U2, result, 1);
        Matrix.copyVector3ToColumn(U3, result, 2);

        return result;
    }

    @Override
    public String toString() {
        String result = "";
        for (int i = 0; i < _numOfRows; ++i){
            result += "|";
            for (int j = 0; j < _numOfCols; ++j){
                result += " (" + _matrix[i][j] + ") ";
            }

            result += "|\n";
        }

        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;

        if (obj == null)
            return false;

        if (getClass() != obj.getClass())
            return false;

        Matrix matrix = (Matrix)obj;

        if (_numOfRows != matrix.getRows() || _numOfCols != matrix.getColumns())
            return false;

        for (int i = 0; i < _numOfRows; ++i){
            for (int j = 0; j < _numOfCols; ++j){
                if (!Util.equals(_matrix[i][j], matrix.get_element(i, j)))
                    return false;
            }
        }

        return true;
    }
}
