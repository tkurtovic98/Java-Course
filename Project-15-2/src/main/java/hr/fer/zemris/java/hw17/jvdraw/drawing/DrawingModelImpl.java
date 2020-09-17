package hr.fer.zemris.java.hw17.jvdraw.drawing;

import java.util.ArrayList;
import java.util.List;
import java.util.PrimitiveIterator.OfDouble;

import hr.fer.zemris.java.hw17.jvdraw.JVDraw;
import hr.fer.zemris.java.hw17.jvdraw.objects.GeometricalObject;
import hr.fer.zemris.java.hw17.jvdraw.objects.GeometricalObjectListener;

/**
 * Class used to represent the {@link DrawingModel} of
 * the {@link JVDraw} program and also 
 * a {@link GeometricalObjectListener} of {@link GeometricalObject}s
 * @author Tomislav Kurtović
 *
 */
public class DrawingModelImpl implements DrawingModel, GeometricalObjectListener{
	/**
	 * List of {@link GeometricalObject}s
	 */
	List<GeometricalObject> geometricalObjects;
	/**
	 * List of {@link DrawingModelListener}s
	 */
	List<DrawingModelListener> drawingModelListeners;
	/**
	 * Modification flag of model
	 */
	private boolean modificationFlag = false;
	
	/**
	 * Constructor of model
	 */
	public  DrawingModelImpl() {
		geometricalObjects = new ArrayList<>();
		drawingModelListeners = new ArrayList<>();
	}
	
	@Override
	public int getSize() {
		return geometricalObjects.size();
	}

	@Override
	public GeometricalObject getObject(int index) {
		return geometricalObjects.get(index);
	}

	@Override
	public void add(GeometricalObject object) {
		if(!geometricalObjects.contains(object)) {
			geometricalObjects.add(object);
			object.addGeometricalObjectListener(this);
			notifyObjectsAdded(object);
			modificationFlag = true;
		}
	}
	/**
	 * Notifies all the {@link DrawingModelListener}s of objects adding
	 * @param o object added
	 */
	private void notifyObjectsAdded(GeometricalObject o) {
		for(DrawingModelListener l : drawingModelListeners) {
			l.objectsAdded(this, geometricalObjects.indexOf(o), geometricalObjects.indexOf(o));
		}
	}

	@Override
	public void remove(GeometricalObject object) {
		if(geometricalObjects.contains(object)) {
			int index = geometricalObjects.indexOf(object);
			geometricalObjects.remove(object);
			notifyObjectsRemoved(index);
			modificationFlag = true;
		}
	}
	/**
	 * Notifies all the {@link DrawingModelListener}s of objects removed
	 * @param o object removed
	 */
	private void notifyObjectsRemoved(int index) {
		for(DrawingModelListener l : drawingModelListeners) {
			l.objectsRemoved(this, index, index);
		}
	}
	
	@Override
	public void changeOrder(GeometricalObject object, int offset) {
		int oldIndex = geometricalObjects.indexOf(object);
		if(oldIndex == 0 && offset < 0) return;
		if(oldIndex == geometricalObjects.size()-1 && offset > 0) return;
		int newIndex = oldIndex + offset;
		geometricalObjects.remove(oldIndex);
		geometricalObjects.add(newIndex, object);
		notifyObjectsChanged(object);
		modificationFlag = true;
	}
	
	@Override
	public int indexOf(GeometricalObject object) {
		return geometricalObjects.indexOf(object);
	}

	@Override
	public void clear() {
		geometricalObjects.clear();
		modificationFlag = true;
	}

	@Override
	public void clearModifiedFlag() {
		modificationFlag = false;
	}

	@Override
	public boolean isModified() {
		return modificationFlag;
	}

	@Override
	public void addDrawingModelListener(DrawingModelListener l) {
		if(!drawingModelListeners.contains(l)) {
			drawingModelListeners.add(l);
		}
	}

	@Override
	public void removeDrawingModelListener(DrawingModelListener l) {
		if(drawingModelListeners.contains(l)) {
			drawingModelListeners.remove(l);
		}
	}

	@Override
	public void geometricalObjectChanged(GeometricalObject o) {
		notifyObjectsChanged(o);
	}
	/**
	 * Notifies all the {@link DrawingModelListener}s of objects change
	 * @param o object changed
	 */
	private void notifyObjectsChanged(GeometricalObject o) {
		for(DrawingModelListener l : drawingModelListeners) {
			l.objectsChanged(this, geometricalObjects.indexOf(o), geometricalObjects.indexOf(o));
		}
	}
}
