package hr.fer.java.zemris.hw07.observer2;


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
	 * Notifies all the observers that a change has occurred 
	 * and passes retrieves information from the IntegerStorageChange
	 * @param istorage instance of {@link IntegerStorageChange} class used
	 * to store information about the {@link IntegerStorage}
	 * 
	 */
	public void valueChanged(IntegerStorageChange istorage);
}
