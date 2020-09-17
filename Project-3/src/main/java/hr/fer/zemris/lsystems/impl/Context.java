package hr.fer.zemris.lsystems.impl;

import hr.fer.zemris.java.custom.collections.ObjectStack;

/**
 * 
 * Class that represents the memory of the 
 * active states and its' variables.
 * It can get the active state, 
 * add a new state or remove a 
 * state
 * 
 * @author Tomica
 *
 */

public class Context {

	/**
	 * Representation of memory unit
	 */
	private ObjectStack<TurtleState> stack;
	
	/**
	 * Constructor that constructs the memory unit
	 */
	public Context() {
		stack = new ObjectStack<>();
	}
	
	/**
	 * Used to retrieve current state
	 * @return current turtle state
	 */
	public TurtleState getCurrentState() {
		return stack.peek();
	}
	
	/**
	 * Used to add a new state
	 * @param state
	 */
	public void pushState(TurtleState state) {
		stack.push(state);
	}
	
	/**
	 * Used to remove the current state
	 */
	public void popState() {
		stack.pop();
	}
}
