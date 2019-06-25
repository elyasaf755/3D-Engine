package primitives;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class UtilTest {

    @Test
    void cubicRoots() {
        //All 3 roots are real
        Complex[] actual1 = Util.cubicRoots(2, -4, -22, 24);
        double[] expected1 = {4, -3, 1};
        for (int i = 0; i < expected1.length; ++i){
            assertEquals(true, Util.equals(expected1[i], actual1[i].get_real()));
        }

        //All 3 roots are real & equal
        Complex[] actual2 = Util.cubicRoots(1, 6, 12, 8);
        double[] expected2 = {-2, -2, -2};
        for (int i = 0; i < expected2.length; ++i){
            assertEquals(true, Util.equals(expected2[i], actual2[i].get_real()));
        }

        //Only 1 real root
        Complex[] actual3 = Util.cubicRoots(3,-10,14,27);
        Complex[] expected3 = {
                new Complex(-1, 0),
                new Complex(2.166666666666666, 2.07498326633146),
                new Complex(2.166666666666666, -2.07498326633146)
        };
        for (int i = 0; i < 3; ++i){
            assertEquals(expected3[i], actual3[i]);
        }
    }

    @Test
    void quarticRoots() {
        //3X4   + 6X3   - 123X2   - 126X + 1,080 = 0
        Complex[] actual1 = Util.quarticRoots(3, 6, -123, -126, 1080);
        double[] expected1 = {5, 3, -4, -6};
        for (int i = 0; i < expected1.length; ++i){
            assertEquals(true, Util.equals(expected1[i], actual1[i].get_real()));
        }

        //-20X4   + 5X3   + 17X2   - 29X + 87 = 0
        Complex[] actual2 = Util.quarticRoots(-20, 5,17,-29,87);

        Complex[] expected2 = {
                new Complex(1.4875831103369122,  0),
                new Complex(0.22221040812421866, 1.2996721990882234),
                new Complex(0.22221040812421866,-1.2996721990882234),
                new Complex(-1.6820039265853495, 0)

        };
        for (int i = 0; i < 4; ++i){
            assertEquals(expected2[i], actual2[i]);
        }

        //2X4   + 4X3   + 6X2   + 8X + 10 = 0
        Complex[] actual3 = Util.quarticRoots(2, 4,6,8,10);

        Complex[] expected3 = {
                new Complex(0.28781547955764797,  1.4160930801719078),
                new Complex(0.28781547955764797, -1.4160930801719078),
                new Complex(-1.287815479557648,0.8578967583284903),
                new Complex(-1.287815479557648, -0.8578967583284903)

        };
        for (int i = 0; i < 4; ++i){
            assertEquals(expected3[i], actual3[i]);
        }
    }

    @Test
    void sign() {
        assertEquals(-1, Util.sign(-56));

        assertEquals(1, Util.sign(56));

        assertEquals(0, Util.sign(0));

        assertEquals(1, Util.sign(0.5));
    }
}