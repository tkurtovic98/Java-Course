package hr.fer.zemris.java.custom.scripting.lexer;

/**
 * Representation of a LexerException that is
 * thrown whenever the lexer is given an
 * invalid argument
 * 
 * @author Tomislav Kurtović
 *
 */
public class LexerException extends RuntimeException {

	
	private static final long serialVersionUID = 1L;
	
	/**
	 * Default constructor
	 */
	public LexerException() {
		super();
	}
	
	/**
	 * Constructor with message
	 * @param message
	 */
	public LexerException (String message) {
		super(message);
	}

}
