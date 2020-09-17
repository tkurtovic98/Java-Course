package hr.fer.zemris.java.hw05.db.Lexer;

/**
 * Different Token Types
 * @author TomislavKurtović
 *
 */
public enum TokenType {
	/**
	 * Field token type
	 */
	FIELD,
	/**
	 * Operator token type
	 */
	OPERATOR,
	/**
	 * String token type
	 */
	STRING,
	/**
	 * End of file token type
	 */
	EOF
}
