package project.ui.graph.component;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;


@SuppressWarnings("serial")
public class Axis extends GraphObject {

	private boolean isXAxis;

	public Axis(int padding, int labelPadding, int pointWidth, int divisions,
			boolean isXAxis) {
		super(padding, labelPadding, pointWidth, divisions);
		this.isXAxis = isXAxis;
	}

	public void reDraw(Graphics g, int height, int width, int max) {

		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);

		FontMetrics metrics = g2.getFontMetrics();
		g2.setColor(Color.BLACK);

		// create hatch marks for x axis.
		for (int i = 0; i <= divisions; i++) {
			int x0, y0, x1, y1;
			if (isXAxis) {
				x0 = (width - padding * 2 - labelPadding) * i / divisions
						+ padding + labelPadding;
				x1 = x0;
				y0 = height - padding - labelPadding;
				y1 = y0 - pointWidth;
			} else {
				x0 = padding + labelPadding;
				x1 = pointWidth + padding + labelPadding;
				y0 = height - (height - padding * 2 - labelPadding) * i
						/ divisions - padding - labelPadding;
				y1 = y0;
			}

			g2.drawLine(x0, y0, x1, y1);

			String label = String.valueOf(max * i / divisions);

			if (isXAxis) {
				g2.rotate(Math.PI / 2, x0 - metrics.stringWidth(label) / 4, y0
						+ metrics.getHeight() + 3);
				g2.drawString(label, x0 - metrics.stringWidth(label) / 2, y0
						+ metrics.getHeight() + 3);
				g2.rotate(-Math.PI / 2, x0 - metrics.stringWidth(label) / 4, y0
						+ metrics.getHeight() + 3);
			} else {
				g2.drawString(label, x0 - metrics.stringWidth(label) - 5, y0
						+ (metrics.getHeight() / 2) - 3);
			}
		}

		if (isXAxis) {
			g.drawLine(padding + labelPadding, height - padding - labelPadding,
					width - padding, height - padding - labelPadding);
		} else {
			g.drawLine(padding + labelPadding, height - padding - labelPadding,
					padding + labelPadding, padding);
		}

	}

	public boolean isXAxis() {
		return isXAxis;
	}

	public boolean isYAxis() {
		return !isXAxis;
	}

}
