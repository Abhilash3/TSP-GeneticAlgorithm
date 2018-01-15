package project.ui.map.component;

import project.genetic.vo.coordinate.Coordinate;

import java.awt.Graphics;

public class Point {

    private Coordinate p;

    public Point(Coordinate p) {
        this.p = p;
    }

    public Coordinate getCoordinate() {
        return p;
    }

    public void draw(Graphics g, double xScale, double yScale) {
        g.fillRoundRect((int) (p.x() * xScale) - 2, (int) (p.y() * yScale) - 2, 4, 4, 1, 1);
        //g.drawString(p.toString(), p.x(), p.y());
    }

    @Override
    public String toString() {
        return "{"+ p + "}";
    }

}
