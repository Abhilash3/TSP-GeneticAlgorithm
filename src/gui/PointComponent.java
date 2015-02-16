package gui;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import javax.swing.JComponent;

@SuppressWarnings("serial")
public class PointComponent extends JComponent {

    private class City {
        
        private int x;
        private int y;
        private Color color;
        
        public City(int x, int y, Color color) {
            this.x = x;
            this.y = y;
            this.color = color;
        }

        @Override
        public String toString() {
        	return "( " + this.x + ", " + this.y + " )";
        }
        
        public void drawPoint(Graphics g) {
        	g.setColor(color);
            g.fillRect(x - 2, y - 2, 4, 4);
            g.drawString(this.toString(), x, y);
        }
        
    }
    
    private static ArrayList<City> Points = new ArrayList<City>();
    
    public void addPoint(int x, int y) {
        addPoint(x, y, Color.BLACK);
    }
    
    public void addPoint(int x, int y, Color color) {
        Points.add(new City(x, y, color));
        repaint();
    }
    
    public void clear() {
        Points.clear();
        repaint();
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        for(int i = 0; i < Points.size(); i++) {
            Points.get(i).drawPoint(g);
        }
    }
    
}
