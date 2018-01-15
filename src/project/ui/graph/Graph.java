package project.ui.graph;

import project.genetic.vo.coordinate.Coordinate;
import project.ui.vo.AArrayList;
import project.ui.vo.LList;
import project.ui.graph.component.Axis;
import project.ui.graph.component.Grid;

import javax.swing.JComponent;
import java.awt.Graphics;
import java.util.List;

import static java.lang.String.format;
import static project.common.Constants.Generations;
import static project.common.Constants.Graphs;
import static project.common.Constants.labelPadding;
import static project.common.Constants.padding;

public class Graph extends JComponent {

    /**
     *
     */
    private static final long serialVersionUID = 7939029888905845071L;

    private int maxScore = Integer.MIN_VALUE;

    private LList<Double> scores;
    private LList<Coordinate> graphPoints;

    private Axis xAxis, yAxis;
    private Grid grid;

    public Graph() {
        this.scores = new AArrayList<>();
        for (int i = 0; i < Graphs; i++) {
            scores.addEmpty();
        }

        this.xAxis = new Axis(true);
        this.yAxis = new Axis(false);
        this.grid = new Grid();
    }

    public void add(List<Double> results) {
        for (int i = 0; i < scores.size(); i++) {
            scores.getAdd(i, results.get(i));

            if (maxScore < results.get(i)) {
                maxScore = 250 * ((int) (results.get(i) / 250) + 1);
            }
        }
        repaint();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        int height = getHeight();
        int width = getWidth();

        double xScale = ((double) width - 2 * padding - labelPadding) / Generations;
        double yScale = ((double) height - 2 * padding - labelPadding) / maxScore;

        graphPoints = new AArrayList<>();
        for (int i = 0; i < scores.size(); i++) {
            graphPoints.addEmpty();
            for (int j = 0; j < scores.getSize(i); j++) {
                int x = (int) (j * xScale + padding + labelPadding);
                int y = (int) ((maxScore - scores.get(i, j)) * yScale + padding);
                graphPoints.getAdd(i, Coordinate.newCoordinate(x, y));
            }
        }

        xAxis.reDraw(g, height, width, Generations);
        yAxis.reDraw(g, height, width, maxScore);
        grid.drawGrid(g, height, width);
        grid.drawChart(g, graphPoints);

    }

    @Override
    public String toString() {
        return format("Graph: %s", scores.toString());
    }

}
