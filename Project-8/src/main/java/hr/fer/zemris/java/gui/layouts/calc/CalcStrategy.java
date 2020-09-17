package hr.fer.zemris.java.gui.layouts.calc;

/**
 * Interface used by diffenrent
 * actions performend by the 
 * {@link Calculator}.
 * @author Tomislav Kurtović
 *
 */
public interface CalcStrategy {
	/**
	 * Used to caclulate different
	 * operations on the calculator
	 * like add numbers, sin, cos, arctan etc.
	 * @param impl {@link CalcModelImpl}
	 * @param isInverse if calculator is in inverse mode
	 */
	void calculate(CalcModelImpl impl, boolean isInverse);
}
