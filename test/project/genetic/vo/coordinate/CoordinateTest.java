package project.genetic.vo.coordinate;

import java.util.List;

import project.Mother;
import junit.framework.TestCase;

public class CoordinateTest extends TestCase {

	private List<ICoordinate> coordinates;

	public void setUp() throws Exception {
		super.setUp();
		coordinates = Mother.getPath();
	}

	public void testDistance() {
		ICoordinate[] city = new Coordinate[2];
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
		for (ICoordinate a : coordinates) {
			for (ICoordinate b : coordinates) {
				if (a == b)
					assertEquals(a.hashCode(), b.hashCode());
				else
					assertNotSame(a.hashCode(), b.hashCode());
			}
		}
	}

}
