package project.genetic.vo.coordinate;

import java.awt.Graphics;

public interface ICoordinate {
	
	public int getX();
	
	public int getY();
	
	public double distanceFrom(ICoordinate iCoordinate);
	
	public void drawLine(Graphics g, ICoordinate iCoordinate);
	
	public void drawPoint(Graphics g, int pointWidth);
	
	public String toString();

}
