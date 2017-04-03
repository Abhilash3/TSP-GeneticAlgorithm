package project.genetic.util;

import project.genetic.vo.coordinate.Coordinate;

public class Coordinates {

    private Coordinates() {
    }

    /**
     * @param c1 coordinate 1
     * @param c2 coordinate 2
     * @return distance between them
     */
    public static double distance(Coordinate c1, Coordinate c2) {
        return Math.hypot(c1.getX() - c2.getX(), c1.getY() - c2.getY());
    }

}
