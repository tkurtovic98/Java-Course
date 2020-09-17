package hr.fer.zemris.java.custom.collections;

/**
 * Interface that represents elements getter
 * @author Tomislav Kurtović
 *
 */
public interface ElementsGetter {

	/**
	 * Checks if collection has more elements
	 * @return true if it does, false otherwise
	 */
	boolean hasNextElement();
	
	/**
	 * Retrieves the next object from the collection
	 * @return
	 */
	Object getNextElement();
	
	/**
	 * While there are elements the processor
	 * will process them
	 * @param p 
	 */
	default void processRemaining(Processor p) {
		while(hasNextElement()) {
			p.process(getNextElement());
		}
	}

}
