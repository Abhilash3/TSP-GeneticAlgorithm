package project.ui.graph.component;

import javax.swing.JComponent;

@SuppressWarnings("serial")
public class GraphObject extends JComponent {

	protected int padding;
	protected int labelPadding;
	protected int pointWidth;
	protected int divisions;

	public GraphObject(int padding, int labelPadding, int pointWidth,
			int divisions) {
		this.padding = padding;
		this.labelPadding = labelPadding;
		this.pointWidth = pointWidth;
		this.divisions = divisions;
	}

}
