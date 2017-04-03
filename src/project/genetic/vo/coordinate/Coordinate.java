package project.genetic.vo.coordinate;

import project.genetic.vo.Cloneable;

import java.io.Serializable;

import static java.lang.String.format;

/**
 * java class definition for a coordinate
 *
 * @author ABHILASHKUMARV
 */
public final class Coordinate implements Cloneable, Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 8524031709417015053L;
    protected final int x;
    protected final int y;

    private transient int hashcode = -1;
    private transient String toString;

    private Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public static Coordinate getCoordinate(int x, int y) {
        return new Coordinate(x, y);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public int hashCode() {
        if (hashcode == -1) {
            int result = 17;
            result = 31 * result + x;
            result = 31 * result + y;
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

        /**
         * Object has to be of Coordinate; verified above
         */
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

}
