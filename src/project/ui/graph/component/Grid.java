package project.ui.graph.component;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.util.List;

import project.genetic.vo.coordinate.ICoordinate;
import project.genetic.vo.list.ILList;

@SuppressWarnings("serial")
public class Grid extends GraphObject {

	private Color pointColor;
	private Color gridColor;
	private Color lineColor;

	private Stroke GRAPH_STROKE = new BasicStroke(1);

	public Grid(int padding, int labelPadding, int pointWidth,
			Color pointColor, Color gridColor, Color lineColor, int size) {
		super(padding, labelPadding, pointWidth, size);
		this.pointColor = pointColor;
		this.gridColor = gridColor;
		this.lineColor = lineColor;
	}

	public void drawGrid(Graphics g, int height, int width) {

		g.setColor(gridColor);

		for (int i = 0; i <= divisions; i += 1) {
			int y = (int) (height - (height - padding * 2 - labelPadding)
					* (double) i / divisions - padding - labelPadding);
			g.drawLine(padding + labelPadding + pointWidth, y, width - padding,
					y);

			int x = (int) ((width - padding * 2 - labelPadding) * (double) i
					/ divisions + padding + labelPadding);
			g.drawLine(x, height - padding - labelPadding - pointWidth, x,
					padding);
		}

	}

	public void drawChart(Graphics g, ILList<ICoordinate> graphPoints) {

		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);

		Stroke oldStroke = g2.getStroke();
		for (int i = 0; i < graphPoints.size(); i++) {
			List<ICoordinate> graph = graphPoints.get(i);

			g2.setColor(lineColor);
			g2.setStroke(GRAPH_STROKE);
			for (int j = 1; j < graph.size(); j++) {
				graph.get(j - 1).drawLine(g2, graph.get(j));
			}

			g2.setColor(pointColor);
			g2.setStroke(oldStroke);
			for (int j = 0; j < graph.size(); j++) {
				graph.get(j).drawPoint(g2, pointWidth);
			}
		}

	}

}
