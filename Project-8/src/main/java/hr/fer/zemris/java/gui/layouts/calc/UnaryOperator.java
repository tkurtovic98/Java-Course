package hr.fer.zemris.java.gui.layouts.calc;

import java.awt.event.ActionListener;

import hr.fer.zemris.java.gui.calc.model.CalcModel;
import hr.fer.zemris.java.gui.calc.model.CalcValueListener;

/**
 * Class used to represent buttons used 
 * as unary operators ( sin,cos,tan,ctg etc.)
 * @author Tomislav Kurtović
 *
 */
public class UnaryOperator extends CustomButton implements CalcValueListener{
	private static final long serialVersionUID = 1L;
	/**
	 * Function to caclulate
	 */
	CalcStrategy function;
	/*
	 * Not inversed name
	 */
	String normal;
	/**
	 * inversed name
	 */
	String inverse;
	/**
	 * {@link CalcModelImpl}
	 */
	CalcModelImpl impl;
	/**
	 * Constructor
	 * @param impl
	 * @param listener
	 * @param function
	 * @param normal
	 * @param inverse
	 */
	public UnaryOperator(CalcModelImpl impl, ActionListener listener, CalcStrategy function, String normal, String inverse) {
		super(listener);
		this.function = function;
		this.normal = normal;
		this.inverse = inverse;
		setText(normal);
		this.impl = impl;
		this.impl.addCalcValueListener(this);
	}
	
	/**
	 * Gets the function of this button
	 * @return
	 */
	public CalcStrategy getFunction() {
		return function;
	}
	
	@Override
	public void valueChanged(CalcModel model) {
		setText(impl.isInverseMode() ? inverse : normal);
	}
}
