package hr.fer.zemris.java.custom.scripting.nodes;

import hr.fer.zemris.java.custom.collections.ArrayIndexedCollection;

/**
 * Class that represents node used in the
 * construction of parser document.
 * It has 3 methods used to store and connect
 * different nodes together
 * @author Tomica
 *
 */
public class Node {

	private ArrayIndexedCollection collection;
	
	/**
	 * Constructor 
	 */
	public Node() {
		collection = null;
	}
	/**
	 * Adds node as a child of this node
	 * @param child node to be added
	 */
	public void addChildNode(Node child) {
		if(collection == null) {
			collection  = new ArrayIndexedCollection();
		}
		collection.add(child);
	}
	
	/**
	 * Number of active children
	 * @return Number of active children
	 */
	public int numberOfChilder() {
		return collection.size();
	}
	
	/**
	 * Gets child from index
	 * @param index position from which to retrieve the child
	 * @return child at given position
	 */
	public Node getChild(int index) {
		return (Node)collection.get(index);
	}
}