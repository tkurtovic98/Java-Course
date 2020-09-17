package hr.fer.zemris.java.hw05.db;

/**
 *  Representation of a QueryParserException 
 *  that is thrown whenever the Queryparser is given an
 * invalid argument
 * 
 * @author Tomislav Kurtović
 *
 */
public class QueryParserException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	/**
	 * Default constructor
	 */
	public QueryParserException() {
		super();
	}

	/**
	 * Constructor that recieves a message and a cause 
	 * @param message message of exeption
	 * @param cause cause
	 */
	public QueryParserException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Constructor that recieves a message 
	 * @param message message of exeption
	 */
	public QueryParserException(String message) {
		super(message);
	}

	/**
	 * Constructor that recieves  a cause 
	 * @param cause cause
	 */
	public QueryParserException(Throwable cause) {
		super(cause);
	}
}
