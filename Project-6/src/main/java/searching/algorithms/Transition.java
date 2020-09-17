package searching.algorithms;

/**
 * Class used to store information about
 * the next configurations of the next
 * states 
 * @author Tomislav Kurtović
 *
 * @param <S>
 */
public class Transition<S> {
	/**
	 * this states state
	 */
	private S state;
	/**
	 * Cost of this state
	 */
	private double cost;

	/**
	 * Returns the state
	 * @return state
	 */
	public S getState() {
		return state;
	}
    /**
     * Returns cost
     * @return
     */
	public double getCost() {
		return cost;
	}

	/**
	 * Default constructor
	 * @param state state
	 * @param cost cost
	 */
	public Transition(S state, double cost) {
		super();
		this.state = state;
		this.cost = cost;
	}

}
