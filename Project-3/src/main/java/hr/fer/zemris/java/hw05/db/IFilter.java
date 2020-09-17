package hr.fer.zemris.java.hw05.db;

/**
 * Interface used to filter out student records
 * from the database output which is printed to 
 * System.out
 * 
 * @author Tomislav Kurtović
 *
 */
public interface IFilter {

	/**
	 * Accepts the record if it satisfied
	 * given condition
	 * 
	 * @param record student record to check
	 * @return true if satisfies, false otherwise
	 */
	public boolean accepts (StudentRecord record);
}
