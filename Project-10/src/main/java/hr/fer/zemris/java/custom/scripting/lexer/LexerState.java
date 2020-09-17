package hr.fer.zemris.java.custom.scripting.lexer;

/**
 * State in which the lexer can 
 * be in
 *
 * @author Tomislav Kurtović
 *
 */

public enum LexerState {
	/**
	 * state outside of tags
	 * 
	 */
	TEXT,
	/**
	 * State in tags
	 * 
	 */
	TAG
}
