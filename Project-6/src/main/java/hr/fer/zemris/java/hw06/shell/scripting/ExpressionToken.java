package hr.fer.zemris.java.hw06.shell.scripting;

/**
 * Class that represents expression token used by 
 * {@link CommandsLexer}
 * @author Tomislav Kurtović
 *
 */
public class ExpressionToken {

	/**
	 * Value of token
	 */
	private String value;
	/**
	 * {@link ExpressionTokenType};
	 */
	private ExpressionTokenType type;
	
	/**
	 * Default constructor
	 * @param value value of token
	 * @param type type 
	 */
	public ExpressionToken(String value, ExpressionTokenType type) {
		this.value = value;
		this.type = type;
	}
	/**
	 * Returns value of token
	 * @return value
	 */
	public String getValue() {
		return value;
	}
	/**
	 * Returns type of token
	 * @return type
	 */
	public ExpressionTokenType getType() {
		return type;
	}
}
