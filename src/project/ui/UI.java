package project.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import project.genetic.vo.coordinate.Coordinate;
import project.ui.graph.Graph;
import project.ui.map.Map;

@SuppressWarnings("serial")
public class UI extends JFrame {

    protected Map map;
    protected Graph graph;
    protected JTextField textField;

    public UI(List<Coordinate> coordinates) {
        super("Simulation");
        setUp();
        drawCoordinates(coordinates);
    }

    private void setUp() {

        map = new Map();
        map.setPreferredSize(new Dimension(700, 650));

        graph = new Graph();
        graph.setPreferredSize(new Dimension(700, 650));

        textField = new JTextField();
        textField.setFont(new Font(textField.getFont().getName(), Font.BOLD,
                textField.getFont().getSize()));
        textField.setHorizontalAlignment(JTextField.CENTER);
        textField.setEditable(false);
        textField.setPreferredSize(new Dimension(700, 50));

        getContentPane().add(map, BorderLayout.LINE_START);
        getContentPane().add(graph, BorderLayout.LINE_END);
        getContentPane().add(textField, BorderLayout.PAGE_START);
        setPreferredSize(new Dimension(1400, 700));

        map.setBackground(Color.WHITE);
        map.setForeground(Color.BLACK);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        pack();
        setVisible(true);
    }

    public void updateGraph(List<Double> scores) {
        graph.add(scores);
    }

    public void clearMapPoints() {
        map.clearPoints();
    }

    public void clearMapPoints(List<Coordinate> coordinates) {
        map.clearPoints(coordinates);
    }

    public void clearMapLines() {
        map.clearLines();
    }

    public void clearMapLines(List<Coordinate> coordinates) {
        map.clearLines(coordinates);
    }

    public void clearMap() {
        map.clear();
    }

    public void drawCoordinates(List<Coordinate> coordinates) {
        map.drawCoordinates(coordinates);
    }

    public void drawMap(List<Coordinate> bestPath) {
        map.drawMap(bestPath);
    }

    public void setText(String str) {
        textField.setText(str);
    }

}
