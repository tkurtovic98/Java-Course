package hr.fer.zemris.java.hw07.demo3.scripting.strategies;

/**
 * Implementation of the {@link ArithmeticStrategy} used 
 * to return an Integer of value 0
 * @author Tomislav Kurtović
 *
 */
public class NullValue implements ArithmeticStrategy {

	/**
	 * Returns Integer of value 0
	 */
	@Override
	public Integer execute(Object value,Object argument,String operation) {
		return Integer.valueOf(0);
	}

}
