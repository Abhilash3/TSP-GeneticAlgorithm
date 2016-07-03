package project.genetic.vo.coordinate;

public class Coordinates {
	
	private Coordinates(){}

	/**
	 * @param c1 coordinate 1
	 * @param c2 coordinate 2
	 * @return distance between them
	 */
	public static double distance (ICoordinate c1, ICoordinate c2) {
		return Math.hypot(c1.getX() - c2.getX(), c1.getY() - c2.getY());
	}

}
