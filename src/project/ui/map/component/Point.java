package project.ui.map.component;

import project.genetic.vo.coordinate.ICoordinate;

import java.awt.Graphics;

public class Point {

    private ICoordinate p;

    public Point(ICoordinate p) {
        this.p = p;
    }

    public ICoordinate getCoordinate() {
        return p;
    }

    public void draw(Graphics g, double xScale, double yScale) {
        g.fillRoundRect((int) (p.getX() * xScale) - 2,
                (int) (p.getY() * yScale) - 2, 4, 4, 1, 1);
        //g.drawString(p.toString(), p.getX(), p.getY());
    }

    @Override
    public String toString() {
        return "{"+ p + "}";
    }

}
