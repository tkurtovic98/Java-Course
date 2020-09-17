package hr.fer.zemris.java.hw05.db.Lexer;

import java.util.Objects;

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
	 * Type of token specified in {@link TokenType}
	 */
	private TokenType type;
	/**
	 * Value of token
	 */
	private String value;
	
	/**
	 * Default constructor of the token 
	 * 
	 * @param type type of token
	 * @param value value of token
	 */ 
	
	public Token(TokenType type, String value) {
		super();
		this.type = Objects.requireNonNull(type);
		this.value = value;
	}

	public TokenType getType() {
		return type;
	}

	public String getValue() {
		return value;
	}
	
}
