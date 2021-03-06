package hr.fer.zemris.java.custom.scripting.lexer;

import hr.fer.zemris.java.hw03.prob1.LexerException;

/**
 * 
 * Class represents a lexer used for character grouping.
 * This lexer is used by the SmartScript parser.
 * It has two states, TEXT AND TAG that dictate the
 * rules by which the lexer operates.
 * It generates tokens that represent different character groups
 * More on token types can be seen in the TokenType enum
 * 
 * @author Tomislav Kurtović
 *
 */


public class Lexer {

	private static char[] operations = {'+', '-','*','/','^'};
	
	/**
	 * Array of characters in which the 
	 * given text is transformed to
	 * 
	 */
	private char[] data;
	
	/**
	 * Current position at which the
	 * lexer is located in the data array
	 */
	private int currentIndex;
	
	/**
	 * Object that is generated by
	 * the lexer and represents 
	 * a character group;
	 * 
	 */
	private Token token;
	/**
	 * State in which the lexer can be.
	 * TEXT or TAG
	 * 
	 */
	private LexerState state;
	/**
	 * Used in tag evaluation to check if its the beginning of a tag
	 */
	private boolean beginningOfTag= true;
	/**
	 * Used to check if tag is empty
	 * 
	 */
	private int tagCounter = 0;
	
	/**
	 * Default constructor that creates the data array 
	 * from the passed document and sets the state to TEXT
	 * @param documentText text used by lexer
	 */
	
	public Lexer(String documentText) {
		documentText.trim();
		data = documentText.toCharArray();
		currentIndex = 0;
		state = LexerState.TEXT;
	}
	
	/**
	 * Evaluates token from data array and returns it
	 * 
	 * @return evaluated token
	 */
	public Token nextToken() { 
		evaluateToken();
		return getToken();
	}
	/**
	 * 
	 * Returns the current token
	 * 
	 * @return current token
	 */
	public Token getToken() {
		return token;
	}
	/**
	 * Sets the state 
	 * 
	 * @param state state to set
	 */
	public void setState(LexerState state) {
		this.state = state;
	}
	
	/**
	 * 
	 * Used for evaluating tokens.
	 * Its main purpose is to check the
	 * lexer state and call the appropriate 
	 * method.
	 * 
	 */
	private void evaluateToken() {
		if(token != null && token.getType().equals(TokenType.EOF)) {
			throw new LexerException("You have already reached the end of the string");
		}
		
		if(data.length == 0 || currentIndex >= data.length-1) {
			token = new Token(TokenType.EOF,null);
			return ;
		}
		
		while(isBlank()) {
			currentIndex++;
			if(currentIndex > data.length -1) {
				return;
			}
		}
		
		if(state == LexerState.TEXT) {
			tokenizeText();
		}else if (state == LexerState.TAG) {
			tokenizeTag();
		}
	}

	
	private boolean isBlank() {
		char currentChar = data[currentIndex];
		
		if(currentChar == '\r'  || currentChar == '\n' || currentChar == '\t' || currentChar == ' ' ) {
			return true;
		}
		return false;
	}

	/**
	 * Used for evaluating tokens in TEXT state
	 * The method makes a string until it gets to
	 * an open tag , when it sets the active 
	 * token  
	 */
	private void tokenizeText() {
		StringBuilder builder = new StringBuilder();		
		
		while(true) {
			
			if(data[currentIndex] == '{') {
				currentIndex++;
				if(data[currentIndex] == '$') {
					if(builder.length() == 0) {
						token = new Token(TokenType.BEGINOFTAG,"{$");
						currentIndex++;
						return;
					}
					currentIndex--;
					break;
				}	
				currentIndex--;
			}
			
			if(isEscape()) {
				currentIndex++;
				checkEscapeValid();
			}
			
			
			builder.append(data[currentIndex]);
			currentIndex++;
			
			if(currentIndex == data.length) break;
		}

		token = new Token(TokenType.TEXT,builder.toString());
	}
	
	/**
	 * Throws exception if character after escape is not valid
	 * 
	 */
	private void checkEscapeValid() {
		
		if(data[currentIndex] == '{' || data[currentIndex] == '\\') {
			return;
		}
		throw new LexerException();
	}

	/**
	 * Checks if character is escape
	 * @return
	 */
	private boolean isEscape() {
		if(data[currentIndex] == '\\') {
			return true;
		}
		return false;
	}
	
	/**
	 * Evaluates token inside the tag.
	 * The first time it is called after the opening tag it
	 * evaluates a tag name.
	 * Afterwards, until it gets to the closing tag, it evaluates different
	 * Token types
	 */
	private void tokenizeTag() {
		
		//Checking for tags -- will only enter at beginning of tag
		if(beginningOfTag) {
			beginningOfTag = false;
			if(Character.isLetter(data[currentIndex])) {
				String tagString = getVariableName();
				token = new Token(TokenType.TAGNAME,tagString);
				tagCounter++;
				return;
			}
			if(data[currentIndex] == '=') {
				String value = "=";
				token = new Token(TokenType.TAGNAME,value);
				tagCounter++;
				currentIndex++;
				return;
			}
			token = new Token(TokenType.UNKNOWN,null);
			return;
		}
		
		//Valid variable name
		if(Character.isLetter(data[currentIndex])) {
			token = new Token(TokenType.VARIABLE,getVariableName());
			return;
		}
		
		//Valid functions 
		if(data[currentIndex] == '@') {
			currentIndex++;
			if(!Character.isLetter(data[currentIndex])) {
				token = new Token(TokenType.UNKNOWN,null);
				return;
			}
			token = new Token(TokenType.FUNCTION, getVariableName());
			return;
		}
		
		//Valid operations
		if(isOperation()) {
			//If after - is a digit then treat it like a negative number
			if(data[currentIndex] == '-') {
				currentIndex++;
				if(Character.isDigit(data[currentIndex]) ) {
					StringBuilder builder = new StringBuilder();
					builder.append('-');
					builder.append(getNumber());
					token = new Token(TokenType.NUMBER,builder.toString());
					return;
				}
				currentIndex--;
			}
			token = new Token(TokenType.OPERATOR,String.valueOf(data[currentIndex]));
			currentIndex++;
			return;
		}
		
		//Valid number
		if(Character.isDigit(data[currentIndex])) {
			String number = getNumber();
			token = new Token(TokenType.NUMBER,number);
			return;
		}
		
		//If its string
		if(data[currentIndex] == '\"') {
			StringBuilder builder = new StringBuilder();
			builder.append("\"");
			currentIndex++;
			while(!(data[currentIndex] == '\"')) {
				if(isEscape()) {
					builder.append(isTagEscapeValid());
				}else {
				builder.append(data[currentIndex]);
				}
				currentIndex++;
			}
			builder.append("\"");
			token = new Token(TokenType.STRING,builder.toString());
			currentIndex++;
			return;
		}
		
	    //End of tag
		if(data[currentIndex] == '$') {
			if(tagCounter == 0) {
				token = new Token(TokenType.TEXT,"");
				return;
			}
			currentIndex++;
			if(!(data[currentIndex] == '}')) {
				throw new LexerException("Closing tag not valid");
			} 
			token = new Token(TokenType.ENDOFTAG,"$}");
			beginningOfTag = true;
			currentIndex++;
			return;
		}

	}
	
	/**
	 * Checks if escape tag is valid for tab ,new line and backslash
	 * @return
	 */
	private String isTagEscapeValid() {
			currentIndex++;
			if(data[currentIndex] == '\\') {
				return "\\";
			}
			if(data[currentIndex] == 'n') {
				return "\n";			
			}
			if(data[currentIndex] == 'r') {
				return "\r";
			}
			if(data[currentIndex] == 't') {
				return "\t";
			}
			throw new LexerException();
	}

	/**
	 * Used to get number token.
	 * The value can be an Integer or Double
	 * @return string representation of number
	 */
	private String getNumber() {
		StringBuilder builder = new StringBuilder();
		while(Character.isDigit(data[currentIndex]) || data[currentIndex] == '.') {
			if(data[currentIndex] == '.') {
				builder.append(".");
				currentIndex++;
				if(!Character.isDigit(data[currentIndex])) {
					builder.deleteCharAt(builder.length());
					currentIndex--;
					break;
				}
			}
			builder.append(data[currentIndex]);
			currentIndex++;
		}
		
		return builder.toString();
	}

	/**
	 * Gets name of valid variable.
	 * Valid variable characters are letters,numbers and underscore
	 * @return string representation of variable name
	 */
	private String getVariableName() {
		StringBuilder builder = new StringBuilder();
		while(Character.isLetter(data[currentIndex]) || Character.isDigit(data[currentIndex])|| data[currentIndex] == '_') {
				builder.append(data[currentIndex]);
				currentIndex++;
			}
		return builder.toString();
	}

	/**
	 * Checks if character is operator
	 * @return true if it is operator, false otherwise
	 */
	private boolean isOperation() {
		for(int i = 0; i<operations.length; i++) {
			if(data[currentIndex] == operations[i]) return true;
		}
		return false;
	}
}
