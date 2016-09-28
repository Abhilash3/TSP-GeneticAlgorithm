package project.ui.map;

import project.genetic.vo.coordinate.ICoordinate;
import project.ui.map.component.Line;
import project.ui.map.component.Point;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.JComponent;

@SuppressWarnings("serial")
public class Map extends JComponent {

    private final Set<Line> lines = new HashSet<Line>();
    private final Set<Point> points = new HashSet<Point>();

    private int[] max = new int[2];

    public Map() {
        super();
        max[0] = 0;
        max[1] = 0;
    }

    /**
     * connect coordinates using the path provided
     *
     * @param path list of cities to draw
     */
    public void drawMap(List<ICoordinate> path) {
        synchronized (lines) {
            if (path.size() > 1)
                lines.add(new Line(path.get(path.size() - 1), path.get(0)));
            for (int i = 1; i < path.size(); i++)
                lines.add(new Line(path.get(i - 1), path.get(i)));
        }
        getMaxValues(path);
        repaint();
    }

    /**
     * draw coordinates on the map
     *
     * @param coordinates list of cities to draw
     */
    public void drawCoordinates(List<ICoordinate> coordinates) {
        synchronized (points) {
            for (ICoordinate iCoordinate : coordinates)
                points.add(new Point(iCoordinate));
        }
        getMaxValues(coordinates);
        repaint();
    }

    public void clearLines() {
        synchronized (lines) {
            lines.clear();
        }
        repaint();
    }

    /**
     * clear line connecting to the given coordinates
     *
     * @param coordinates list of cities to remove
     */
    public void clearLines(List<ICoordinate> coordinates) {
        Set<Line> linesToBeRemoved = new HashSet<Line>();
        for (ICoordinate iCoordinate : coordinates)
            for (Line line : lines)
                if (line.isVisiting(iCoordinate))
                    linesToBeRemoved.add(line);
        synchronized (lines) {
            lines.removeAll(linesToBeRemoved);
        }
        repaint();
    }

    public void clearPoints() {
        synchronized (points) {
            points.clear();
        }
        repaint();
    }

    /**
     * clear coordinates from map
     *
     * @param coordinates list of cities to remove
     */
    public void clearPoints(List<ICoordinate> coordinates) {
        Set<Point> pointsToBeRemoved = new HashSet<Point>();
        for (Point point : points)
            if (coordinates.contains(point.getCoordinate()))
                pointsToBeRemoved.add(point);
        synchronized (points) {
            points.removeAll(pointsToBeRemoved);
        }
        repaint();
    }

    public void clear() {
        synchronized (lines) {
            lines.clear();
        }
        synchronized (points) {
            points.clear();
        }
        repaint();
    }

    /**
     * clear coordinates and lines connecting to those coordinates
     *
     * @param coordinates list of cities to remove
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

        double xScale = ((double) getWidth() - 20) / max[0];
        double yScale = ((double) getHeight() - 20) / max[1];

        synchronized (lines) {
            for (Line line : lines)
                line.draw(g2, xScale, yScale);
        }
        synchronized (points) {
            for (Point point : points)
                point.draw(g2, xScale, yScale);
        }
    }

    private void getMaxValues(List<ICoordinate> list) {
        for (ICoordinate coordinate : list) {
            int x = coordinate.getX();
            int y = coordinate.getY();
            if (max[0] < x)
                max[0] = x;
            if (max[1] < y)
                max[1] = y;
        }
    }

    @Override
    public String toString() {
        return "Map: " + lines + ", " + points;
    }

}
