package hr.fer.zemris.java.custom.collections;

/**
 * Interface that represents an empty implementation
 * of a Collection.
 * It has all the basic methods every collection
 * must have.
 * 
 * @author Tomislav Kurtović
 *
 */
public interface Collection <T>{
	
	/**
	 * returns whether the 
	 * collection has elements in it or not
	 * 
	 * @return true if there is an element in the collection and false otherwise
	 */
	default boolean isEmpty() { 
		return size()==0;
	}
	
	/**
	 *  returns the size of the collection
	 *   
	 * @return size of the collection
	 */
    int size() ;
	
	
	/**
	 * Adds the given non null value to the Collection
	 * @param value value to be added
	 */
	void add(T value) ;
	
	/**
	 * Checks if the collection contains the given value
	 * @param value value to search for
	 * @return true if the collection contains the value , false otherwise
	 */
	
	/// MORAM PROVJERITI OVAJ OBJECT JE LI TREBA IĆ neki parametar ili ne /// 
	boolean contains(Object value);
	
	
	/**
	 * Removes the value from the collection
	 * @param value value to remove
	 * @return true if the value was succesfully removed, false otherwise
	 */
	
	boolean remove(Object value);
	
	/**
	 * Makes an array out of the collection
	 * @return array of objects
	 */
	Object[] toArray();
	
	/**
	 * Goes through each element of the collection
	 * and calls the process method of the processor
	 * @param processor
	 */
	default void forEach(Processor<? super T> processor) {
		ElementsGetter<T> getter = createElementsGetter();
		while(getter.hasNextElement()) {
			processor.process(getter.getNextElement());
		}
	}
	
	/**
	 * Copies all the elements from the other collection to this
	 * collection 
	 * @param other collection from which to copy elements
	 */
	default void addAll(Collection<? extends T> other) {
		class LocalProcessor implements Processor<T>{
			@Override
			public void process(T value) {
				add(value);
			}
		}
		other.forEach(new LocalProcessor());
	}
	
	/**
	 * Clears the collection of all elements
	 */
	
	void clear();
	
	/**
	 * Creates new Elements Getter
	 * @return new elements getter
	 */
	ElementsGetter<T> createElementsGetter();
	
	/**
	 * Default method that puts the element to the test
	 * @param col collection from which elements are retrieved
	 * @param tester tester
	 */
	default void addAllSatisfying(Collection<T> col,Tester<? super T> tester) {
		ElementsGetter<T> getter = col.createElementsGetter();
		while(getter.hasNextElement()) {
			T elementToTest = getter.getNextElement();
			if(tester.test(elementToTest)) {
				add(elementToTest);
			}
		}
	}
	
}