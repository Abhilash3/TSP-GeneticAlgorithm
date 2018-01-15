package project.genetic.vo.coordinate;

import org.junit.Before;
import org.junit.Test;
import project.Mother;
import project.genetic.util.Coordinates;
import project.genetic.vo.individual.Individual;

import static org.junit.Assert.*;

public class CoordinateTest {

    private Individual<Coordinate> coordinates;

    @Before
    public void setUp() throws Exception {
        coordinates = Mother.getPath();
    }

    @Test
    public void distance() {
        Coordinate[] city = new Coordinate[2];
        for (int i = 1; i < coordinates.size(); i++) {
            city[0] = coordinates.get(i - 1);
            city[1] = coordinates.get(i);

            assertEquals(0, Double.compare(Coordinates.distance(city[0], city[1]),
                    Coordinates.distance(city[1], city[0])));

            assertEquals(0, Double.compare(0.0, Coordinates.distance(city[0], city[0])));
            assertEquals(0, Double.compare(0.0, Coordinates.distance(city[1], city[1])));
        }
    }

    @Test
    public void HashCode() {
        for (Coordinate a : coordinates) {
            for (Coordinate b : coordinates) {
                if (a.equals(b))
                    assertEquals(a.hashCode(), b.hashCode());
                else
                    assertNotSame(a.hashCode(), b.hashCode());
            }
        }
    }

    @Test
    public void ToString() {
        for (Coordinate a : coordinates) {
            for (Coordinate b : coordinates) {
                if (a.equals(b))
                    assertTrue(a.toString().equals(b.toString()));
                else
                    assertFalse(a.toString().equals(b.toString()));
            }
        }
    }

    @Test
    public void equals() {
        Coordinate coordinate1 = Coordinate.newCoordinate(10, 10);
        Coordinate coordinate2 = Coordinate.newCoordinate(20, 20);

        assertFalse(coordinate1.equals(null));
        assertFalse(coordinate2.equals(null));

        assertFalse(coordinate1.equals(coordinate2));
        assertFalse(coordinate2.equals(coordinate1));

        assertTrue(coordinate1.equals(coordinate1));
        assertTrue(coordinate2.equals(coordinate2));
    }

}
