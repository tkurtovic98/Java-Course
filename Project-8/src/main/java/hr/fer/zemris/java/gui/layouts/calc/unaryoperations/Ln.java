package hr.fer.zemris.java.gui.layouts.calc.unaryoperations;

import hr.fer.zemris.java.gui.layouts.calc.CalcModelImpl;
import hr.fer.zemris.java.gui.layouts.calc.CalcStrategy;

/**
 * {@link CalcStrategy} that is used to calculate
 * the ln and e functions
 * @author Tomislav Kurtović
 *
 */
public class Ln implements CalcStrategy {

	@Override
	public void calculate(CalcModelImpl impl, boolean isInverse) {
		impl.setValue(isInverse ? Math.exp(impl.getValue()): Math.log(impl.getValue()));
	}

}
