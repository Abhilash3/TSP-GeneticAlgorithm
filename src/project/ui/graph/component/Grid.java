package project.ui.graph.component;

import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.util.List;

import project.genetic.vo.coordinate.ICoordinate;
import project.genetic.vo.list.LList;

import static project.common.Constants.padding;
import static project.common.Constants.labelPadding;
import static project.common.Constants.pointWidth;

import static project.common.Constants.GridColor;
import static project.common.Constants.LineColor;
import static project.common.Constants.PointColor;

import static project.common.Constants.GraphDivisions;

public class Grid {

    private int height;
    private int width;

    private Stroke GRAPH_STROKE = new BasicStroke(1);

    public void drawGrid(Graphics g, int height, int width) {

        this.height = height;
        this.width = width;

        g.setColor(GridColor);

        int x, y;
        for (int i = 0; i <= GraphDivisions; i += 1) {
            y = (int) (height - (height - padding * 2 - labelPadding)
                    * (double) i / GraphDivisions - padding - labelPadding);
            g.drawLine(padding + labelPadding + pointWidth, y, width - padding,
                    y);

            x = (int) ((width - padding * 2 - labelPadding) * (double) i
                    / GraphDivisions + padding + labelPadding);
            g.drawLine(x, height - padding - labelPadding - pointWidth, x,
                    padding);
        }

    }

    public void drawChart(Graphics g, LList<ICoordinate> graphPoints) {

        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        Stroke oldStroke = g2.getStroke();
        List<ICoordinate> graph;
        ICoordinate iCoordinate;
        for (int i = 0; i < graphPoints.size(); i++) {
            graph = graphPoints.get(i);

            g2.setColor(LineColor);
            g2.setStroke(GRAPH_STROKE);
            if (graph.size() > 0) {
                iCoordinate = graph.get(graph.size() - 1);
                g.drawLine(padding + labelPadding, iCoordinate.getY(), width
                        - padding, iCoordinate.getY());
                g.drawLine(iCoordinate.getX(), height - padding - labelPadding,
                        iCoordinate.getX(), padding);
            }

            g2.setColor(PointColor);
            g2.setStroke(oldStroke);
            for (int j = 0; j < graph.size(); j++) {
                iCoordinate = graph.get(j);
                g2.fillRoundRect(iCoordinate.getX() - pointWidth / 2,
                        iCoordinate.getY() - pointWidth / 2, pointWidth,
                        pointWidth, pointWidth / 4, pointWidth / 4);
            }
        }

    }

}
