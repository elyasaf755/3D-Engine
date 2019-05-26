package primitives;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Point2DTest {
    Point2D _p1 = new Point2D(new Coordinate(1), new Coordinate(1));
    Point2D _p2 = new Point2D(new Coordinate(1), new Coordinate(0));

    @Test
    void getX() {
        assertEquals(_p1._x, _p1.getX());
    }

    @Test
    void getY() {
        assertEquals(_p1._y, _p1.getY());
    }

    @Test
    void subtract() {
        assertEquals(new Point2D(new Coordinate(0), new Coordinate(1)), _p1.subtract(_p2));
    }

    @Test
    void add() {
        assertEquals(new Point2D(new Coordinate(2), new Coordinate(1)), _p1.add(_p2));
    }

    @Test
    void equals() {
        assertEquals(true, _p1.equals(_p1));
        assertEquals(false, _p1.equals(_p2));
    }

    @Test
    void toStringTest() {
        assertEquals("(1.0, 1.0)", _p1.toString());
    }
}