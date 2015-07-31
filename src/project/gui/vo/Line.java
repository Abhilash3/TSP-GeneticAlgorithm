package project.gui.vo;

import project.genetic.vo.City;

import java.awt.Graphics;

/**
 * java class definition for drawing line
 * 
 * @author ABHILASHKUMARV
 * 
 */
@SuppressWarnings("serial")
public class Line extends Component {

	public void addLine(City city) {
		list.add(city);
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		for (int i = 1; i < list.size(); i++) {
			list.get(i).drawLine(g, list.get(i - 1));
		}
	}

	@Override
	public String toString() {
		return "Lines: " + list;
	}

}