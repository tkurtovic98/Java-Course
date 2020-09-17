package hr.fer.zemris.java.gui.layouts.calc;

import java.util.ArrayList;
import java.util.List;
import java.util.function.DoubleBinaryOperator;

import hr.fer.zemris.java.gui.calc.model.CalcModel;
import hr.fer.zemris.java.gui.calc.model.CalcValueListener;
import hr.fer.zemris.java.gui.calc.model.CalculatorInputException;

/**
 * Class that represents implementation
 * of a calculator model which can 
 * store operands and operations
 * @author Tomislav Kurtović
 */
public class CalcModelImpl implements CalcModel {

	/**
	 * Flag to se if new digits can be addded
	 */
	private boolean isEditable = true;

	/**
	 * Flag to se if number is positive
	 */
	private boolean isPositive = true;

	/**
	 * Current value
	 */
	private String value = "";
	/**
	 * Current value
	 */
	private double numericValue = 0;
	/**
	 * Current operand value
	 */
	private double activeOperand = 0;
	/**
	 * Flag to check if active operand was set
	 */
	private boolean isActiveOperandSet = false;
	/**
	 * Current pending operation
	 */
	private DoubleBinaryOperator pendingOperation = null ;
	/**
	 * List of calcValueListeners
	 */
	private List<CalcValueListener> calcValueListeners;
	/**
	 * Checks if calculator is in inverse mode
	 */
	private boolean inverseMode = false;
	
	
	@Override
	public void addCalcValueListener(CalcValueListener l) {
		if(calcValueListeners == null) {
			calcValueListeners = new ArrayList<>();
		}
		calcValueListeners.add(l);
	}

	@Override
	public void removeCalcValueListener(CalcValueListener l) {
		if(calcValueListeners == null) return;
		if(calcValueListeners.contains(l)) {
			calcValueListeners.remove(l);
		}
	}
	
	private void notifyCalcValueListeners() {
		if(calcValueListeners == null) return;
		for(CalcValueListener calc : calcValueListeners) {
			calc.valueChanged(this);
		}
	}

	@Override
	public double getValue() {
		return numericValue;
	}

	@Override
	public void setValue(double value) {
		numericValue = value;
		this.value = String.valueOf(value);
		isEditable = false;
		notifyCalcValueListeners();
	}

	@Override
	public boolean isEditable() {
		return isEditable;
	}

	@Override
	public void clear() {
		value = "";
		numericValue = 0;
		isEditable = true;
		notifyCalcValueListeners();
	}

	@Override
	public void clearAll() {
		value = "";
		numericValue = 0;
		activeOperand = 0;
		pendingOperation = null;
		isActiveOperandSet = false;
		isEditable = true;
		notifyCalcValueListeners();
	}

	@Override
	public void swapSign() throws CalculatorInputException {
		if(!isEditable) {
			throw new CalculatorInputException("Can not swap signs if not editable!");
		}
		isPositive = !isPositive;
		numericValue = -numericValue;
		notifyCalcValueListeners();
	}

	@Override
	public void insertDecimalPoint() throws CalculatorInputException {
		if(!isEditable) {
			throw new CalculatorInputException("Can not add '.' if not editable!");
		}
		if(value.isBlank()) {
			throw new CalculatorInputException("Can not add '.' if no number is present!");
		}
		if(value.contains(".")) {
			throw new CalculatorInputException("'.' is contained in the value!");
		}
		value = value.concat(".");
		numericValue = (isPositive) ? Double.parseDouble(value) : -Double.parseDouble(value);
		notifyCalcValueListeners();
	}

	@Override
	public void insertDigit(int digit) throws CalculatorInputException, IllegalArgumentException {
		if(!isEditable) {
			throw new CalculatorInputException("Can not add new digits, not editable!");
		}
		if("0".equals(value)) {
			if(digit == 0) return;
			value = value.replace("0", "");
		}
		String copy = value.concat(String.valueOf(digit));
		double parsedValue = Double.parseDouble(copy);
		if(parsedValue > Double.MAX_VALUE) {
			throw new CalculatorInputException("Value is to large");
		}
		numericValue = (isPositive) ? parsedValue : -parsedValue;
		value = copy;
		notifyCalcValueListeners();
	}

	@Override
	public boolean isActiveOperandSet() {
		return isActiveOperandSet;
	}

	@Override
	public double getActiveOperand() throws IllegalStateException {
		if(!isActiveOperandSet()) {
			throw new IllegalStateException("Operand not set!");
		}
		return activeOperand;
	}

	@Override
	public void setActiveOperand(double activeOperand) {
		this.activeOperand = activeOperand;
		isActiveOperandSet = true;
		notifyCalcValueListeners();
	}

	@Override
	public void clearActiveOperand() {
		activeOperand = 0;
		isActiveOperandSet = false;
		notifyCalcValueListeners();
	}

	@Override
	public DoubleBinaryOperator getPendingBinaryOperation() {
		return pendingOperation;
	}

	@Override
	public void setPendingBinaryOperation(DoubleBinaryOperator op) {
		this.pendingOperation = op;
		notifyCalcValueListeners();
	}

	@Override
	public String toString() {
		if(value.isBlank()) {
			return (isPositive) ? "0" : "-0";
		}
		return (isPositive) ? value : "-" + value;
	}
	
	/**
	 * Sets inverse mode of calculator
	 * @param inverseMode mode to set calculator
	 */
	public void setInverseMode(boolean inverseMode) {
		this.inverseMode = inverseMode;
		notifyCalcValueListeners();
	}
	
	/**
	 * returns the current mode
	 * @return
	 */
	public boolean isInverseMode() {
		return inverseMode;
	}
}
