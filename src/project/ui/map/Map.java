package project.ui.map;

import project.genetic.vo.coordinate.ICoordinate;
import project.ui.map.component.Line;
import project.ui.map.component.Point;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.List;

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
	public void drawMap(List<ICoordinate> bestPath) {
		line.addAll(bestPath);
		repaint();
	}

	/**
	 * draw coordinates on the map
	 * 
	 * @param coordinates
	 */
	public void drawCoordinates(List<ICoordinate> coordinates) {
		point.addAll(coordinates);
		repaint();
	}

	public void clearLines() {
		line.clear();
		repaint();
	}

	public void clearLine(ICoordinate iCoordinate) {
		line.getList().remove(iCoordinate);
		repaint();
	}

	/**
	 * clear line connecting to the given coordinates
	 * 
	 * @param coordinates
	 */
	public void clearLines(List<ICoordinate> coordinates) {
		line.getList().removeAll(coordinates);
		repaint();
	}

	public void clearPoints() {
		point.clear();
		repaint();
	}

	public void clearPoint(ICoordinate iCoordinate) {
		point.getList().remove(iCoordinate);
		repaint();
	}

	/**
	 * clear coordinates from map
	 * 
	 * @param coordinates
	 */
	public void clearPoints(List<ICoordinate> coordinates) {
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
	public void clear(List<ICoordinate> coordinates) {
		clearLines(coordinates);
		clearPoints(coordinates);
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);

		point.draw(g);
		line.draw(g);
	}

	@Override
	public String toString() {
		return "Map: [Line: " + line + "], [Point: " + point + "]";
	}

}
