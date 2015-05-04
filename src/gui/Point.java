package gui;

import gui.vo.City;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import javax.swing.JComponent;

@SuppressWarnings("serial")
public class Point extends JComponent {

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
