package hr.fer.zemris.java.hw17.jvdraw.drawing;

import hr.fer.zemris.java.hw17.jvdraw.objects.GeometricalObject;

/**
 * Interface used to track 
 * modifications made to {@link GeometricalObject}s
 * that are drawn on the {@link JDrawingCanvas}
 * @author Tomislav Kurtović
 *
 */
public interface DrawingModelListener {
	/**
	 * Called when an object has been added
	 * @param source {@link DrawingModel} that contains all the {@link GeometricalObject}s 
	 * @param index0 index of {@link GeometricalObject}
	 * @param index1 index of {@link GeometricalObject}
	 */
	public void objectsAdded(DrawingModel source, int index0, int index1);
	/**
	 * Called when an object has been removed
	 * @param source {@link DrawingModel} that contains all the {@link GeometricalObject}s 
	 * @param index0 index of {@link GeometricalObject}
	 * @param index1 index of {@link GeometricalObject}
	 */
	public void objectsRemoved(DrawingModel source, int index0, int index1);
	/**
	 * Called when an object has been  changed
	 * @param source {@link DrawingModel} that contains all the {@link GeometricalObject}s 
	 * @param index0 index of {@link GeometricalObject}
	 * @param index1 index of {@link GeometricalObject}
	 */
	public void objectsChanged(DrawingModel source, int index0, int index1);
}
