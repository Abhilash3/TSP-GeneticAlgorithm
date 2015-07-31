package project.genetic.vo;

import java.awt.Graphics;

/**
 * java class definition for a city
 * 
 * @author ABHILASHKUMARV
 * 
 */
public class City extends Coordinate {

	public City(int x, int y) {
		super(x, y);
	}

	@Override
	public void drawPoint(Graphics g, int width) {
		super.drawPoint(g, width);
		g.drawString(this.toString(), x, y);
	}

}
