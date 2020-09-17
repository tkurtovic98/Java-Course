package hr.fer.zemris.java.hw05.db;

/**
 * Class used to represent the types of 
 * field value getters used by the query
 * simulator 
 * 
 * @author Tomislav Kurtović
 *
 */
public class FieldValueGetters {

	/**
	 * Field value getter for first name
	 */
	public static final IFieldValueGetter FIRST_NAME =
			record -> record.getFirstName();
	/**
	 * Field value getter for first name
	 */
	public static final IFieldValueGetter LAST_NAME =
			record -> record.getLastName();
	/**
	 * Field value getter for first name
	 */
	public static final IFieldValueGetter JMBAG =
			record -> record.getJmbag();
}
