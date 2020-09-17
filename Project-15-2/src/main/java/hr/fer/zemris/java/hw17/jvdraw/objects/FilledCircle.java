package hr.fer.zemris.java.hw17.jvdraw.objects;

import java.awt.Color;
import java.awt.Point;

import hr.fer.zemris.java.hw17.jvdraw.objects.editors.FilledCircleEditor;
import hr.fer.zemris.java.hw17.jvdraw.objects.editors.GeometricalObjectEditor;

/**
 * {@link GeometricalObject} that
 * represents a filled circle drawing
 * @author Tomislav Kurtović
 *
 */
public class FilledCircle extends GeometricalObject {
	/**
	 * Circle radius
	 */
	protected int radius;
	/**
	 * border color
	 */
	protected Color border;
	/**
	 * Constructor of the filled circle
	 * @param start center point of circle
	 * @param radius radius
	 * @param color color
	 * @param border border color
	 */
	public FilledCircle(Point start, int radius, Color fill, Color border) {
		super(start, fill);
		this.radius = radius;
		this.border = border;
	}

	@Override
	public void accept(GeometricalObjectVisitor v) {
		v.visit(this);
	}


	@Override
	public void addGeometricalObjectListener(GeometricalObjectListener l) {
		if (!listeners.contains(l)) {
			listeners.add(l);
		}

	}

	@Override
	public void removeGeometricalObjectListener(GeometricalObjectListener l) {
		if (listeners.contains(l)) {
			listeners.remove(l);
		}
	}

	@Override
	public String toString() {
		return "Filled circle ("+ formatStart() + ")" + "," + radius + ","
				+ hexColor();
	}
	
	public String formatStart() {
		return start.x + "," + start.y;
	}
	
	public int getRadius() {
		return radius;
	}

	public Color getBorderColor() {
		return border;
	}
	
	@Override
	public GeometricalObjectEditor createGeometricalObjectEditor() {
		return new FilledCircleEditor(this);
	}

	public String hexBorderColor() {
		return String.format("#%02X%02X%02X", border.getRed(), border.getGreen(), border.getBlue());
	}
	
	public void setValues(Point start, int radius, Color fill, Color border) {
		this.radius = radius;
		this.start = start;
		this.main = fill;
		this.border = border;
		notifyListeners();
	}
	
	private void notifyListeners() {
		for(GeometricalObjectListener l : listeners) {
			l.geometricalObjectChanged(this);
		}
	}
	

}
