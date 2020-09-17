package hr.fer.java.zemris.hw07.observer1;

/**
 * Interface used by the {@link IntegerStorage} class.
 * Different observers have different functionalities
 * and are specified in the concrete implementation
 * of this interface
 * 
 * @author Tomislav Kurtović
 *
 */
public interface IntegerStorageObserver {
	
	/**
	 * Notifies all the observers that a change has occured 
	 * and retrieves information from the IntegerStorage
	 * @param istorage instance of {@link IntegerStorage} class used
	 * to handle all the observers 
	 * 
	 */
	public void valueChanged(IntegerStorage istorage);
}
