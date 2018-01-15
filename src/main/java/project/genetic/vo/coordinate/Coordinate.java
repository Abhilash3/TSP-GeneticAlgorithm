package project.genetic.vo.coordinate;

import project.genetic.util.Coordinates;
import project.genetic.vo.individual.gene.IGene;

import java.io.Serializable;

import static java.lang.String.format;

public final class Coordinate implements IGene, Serializable {

    private static final Coordinate ZERO = new Coordinate(0, 0);

    private static final long serialVersionUID = 8524031709417015053L;
    private final int x;
    private final int y;

    private transient int hashcode = -1;
    private transient String toString;

    private Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public static Coordinate newCoordinate(int x, int y) {
        return new Coordinate(x, y);
    }

    public int x() {
        return x;
    }

    public int y() {
        return y;
    }

    @Override
    public int hashCode() {
        if (hashcode == -1) {
            int result = 17;
            result = 31 * result + x();
            result = 31 * result + y();
            hashcode = result;
        }
        return hashcode;
    }

    @Override
    public boolean equals(Object object) {
        if (object == null)
            return false;
        if (this == object)
            return true;
        if (!(object instanceof Coordinate))
            return false;

        Coordinate coordinate = (Coordinate) object;
        return x == coordinate.x && y == coordinate.y;
    }

    @Override
    public String toString() {
        if (toString == null) toString = format("(%d, %d)", x, y);
        return toString;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Coordinate doClone() {
        return new Coordinate(x, y);
    }

    @Override
    public double value() {
        return value(ZERO);
    }

    @Override
    public double value(IGene gene) {
        if (gene.getClass() == Coordinate.class) {
            return Coordinates.distance(this, (Coordinate) gene);
        }
        return 0;
    }
}
