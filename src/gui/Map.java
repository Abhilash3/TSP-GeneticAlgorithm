package gui;

import gui.vo.City;

import java.awt.Graphics;
import javax.swing.JComponent;

@SuppressWarnings("serial")
public class Map extends JComponent {
    
    private Line line = new Line();
    private Point point = new Point();
    
    public void drawPoint(City city) {
        point.addPoint(city);
        repaint();
    }
    
    public void drawLine(City city) {
        line.addLine(city);
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
