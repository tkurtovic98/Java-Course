package hr.fer.zemris.java.hw07.demo3.scripting.strategies;

/**
 * Implementation of the {@link ArithmeticStrategy} used 
 * to perform a operation on Strings
 * @author Tomislav Kurtović
 *
 */
public class StringValue implements ArithmeticStrategy {

	/**
	 * It check if given argument is a 
	 * decimal or not and then returns the
	 * appropriate value
	 */
	@Override
	public Number execute(Object value,Object argument,String operation) {
		String literal = (String) argument;
		
		try{
			if(literal.contains(".") || literal.contains("E")) {
				return Double.parseDouble(literal);
		}
			return Integer.parseInt(literal);
		}catch(NumberFormatException ex) {
			throw new RuntimeException("Unable to convert!");
		}
	}
}
