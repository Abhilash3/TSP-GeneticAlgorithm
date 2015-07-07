package gui;

import genetic.TSP;
import gui.vo.City;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComponent;

@SuppressWarnings("serial")
public class Graph extends JComponent {

    private int padding = 50;
    private int labelPadding = 20;
    private Color lineColor = Color.DARK_GRAY;
    private Color pointColor = Color.BLACK;
    private Color gridColor = Color.LIGHT_GRAY;
    private Stroke GRAPH_STROKE = new BasicStroke(1);
    private int pointWidth = 3;
    private int numberYDivisions = 20;
    
    private double maxScore = Double.MIN_VALUE;
    private double minScore = Double.MAX_VALUE;
    private ArrayList<ArrayList<Double>> scores;

    public Graph() {
        this.scores = new ArrayList<ArrayList<Double>>();
        for (int i = 0; i < TSP.Graphs; i++) {
            scores.add(new ArrayList<Double>());
        }
    }
    
    public void add(List<Double> log) {
        for (int i = 0; i < TSP.Graphs; i++) {
            scores.get(i).add(log.get(i));
            if (maxScore < log.get(i)) {
                maxScore = log.get(i);
            } else if (minScore > log.get(i)) {
                minScore = log.get(i);
            }
        }
        repaint();
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        
        int height = getHeight();
        int width = getWidth();
        
        double xScale = ((double) width - (2 * padding) - labelPadding)
                / (scores.get(0).size() - 1);
        double yScale = ((double) height - 2 * padding - labelPadding)
                / (maxScore - minScore);
        
        ArrayList<ArrayList<City>> graphPoints = new ArrayList<ArrayList<City>>();
        for (int i = 0; i < TSP.Graphs; i++) {
            graphPoints.add(new ArrayList<City>());
            for (int j = 0; j < scores.get(i).size(); j++) {
                int x1 = (int) (j * xScale + padding + labelPadding);
                int y1 = (int) ((maxScore - scores.get(i).get(j)) * yScale + padding);
                graphPoints.get(i).add(new City(x1, y1));
            }
        }
        
        // draw white background
        g2.setColor(Color.WHITE);
        g2.fillRect(padding + labelPadding, padding, width - (2 * padding)
                - labelPadding, height - 2 * padding - labelPadding);
        
        createGraphGrid(g2, height, width);
        
        Stroke oldStroke = g2.getStroke();
        g2.setColor(lineColor);
        g2.setStroke(GRAPH_STROKE);
        for (ArrayList<City> graph : graphPoints) {
            for (int i = 1; i < graph.size(); i++) {
                g2.drawLine(graph.get(i - 1).getX(), graph.get(i - 1).getY(),
                        graph.get(i).getX(), graph.get(i).getY());
            }
        }
        
        g2.setStroke(oldStroke);
        g2.setColor(pointColor);
        for (ArrayList<City> graph : graphPoints) {
            for (City point : graph) {
                int x = point.getX() - pointWidth / 2;
                int y = point.getY() - pointWidth / 2;
                int rectW = pointWidth;
                int rectH = pointWidth;
                g2.fillRect(x, y, rectW, rectH);
            }
        }
    }
    
    private void createGraphGrid(Graphics2D g2, int height, int width) {
        
        g2.setColor(Color.BLACK);
        
        // create x and y axes
        g2.drawLine(padding + labelPadding, height - padding - labelPadding
                - 10, padding + labelPadding + 5, height - padding
                - labelPadding - 10);
        g2.drawLine(padding + labelPadding + 5, height - padding - labelPadding
                - 10, padding + labelPadding - 5, height - padding
                - labelPadding);
        g2.drawLine(padding + labelPadding - 5,
                height - padding - labelPadding, padding + labelPadding, height
                        - padding - labelPadding);
        g2.drawLine(padding + labelPadding, height - padding - labelPadding
                - 10, padding + labelPadding, padding);
        g2.drawLine(padding + labelPadding, height - padding - labelPadding,
                width - padding, height - padding - labelPadding);
        
        FontMetrics metrics = g2.getFontMetrics();
        
        // create hatch marks and grid lines for y axis.
        for (int i = 0; i < numberYDivisions + 1; i++) {
            int x0 = padding + labelPadding;
            int x1 = pointWidth + padding + labelPadding;
            int y0 = height
                    - ((i * (height - padding * 2 - labelPadding))
                            / numberYDivisions + padding + labelPadding);
            int y1 = y0;
            
            g2.drawLine(x0, y0, x1, y1);
            
            g2.setColor(gridColor);
            g2.drawLine(padding + labelPadding + 1 + pointWidth, y0, width
                    - padding, y1);
            g2.setColor(Color.BLACK);
            
            String yLabel = String
                    .valueOf(((int) ((minScore + (maxScore - minScore)
                        * ((i * 1.0) / numberYDivisions)) * 100)) / 100.0);
            
            g2.drawString(yLabel, x0 - metrics.stringWidth(yLabel) - 5, y0
                    + (metrics.getHeight() / 2) - 3);
        }
        
        // create hatch marks and grid lines for x axis.
        for (int i = 0; i < scores.get(0).size(); i++) {
            if (scores.get(0).size() > 1) {
                int x0 = i * (width - padding * 2 - labelPadding)
                        / (scores.get(0).size() - 1) + padding + labelPadding;
                int x1 = x0;
                int y0 = height - padding - labelPadding;
                int y1 = y0 - pointWidth;
                if ((i % ((int) (scores.get(0).size() / 20.0) + 1)) == 0) {
                    g2.drawLine(x0, y0, x1, y1);
                    
                    g2.setColor(gridColor);
                    g2.drawLine(x0, height - padding - labelPadding - 1
                            - pointWidth, x1, padding);
                    g2.setColor(Color.BLACK);
                    
                    String xLabel = String.valueOf(i);
                    
                    g2.rotate(Math.PI / 2,
                            x0 - metrics.stringWidth(xLabel) / 4,
                            y0 + metrics.getHeight() + 3);
                    g2.drawString(xLabel, x0 - metrics.stringWidth(xLabel) / 2,
                            y0 + metrics.getHeight() + 3);
                    g2.rotate(-Math.PI / 2, x0 - metrics.stringWidth(xLabel)
                            / 4, y0 + metrics.getHeight() + 3);
                }
            }
        }
    }
    
}
