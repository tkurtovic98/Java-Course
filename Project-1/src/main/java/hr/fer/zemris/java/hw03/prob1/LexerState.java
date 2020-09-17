package hr.fer.zemris.java.hw03.prob1;


/**
 * State in which the lexer can 
 * be in
 *
 * @author Tomislav Kurtović
 *
 */
public enum LexerState {
	/**
	 * default state
	 * 
	 */
	BASIC,
	
	/**
	 * End user can change the state to extended
	 * 
	 */
	EXTENDED
}
