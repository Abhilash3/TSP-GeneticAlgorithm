package project.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import project.genetic.vo.coordinate.ICoordinate;
import project.ui.graph.Graph;
import project.ui.map.Map;

public class UI {

	public final int Graphs = 3;
	private int graphDivisions = 25;

	private int padding = 50;
	private int pointWidth = 3;
	private int labelPadding = 20;

	protected Map map;
	protected Graph graph;
	protected JTextField textField;

	public UI(List<ICoordinate> coordinates) {
		setUp();
		drawCoordinates(coordinates);
	}

	private void setUp() {

		JFrame frame = new JFrame("Simulation");

		map = new Map();
		map.setPreferredSize(new Dimension(700, 650));

		graph = new Graph(Graphs, graphDivisions, padding, pointWidth, labelPadding);
		graph.setPreferredSize(new Dimension(700, 650));

		textField = new JTextField();
		textField.setFont(new Font(textField.getFont().getName(), Font.BOLD,
				textField.getFont().getSize()));
		textField.setHorizontalAlignment(JTextField.CENTER);
		textField.setEditable(false);
		textField.setPreferredSize(new Dimension(700, 50));

		frame.getContentPane().add(map, BorderLayout.LINE_START);
		frame.getContentPane().add(graph, BorderLayout.LINE_END);
		frame.getContentPane().add(textField, BorderLayout.PAGE_START);
		frame.setPreferredSize(new Dimension(1400, 700));

		map.setBackground(Color.WHITE);
		map.setForeground(Color.BLACK);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		frame.pack();
		frame.setVisible(true);
	}

	public void updateGraph(List<Double> scores) {
		graph.add(scores);
	}

	public void clearMapPoints() {
		map.clearPoints();
	}

	public void clearMapPoint(ICoordinate iCoordinate) {
		map.clearPoint(iCoordinate);
	}

	public void clearMapPoints(List<ICoordinate> coordinates) {
		map.clearPoints(coordinates);
	}

	public void clearMapLines() {
		map.clearLines();
	}

	public void clearMapLine(ICoordinate iCoordinate) {
		map.clearLine(iCoordinate);
	}

	public void clearMapLines(List<ICoordinate> coordinates) {
		map.clearLines(coordinates);
	}

	public void clearMap() {
		map.clear();
	}

	public void drawCoordinates(List<ICoordinate> coordinates) {
		map.drawCoordinates(coordinates);
	}

	public void drawMap(List<ICoordinate> bestPath) {
		map.drawMap(bestPath);
	}

	public void setText(String str) {
		textField.setText(str);
	}

}
