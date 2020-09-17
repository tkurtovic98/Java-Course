package hr.fer.zemris.java.hw07.demo3.scripting.strategies;

import hr.fer.zemris.java.hw07.demo3.ValueWrapper;

/**
 * Interface used to create new classes which are 
 * used by the {@link ValueWrapper} to determine if the 
 * given argument and current value are valid
 * @author Tomislav Kurtović
 *
 */
public interface ArithmeticStrategy {
	/**
	 * Either changes the type of the given argument or value and 
	 * returns a new type or performs the operation on the 
	 * value and argument and returns the result of the operation 
	 * @param value current value of {@link ValueWrapper}
	 * @param argument passed argument to the operation of the 
	 * {@link ValueWrapper}
	 * @param operation operation to perform ( can be +, -, * or / )
	 * @return object 
	 */
	 Object execute(Object value,Object argument,String operation);
}
