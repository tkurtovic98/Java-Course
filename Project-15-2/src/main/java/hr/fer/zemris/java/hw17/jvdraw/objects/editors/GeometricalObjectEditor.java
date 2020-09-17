package hr.fer.zemris.java.hw17.jvdraw.objects.editors;

import javax.swing.JPanel;

import hr.fer.zemris.java.hw17.jvdraw.drawing.DrawingModel;
import hr.fer.zemris.java.hw17.jvdraw.objects.GeometricalObject;

/**
 * Class that represents a editor used in
 * editing {@link GeometricalObject} components 
 * once it was added to the {@link DrawingModel} and 
 * painted to the canvas
 * @author Tomislav Kurtović
 *
 */
public abstract class GeometricalObjectEditor extends JPanel {
	private static final long serialVersionUID = 1L;
	/**
	 * Checks if all the field values for updating are correct
	 */
	public abstract void checkEditing();
	/**
	 * Edits the values of the {@link GeometricalObject}
	 */
	public abstract void acceptEditing();
}
