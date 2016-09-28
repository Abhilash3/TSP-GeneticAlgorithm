package project.ui.map.component;

import java.awt.Graphics;

import project.genetic.vo.coordinate.ICoordinate;

/**
 * java class definition for drawing line
 *
 * @author ABHILASHKUMARV
 */
public class Line {

    private ICoordinate p1;
    private ICoordinate p2;

    public Line(ICoordinate p1, ICoordinate p2) {
        this.p1 = p1;
        this.p2 = p2;
    }

    public boolean isVisiting(ICoordinate iCoordinate) {
        return p1.equals(iCoordinate) || p2.equals(iCoordinate);
    }

    public void draw(Graphics g, double xScale, double yScale) {
        g.drawLine((int) (p1.getX() * xScale),
                (int) (p1.getY() * yScale),
                (int) (p2.getX() * xScale),
                (int) (p2.getY() * yScale));
    }

    @Override
    public String toString() {
        return new StringBuilder().append("{").append(p1).append("->")
                .append(p2).append("}").toString();
    }

}