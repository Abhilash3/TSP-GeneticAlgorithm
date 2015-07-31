package project.gui.vo;

import project.genetic.vo.City;

import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JComponent;

@SuppressWarnings("serial")
public class Map extends JComponent {

	private Line line = new Line();
	private Point point = new Point();

	/**
	 * connect coordinates using the path provided
	 * 
	 * @param coordinates
	 * @param bestPath
	 */
	public void drawMap(ArrayList<City> coordinates, ArrayList<Integer> bestPath) {
		for (int i = 0; i < coordinates.size(); i++) {
			line.addLine(coordinates.get(bestPath.get(i)));
		}
		repaint();
	}

	/**
	 * draw coordinates on the map
	 * 
	 * @param coordinates
	 */
	public void drawCoordinates(ArrayList<City> coordinates) {
		for (City city : coordinates) {
			point.addPoint(city);
		}
		repaint();
	}

	public void clearLines() {
		line.clear();
		repaint();
	}

	/**
	 * clear line connecting to the given coordinates
	 * 
	 * @param coordinates
	 */
	public void clearLines(ArrayList<City> coordinates) {
		line.getList().removeAll(coordinates);
		repaint();
	}

	public void clearPoints() {
		point.clear();
		repaint();
	}

	/**
	 * clear coordinates from map
	 * 
	 * @param coordinates
	 */
	public void clearPoints(ArrayList<City> coordinates) {
		point.getList().removeAll(coordinates);
		repaint();
	}

	public void clear() {
		line.clear();
		point.clear();
		repaint();
	}

	/**
	 * clear coordinates and lines connecting to those coordinates
	 * 
	 * @param coordinates
	 */
	public void clear(ArrayList<City> coordinates) {
		clearLines(coordinates);
		clearPoints(coordinates);
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		point.paintComponent(g);
		line.paintComponent(g);
	}

	@Override
	public String toString() {
		return "Map: [" + line + "], [" + point + "]";
	}

}
