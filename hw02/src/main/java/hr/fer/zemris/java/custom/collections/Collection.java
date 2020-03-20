package hr.fer.zemris.java.custom.collections;

/**
 * Class that represents an empty implementation
 * of a Collection.
 * It has all the basic methods every collection
 * must have.
 * 
 * @author Tomislav Kurtović
 *
 */
public class Collection {
	
	/**
	 * Protected constructor of the Collection
	 */
	
	protected Collection() {
		
	}
	
	/**
	 * returns whether the 
	 * collection has elements in it or not
	 * 
	 * @return true if there is an element in the collection and false otherwise
	 */
	public boolean isEmpty() {
		return size() == 0;
	}
	
	/**
	 *  returns the size of the collection
	 *   
	 * @return size of the collection
	 */
	public int size() {
		return 0;
	}
	
	/**
	 * Adds the given non null value to the Collection
	 * @param value value to be added
	 */
	public void add(Object value) {
		
	}
	
	/**
	 * Checks if the collection contains the given value
	 * @param value value to search for
	 * @return true if the collection contains the value , false otherwise
	 */
	public boolean contains(Object value) {
		return false;
	}
	
	/**
	 * Removes the value from the collection
	 * @param value value to remove
	 * @return true if the value was succesfully removed, false otherwise
	 */
	
	public boolean remove(Object value) {
		return false;
	}
	
	/**
	 * Makes an array out of the collection
	 * @return array of objects
	 */
	public Object[] toArray() {
		throw new UnsupportedOperationException();
	}
	
	/**
	 * Goes through each element of the collection
	 * and calls the process method of the processor
	 * @param processor
	 */
	public void forEach(Processor processor) {
		
	}
	
	/**
	 * Copies all the elements from the other collection to this
	 * collection 
	 * @param other collection from which to copy elements
	 */
	public void addAll(Collection other) {
		class LocalProcessor extends Processor{
			@Override
			public void process(Object value) {
				add(value);
			}
		}
		other.forEach(new LocalProcessor());
	}
	
	/**
	 * Clears the collection of all elements
	 */
	
	public void clear() {
		
	}

}
