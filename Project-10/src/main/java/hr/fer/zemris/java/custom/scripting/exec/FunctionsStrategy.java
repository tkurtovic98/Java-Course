package hr.fer.zemris.java.custom.scripting.exec;

/**
 * Interface that is used by various 
 * classes that use functions that 
 * can be used in one place witout
 * checking which one it is
 * @author Tomislav Kurtović
 *
 */
public interface FunctionsStrategy {
	/**
	 * Executes the given fuction given the 
	 * left and right object.
	 * Depending on wether the right object is null or not
	 * the fuction acts accordingly. 
	 * It can be called by unary or binary operations 
	 * @param left left argument 
	 * @param right right argument 
	 */
	public void executeFunction(Object left, Object right);
}
