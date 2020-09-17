package hr.fer.zemris.java.hw17.jvdraw.list;

import javax.swing.AbstractListModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

import hr.fer.zemris.java.hw17.jvdraw.JVDraw;
import hr.fer.zemris.java.hw17.jvdraw.drawing.DrawingModel;
import hr.fer.zemris.java.hw17.jvdraw.drawing.DrawingModelListener;
import hr.fer.zemris.java.hw17.jvdraw.objects.GeometricalObject;

/**
 * ListModel that is used to render information about 
 * the current objects that the {@link JVDraw} program has 
 * painted to screen
 * @author Tomislav Kurtović
 *
 */
public class DrawingObjectListModel extends AbstractListModel<GeometricalObject> implements DrawingModelListener{
	private static final long serialVersionUID = 1L;
	/**
	 * Reference to {@link DrawingModel}
	 */
	DrawingModel model;
	
	/**
	 * Constructor of the {@link DrawingObjectListModel}
	 * @param model reference to {@link DrawingModel}
	 */
	public  DrawingObjectListModel(DrawingModel model) {
		this.model = model;
		model.addDrawingModelListener(this);
	}
	
	@Override
	public int getSize() {
		return model.getSize();
	}

	@Override
	public GeometricalObject getElementAt(int index) {
		return model.getObject(index);
	}

	@Override
	public void objectsAdded(DrawingModel source, int index0, int index1) {
		ListDataEvent event = new ListDataEvent(this, ListDataEvent.INTERVAL_ADDED, index0, index1);
		for(ListDataListener l : getListDataListeners()) {
			l.intervalAdded(event);
		}
	}

	@Override
	public void objectsRemoved(DrawingModel source, int index0, int index1) {
		ListDataEvent event = new ListDataEvent(this, ListDataEvent.INTERVAL_REMOVED, index0, index1);
		for(ListDataListener l : getListDataListeners()) {
			l.intervalRemoved(event);
		}
	}

	@Override
	public void objectsChanged(DrawingModel source, int index0, int index1) {
		ListDataEvent event = new ListDataEvent(this, ListDataEvent.CONTENTS_CHANGED, index0, index1);
		for(ListDataListener l : getListDataListeners()) {
			l.contentsChanged(event);
		}
	}
}
