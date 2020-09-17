package hr.fer.zemris.java.hw05.db;


/**
 * Class used to represent the types of 
 * comparison operators used by the query
 * simulator 
 * 
 * @author Tomislav Kurtović
 *
 */
public class ComparisonOperators {

	/**
	 * Represents the less comparator
	 */
	public static final IComparisonOperator LESS = 
			(str1,str2) ->  str1.compareTo(str2) < 0;
	/**
	 * Represents the less or equals comparator
	 */
	public static final IComparisonOperator LESS_OR_EQUALS =
			(str1,str2) ->  str1.compareTo(str2) <= 0;
			
	/**	
	 * Represents the greater or equals comparator
	 */
	public static final IComparisonOperator GREATER =
			(str1,str2) ->  str1.compareTo(str2) > 0;
	/**	
	 * Represents the greater or equals comparator
	 */		
	public static final IComparisonOperator GREATER_OR_EQUALS = 
			(str1,str2) ->  str1.compareTo(str2) >= 0;
	/**	
	 * Represents the equals comparator
	 */
	public static final IComparisonOperator EQUALS =
			(str1,str2) ->  str1.compareTo(str2) == 0;
	/**	
	 * Represents the not equals comparator
	 */
	public static final IComparisonOperator NOT_EQUALS =
			(str1,str2) ->  str1.compareTo(str2) != 0;
	/**	
	 * Represents the like comparator
	 */	
	public static final IComparisonOperator LIKE =
			(str,pattern) -> {
				if(pattern.endsWith("*")) {
					return str.startsWith(pattern.substring(0, pattern.length()-1));
				}
				if(pattern.startsWith("*")) {
					return str.endsWith(pattern.substring(1, pattern.length()));
				}
				if(pattern.charAt(pattern.length()/2) == '*') {
					String start = pattern.substring(0, pattern.length()/2);
					String end = pattern.substring(pattern.length()/2 + 1,pattern.length());
					return str.substring(0,start.length()).equals(start) && 
							str.substring(str.length() - end.length(), str.length()).equals(end) &&
							str.length() >= start.length() + end.length();
				}
				return str.contains(pattern);
			};
}
