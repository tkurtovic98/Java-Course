package hr.fer.zemris.java.custom.scripting.exec;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Class that represents a stack-like collection,
 * but is not a stack.
 * @author Tomislav Kurtović
 *
 */
public class ObjectMultistack {
	/**
	 * used to store key values and values 
	 * of this class 
	 */
	private Map<String, MultiStackEntry> map;
	
	/**
	 * Constructor that instantiates the map
	 */
	public ObjectMultistack() {
		map = new HashMap<>();
	}
	
	/**
	 * Stores new key - value pairs in this class.
	 * If either keyName or valueWrapper are null, 
	 * a {@link NullPointerException} will be thrown.
	 * 
	 * @param keyName key of value to store, non-null
	 * @param valueWrapper value to store, non-null
	 */
	public void push(String keyName, ValueWrapper valueWrapper) {
		Objects.requireNonNull(keyName);
		Objects.requireNonNull(valueWrapper);
		if(map.size() != 0) {
			MultiStackEntry oldEntry = map.get(keyName);
			map.put(keyName, new MultiStackEntry(valueWrapper, oldEntry));
			return;
		}
		map.put(keyName, new MultiStackEntry(valueWrapper, null));
	}
	
	/**
	 * Removes the element from the collection
	 * with the given key
	 * @param keyName key of value to remove
	 * @return removed value
	 */
	public ValueWrapper pop(String keyName) {
		ValueWrapper wrapper = peek(keyName);
		map.put(keyName, map.get(keyName).next);
		return wrapper;
	} 
	
	/**
	 * Gets top value from the collection with given
	 * key
	 * @param keyName key of value to retrieve
	 * @return top element with given key
	 */
	public ValueWrapper peek(String keyName) {
		emptyCheck(keyName);
		return map.get(keyName).wrapper;
	}
	
	/**
	 * Checks if the collection is empty and 
	 * throws exception if is
	 * @param keyName key of slot to check
	 */
	private void emptyCheck(String keyName) {
		if(isEmpty(keyName)) {
			throw new UnsupportedOperationException("Cannot perform operation on empty stack!");
		}
	}

	/**
	 * Checks whether the collection we want to 
	 * modify is empty or not. 
	 * 
	 * @param keyName key of slot to check
	 * @return true if slot does not exist, false otherwise
	 */
	public boolean isEmpty(String keyName) {
		return !map.containsKey(keyName) || map.get(keyName) == null ;
	}
	
	/**
	 * Nested class that is used to store values that 
	 * are put into the {@link ObjectMultistack}
	 * It is used to form a list representation
	 * @author Tomislav Kurtović
	 *
	 */
	private static class MultiStackEntry {
		/**
		 * value of the entry
		 */
		private ValueWrapper wrapper;
		/**
		 * Next element in list
		 */
		private MultiStackEntry next;
		
		/**
		 * Constructor that instantiates the {@link MultiStackEntry}
		 * with the given wrapper and its neighboring element in
		 * the {@link ObjectMultistack} slot
		 * @param wrapper value
		 * @param next next element
		 */
		private MultiStackEntry(ValueWrapper wrapper, MultiStackEntry next) {
			this.wrapper = wrapper;
			this.next = next;
		}
	}
}
