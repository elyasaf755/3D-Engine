package primitives;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Matrix3Test {

    @Test
    void getMatrix() {
    }

    @Test
    void set_element() {
    }

    @Test
    void add() {
    }

    @Test
    void sub() {
    }

    @Test
    void mult() {
    }

    @Test
    void mult1() {
    }

    @Test
    void mult2() {
    }

    @Test
    void scale() {
    }

    @Test
    void equals1() {
        double[][] M1 = {
                {1,0,0},
                {2,0,0},
                {3,0,0}
        };

        double[][] M2 = {
                {1,2,3},
                {0,0,0},
                {0,0,0}
        };

        Matrix3 actual = new Matrix3(M1).transpose();
        Matrix3 expected = new Matrix3(M2);

        assertEquals(expected, actual);
    }

    @Test
    void transpose() {
        double[][] M1 = {
                {1,0,0},
                {2,0,0},
                {3,0,0}
        };

        double[][] M2 = {
                {1,2,3},
                {0,0,0},
                {0,0,0}
        };

        Matrix3 actual = new Matrix3(M1).transpose();
        Matrix3 expected = new Matrix3(M2);

        assertEquals(expected, actual);
    }
}