package project.ui.map.component;

import project.genetic.vo.coordinate.ICoordinate;

import java.awt.Graphics;

@SuppressWarnings("serial")
public class Point extends MapObject {

	@Override
	public void draw(Graphics g) {
		super.paintComponent(g);
		for (ICoordinate iCoordinate : list) {
			iCoordinate.drawPoint(g, 4);
		}
	}

	@Override
	public String toString() {
		return "Points: " + list;
	}

}
