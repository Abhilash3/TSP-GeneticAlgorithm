package project.gui.vo;

import java.util.ArrayList;

import javax.swing.JComponent;

import project.genetic.vo.City;

@SuppressWarnings("serial")
public abstract class Component extends JComponent {

	protected ArrayList<City> list = new ArrayList<City>();

	public void clear() {
		list.clear();
	}

	public ArrayList<City> getList() {
		return list;
	}
	
	public abstract String toString();

}
