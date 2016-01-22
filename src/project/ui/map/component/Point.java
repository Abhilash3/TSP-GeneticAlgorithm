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

	public void draw(Graphics g) {
		int x = p.getX();
		int y = p.getY();
		g.fillRoundRect(x - 2, y - 2, 4, 4, 1, 1);
		//g.drawString(p.toString(), x, y);
	}

	@Override
	public String toString() {
		return new StringBuilder().append("{").append(p).append("}").toString();
	}

}
