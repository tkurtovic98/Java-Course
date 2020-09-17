package hr.fer.zemris.java.custom.collections;

/**
 * Class that has empty method process and can
 * be used in a Collection
 * 
 * @author Tomislav Kurtović
 *
 */
public interface Processor {
	/**
	 * Empty method that should process the given value in 
	 * some way
	 * 
	 * @param value value to process
	 */
	public void process(Object value);

}
