package hr.fer.zemris.java.custom.collections.demo;

import hr.fer.zemris.java.custom.collections.EmptyStackException;

import hr.fer.zemris.java.custom.collections.ObjectStack;

/**
 * 
 * Class that uses a stack to calculate
 * expressions with: +,-,*,/,%.
 * The stack calculates the numbers in postfix notation
 * and when invalid operations are used on the
 * stack appropriate exceptions are thrown
 * 
 * @author Tomislav Kurtović
 *
 */

public class StackDemo {

	/**
	 * The main method used to retrieve the string 
	 * containing the arguments from the command line.
	 * It then splits the string to get to those 
	 * arguments and calculates the result by calling 
	 * helping methods.
	 * If an error occurred during the process an appropriate
	 * message will be displayed
	 * 
	 * @param args string containing all the arguments for the calculation
	 */
	public static void main(String[] args) {
		
		if(args.length != 1) {
			System.out.println("Invalid number of arguments");
			return;
		}
		
		if(args.length == 1) {
			String[] elementsForStack = args[0].split("\\s+");
			try{
				int result = evaluateExpression(elementsForStack);
				if(result != -1) {
					System.out.printf("Expression evaluates to %d!%n",result );
				} else {
					System.out.printf("ERROR: Could not get result!%n",result );
				}
			}catch(EmptyStackException ex) {
				System.out.println("ERROR: invalid operations found");
			}
		}
	}

	/**
	 * Used to calculate the given expression by using 
	 * postfix notation.
	 * It goes through the arguments and tries to evaluate 
	 * whether it is a number or symbol.
	 * 
	 * @param elementsForStack string array from where the arguments are taken
	 * @return result of the calculation if successful, -1 otherwise
	 */
	private static int evaluateExpression(String[] elementsForStack) {
		ObjectStack stack = new ObjectStack();
		
		for(int i=0; i < elementsForStack.length; i++) {
			try {
				Integer valueToPush = Integer.parseInt(elementsForStack[i]);
				stack.push(valueToPush);
			}catch(NumberFormatException ex) {
				try {
					stack = popNumbersFromStack(stack,elementsForStack[i]);
				} catch(ArithmeticException e) {
					System.out.println(e.getMessage());
					return -1;
				}
			}
		}
		if(stack.size() == 1) {
			return (Integer)stack.pop();
		} else {
			return -1;
		}
		
	}
	/**
	 * Used for poping numbers from the stack and checking which
	 * calculation should be made depending on the operator that
	 * was passed as an argument
	 * 
	 * @param stack stack used for storing values
	 * @param operator symbol of mathematical operation that has to be done on the two numbers of the stack
	 * @return the stack with new numbers 
	 */
	private static ObjectStack popNumbersFromStack(ObjectStack stack,String operator) {
		Integer firstNumber = (Integer)stack.pop();
		Integer secondNumber=(Integer) stack.pop();
		Integer resultToPush= 0;
		
		switch(operator){
			case "+":
				resultToPush = secondNumber + firstNumber ;
				break;
			case "*":
				resultToPush = secondNumber * firstNumber ;
				break;
			case "/":
				if(firstNumber == 0) {
					throw new ArithmeticException("Cannot divide by zero");
				}
				resultToPush = secondNumber / firstNumber;
				break;
			case "%":
				if(firstNumber == 0) {
					throw new ArithmeticException("Cannot divide by zero");
				}
				resultToPush = secondNumber % firstNumber;
				break;
			case "-":
				resultToPush = secondNumber - firstNumber;
				break;				
		}
		stack.push(resultToPush);
		return stack;
	}
}






