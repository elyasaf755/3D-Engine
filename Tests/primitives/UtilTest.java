package primitives;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class UtilTest {

    @Test
    void cubicRoots() {
        double[] actual = Util.cubicRoots(2, -4, -22, 24);
        double[] expected = {4, -3, 1};
        for (int i = 0; i < expected.length; ++i){
            assertEquals(true, Util.equals(expected[i], actual[i]));
        }
    }

    @Test
    void quarticRoots() {
        //3X4   + 6X3   - 123X2   - 126X + 1,080 = 0
        double[] actual1 = Util.quarticRoots(3, 6, -123, -126, 1080);
        double[] expected1 = {5, 3, -4, -6};
        for (int i = 0; i < expected1.length; ++i){
            assertEquals(true, Util.equals(expected1[i], actual1[i]));
        }

        //-20X4   + 5X3   + 17X2   - 29X + 87 = 0
        Complex[] actual2 = Util.quarticRoots(
                new Complex(-20, 0),
                new Complex(5, 0),
                new Complex(17, 0),
                new Complex(-29, 0),
                new Complex(87, 0)
        );

        Complex[] expected2 = {
                new Complex(1.48758311033, 0),
                new Complex( 0.222210408124,1.29967219908),
                new Complex(0.222210408124, -1.29967219908),
                new Complex(-1.68200392658, 0)

        };
        assertEquals(expected2, actual2);
    }

    @Test
    void sign() {
        assertEquals(-1, Util.sign(-56));

        assertEquals(1, Util.sign(56));

        assertEquals(0, Util.sign(0));

        assertEquals(1, Util.sign(0.5));
    }
}