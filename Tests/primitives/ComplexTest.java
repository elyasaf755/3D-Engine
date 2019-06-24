package primitives;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ComplexTest {

    @Test
    void get_real() {
    }

    @Test
    void get_imaginary() {
    }

    @Test
    void set_real() {
    }

    @Test
    void set_imaginary() {
    }

    //(3+4i),(1+2i)
    @Test
    void add() {
        Complex z1 = new Complex(3,4);
        Complex z2 = new Complex(1,2);
        Complex actual1 = z1.add(z2);
        Complex expected1 = new Complex(4,6);
        assertEquals(expected1, actual1);
    }

    @Test
    void addReal() {
        Complex z1 = new Complex(3,4);
        Complex actual1 = z1.add(2);
        Complex expected1 = new Complex(5,4);
        assertEquals(expected1, actual1);
    }

    @Test
    void sub() {
        Complex z1 = new Complex(3,4);
        Complex z2 = new Complex(1,2);
        Complex actual1 = z1.sub(z2);
        Complex expected1 = new Complex(2,2);
        assertEquals(expected1, actual1);
    }

    @Test
    void subScalar() {
        Complex z1 = new Complex(3,4);
        Complex actual1 = z1.sub(2);
        Complex expected1 = new Complex(1,4);
        assertEquals(expected1, actual1);
    }

    @Test
    void mult() {
        Complex z1 = new Complex(3,4);
        Complex z2 = new Complex(1,2);
        Complex actual1 = z1.mult(z2);
        Complex expected1 = new Complex(-5,10);
        assertEquals(expected1, actual1);
    }

    @Test
    void multReal() {
        Complex z1 = new Complex(3,4);
        Complex z2 = new Complex(1,2);
        Complex actual1 = z1.mult(4);
        Complex expected1 = new Complex(12,16);
        assertEquals(expected1, actual1);
    }

    @Test
    void squared() {
        Complex z1 = new Complex(3,4);
        Complex actual1 = z1.squared();
        Complex expected1 = new Complex(-7,24);
        assertEquals(expected1, actual1);
    }

    @Test
    void conjugate() {
        Complex z1 = new Complex(3,4);
        Complex actual1 = z1.conjugate();
        Complex expected1 = new Complex(3,-4);
        assertEquals(expected1, actual1);
    }

    @Test
    void reciprocal() {
        Complex z1 = new Complex(3,4);
        Complex actual1 = z1.reciprocal();
        Complex expected1 = new Complex(3.0/25,-4.0/25);
        assertEquals(expected1, actual1);
    }

    @Test
    void div() {
        Complex z1 = new Complex(3,4);
        Complex z2 = new Complex(1,2);
        Complex actual1 = z1.div(z2);
        Complex expected1 = new Complex(2.2,-0.4);
        assertEquals(expected1, actual1);
    }

    @Test
    void exp() {
        Complex z1 = new Complex(3,4);
        Complex actual1 = z1.exp();
        Complex expected1 = new Complex(
                -13.128783081462158,
            -15.200784463067954);//Wolfarm's result
        assertEquals(expected1, actual1);
    }

    @Test
    void sin() {
        Complex z1 = new Complex(3,4);
        Complex actual1 = z1.sin();
        Complex expected1 = new Complex(
                3.85373803791937732,
           -27.0168132580039344);//Wolfarm's result
        assertEquals(expected1, actual1);
    }

    @Test
    void cos() {
        Complex z1 = new Complex(3,4);
        Complex actual1 = z1.cos();
        Complex expected1 = new Complex(
                -27.03494560307422,
            -3.851153334811777);//Wolfarm's result
        assertEquals(expected1, actual1);
    }

    @Test
    void tan() {
        Complex z1 = new Complex(3,4);
        Complex actual1 = z1.tan();
        Complex expected1 = new Complex(
                -0.000187346204629,
             0.999355987381473);//Wolfarm's result
        assertEquals(expected1, actual1);
    }

    @Test
    void lengthSquared() {
        Complex z1 = new Complex(3,4);
        double actual1 = z1.lengthSquared();
        double expected1 = 25;
        assertEquals(expected1, actual1);
    }

    @Test
    void length() {
        Complex z1 = new Complex(3,4);
        double actual1 = z1.length();
        double expected1 = 5;
        assertEquals(expected1, actual1);
    }

    @Test
    void sqrt() {
        Complex z1 = new Complex(3,4);
        Complex actual1 = z1.sqrt();
        Complex expected1 = new Complex(2,1);
        assertEquals(expected1, actual1);
    }

    @Test
    void sqrtIndex() {
        Complex z1 = new Complex(3,4);
        Complex actual1 = z1.sqrt(0);
        Complex expected1 = new Complex(2,1);
        assertEquals(expected1, actual1);

        Complex actual2 = z1.sqrt(1);
        Complex expected2 = new Complex(
                -2.00000000000000,
                -1.0000000000000);
        assertEquals(expected2, actual2);

        Complex z3 = new Complex(1,1);
        Complex actual3 = z3.sqrt(0);
        Complex expected3 = new Complex(1.098684113467810,0.455089860562227);
        assertEquals(expected3, actual3);

        Complex actual4 = z3.sqrt(1);
        Complex expected4 = new Complex(-1.09868411346781,-0.45508986056223);
        assertEquals(expected4, actual4);
    }

    @Test
    void cbrt() {
        Complex z1 = new Complex(3,4);
        Complex actual1 = z1.cbrt();
        Complex expected1 = new Complex(
                1.62893714592217587521460,
                0.52017450230454583954569);
        assertEquals(expected1, actual1);
    }

    @Test
    void cbrtIndex() {
        Complex z1 = new Complex(3,4);
        Complex actual1 = z1.cbrt(0);
        Complex expected1 = new Complex(
                1.62893714592217587521460,
                0.52017450230454583954569);
        assertEquals(expected1, actual1);

        Complex actual2 = z1.cbrt(1);
        Complex expected2 = new Complex(
                -1.26495290635775,
                1.15061369838445);
        assertEquals(expected2, actual2);

        Complex actual3 = z1.cbrt(2);
        Complex expected3 = new Complex(
                -0.36398423956442,
                -1.67078820068900);
        assertEquals(expected3, actual3);
    }

    @Test
    void angle() {
        Complex z1 = new Complex(3,4);
        double actual1 = z1.angle();
        double expected1 = 0.9272952180016122;
        assertEquals(expected1, actual1);
    }

    @Test
    void pow() {
        Complex z1 = new Complex(3,4);
        Complex actual1 = z1.pow(3);
        Complex expected1 = new Complex(-117,44);
        assertEquals(expected1, actual1);

        Complex actual2 = z1.pow(1.0 / 3);
        Complex expected2 = new Complex(
                1.62893714592217587521460,
            0.52017450230454583954569);
        assertEquals(expected2, actual2);

        Complex actual3 = z1.pow(1.0 / 2);
        Complex expected3 = z1.sqrt();
        assertEquals(expected3, actual3);
    }

    @Test
    void powIndex() {
        Complex z1 = new Complex(3,4);
        Complex actual = z1.pow(3, 1);
        Complex expected = new Complex(-117,44);
        assertEquals(expected, actual);

        Complex actual1 = z1.pow(1.0/3.0, 0);
        Complex expected1 = new Complex(
                1.62893714592217587521460,
                0.52017450230454583954569);
        assertEquals(expected1, actual1);

        Complex actual2 = z1.pow(1.0/3.0, 1);
        Complex expected2 = new Complex(
                -1.26495290635775,
                1.15061369838445);
        assertEquals(expected2, actual2);

        Complex actual3 = z1.pow(1.0/3.0, 2);
        Complex expected3 = new Complex(
                -0.36398423956442,
                -1.67078820068900);
        assertEquals(expected3, actual3);

        Complex actual4 = z1.pow(1.0/2.0, 0);
        Complex expected4 = new Complex(2,1);
        assertEquals(expected4, actual4);

        Complex actual5 = z1.pow(1.0/2.0, 1);
        Complex expected5 = new Complex(
                -2.00000000000000,
                -1.0000000000000);
        assertEquals(expected5, actual5);

        Complex z2 = new Complex(1,1);
        Complex actual6 = z2.pow(1.0/2.0, 0);
        Complex expected6 = new Complex(1.098684113467810,0.455089860562227);
        assertEquals(expected6, actual6);

        Complex actual7 = z2.pow(1.0/2.0, 1);
        Complex expected7 = new Complex(-1.09868411346781,-0.45508986056223);
        assertEquals(expected7, actual7);

    }

    @Test
    void cis() {
    }

    @Test
    void equals1() {
    }

    @Test
    void toString1() {
        Complex z1 = new Complex(3,4);
        String actual1 = z1.toString();
        String expected1 = "3.0+4.0i";
        assertEquals(expected1, actual1);

        Complex z2 = new Complex(3,-4);
        String actual2 = z2.toString();
        String expected2 = "3.0-4.0i";
        assertEquals(expected2, actual2);
    }

}