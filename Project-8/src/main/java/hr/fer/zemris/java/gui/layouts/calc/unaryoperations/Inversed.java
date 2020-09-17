package hr.fer.zemris.java.gui.layouts.calc.unaryoperations;

import hr.fer.zemris.java.gui.layouts.calc.CalcModelImpl;
import hr.fer.zemris.java.gui.layouts.calc.CalcStrategy;

/**
 * {@link CalcStrategy} that is used to calculate
 * the inverse ( 1/x) funcition
 * @author Tomislav Kurtović
 *
 */
public class Inversed implements CalcStrategy {

	@Override
	public void calculate(CalcModelImpl impl, boolean isInverse) {
		impl.setValue((double)1/impl.getValue());
	}
}
