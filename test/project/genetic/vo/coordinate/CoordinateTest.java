package project.genetic.vo.coordinate;

import java.util.List;

import junit.framework.TestCase;
import project.Mother;
import project.genetic.util.Coordinates;

public class CoordinateTest extends TestCase {

    private List<Coordinate> coordinates;

    public void setUp() throws Exception {
        super.setUp();
        coordinates = Mother.getPath();
    }

    public void testDistance() {
        Coordinate[] city = new Coordinate[2];
        for (int i = 1; i < coordinates.size(); i++) {
            city[0] = coordinates.get(i - 1);
            city[1] = coordinates.get(i);

            assertEquals(Coordinates.distance(city[0], city[1]),
                    Coordinates.distance(city[1], city[0]));

            assertEquals(0.0, Coordinates.distance(city[0], city[0]));
            assertEquals(0.0, Coordinates.distance(city[1], city[1]));
        }
    }

    public void testHashCode() {
        for (Coordinate a : coordinates) {
            for (Coordinate b : coordinates) {
                if (a.equals(b))
                    assertEquals(a.hashCode(), b.hashCode());
                else
                    assertNotSame(a.hashCode(), b.hashCode());
            }
        }
    }

    public void testToString() {
        for (Coordinate a : coordinates) {
            for (Coordinate b : coordinates) {
                if (a.equals(b))
                    assertTrue(a.toString().equals(b.toString()));
                else
                    assertFalse(a.toString().equals(b.toString()));
            }
        }
    }

    public void testEquals() {
        Coordinate coordinate1 = Coordinate.getCoordinate(10, 10);
        Coordinate coordinate2 = Coordinate.getCoordinate(20, 20);

        assertFalse(coordinate1.equals(null));
        assertFalse(coordinate2.equals(null));

        assertFalse(coordinate1.equals(coordinate2));
        assertFalse(coordinate2.equals(coordinate1));

        assertTrue(coordinate1.equals(coordinate1));
        assertTrue(coordinate2.equals(coordinate2));
    }

}
