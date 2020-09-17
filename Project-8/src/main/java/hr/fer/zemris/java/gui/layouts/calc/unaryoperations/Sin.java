package hr.fer.zemris.java.gui.layouts.calc.unaryoperations;

import hr.fer.zemris.java.gui.layouts.calc.CalcModelImpl;
import hr.fer.zemris.java.gui.layouts.calc.CalcStrategy;

/**
 * {@link CalcStrategy} that is used to calculate
 * the sin and arcsin functions
 * @author Tomislav Kurtović
 *
 */
public class Sin implements CalcStrategy{
	
	@Override
	public void calculate(CalcModelImpl impl, boolean isInverse) {
		impl.setValue( isInverse ? Math.asin(impl.getValue()) : Math.sin(impl.getValue()));
	}
}
