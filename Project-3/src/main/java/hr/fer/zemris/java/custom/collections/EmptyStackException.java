package hr.fer.zemris.java.custom.collections;

/**
 * Class that represents the exception
 * that is thrown when a class that
 * implements a stack tries to pop elements
 * of an empty stack
 * 
 * @author Tomislav Kurtović
 *
 */
public class EmptyStackException extends RuntimeException{
	private static final long serialVersionUID = 1L;
	
	/**
	 * Default constructor
	 * 
	 */
    public EmptyStackException() {
    	super();
    }
    
    /**
     * Constructor with the message added
     * 
     * @param message exception message
     */
	public EmptyStackException(String message) {
		super(message);
	}
}
