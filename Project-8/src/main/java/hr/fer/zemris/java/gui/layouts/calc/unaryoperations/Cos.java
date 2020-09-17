package hr.fer.zemris.java.gui.layouts.calc.unaryoperations;

import hr.fer.zemris.java.gui.layouts.calc.CalcModelImpl;
import hr.fer.zemris.java.gui.layouts.calc.CalcStrategy;

/**
 * {@link CalcStrategy} that is used to calculate
 * the cos and arccos functions
 * @author Tomislav Kurtović
 *
 */
public class Cos implements CalcStrategy {
	
	@Override
	public void calculate(CalcModelImpl impl, boolean isInverse) {
		impl.setValue( isInverse ? Math.acos(impl.getValue()) : Math.cos(impl.getValue()));
	}

}
