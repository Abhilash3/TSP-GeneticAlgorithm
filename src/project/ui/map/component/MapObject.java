package project.ui.map.component;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComponent;

import project.genetic.vo.coordinate.ICoordinate;

@SuppressWarnings("serial")
public abstract class MapObject extends JComponent {

	protected List<ICoordinate> list = new ArrayList<ICoordinate>();

	public void add(ICoordinate iCoordinate) {
		list.add(iCoordinate);
	}

	public void addAll(List<ICoordinate> coordinates) {
		list.addAll(coordinates);
	}

	public void clear() {
		list.clear();
	}

	public List<ICoordinate> getList() {
		return list;
	}
	
	public abstract String toString();
	
	public abstract void draw(Graphics g);

}
