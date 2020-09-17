package hr.fer.zemris.java.gui.layouts.calc.unaryoperations;

import hr.fer.zemris.java.gui.layouts.calc.CalcModelImpl;
import hr.fer.zemris.java.gui.layouts.calc.CalcStrategy;

/**
 * {@link CalcStrategy} that is used to calculate
 * the log and 10^x functions
 * @author Tomislav Kurtović
 *
 * @author Tomislav Kurtović
 *
 */
public class Log implements CalcStrategy{
	
	@Override
	public void calculate(CalcModelImpl impl, boolean isInverse) {
		impl.setValue( isInverse ? Math.log10(impl.getValue()) : Math.pow(10, impl.getValue()));
	}
	
}
