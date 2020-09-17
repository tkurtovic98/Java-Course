package hr.fer.zemris.java.hw05.db.Lexer;

import java.util.Arrays;
import java.util.List;

import hr.fer.zemris.java.hw05.db.QueryParser;

/**
 * 
 * Class represents a lexer used for character grouping.
 * This lexer is used by the {@link QueryParser}.
 * It operates in one state.
 * It generates tokens that represent different character groups
 * More on token types can be seen in the {@link TokenType} 
 * 
 * @author Tomislav Kurtović
 *
 */

public class QueryLexer {

	/**
	 * Constant list to check if the group of fields is known
	 */
	private static final List<String> FIELDS = Arrays.asList("lastName", "firstName", "jmbag");
	/**
	 * Constant list to check if the group of string operators is known
	 */
	private static final List<String> STRING_OPERATORS = Arrays.asList("LIKE", "AND");
	/**
	 * Constant list to check if the group of symbol is known
	 */
	private static final List<String> SYMBOL_OPERATORS = Arrays.asList(">", "<", "=");
	
	/**
	 * Used to store characters of the input
	 */
	private char[] data;
	
	/**
	 * Representation of {@link Token}
	 */
	private Token token;
	
	/**
	 * Index of current character
	 */
	private int currentIndex;
	
	/**
	 * Constructs a new query lexer given the
	 * query 
	 * 
	 * @param query query to evaluate
	 */
	public QueryLexer(String query) {
		data = query.trim().toCharArray();
	}
	
	/**
	 * Retrieves the current token
	 * @return current token
	 */
	public Token getToken() {
		return token;
	}
	
	/**
	 *  gets next token
	 *  @return extracted next token
	 */
	public Token nextToken() {
		extractToken();
		return getToken();
	}
	
	/**
	 * Used to extract tokens and send them
	 * to the parser for further checking
	 * It goes through the input to find a known character
	 * group which could then be retrieved
	 */
	private void extractToken() {
		if(currentIndex > data.length - 1 && token.getType().equals(TokenType.EOF)) {
			throw new UnsupportedOperationException("End of query reached");
		}
		
		if(currentIndex > data.length - 1) {
			token = new Token(TokenType.EOF,null);
			return;
		}
		
		while(isBlank()) {
			currentIndex++;
		}
		
		if(Character.isLetter(data[currentIndex])) {
			getFieldNameOrOperator();
			return;
		}
		
		if(data[currentIndex] == '\"') {
			currentIndex++;
			getStringValue();
			return;
		}
		
		if(currentIndex != data.length -1  && data[currentIndex] == '!') {
			if(data[currentIndex + 1] == '=') {
				currentIndex+=2;
				token = new Token(TokenType.OPERATOR, "!=");
				return;
			}
			throw new QueryLexerExeption("Invalid operator found!");
		}
		
		if(currentIndex != data.length - 1  && SYMBOL_OPERATORS.contains(String.valueOf(data[currentIndex]))) {
			StringBuilder builder = new StringBuilder();
			if(SYMBOL_OPERATORS.contains(String.valueOf(data[currentIndex +1]))) {
				builder.append(data[currentIndex]).append(data[currentIndex + 1]);
				currentIndex += 2;
			} else {
				builder.append(data[currentIndex]);
				currentIndex++;
			}
			
			token = new Token(TokenType.OPERATOR, builder.toString());
			return;
		}
	}

	/**
	 * Helping method used to get field name or operator values and character groups
	 */
	private void getFieldNameOrOperator()  {
		StringBuilder builder = new StringBuilder();
		
		while(currentIndex < data.length && Character.isLetter(data[currentIndex])) {
			builder.append(data[currentIndex++]);
		}
		
		if(FIELDS.contains(builder.toString())) {
			token = new Token(TokenType.FIELD, builder.toString());
			return;
		}
		if(STRING_OPERATORS.contains(builder.toString().toUpperCase())) {
			token = new Token(TokenType.OPERATOR, builder.toString());
			return;
		}
		
		throw new QueryLexerExeption("Query has invalid input");
	}
	
	/**
	 * Helping method used to get sting value and character group
	 */
	private void getStringValue() {
		StringBuilder builder = new StringBuilder();
		
		while(currentIndex < data.length && data[currentIndex] != '\"') {
			builder.append(data[currentIndex++]);
		}
		currentIndex++;
		token = new Token(TokenType.STRING,builder.toString());
		return;
	}

	/**
	 * Checks if current character is tab or whitespace
	 * @return true if character is tab or whitespace, false otherwise
	 */
	private boolean isBlank() {
		if(data[currentIndex] == '\t' || data[currentIndex] == ' ') 
			return true;
		return false;
	}
}
