package hr.fer.zemris.java.hw06.shell.scripting;

/**
 * Class that is a representation of a lexer used by 
 * the {@link} NameBuilderParser in order to group the 
 * arguments and expressions into their categories 
 * @author Tomislav Kurtović
 *
 */
public class CommandsLexer {
	/**
	 * Array of chars 
	 */
	private char[] data ;
	/**
	 * Current indexx
	 */
	private int index ;
	
	/**
	 * default constructor
	 * @param arguments
	 */
	public CommandsLexer(String arguments) {
		data = arguments.trim().toCharArray();
		index = 0;
	}

	/**
	 * Gets all the values for the arguments of the 
	 * command
	 * @return
	 */
	public String getValueOfArg() {
		if(index > data.length -1) return null;
		
		while(isBlank()) {
			index ++;
		}
		
		if(data[index] == '\"') {
			index++;
			StringBuilder builder = new StringBuilder();
			while(index < data.length-1 && data[index] != '\"') {
				if(data[index] == '\\' && isEscape(data, index)) {						
					index++;
				}
				builder.append(data[index]);
				index++;
			}
			return builder.toString();
		}
		
		if(Character.isLetter(data[index])){		
			return textValue();
		}
		
		throw new IllegalArgumentException("Illegal arguments found!");
	}
	
	/**
	 * Returns text value
	 * @return
	 */
	private String textValue() {
		StringBuilder builder = new StringBuilder();
		while(index < data.length && !isBlank()) {
			builder.append(data[index++]);
		}
		return builder.toString();
	}
	
	/**
	 * Returns values of expression in form
	 * of a {@link ExpressionToken}
	 * @return
	 */
	public ExpressionToken getValueOfExpression() {
		if(index > data.length-1) return null;
		
		while(isBlank()) {
			index++;
		}
		
		if(data[index] != '$'){	
			StringBuilder builder = new StringBuilder();
			while(index < data.length && data[index] != '$') {
				builder.append(data[index++]);
			}
			return new ExpressionToken(builder.toString(),ExpressionTokenType.TEXT);
		}
		
		if(data[index] == '$') {
			if(data[index + 1] != '{') {
				throw new IllegalArgumentException("Expression is invalid!");
			}
			index+=2;
			StringBuilder builder = new StringBuilder();
			
			while(data[index] != '}') {
				builder.append(data[index++]);
			}
			index++;
			return new ExpressionToken(builder.toString(), ExpressionTokenType.GROUP);
		}
		throw new IllegalArgumentException("Invalid arguments found in expression!");
	}
	
	/**
	 * Checks if current character is blank
	 * @return
	 */
	private boolean isBlank() {
		char currentChar = data[index];
		if (currentChar == '\r' || currentChar == '\n' || currentChar == '\t' || currentChar == ' ') {
			return true;
		}
		return false;
	}

	/**
	 * Checks if escape sequence is valid
	 * @param quote char array
	 * @param index current index
	 * @return true if is, false otherwise
	 */
	private static boolean isEscape(char[] quote, int index) {
		if(quote[index + 1] == '\\' || quote[index + 1] == '\"') {
			return true;
		}
		return false;
	}
}
