package hr.fer.zemris.java.gui.layouts.calc.unaryoperations;

import hr.fer.zemris.java.gui.layouts.calc.CalcModelImpl;
import hr.fer.zemris.java.gui.layouts.calc.CalcStrategy;

/**
 * {@link CalcStrategy} that is used to calculate
 * the ctg and arctg functions
 * @author Tomislav Kurtović
 *
 */
public class Ctg implements CalcStrategy {

	@Override
	public void calculate(CalcModelImpl impl, boolean isInverse) {
		impl.setValue(isInverse ? 1/Math.atan(impl.getValue()) : 1/Math.tan(impl.getValue()));
	}
}
