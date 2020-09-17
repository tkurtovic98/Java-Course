package hr.fer.zemris.java.hw17.jvdraw.drawing;

import hr.fer.zemris.java.hw17.jvdraw.JVDraw;
import hr.fer.zemris.java.hw17.jvdraw.objects.GeometricalObject;
/**
 * Interface used to represent the 
 * drawing model used by the {@link JVDraw} 
 * program
 * @author Tomislav Kurtović
 *
 */
public interface DrawingModel {
	
	/**
	 * Returns size of model
	 * @return size of model
	 */
	public int getSize();
	/**
	 * Returns object from model
	 * @param index index of object in model
	 * @return {@link GeometricalObject} from model at index
	 */
	public GeometricalObject getObject(int index);
	/**
	 * Adds object to model
	 * @param object {@link GeometricalObject} to add
	 */
	public void add(GeometricalObject object);
	/** 
	 * Removes object from model
	 * @param object {@link GeometricalObject} to remove
	 */
	public void remove(GeometricalObject object);
	/**
	 * Changes order of objects in model
	 * @param object object whose position should be changed
 	 * @param offset offset by which the object should be shifted
 	 */
	public void changeOrder(GeometricalObject object, int offset);
	/**
	 * Returns index of {@link GeometricalObject} found in model
 	 * @param object object whose index is to be retrieved
	 * @return index of specified object
	 */
	public int indexOf(GeometricalObject object);
	/**
	 * Clears model from all {@link GeometricalObject}s
	 */
	public void clear();
	/**
	 * Sets the modification flag to false
	 */
	public void clearModifiedFlag();
	/**
	 * Returns whether or not the model has been modified
	 * @return true if modification flag is true, false otherwise
	 */
	public boolean isModified();
	/**
	 * Used to add {@link DrawingModelListener} to the model
	 * @param l {@link DrawingModelListener} to add
	 */
	public void addDrawingModelListener(DrawingModelListener l);
	/**
	 * Used to remove {@link DrawingModelListener} from model
	 * @param l {@link DrawingModelListener} to remove
	 */
	public void removeDrawingModelListener(DrawingModelListener l);
}
