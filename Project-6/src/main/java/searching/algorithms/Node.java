package searching.algorithms;

/**
 * Class used to store information about the
 * state and cost of a move in a form
 * of a Node
 * @author Tomislav Kurtović
 *
 * @param <S>
 */
public class Node<S> {
	/**
	 * Parent of this node
	 */
	private Node<S> parent;
	/**
	 * Cost of node, number of moves
	 */
	private double cost;
	/**
	 * Configuration of this node or state
	 */
	private S state;

	/**
	 * Default constructor
	 * @param parent parent of node
	 * @param cost cost of move
	 * @param state state 
	 */
	public Node(Node<S> parent, double cost, S state) {
		super();
		this.parent = parent;
		this.cost = cost;
		this.state = state;
	}

	/**
	 * Returns the state
	 * @return state
	 */
	public S getState() {
		return state;
	}

	/**
	 * Returns the cost
	 * @return cost
	 */
	public double getCost() {
		return cost;
	}

	/**
	 * Returns the parent
	 * @return parent
	 */
	public Node<S> getParent() {
		return parent;
	}
}
