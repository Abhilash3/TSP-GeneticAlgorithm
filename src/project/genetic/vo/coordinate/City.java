package project.genetic.vo.coordinate;

import java.awt.Graphics;

/**
 * java class definition for a city
 * 
 * @author ABHILASHKUMARV
 * 
 */
public class City extends Coordinate {

	public City(Integer x, Integer y) {
		super(x, y);
	}

	@Override
	public void drawPoint(Graphics g, int width) {
		super.drawPoint(g, width);
		g.drawString(this.toString(), x, y);
	}

}
