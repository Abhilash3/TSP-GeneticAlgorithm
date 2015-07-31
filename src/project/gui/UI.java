package project.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import project.genetic.vo.City;
import project.gui.vo.Graph;
import project.gui.vo.Map;

public class UI {

	public final int Graphs = 2;

	protected Map map;
	protected Graph graph;
	protected JTextField textField;

	public UI(ArrayList<City> coordinates) {
		setUp();
		this.drawCoordinates(coordinates);
	}

	private void setUp() {

		JFrame frame = new JFrame("Simulation");

		map = new Map();
		map.setPreferredSize(new Dimension(700, 650));

		graph = new Graph(Graphs);
		graph.setPreferredSize(new Dimension(700, 650));

		textField = new JTextField();
		textField.setFont(new Font(textField.getFont().getName(), Font.BOLD,
				textField.getFont().getSize()));
		textField.setHorizontalAlignment(JTextField.CENTER);
		textField.setEditable(false);
		textField.setPreferredSize(new Dimension(700, 50));

		frame.getContentPane().add(map, BorderLayout.LINE_START);
		frame.getContentPane().add(graph, BorderLayout.LINE_END);
		frame.getContentPane().add(textField, BorderLayout.PAGE_END);
		frame.setPreferredSize(new Dimension(1400, 700));

		map.setBackground(Color.WHITE);
		map.setForeground(Color.BLACK);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		frame.pack();
		frame.setVisible(true);

	}

	public void drawCoordinates(ArrayList<City> coordinates) {
		map.drawCoordinates(coordinates);
	}

	public void drawMap(ArrayList<City> coordinates, ArrayList<Integer> bestPath) {
		map.drawMap(coordinates, bestPath);
	}

	public void updateGraph(ArrayList<Double> scores) {
		graph.add(scores);
	}

	public void clearPoints() {
		map.clearPoints();
	}

	public void clearLines() {
		map.clearLines();
	}

	public void clearLines(ArrayList<City> coordinates) {
		map.clearLines(coordinates);
	}

	public void clearMap() {
		map.clear();
	}

	public void setText(String str) {
		textField.setText(str);
	}

}
