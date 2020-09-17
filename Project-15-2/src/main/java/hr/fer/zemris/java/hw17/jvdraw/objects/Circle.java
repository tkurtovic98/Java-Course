package hr.fer.zemris.java.hw17.jvdraw.objects;

import java.awt.Color;
import java.awt.Point;

import hr.fer.zemris.java.hw17.jvdraw.actions.JVDrawSaveVisitor;
import hr.fer.zemris.java.hw17.jvdraw.objects.editors.CircleEditor;
import hr.fer.zemris.java.hw17.jvdraw.objects.editors.GeometricalObjectEditor;
/**
 * {@link GeometricalObject} that
 * represents an empty circle drawing
 * @author Tomislav Kurtović
 *
 */
public class Circle extends GeometricalObject {

	/**
	 * Circle radius
	 */
	protected int radius;
	/**
	 * Constructor of the circle object
	 * @param start center of circle
	 * @param radius radius of circle
	 */
	public Circle(Point start, int radius, Color fill) {
		super(start, fill);
		this.radius = radius;
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
		return "Circle ("+ formatStart() +")" + "," + radius;
	}

	public String formatStart() {
		return start.x + "," + start.y;
	}
	
	public int getRadius() {
		return radius;
	}
	
	@Override
	public GeometricalObjectEditor createGeometricalObjectEditor() {
		return new CircleEditor(this);
	}

	public void setValues(Point start, int radius, Color color) {
		this.radius = radius;
		this.start = start;
		this.main = color;
		notifyListeners();
	}
	
	private void notifyListeners() {
		for(GeometricalObjectListener l : listeners) {
			l.geometricalObjectChanged(this);
		}
	}
}
