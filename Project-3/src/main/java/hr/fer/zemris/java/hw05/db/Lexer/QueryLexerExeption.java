package hr.fer.zemris.java.hw05.db.Lexer;

/**
 * Representation of a QueryLexerException that is
 * thrown whenever the {@link QueryLexer} is given an
 * invalid argument
 * 
 * @author Tomislav Kurtović
 *
 */
public class QueryLexerExeption extends RuntimeException {
	private static final long serialVersionUID = 1L;

	/**
	 * Default constructor
	 */
	public QueryLexerExeption() {
		super();
	}

	/**
	 * Default constructor
	 */
	public QueryLexerExeption(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	/**
	 * Default constructor
	 */
	public QueryLexerExeption(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Default constructor
	 */
	public QueryLexerExeption(String message) {
		super(message);
	}

	/**
	 * Default constructor
	 */
	public QueryLexerExeption(Throwable cause) {
		super(cause);
	}
}
