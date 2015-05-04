package gui;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JComponent;

@SuppressWarnings("serial")
public class Map extends JComponent {
    
    private Line line = new Line();
    private Point point = new Point();
    
    public void drawPoint(int x, int y) {
        drawPoint(x, y, Color.BLACK);
    }
    
    public void drawPoint(int x, int y, Color color) {
        point.addPoint(x, y, color);
        repaint();
    }
    
    public void drawLine(int x, int y) {
        drawLine(x, y, Color.BLACK);
    }
    
    public void drawLine(int x, int y, Color color) {
        line.addLine(x, y, color);
        repaint();
    }
    
    public void clearLines() {
        line.clear();
        repaint();
    }
    
    public void clearPoints() {
    	point.clear();
    	repaint();
    }
    
    public void clear() {
    	line.clear();
    	point.clear();
    	repaint();
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        line.paintComponent(g);
        point.paintComponent(g);
    }
    
}
