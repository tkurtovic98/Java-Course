package hr.fer.zemris.java.hw03.prob1;

/**
 * Representation of the character groups
 * that the lexer evaluates.
 * It hold the type of the group and the
 * value of the same.
 * 
 * 
 * @author Tomislav Kurtović
 *
 */

public class Token {
	
	/**
	 * Type of token specified in TokenType
	 */
	private TokenType type;
	
	/**
	 * Value of token
	 */
	private Object value;
	
	/**
	 * Default constructor of the token 
	 * 
	 * @param type type of token
	 * @param value value of token
	 */ 
	public Token(TokenType type, Object value) {
		if(type == null) {
			throw new NullPointerException("Type cannot be null");
		}
		this.type = type;
		this.value = value;
	}
	
	public Object getValue() {
		return value;
	}
	
	public TokenType getType() {
		return type;
	}

}
