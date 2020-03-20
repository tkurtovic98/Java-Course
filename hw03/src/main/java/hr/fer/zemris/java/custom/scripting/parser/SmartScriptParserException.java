package hr.fer.zemris.java.custom.scripting.parser;

/**
 *  Representation of a SmartScriptParserException 
 *  that is thrown whenever the parser is given an
 * invalid argument
 * 
 * @author Tomislav Kurtović
 *
 */
public class SmartScriptParserException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	/**
	 * Default constructor
	 */
	public SmartScriptParserException() {
		super();
	}
	
	public SmartScriptParserException(String message) {
		super(message);
	}
}
