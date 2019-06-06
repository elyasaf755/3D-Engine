package primitives;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MatrixTest {


    @Test
    void getRows() {
        Matrix matrix = new Matrix(3,3);
        assertEquals(3, matrix.getRows());
    }

    @Test
    void getColumns() {
        Matrix matrix = new Matrix(3,3);
        assertEquals(3, matrix.getColumns());
    }

    @Test
    void get_matrix() {
        Matrix matrix = new Matrix(3,3);
        double[][] expected = new double[3][3];
        for (int i = 0; i < matrix.getRows(); ++i){
            for (int j = 0; j < matrix.getColumns(); ++j){
                matrix.set_element(i, j, i*j+j+1);
                expected[i][j] = i*j+j+1;
            }
        }

        assertArrayEquals(expected, matrix.get_matrix());
    }

    @Test
    void get_element() {
        Matrix matrix = new Matrix(3,3);
        for (int i = 0; i < matrix.getRows(); ++i){
            for (int j = 0; j < matrix.getColumns(); ++j){
                matrix.set_element(i, j, i*3+j+1); }
        }

        assertEquals(1, matrix.get_element(0,0));
        assertEquals(9, matrix.get_element(2,2));
    }

    @Test
    void set_element() {
        Matrix matrix = new Matrix(3,3);

        matrix.set_element(1,1,4);
        assertEquals(4, matrix.get_element(1,1));
    }

    @Test
    void set_matrix() {
        Matrix matrix = new Matrix(3,3);
        double[][] expected = new double[3][3];
        for (int i = 0; i < 3; ++i){
            for (int j = 0; j < 3; ++j){
                expected[i][j] = i*3+j+1;
            }
        }

        matrix.set_matrix(expected);

        assertEquals(new Matrix(expected), matrix);
    }

    @Test
    void add() {
        Matrix matrix = new Matrix(3,3);
        Matrix expected = new Matrix(3,3);
        for (int i = 0; i < 3; ++i){
            for (int j = 0; j < 3; ++j){
                expected.set_element(i, j, 1);
            }
        }

        assertEquals(expected, matrix.add(expected));
    }

    @Test
    void sub() {
        Matrix matrix = new Matrix(3,3);
        Matrix expected = new Matrix(3,3);

        for (int i = 0; i < matrix.getRows(); ++i){
            for (int j = 0; j < matrix.getColumns(); ++j){
                matrix.set_element(i, j, 1);
            }
        }

        assertEquals(expected, matrix.sub(matrix));
    }

    @Test
    void mult() {
        Matrix A = new Matrix(3,3);
        Matrix B = new Matrix(3,3);
        Matrix expected = new Matrix(3,3);

        for (int i = 0; i < A.getRows(); ++i){
            for (int j = 0; j < A.getColumns(); ++j){
                A.set_element(i, j, i*A.getColumns()+j+1);
            }
        }

        for (int i = 0; i < B.getRows(); ++i){
            for (int j = 0; j < B.getColumns(); ++j){
                B.set_element(i, j, 2);
            }
        }

        expected.set_element(0, 0, 12);
        expected.set_element(0, 1, 12);
        expected.set_element(0, 2, 12);
        expected.set_element(1, 0, 30);
        expected.set_element(1, 1, 30);
        expected.set_element(1, 2, 30);
        expected.set_element(2, 0, 48);
        expected.set_element(2, 1, 48);
        expected.set_element(2, 2, 48);

        Matrix res = A.mult(B);
        assertEquals(expected, A.mult(B));
    }

    @Test
    void mult1() {
        Matrix matrix = new Matrix(3,3);
        Vector3D vector = new Vector3D(1,2,3);
        Vector3D expected = new Vector3D(14,32,50);

        for (int i = 0; i < matrix.getRows(); ++i){
            for (int j = 0; j < matrix.getColumns(); ++j){
                matrix.set_element(i, j, i*matrix.getColumns()+j+1);
            }
        }
        assertEquals(expected, matrix.mult(vector));
    }

    @Test
    void multRow() {
        Matrix A = new Matrix(3,3);
        Matrix expected = new Matrix(3,3);

        for (int i = 0; i < A.getRows(); ++i){
            for (int j = 0; j < A.getColumns(); ++j){
                A.set_element(i, j, 1);
            }
        }

        expected.set_element(0, 0, 1);
        expected.set_element(0, 1, 1);
        expected.set_element(0, 2, 1);
        expected.set_element(1, 0, 2);
        expected.set_element(1, 1, 2);
        expected.set_element(1, 2, 2);
        expected.set_element(2, 0, 1);
        expected.set_element(2, 1, 1);
        expected.set_element(2, 2, 1);

        assertEquals(expected, A.multRow(1, 2));
    }

    @Test
    void multCol() {
        Matrix A = new Matrix(3,3);
        Matrix expected = new Matrix(3,3);

        for (int i = 0; i < A.getRows(); ++i){
            for (int j = 0; j < A.getColumns(); ++j){
                A.set_element(i, j, 1);
            }
        }

        expected.set_element(0, 0, 1);
        expected.set_element(0, 1, 2);
        expected.set_element(0, 2, 1);
        expected.set_element(1, 0, 1);
        expected.set_element(1, 1, 2);
        expected.set_element(1, 2, 1);
        expected.set_element(2, 0, 1);
        expected.set_element(2, 1, 2);
        expected.set_element(2, 2, 1);

        assertEquals(expected, A.multCol(1, 2));
    }

    @Test
    void determinant() {
        Matrix matrix1 = new Matrix(3,3);

        for (int i = 0; i < matrix1.getRows(); ++i){
            for (int j = 0; j < matrix1.getColumns(); ++j){
                matrix1.set_element(i, j, i*matrix1.getColumns()+j+1);
            }
        }

        assertEquals(0, matrix1.determinant());

        Matrix matrix2 = new Matrix(3,3);

        matrix2.set_element(0, 0, 1);
        matrix2.set_element(0, 1, 3);
        matrix2.set_element(0, 2, 2);
        matrix2.set_element(1, 0, 2);
        matrix2.set_element(1, 1, 2);
        matrix2.set_element(1, 2, 6);
        matrix2.set_element(2, 0, 1);
        matrix2.set_element(2, 1, 5);
        matrix2.set_element(2, 2, 6);

        assertEquals(-20, matrix2.determinant());
    }

    @Test
    void staticDeterminant() {
        Matrix matrix1 = new Matrix(3,3);

        for (int i = 0; i < matrix1.getRows(); ++i){
            for (int j = 0; j < matrix1.getColumns(); ++j){
                matrix1.set_element(i, j, i*matrix1.getColumns()+j+1);
            }
        }

        assertEquals(0, Matrix.staticDeterminant(matrix1.get_matrix()));

        Matrix matrix2 = new Matrix(3,3);

        matrix2.set_element(0, 0, 1);
        matrix2.set_element(0, 1, 3);
        matrix2.set_element(0, 2, 2);
        matrix2.set_element(1, 0, 2);
        matrix2.set_element(1, 1, 2);
        matrix2.set_element(1, 2, 6);
        matrix2.set_element(2, 0, 1);
        matrix2.set_element(2, 1, 5);
        matrix2.set_element(2, 2, 6);

        assertEquals(-20, Matrix.staticDeterminant(matrix2.get_matrix()));
    }

    @Test
    void staticDeterminant1() {
        Matrix matrix1 = new Matrix(3,3);

        for (int i = 0; i < matrix1.getRows(); ++i){
            for (int j = 0; j < matrix1.getColumns(); ++j){
                matrix1.set_element(i, j, i*matrix1.getColumns()+j+1);
            }
        }

        assertEquals(0, Matrix.staticDeterminant(matrix1));

        Matrix matrix2 = new Matrix(3,3);

        matrix2.set_element(0, 0, 1);
        matrix2.set_element(0, 1, 3);
        matrix2.set_element(0, 2, 2);
        matrix2.set_element(1, 0, 2);
        matrix2.set_element(1, 1, 2);
        matrix2.set_element(1, 2, 6);
        matrix2.set_element(2, 0, 1);
        matrix2.set_element(2, 1, 5);
        matrix2.set_element(2, 2, 6);

        assertEquals(-20, Matrix.staticDeterminant(matrix2));
    }


    @Test
    void transpose() {
        Matrix matrix = new Matrix(3,3);
        Matrix expected = new Matrix(3,3);

        for (int i = 0; i < matrix.getRows(); ++i){
            for (int j = 0; j < matrix.getColumns(); ++j){
                matrix.set_element(i, j, i*matrix.getColumns()+j+1);
            }
        }

        expected.set_element(0, 0, 1);
        expected.set_element(0, 1, 4);
        expected.set_element(0, 2, 7);
        expected.set_element(1, 0, 2);
        expected.set_element(1, 1, 5);
        expected.set_element(1, 2, 8);
        expected.set_element(2, 0, 3);
        expected.set_element(2, 1, 6);
        expected.set_element(2, 2, 9);

        assertEquals(expected, matrix.transpose());
    }

    @Test
    void copyColumnToColumn() {
        Matrix matrix = new Matrix(3,3);
        matrix.set_element(0, 0, 1);
        matrix.set_element(0, 1, 0);
        matrix.set_element(0, 2, 0);
        matrix.set_element(1, 0, 1);
        matrix.set_element(1, 1, 0);
        matrix.set_element(1, 2, 0);
        matrix.set_element(2, 0, 1);
        matrix.set_element(2, 1, 0);
        matrix.set_element(2, 2, 0);

        Matrix.copyColumnToColumn(matrix, 0, matrix, 1);

        Matrix expected = new Matrix(3,3);
        expected.set_element(0, 0, 1);
        expected.set_element(0, 1, 1);
        expected.set_element(0, 2, 0);
        expected.set_element(1, 0, 1);
        expected.set_element(1, 1, 1);
        expected.set_element(1, 2, 0);
        expected.set_element(2, 0, 1);
        expected.set_element(2, 1, 1);
        expected.set_element(2, 2, 0);

        assertEquals(expected, matrix);
    }

    @Test
    void copyRowToRow() {
        Matrix matrix = new Matrix(3,3);
        matrix.set_element(0, 0, 1);
        matrix.set_element(0, 1, 1);
        matrix.set_element(0, 2, 1);
        matrix.set_element(1, 0, 0);
        matrix.set_element(1, 1, 0);
        matrix.set_element(1, 2, 0);
        matrix.set_element(2, 0, 0);
        matrix.set_element(2, 1, 0);
        matrix.set_element(2, 2, 0);

        Matrix.copyRowToRow(matrix, 0, matrix, 1);

        Matrix expected = new Matrix(3,3);
        expected.set_element(0, 0, 1);
        expected.set_element(0, 1, 1);
        expected.set_element(0, 2, 1);
        expected.set_element(1, 0, 1);
        expected.set_element(1, 1, 1);
        expected.set_element(1, 2, 1);
        expected.set_element(2, 0, 0);
        expected.set_element(2, 1, 0);
        expected.set_element(2, 2, 0);

        assertEquals(expected, matrix);
    }

    @Test
    void inversedMatrix3X3() {
        Matrix matrix = new Matrix(3,3);

        matrix.set_element(0, 0, 1);
        matrix.set_element(0, 1, 3);
        matrix.set_element(0, 2, 2);
        matrix.set_element(1, 0, 2);
        matrix.set_element(1, 1, 2);
        matrix.set_element(1, 2, 6);
        matrix.set_element(2, 0, 1);
        matrix.set_element(2, 1, 5);
        matrix.set_element(2, 2, 6);

        Matrix expected = new Matrix(3,3);

        expected.set_element(0, 0, 1);
        expected.set_element(0, 1, 0);
        expected.set_element(0, 2, 0);
        expected.set_element(1, 0, 0);
        expected.set_element(1, 1, 1);
        expected.set_element(1, 2, 0);
        expected.set_element(2, 0, 0);
        expected.set_element(2, 1, 0);
        expected.set_element(2, 2, 1);

        Matrix m = matrix.inversedMatrix3X3();


        assertEquals(expected, matrix.mult(matrix.inversedMatrix3X3()));
    }

    @Test
    void inverse3x3() {
        Matrix matrix = new Matrix(3,3);

        matrix.set_element(0, 0, 1);
        matrix.set_element(0, 1, 3);
        matrix.set_element(0, 2, 2);
        matrix.set_element(1, 0, 2);
        matrix.set_element(1, 1, 2);
        matrix.set_element(1, 2, 6);
        matrix.set_element(2, 0, 1);
        matrix.set_element(2, 1, 5);
        matrix.set_element(2, 2, 6);

        Matrix expected = new Matrix(3,3);

        expected.set_element(0, 0, 1);
        expected.set_element(0, 1, 0);
        expected.set_element(0, 2, 0);
        expected.set_element(1, 0, 0);
        expected.set_element(1, 1, 1);
        expected.set_element(1, 2, 0);
        expected.set_element(2, 0, 0);
        expected.set_element(2, 1, 0);
        expected.set_element(2, 2, 1);

        Matrix inversed = new Matrix(matrix);
        inversed.inverse3x3();

        assertEquals(expected, matrix.mult(inversed));
    }

    @Test
    void toString1() {
    }

    @Test
    void grahmSchmidt3X3() {
        Matrix matrix = new Matrix(3,3);
        Vector3D v1 = new Vector3D(1,1,1);
        Vector3D v2 = new Vector3D(6,4,5);
        Vector3D v3 = new Vector3D(3,6,9);
        matrix.set_matrix_3x3(v1, v2, v3);

        Matrix expected = new Matrix(3,3);


        Vector3D e1 = new Vector3D(1,1,1);
        Vector3D e2 = new Vector3D(1,-1,0);
        double a = -3;
        double b = 2;
        double ab = a/b;
        Vector3D e3 = new Vector3D(ab, ab, 3);

        expected.set_matrix_3x3(e1.normalized(), e2.normalized(), e3.normalized());
        Matrix actual = Matrix.grahmSchmidt3X3(matrix);
        assertEquals(expected, actual);
    }

    @Test
    void orthonormalBasis3X3() {
        Vector3D u1 = new Vector3D(2,2,1);
        Vector3D u2 = new Vector3D(1,0,-2);
        Vector3D u3 = new Vector3D(-4,5,-2);

        Matrix expected1 = new Matrix(3,3);

        expected1.set_matrix_3x3(u1.normalized(), u2.normalized(), u3.normalized());

        Matrix actual1 = Matrix.orthonormalBasis3X3(u1);

        Vector3D U1 = Matrix.getColumnAsVector3(actual1, 0);
        Vector3D U2 = Matrix.getColumnAsVector3(actual1, 1);
        Vector3D U3 = Matrix.getColumnAsVector3(actual1, 2);

        double a1 = U1.dotProduct(U2);
        double b1 = U1.dotProduct(U3);
        double c1 = U2.dotProduct(U3);

        assertEquals(true, a1 == 0 && b1 == 0 && c1 ==0);
        assertEquals(true, U1.length() == 1 && U2.length() == 1 && U3.length() == 1);
        assertEquals(U1, u1.normalized());

        /***********************/

        Vector3D v1 = new Vector3D(4,4,2);

        Matrix actual2 = Matrix.orthonormalBasis3X3(v1);

        Vector3D V1 = Matrix.getColumnAsVector3(actual2, 0);
        Vector3D V2 = Matrix.getColumnAsVector3(actual2, 1);
        Vector3D V3 = Matrix.getColumnAsVector3(actual2, 2);

        double a2 = U2.dotProduct(U1);
        double b2 = U3.dotProduct(U1);
        double c2 = U2.dotProduct(U3);

        assertEquals(true, a2 == 0 && b2 == 0 && c2 ==0);
        assertEquals(true, V1.length() == 1 && V2.length() == 1 && V3.length() == 1);
    }

    @Test
    void orthonormalized() {
        Matrix matrix = new Matrix(3,3);
        Vector3D v1 = new Vector3D(1,1,1);
        Vector3D v2 = new Vector3D(6,4,5);
        Vector3D v3 = new Vector3D(3,6,9);
        matrix.set_matrix_3x3(v1, v2, v3);

        Matrix expected = new Matrix(3,3);


        Vector3D e1 = new Vector3D(1,1,1);
        Vector3D e2 = new Vector3D(1,-1,0);
        double a = -3;
        double b = 2;
        double ab = a/b;
        Vector3D e3 = new Vector3D(ab, ab, 3);

        expected.set_matrix_3x3(e1.normalized(), e2.normalized(), e3.normalized());

        assertEquals(expected, matrix.orthonormalized());
    }

    @Test
    void orthonormalize() {
        Matrix matrix = new Matrix(3,3);
        Vector3D v1 = new Vector3D(1,1,1);
        Vector3D v2 = new Vector3D(6,4,5);
        Vector3D v3 = new Vector3D(3,6,9);
        matrix.set_matrix_3x3(v1, v2, v3);

        Matrix expected = new Matrix(3,3);


        Vector3D e1 = new Vector3D(1,1,1);
        Vector3D e2 = new Vector3D(1,-1,0);
        double a = -3;
        double b = 2;
        double ab = a/b;
        Vector3D e3 = new Vector3D(ab, ab, 3);

        expected.set_matrix_3x3(e1.normalized(), e2.normalized(), e3.normalized());
        matrix.orthonormalize();

        assertEquals(expected, matrix);
    }
}