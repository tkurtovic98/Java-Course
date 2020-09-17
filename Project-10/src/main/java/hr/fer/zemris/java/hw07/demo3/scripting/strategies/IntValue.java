package hr.fer.zemris.java.hw07.demo3.scripting.strategies;

/**
 * Implementation of the {@link ArithmeticStrategy} used 
 * to perform a operation on Integers
 * @author Tomislav Kurtović
 *
 */
public class IntValue implements ArithmeticStrategy {

	/**
	 * It performs the given operation on the two passed numbers.
	 * It then returns the result
	 */
	@Override
	public Integer execute(Object value, Object argument, String operation) {
		Integer integerValue = (Integer) value;
		Integer integerArgument = (Integer) argument;
		
		switch(operation) {
		case "+":	
			return integerValue + integerArgument;
		case "-":
			return integerValue - integerArgument;
		case "*":
			return integerValue * integerArgument;
		case "/":
			if(integerArgument == 0) {
				throw new ArithmeticException("Can't divide by 0");
			}
			return integerValue/integerArgument;
		}
		throw new UnsupportedOperationException("Operation not found!");
	}
}

