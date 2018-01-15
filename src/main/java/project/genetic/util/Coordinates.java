package project.genetic.util;

import project.genetic.vo.coordinate.Coordinate;

public class Coordinates {

    private Coordinates() {
        throw new UnsupportedOperationException("Util Class");
    }

    public static double distance(Coordinate c1, Coordinate c2) {
        return Math.hypot(c1.x() - c2.x(), c1.y() - c2.y());
    }

}
