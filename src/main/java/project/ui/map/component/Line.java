package project.ui.map.component;

import project.genetic.vo.coordinate.Coordinate;

import java.awt.Graphics;

import static java.lang.String.format;

/**
 * java class definition for drawing line
 *
 * @author ABHILASHKUMARV
 */
public class Line {

    private Coordinate p1;
    private Coordinate p2;

    public Line(Coordinate p1, Coordinate p2) {
        this.p1 = p1;
        this.p2 = p2;
    }

    public boolean isVisiting(Coordinate Coordinate) {
        return p1.equals(Coordinate) || p2.equals(Coordinate);
    }

    public void draw(Graphics g, double xScale, double yScale) {
        g.drawLine((int) (p1.x() * xScale),
                (int) (p1.y() * yScale),
                (int) (p2.x() * xScale),
                (int) (p2.y() * yScale));
    }

    @Override
    public String toString() {
        return format("{%s->%s}", p1, p2);
    }

}