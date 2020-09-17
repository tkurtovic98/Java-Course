package hr.fer.zemris.java.hw17.jvdraw.objects;

import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.hw17.jvdraw.JVDraw;
import hr.fer.zemris.java.hw17.jvdraw.actions.JVDrawSaveVisitor;
import hr.fer.zemris.java.hw17.jvdraw.objects.editors.GeometricalObjectEditor;

/**
 * Top class of all the geometrical objects 
 * the {@link JVDraw} program supports.
 * Children of this class are:
 * <li> {@link Line}
 * <li> {@link Circle}
 * <li> {@link FilledCircle}
 * @author Tomislav Kurtović
 *
 */
public abstract class GeometricalObject {
	/**
	 * Starting point of object
	 */
	protected Point start;
	/**
	 * fill color of object
	 */
	protected Color main;
	
	/**
	 * List of {@link GeometricalObjectListener}s
	 */
	protected List<GeometricalObjectListener> listeners;
	
	/**
	 * Constructor of the {@link GeometricalObject}
	 * @param start start point of drawing
	 */
	public GeometricalObject(Point start, Color main) {
		this.start = start;
		this.main = main;
		listeners = new ArrayList<>();
	}
	/**
	 * Used to get fill color of object
	 * @return
	 */
	public Color getColor() {
		return main;
	}
	/**
	 * Used to return formated fill color of object
	 * @return hex representation of fill color of object
	 */
	public String hexColor() {
		return String.format("#%02X%02X%02X", main.getRed(), main.getGreen(), main.getBlue());
	}
	
	/**
	 * Used to accept the {@link GeometricalObjectVisitor}
	 * @param v {@link GeometricalObjectVisitor} that the object should accept
	 */
	public abstract void accept(GeometricalObjectVisitor v);
	/**
	 * Used to add the {@link GeometricalObjectListener}s
	 * for later use
	 * @param l {@link GeometricalObjectListener} that the object should add
	 */
	public abstract void addGeometricalObjectListener(GeometricalObjectListener l);
	/**
	 * Used to add the {@link GeometricalObjectListener}s
	 * for later use
	 * @param l {@link GeometricalObjectListener} that the object should remove
	 */
	public abstract void removeGeometricalObjectListener(GeometricalObjectListener l);
	/**
	 * Used to create a new instance of {@link GeometricalObjectEditor} for each type of {@link GeometricalObject}
	 * @return new instance of {@link GeometricalObjectEditor}
	 */
	public abstract GeometricalObjectEditor createGeometricalObjectEditor();


}
