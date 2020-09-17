package hr.fer.zemris.java.custom.collections;

/**
 * Interface that represents elements getter
 * @author Tomislav Kurtović
 *
 */
public interface ElementsGetter<T> {

	/**
	 * Checks if collection has more elements
	 * @return true if it does, false otherwise
	 */
	boolean hasNextElement();
	
	/**
	 * Retrieves the next object from the collection
	 * @return
	 */
	T getNextElement();
	
	/**
	 * While there are elements the processor
	 * will process them
	 * @param p 
	 */
	default void processRemaining(Processor<T> p) {
		while(hasNextElement()) {
			p.process(getNextElement());
		}
	}

}
