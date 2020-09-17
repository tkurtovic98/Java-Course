package hr.fer.zemris.java.hw17.jvdraw.objects;

import java.awt.Color;
import java.awt.Point;

import hr.fer.zemris.java.hw17.jvdraw.actions.JVDrawSaveVisitor;
import hr.fer.zemris.java.hw17.jvdraw.objects.editors.GeometricalObjectEditor;
import hr.fer.zemris.java.hw17.jvdraw.objects.editors.LineEditor;

/**
 * {@link GeometricalObject} that represents a line drawing
 * 
 * @author Tomislav Kurtović
 *
 */
public class Line extends GeometricalObject {
	/*
	 * End point of line
	 */
	protected Point end;

	/**
	 * Constructor of the line
	 * 
	 * @param start start point
	 * @param end   end point
	 */
	public Line(Point start, Point end, Color fill) {
		super(start, fill);
		this.end = end;
	}

	public void setEnd(Point end) {
		this.end = end;
	}

	public void setValues(Point start, Point end, Color color) {
		this.start = start;
		this.end = end;
		this.main = color;
		notifyListeners();
	}

	private void notifyListeners() {
		for(GeometricalObjectListener l : listeners) {
			l.geometricalObjectChanged(this);
		}
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
		return "Line (" + formatStart() + ") - (" + formatEnd() + ")";
	}

	public String formatStart() {
		return start.x + "," + start.y;
	}

	public String formatEnd() {
		return end.x + "," + end.y;
	}

	@Override
	public GeometricalObjectEditor createGeometricalObjectEditor() {
		return new LineEditor(this);
	}
}
