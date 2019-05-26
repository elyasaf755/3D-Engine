package geometries;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RadialGeometryTest {
    RadialGeometry rg = new RadialGeometry(4d);

    @Test
    void get_radius() {
        assertEquals(4, rg.get_radius());
    }

    @Test
    void get_normal() {
        //TODO: Implement
    }
}