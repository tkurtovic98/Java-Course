package hr.fer.zemris.java.hw05.db;

/**
 * Interface used to represent the types of 
 * comparison operators used by the query
 * simulator 
 * 
 * @author Tomislav Kurtović
 *
 */
public interface IComparisonOperator {

	/**
	 * Used to check wheater a row from the database
	 * satisfies the given conditions entered in 
	 * the query
	 * @param value1 value of first string
	 * @param value2 value of second string
	 * @return true if satisfied, false otherwise
	 */
	public boolean satisfied(String value1, String value2);
}
