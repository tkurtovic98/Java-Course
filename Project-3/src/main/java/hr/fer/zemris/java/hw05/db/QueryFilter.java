package hr.fer.zemris.java.hw05.db;

import java.util.List;

/**
 * Class used to filter query values for
 * a set of given conditions.
 * It return the filtered list of Student Records
 * @author Tomislav Kurtović
 *
 */
public class QueryFilter implements IFilter {

	/**
	 * List of conditional expressions
	 */
	private List<ConditionalExpression> expresions;
	
	/**
	 * Constructor of query filter
	 * @param expresions list of conditional expressions
	 */
	public QueryFilter(List<ConditionalExpression> expresions) {
		super();
		this.expresions = expresions;
	}

	/**
	 * Accepts a record if it satisfies ALL the conditions in 
	 * the list
	 */
	@Override
	public boolean accepts(StudentRecord record) {
		boolean accepts = false;
		for(ConditionalExpression ex : expresions) {
			accepts = ex.getComparisonOperator().satisfied(ex.getFieldValueGetter().get(record), ex.getLiteral());
			if(!accepts) return false;
		}
		return true;
	}
}
