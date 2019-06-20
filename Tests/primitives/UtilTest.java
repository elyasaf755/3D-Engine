package primitives;

import org.junit.jupiter.api.Test;

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
        double[] actual = Util.quarticRoots(3, 6, -123, -126, 1080);
        double[] expected = {5, 3, -4, -6};
        for (int i = 0; i < expected.length; ++i){
            assertEquals(true, Util.equals(expected[i], actual[i]));
        }
    }
}