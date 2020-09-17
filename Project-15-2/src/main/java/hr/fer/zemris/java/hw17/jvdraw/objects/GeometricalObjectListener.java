package hr.fer.zemris.java.hw17.jvdraw.objects;

/**
 * Interface that is used in determining 
 * whether a {@link GeometricalObject} has changed 
 * its values of start point, end point etc.
 * @author Tomislav Kurtović
 *
 */
public interface GeometricalObjectListener {
	
	/**
	 * Called when there is a change in the {@link GeometricalObject} who is calling the method
	 * @param o {@link GeometricalObject} that calls the method
	 */
	public void geometricalObjectChanged(GeometricalObject o);
}
