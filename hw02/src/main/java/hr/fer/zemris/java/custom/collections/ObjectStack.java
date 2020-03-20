package hr.fer.zemris.java.custom.collections;

import java.util.Objects;


/**
 * The class represents an implementation
 * of a normal stack.
 * It contains all the methods that a stack
 * has such as push, pop etc.
 * 
 * @author Tomislav Kurtović
 *
 */
public class ObjectStack {

	private ArrayIndexedCollection instance;
	
	/**
	 * Constructor that initiates new instance of
	 * the ArrayIndexedCollecion class.
	 * The methods from this class are used here
	 */
	public ObjectStack() {
		instance = new ArrayIndexedCollection();
	}
	
	/**
	 * Checks if the stack is empty or not
	 * @return true if stack is empty, false otherwise
	 */
	
	public boolean isEmpty() {
		return instance.isEmpty();
	}
	
	/**
	 * Returns the size of the stack
	 * @return size of stack
	 */
	public int size() {
		return instance.size();
	}
	
	/**
	 * Pushes non null values onto the stack
	 * @param value object to be pushed onto stack
	 */
	public void push(Object value) {
		Objects.requireNonNull(value);
		instance.add(value);		
	}
	
	/**
	 * Retrieves the top object from the stack and
	 * removes it from the stack.
	 * @return object from the top of the stack.
	 */
	public Object pop() {
		checkIfEmpty();
		Object topElement = instance.get(size()-1);
		instance.remove(size()-1);
		return topElement;
	}
	
	/**
	 * Retrieves the object from the top
	 * of the stack, but does not remove it.
	 * 
	 * @return
	 */
	public Object peek() {
		checkIfEmpty();
		return instance.get(size()-1);
	}
	
	/**
	 * Clears stack from all elements 
	 */
	public void clear() {
		instance.clear();
	}
	
	/**
	 * Checks if the stack is empty and throws exception
	 * @throws EmptyStackException
	 */
	private void checkIfEmpty() {
		if(instance.isEmpty()) {
			throw new EmptyStackException("Stack is empty!");
		}
		
	}
	
}
