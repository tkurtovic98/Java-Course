package hr.fer.zemris.java.hw07.demo3.scripting.strategies;

/**
 * Implementation of the {@link ArithmeticStrategy} used 
 * to perform a operation on Doubles
 * @author Tomislav Kurtović
 *
 */
public class DoubleValue implements ArithmeticStrategy {

	/**
	 * It converts the current value and argument to Doubles and
	 * performs the given operation on the two numbers
	 * It then returns the result
	 */
	@Override
	public Double execute(Object value, Object argument, String operation) {
		Double doubleValue;
		Double doubleArgument;
		
		if(value instanceof Integer) {
			doubleValue = ((Integer)value).doubleValue();
		}else {
			doubleValue =(Double) value;
		}
		
		if(argument instanceof Integer) {
			doubleArgument = ((Integer)argument).doubleValue();
		}else {
			doubleArgument =(Double) argument;
		}
		
		switch(operation) {
		case "+":	
			return doubleValue + doubleArgument;
		case "-":
			return doubleValue - doubleArgument;
		case "*":
			return doubleValue * doubleArgument;
		case "/":
			if(doubleArgument == 0) {
				throw new ArithmeticException("Can't divide by 0");
			}
			return doubleValue/doubleArgument;
		}
		throw new UnsupportedOperationException("Operation not found!");
	}

}
