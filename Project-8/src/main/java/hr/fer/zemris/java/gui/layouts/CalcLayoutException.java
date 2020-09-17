package hr.fer.zemris.java.gui.layouts;

import hr.fer.zemris.java.gui.layouts.calc.CalcModelImpl;

/**
 * Exception to be throwns when something goes wrong inside
 * the {@link CalcModelImpl}
 * @author Tomislav Kurtović
 *
 */
public class CalcLayoutException extends RuntimeException{
	private static final long serialVersionUID = 1L;
	
	/**
	 * Constructor
	 */
	public CalcLayoutException() {
		super();
	}
	/**
	 * Constructor
	 */
	public CalcLayoutException(String message, Throwable cause) {
		super(message, cause);
	}
	/**
	 * Constructor
	 */
	public CalcLayoutException(String message) {
		super(message);
	}
	/**
	 * Constructor
	 */
	public CalcLayoutException(Throwable cause) {
		super(cause);
	}
}
