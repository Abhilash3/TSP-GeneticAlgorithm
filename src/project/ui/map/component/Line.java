package project.ui.map.component;

import java.awt.Graphics;


/**
 * java class definition for drawing line
 * 
 * @author ABHILASHKUMARV
 * 
 */
@SuppressWarnings("serial")
public class Line extends MapObject {

	@Override
	public void draw(Graphics g) {
		super.paintComponent(g);
		for (int i = 1; i < list.size(); i++) {
			list.get(i - 1).drawLine(g, list.get(i));
		}
	}

	@Override
	public String toString() {
		return "Lines: " + list;
	}

}