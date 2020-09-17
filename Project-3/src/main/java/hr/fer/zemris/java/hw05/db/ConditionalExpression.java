package hr.fer.zemris.java.hw05.db;

/**
 * Class used to represent the types of 
 * comparison expressions used by the query
 * simulator 
 * 
 * @author Tomislav Kurtović
 *
 */
public class ConditionalExpression {

	/**
	 * Field value getter
	 */
	private IFieldValueGetter fieldValueGetter;
	/**
	 * literal of query field 
	 */
	private String literal;
	/**
	 * comparison operator of field 
	 */
	private IComparisonOperator comparisonOperator;
	
	/**
	 * Constructor that constructs the conditional expression 
	 * object 
	 * 
	 * @param fieldValueGetter field value getter
	 * @param literal literal
	 * @param comparisonOperator comparison operator
	 */
	public ConditionalExpression(IFieldValueGetter fieldValueGetter, String literal,
			IComparisonOperator comparisonOperator) {
		super();
		this.fieldValueGetter = fieldValueGetter;
		this.literal = literal;
		this.comparisonOperator = comparisonOperator;
	}

	/**
	 * Field value getter
	 * @return field value getter
	 */
	public IFieldValueGetter getFieldValueGetter() {
		return fieldValueGetter;
	}

	/**
	 * Literal getter
	 * @return literal
	 */
	public String getLiteral() {
		return literal;
	}

	/**
	 * comparison operator getter
	 * @return comparison operator getter
	 */
	public IComparisonOperator getComparisonOperator() {
		return comparisonOperator;
	}
}
