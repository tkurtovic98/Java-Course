package hr.fer.zemris.java.gui.layouts.calc;

import java.awt.event.ActionListener;
import java.util.function.DoubleBinaryOperator;

import hr.fer.zemris.java.gui.calc.model.CalcModel;
import hr.fer.zemris.java.gui.calc.model.CalcValueListener;

/**
 * Class used to represent {@link Calculator} button
 * that represents binary operators ( +, -, *, /)
 * @author Tomislav Kurtović
 *
 */
public class BinaryOperator extends CustomButton implements CalcStrategy, CalcValueListener{
	private static final long serialVersionUID = 1L;

	/**
	 * Operator of normal function
	 */
	private DoubleBinaryOperator normalOperator;
	/**
	 * Operator of inversed function
	 */
	private DoubleBinaryOperator inversedOperator;
	/**
	 * Name of inversed function
	 */
	private String inverse;
	/**
	 * Operator of normal function
	 */
	private String normal;
	/**
	 * reference to a {@link CalcModelImpl}
	 */
	private CalcModelImpl impl;
	
	/**
	 * Constructor
	 * @param impl
	 * @param listener
	 * @param normal
	 * @param inverse
	 * @param normalOperator
	 * @param inversedOperator
	 */
	public BinaryOperator(CalcModelImpl impl, ActionListener listener, String normal, 
			String inverse, DoubleBinaryOperator normalOperator, DoubleBinaryOperator inversedOperator) {
		super(listener);
		this.normal = normal;
		this.inverse = inverse;
		this.normalOperator = normalOperator;
		this.inversedOperator = inversedOperator;
		this.impl = impl;
		setText(normal);
		impl.addCalcValueListener(this);
	}
	
	/**
	 * Constructor
	 * @param impl
	 * @param listener
	 * @param text
	 * @param operator
	 */
	public BinaryOperator(CalcModelImpl impl, ActionListener listener, String text, DoubleBinaryOperator operator) {
		this(impl, listener, text, null, operator, null);
	}
	
	@Override
	public void calculate(CalcModelImpl impl, boolean isInverse) {
		if(impl.getPendingBinaryOperation() != null) {
			impl.setActiveOperand(impl.getPendingBinaryOperation().applyAsDouble(impl.getActiveOperand(), impl.getValue()));
			impl.setValue(impl.getActiveOperand());
			impl.setPendingBinaryOperation(null);
		}else {
			impl.setActiveOperand(impl.getValue());
		}
		if(inversedOperator != null) {
			impl.setPendingBinaryOperation(impl.isInverseMode() ? inversedOperator : normalOperator);
		} else {
			impl.setPendingBinaryOperation(normalOperator);
		}
		impl.clear();
	}
	
	@Override
	public void valueChanged(CalcModel model) {
		if(inverse == null) return;
		setText(impl.isInverseMode() ? inverse : normal);
	}
}
