package hr.fer.zemris.java.hw05.db;

/**
 * Interface used to represent the types of 
 * field value getters used by the query
 * simulator 
 * 
 * @author Tomislav Kurtović
 *
 */
public interface IFieldValueGetter {
	/**
	 * Used to get information from a {@link StudentRecord} from the database
	 * with specified field names
	 * 
	 * @param record student record whoose info is to be retrieved
	 */
	public String get(StudentRecord record);
}
