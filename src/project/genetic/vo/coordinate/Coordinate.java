package project.genetic.vo.coordinate;

import java.awt.Graphics;

/**
 * java class definition for a coordinate
 * 
 * @author ABHILASHKUMARV
 * 
 */
public class Coordinate implements ICoordinate {

	protected int x;
	protected int y;

	public Coordinate(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	/**
	 * this coordiante's distance from coordinate passed
	 * 
	 * @param coordinate
	 * @return distance
	 */
	public double distanceFrom(ICoordinate coordinate) {
		return Math.hypot(x - coordinate.getX(), y - coordinate.getY());
	}

	/**
	 * drawing this coordinate on map
	 * 
	 * @param g
	 * @param width
	 */
	public void drawPoint(Graphics g, int width) {
		g.fillRoundRect(x - width / 2, y - width / 2, width, width, width / 4,
				width / 4);
	}

	/**
	 * drawing line from this coordinate to coordinate passed
	 * 
	 * @param g
	 * @param coordinate
	 */
	public void drawLine(Graphics g, ICoordinate coordinate) {
		g.drawLine(x, y, coordinate.getX(), coordinate.getY());
	}

	@Override
	public String toString() {
		return "(" + this.x + ", " + this.y + ")";
	}

}
