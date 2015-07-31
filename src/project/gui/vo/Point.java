package project.gui.vo;

import project.genetic.vo.City;

import java.awt.Graphics;

@SuppressWarnings("serial")
public class Point extends Component {

	public void addPoint(City city) {
		list.add(city);
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		for (City city : list) {
			city.drawPoint(g, 4);
		}
	}

	@Override
	public String toString() {
		return "Points: " + list;
	}

}
