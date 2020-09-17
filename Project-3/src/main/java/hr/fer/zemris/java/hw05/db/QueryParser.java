package hr.fer.zemris.java.hw05.db;

import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.hw05.db.Lexer.QueryLexer;
import hr.fer.zemris.java.hw05.db.Lexer.QueryLexerExeption;
import hr.fer.zemris.java.hw05.db.Lexer.TokenType;

/**
 * Used to parse query using a {@link Querylexer} to group
 * the characters by various types.
 * It is used to then determine what student records should be 
 * printed out or not
 * More about the elements can be found in their
 * respective classes
 * 
 * @author Tomislav Kurtović
 *
 */
public class QueryParser {
	
	/**
	 * Query to parse
	 */
	private String query;
	/**
	 * lexer of the parser
	 */
	private QueryLexer lexer;
	/**
	 * List of conditional expressions
	 *
	 */
	private List<ConditionalExpression> conditionalExpressions;
	
	/**
	 * Used for checking if query is direct
	 */
	private boolean isJmbag = false;
	
	/**
	 * Constructor that recieves a query and starts parsing 
	 * by generating tokens and evaluating each one
	 * 
	 * @param query query to parse
	 */
	public QueryParser(String query) {
		if(query.isBlank()) {
			throw new NullPointerException("Query cannot be blank!");
		}
		
		this.query = query.replace("query", "");
		lexer = new QueryLexer(this.query);
		conditionalExpressions = new ArrayList<>();
		try {
			parse();
		} catch(QueryLexerExeption e) {
			throw new QueryParserException(e.getMessage());
		}
	}

	/**
	 * Checks if query is direct
	 */
	public boolean isDirectQuery() {
		return isJmbag && conditionalExpressions.size() == 1 && 
				conditionalExpressions.get(0).getComparisonOperator().equals(ComparisonOperators.EQUALS);
	}
	
	/**
	 * Gets jmbag if query is direct
	 * @return jmbag
	 */
	public String getQueriedJMBAG() {
		if(!isDirectQuery()) {
			throw new QueryParserException("Query was not a direct Query");
		}
		return conditionalExpressions.get(0).getLiteral();
	}

	/**
	 * Returns the list of the conditional expressions used
	 * in filtering of query values
	 * @return list of conditional expressions
	 */
	public List<ConditionalExpression>  getQuery(){
		return conditionalExpressions;
	}
	
	/**
	 * 
	 * Method that is responsible for getting the tokens
	 * from the QueryLexer that generates them.
	 * When it finds the appropriate token type the 
	 * method calls other methods who then do the
	 * task assigned.
	 * If an error occurs during the parsing process an
	 * {@link QueryParserException} is thrown
	 * It does not return anything, it only puts all the conditional expresion
	 * in the conditional expression list
	 * 
	 */
	private void parse() {
		IFieldValueGetter fieldValue = null;
		String literal = null;
		IComparisonOperator comparisons = null;
		String comparisonValue="";
		int counter = 0;
		
		lexer.nextToken();
		
		while(true) {
			
			try {
				if(counter == 3) {
					throw new QueryParserException("Illegal query syntax, no and found after literal!");
				}
				
				if(equalsTokenType(TokenType.FIELD) ) {
					fieldValue = evaluateField();
					counter++;
					lexer.nextToken();
				}
				
				if(equalsTokenType(TokenType.OPERATOR)) {
					if(fieldValue == null || "AND".equals(lexer.getToken().getValue().toUpperCase())) {
						throw new QueryParserException("Invalid input!");
					}
					comparisons = evaluateComparison();
					comparisonValue = lexer.getToken().getValue();
					counter++;
					lexer.nextToken();
				} else {
					throw new QueryParserException("Invalid operation!");
				}
				
				if(equalsTokenType(TokenType.STRING)) {	
					if(comparisonValue == null) {
						throw new QueryParserException("Invalid input!");
					}
					literal = evaluateLiteral(comparisonValue);
					counter++;
					lexer.nextToken();
				}
				
				if(equalsTokenType(TokenType.EOF)){
					conditionalExpressions.add(new ConditionalExpression(
							fieldValue,
							literal,
							comparisons));
					break;
				}
				
				if("AND".equals(lexer.getToken().getValue().toUpperCase())) {
					counter = 0;
					conditionalExpressions.add(new ConditionalExpression(
							fieldValue,
							literal,
							comparisons));
					lexer.nextToken();
				}
			}catch(QueryLexerExeption e){
				throw new QueryParserException(e.getMessage());
			}
		}
	}
	
	/**
	 * Checks if lexer token is of certain type
	 * @param type type of token to check
	 * @return true if is of type, false otherwise
	 */
	private boolean equalsTokenType(TokenType type) {
		return lexer.getToken().getType().equals(type);
	}	
	
	/**
	 * Evaluates the field used by the filters
	 * @return field value getter
	 */
	private IFieldValueGetter evaluateField() {
		String valueOfField = lexer.getToken().getValue();
		
		switch(valueOfField) {
			case "jmbag":
				isJmbag = true;
				return (IFieldValueGetter) FieldValueGetters.JMBAG;
			case "lastName":
				return (IFieldValueGetter) FieldValueGetters.LAST_NAME;
			case "firstName":
				return (IFieldValueGetter) FieldValueGetters.FIRST_NAME;
			default:
				throw new QueryParserException("Invalid field");
		}
	}
	
	/**
	 * Evaluates the comparison operators used by the filters
	 * @return field value getter
	 */
	private IComparisonOperator evaluateComparison() {
		String valueOfComparison = lexer.getToken().getValue();
		
		switch(valueOfComparison) {
			case">":
				return (IComparisonOperator) ComparisonOperators.GREATER;
			case ">=":
				return (IComparisonOperator) ComparisonOperators.GREATER_OR_EQUALS;
			case "<":
				return (IComparisonOperator) ComparisonOperators.LESS;
			case "<=":
				return (IComparisonOperator) ComparisonOperators.LESS_OR_EQUALS;
			case "=":
				return (IComparisonOperator) ComparisonOperators.EQUALS;
			case "!=":
				return (IComparisonOperator) ComparisonOperators.NOT_EQUALS;
			case "LIKE":
				return (IComparisonOperator) ComparisonOperators.LIKE;
			default:
				throw new QueryParserException("Invalid comparison operator!");
		}
	}

	/**
	 * Evaluates the string literal used by the filters
	 * @return field value getter
	 */
	private String evaluateLiteral(String comparisonValue) {
		if(comparisonValue == null) {
			throw new QueryParserException("Comparison was not found!");
		}
		String literal = lexer.getToken().getValue();
		
		if("LIKE".equals(comparisonValue)) {
			if(numberOfWildCards(literal) >1) {
				throw new QueryParserException("Number of * symbols is invalid");
			}
		} else {
			if(numberOfWildCards(literal) != 0) {
				throw new QueryParserException("Number of * symbols is invalid");
			}
		}
				
		return literal;
	}
	
	/**
	 * Checks number of wildcards in LIKE operator
	 * @param literal pattern in LIKE operator
	 * @return number of * symbols
	 */
	private int numberOfWildCards(String literal) {
		return literal.length() - literal.replace("*", "").length();
	}
}
