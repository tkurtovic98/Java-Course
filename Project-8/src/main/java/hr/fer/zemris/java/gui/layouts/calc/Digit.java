package hr.fer.zemris.java.gui.layouts.calc;

import java.awt.event.ActionListener;

/**
 * Class used to insert digits into the calculator
 * @author Tomislav Kurtović
 *
 */
public class Digit extends CustomButton {
	private static final long serialVersionUID = 1L;
	/**
	 * Digit to be added to calc
	 */
	private int digit;
	
	/**
	 * Constructor
	 * @param digit
	 * @param listener
	 */
	public Digit(int digit, ActionListener listener) {
		super(listener);
		this.digit = digit;
		setText("" + digit + "");
		setFont(getFont().deriveFont(35f));
	}
	
	/**
	 * Sets digit of {@link CalcModelImpl}
	 * @param impl
	 */
	public void setDigit(CalcModelImpl impl) {
		impl.insertDigit(digit);
	}
}
