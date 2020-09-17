package hr.fer.zemris.java.gui.layouts.calc.unaryoperations;

import hr.fer.zemris.java.gui.layouts.calc.CalcModelImpl;
import hr.fer.zemris.java.gui.layouts.calc.CalcStrategy;

/**
 * {@link CalcStrategy} that is used to calculate
 * the tan and arctan functions
 * @author Tomislav Kurtović
 *
 */
public class Tan implements CalcStrategy {

	@Override
	public void calculate(CalcModelImpl impl, boolean isInverse) {
		impl.setValue( isInverse ? Math.atan(impl.getValue()) : Math.tan(impl.getValue()));
	}
}
