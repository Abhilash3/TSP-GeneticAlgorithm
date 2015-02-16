package gui;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JComponent;

@SuppressWarnings("serial")
public class MapComponent extends JComponent {
    
    private LineComponent line = new LineComponent();
    private PointComponent point = new PointComponent();
    
    public void draw(int x, int y) {
        draw(x, y, Color.BLACK);
    }
    
    public void draw(int x, int y, Color color) {
        point.addPoint(x, y, color);
        repaint();
    }
    
    public void draw(int x1, int y1, int x2, int y2) {
        draw(x1, y1, x2, y2, Color.BLACK);
    }
    
    public void draw(int x1, int y1, int x2, int y2, Color color) {
        line.addLine(x1, y1, x2, y2, color);
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
