package project.ui.graph;

import project.genetic.vo.coordinate.Coordinate;
import project.genetic.vo.coordinate.ICoordinate;
import project.genetic.vo.list.ILList;
import project.genetic.vo.list.MMagicList;
import project.ui.graph.component.Axis;
import project.ui.graph.component.Grid;

import java.awt.Color;
import java.awt.Graphics;
import java.util.List;

import javax.swing.JComponent;

@SuppressWarnings("serial")
public class Graph extends JComponent {

	private int padding = 50;
	private int pointWidth = 3;
	private int labelPadding = 20;
	private int maxScore = Integer.MIN_VALUE;

	private Color lineColor = Color.DARK_GRAY;
	private Color pointColor = Color.BLACK;
	private Color gridColor = Color.LIGHT_GRAY;

	private ILList<Double> scores;

	private Axis x;
	private Axis y;
	private Grid grid;

	public Graph(int graphs, int divisions) {
		this.scores = new MMagicList<Double>();
		for (int i = 0; i < graphs; i++) {
			scores.addEmpty();
		}

		this.x = new Axis(padding, labelPadding, pointWidth, divisions, true);
		this.y = new Axis(padding, labelPadding, pointWidth, divisions, false);
		this.grid = new Grid(padding, labelPadding, pointWidth, pointColor,
				gridColor, lineColor, divisions);
	}

	public void add(List<Double> results) {
		for (int i = 0; i < scores.size(); i++) {
			scores.getAdd(i, results.get(i));

			if (maxScore < results.get(i)) {
				maxScore = ceil(results.get(i));
			}
		}
		repaint();
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		int height = getHeight();
		int width = getWidth();

		double xScale = ((double) width - 2 * padding - labelPadding) / 3000;
		double yScale = ((double) height - 2 * padding - labelPadding)
				/ maxScore;

		ILList<ICoordinate> graphPoints = new MMagicList<ICoordinate>();
		for (int i = 0; i < scores.size(); i++) {
			graphPoints.addEmpty();
			for (int j = 0; j < scores.getSize(i); j++) {
				int x = (int) (j * xScale + padding + labelPadding);
				int y = (int) ((maxScore - scores.get(i, j)) * yScale + padding);
				graphPoints.getAdd(i, new Coordinate(x, y));
			}
		}

		grid.drawGrid(g, height, width);
		x.reDraw(g, height, width, 3000);
		y.reDraw(g, height, width, maxScore);
		grid.drawChart(g, graphPoints);

	}

	@Override
	public String toString() {
		return scores.toString();
	}

	private int ceil(double max) {
		int i = 0;
		for (; i < max; i += 3000)
			;
		return i;
	}

}
