package gui;

import gui.vo.City;

import java.awt.Graphics;
import java.util.ArrayList;
import javax.swing.JComponent;

@SuppressWarnings("serial")
public class Point extends JComponent {
    
    private static ArrayList<City> Points = new ArrayList<City>();
    
    public void addPoint(City city) {
        Points.add(city);
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
