package hr.fer.zemris.java.custom.scripting.exec;

/**
 * Class that is used as a holder 
 * for the operations: +, -, *, / 
 * It holds one static method that exectutes 
 * the appropriate operation and 
 * returns the result
 * @author Tomislav Kurtović
 *
 */
public class Operations {

	
	/**
	 * Static method used to execute the given operation.
	 * The operations supported can be: '+', '-', '*', '/'.
	 * Other operations will not return a result of an operation ( they will return null)
 	 * @param x left operand
	 * @param y right operand 
	 * @param operator operation 
	 * @return result of operation
	 */
	static int executeOperation(int x, int y, String operator) {
		switch (operator) {
		case "+":
			return  x + y;
		case "-":
			return  x - y;
		case "*":
			return  x * y;
		case "/":
			if(y == 0) {
				throw new IllegalArgumentException("cannot divide by 0");
			}
			return  x / y;
		}
		return 0;
	}
}

