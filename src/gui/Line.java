package gui;

import gui.vo.City;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComponent;

@SuppressWarnings("serial")
public class Line extends JComponent {
    
    private static List<City> Lines = new ArrayList<City>();
    
    public void addLine(int x, int y) {
        addLine(x, y, Color.BLACK);
    }
    
    public void addLine(int x, int y, Color color) {
        Lines.add(new City(x, y, color));
        repaint();
    }
    
    public void clear() {
        Lines.clear();
        repaint();
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        for(int i = 1; i < Lines.size(); i++) {
            Lines.get(i).drawLine(g, Lines.get(i - 1));
        }
    }
    
}
