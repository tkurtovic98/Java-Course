package hr.fer.zemris.java.gui.layouts.calc;

import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionListener;
import java.util.Stack;

import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import hr.fer.zemris.java.gui.calc.model.CalcModel;
import hr.fer.zemris.java.gui.calc.model.CalcValueListener;
import hr.fer.zemris.java.gui.layouts.CalcLayout;
import hr.fer.zemris.java.gui.layouts.RCPosition;
import hr.fer.zemris.java.gui.layouts.calc.unaryoperations.Cos;
import hr.fer.zemris.java.gui.layouts.calc.unaryoperations.Ctg;
import hr.fer.zemris.java.gui.layouts.calc.unaryoperations.Inversed;
import hr.fer.zemris.java.gui.layouts.calc.unaryoperations.Ln;
import hr.fer.zemris.java.gui.layouts.calc.unaryoperations.Log;
import hr.fer.zemris.java.gui.layouts.calc.unaryoperations.Sin;
import hr.fer.zemris.java.gui.layouts.calc.unaryoperations.Tan;

/**
 * Class used to represent a calculator 
 * with buttons for digits and some 
 * binary and unary operators 
 * @author Tomislav Kurtović
 *
 */
public class Calculator extends JFrame{

	private static final long serialVersionUID = 1L;
	/**
	 * Implementation of {@link CalcModel}
	 */
	private static CalcModelImpl impl = new CalcModelImpl();
	
	/**
	 * Constructor
	 */
	public Calculator() {
		setTitle("My Calculator");
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		initGUI();
		pack();
	}

	/**
	 * Initializes the gui
	 */
	private void initGUI() {
		Container cp = getContentPane();
		cp.setLayout(new CalcLayout(3));
		setDigitHolder(cp);
		setDigits(cp);
		setBinaryOperations(cp);
		setUnaryOperations(cp);
		setDecimal(cp);
		setInv(cp);
		setEquals(cp);
		setClrAndReset(cp);
		setPushPop(cp);
		setPlusMinus(cp);
	}
	
	/**
	 * Used to set the +/- button
	 * @param cp
	 */
	private void setPlusMinus(Container cp) {
		ActionListener listener = (e) ->impl.setValue(-impl.getValue());
		CustomButton  plusMinus = new CustomButton(listener) {
			private static final long serialVersionUID = 1L;
		};
		plusMinus.setText("+/-");
		cp.add(plusMinus,new RCPosition(5,4));
	}

	/**
	 * Used to set the push button
	 * @param cp container of the button
	 */
	private void setPushPop(Container cp) {
		Stack<Double> stack = new Stack<>();
		ActionListener listener1 = (e) ->stack.push(impl.getValue());
		CustomButton push = new CustomButton(listener1) {
			private static final long serialVersionUID = 1L;
		};
		push.setText("push");
		cp.add(push,new RCPosition(3,7));
		
		ActionListener listener2 = (e) ->{
			if(stack.isEmpty()) {
				System.out.println("Empty stack!");
				return;
			}
			impl.setValue(stack.pop());
		};
		CustomButton pop = new CustomButton(listener2) {
			private static final long serialVersionUID = 1L;
		};
		pop.setText("pop");
		cp.add(pop,new RCPosition(4,7));
	}

	/**
	 * Used to set the clear and reset buttons
	 * @param cp container of the button
	 */
	private void setClrAndReset(Container cp) {
		ActionListener clear = (e) ->impl.clear();
		CustomButton clr = new CustomButton(clear) {
			private static final long serialVersionUID = 1L;
		};
		clr.setText("clr");
		cp.add(clr,new RCPosition(1,7));
		
		ActionListener reset = (e) ->impl.clearAll();
		CustomButton res = new CustomButton(reset) {
			private static final long serialVersionUID = 1L;
		};
		res.setText("reset");
		cp.add(res,new RCPosition(2,7));
	}

	/**
	 * Used to set the Equals button
	 * @param cp container of the button
	 */
	private void setEquals(Container cp) {
		ActionListener listener = (e) -> {
			if(impl.getPendingBinaryOperation() == null || !impl.isActiveOperandSet()) return;
			impl.setValue(impl.getPendingBinaryOperation().applyAsDouble(impl.getActiveOperand(), impl.getValue()));
			impl.clearActiveOperand();
			impl.setPendingBinaryOperation(null);
		};
		CustomButton equals = new CustomButton(listener) {
			private static final long serialVersionUID = 1L;
		};
		equals.setText("=");
		cp.add(equals,new RCPosition(1,6));
	}

	/**
	 * Used to set the binary operations
	 * @param cp container of the button
	 */
	private void setBinaryOperations(Container cp) {
		ActionListener listener = (e) -> {
			BinaryOperator op = (BinaryOperator)e.getSource();
			op.calculate(impl, impl.isInverseMode());
		};
		
		cp.add(new BinaryOperator(impl, listener, "/", (x,y)-> x/y), new RCPosition(2,6));
		cp.add(new BinaryOperator(impl, listener, "*", (x,y)-> x*y), new RCPosition(3,6));
		cp.add(new BinaryOperator(impl, listener, "-", (x,y)-> x-y), new RCPosition(4,6));
		cp.add(new BinaryOperator(impl, listener, "+", (x,y)-> x+y), new RCPosition(5,6));
		cp.add(new BinaryOperator(impl, listener, "x^n", "x^(1/n)",(x,y)->Math.pow(x, y),
				(x,y)->Math.pow(x,1/y)), new RCPosition(5,1));
	}

	/**
	 * Used to set inverse checkBox
	 * @param cp container of the button
	 */
	private void setInv(Container cp) {
		JCheckBox inverse = new JCheckBox("Inv");
		inverse.setSize(20,20);
		inverse.addChangeListener((e) -> {
			impl.setInverseMode(inverse.isSelected());
		});
		cp.add(inverse, new RCPosition(5,7));
	}
	/**
	 * Used to set the decimal point of a number
	 * @param cp container of the button
	 */
	private void setDecimal(Container cp) {
		ActionListener listener = (e) -> impl.insertDecimalPoint();
		CustomButton dot = new CustomButton(listener) {
			private static final long serialVersionUID = 1L;
		};
		dot.setText(".");
		cp.add(dot,new RCPosition(5,5));
	}

	/**
	 * Used to set the unary operation
	 * @param cp container of the button
	 */
	private void setUnaryOperations(Container cp) {
		ActionListener listener = (e) -> {
			UnaryOperator operator = (UnaryOperator)e.getSource();
			operator.function.calculate(impl, impl.isInverseMode());
		};
		
		cp.add(new UnaryOperator(impl, listener, new Sin(), "sin", "asin"), new RCPosition(2,2));
		cp.add(new UnaryOperator(impl, listener, new Cos(), "cos", "arccos"), new RCPosition(3,2));
		cp.add(new UnaryOperator(impl, listener, new Tan(), "tan", "arctan"), new RCPosition(4,2));
		cp.add(new UnaryOperator(impl, listener, new Inversed(), "1/x", "1/x"), new RCPosition(2,1));
		cp.add(new UnaryOperator(impl, listener, new Log(), "log", "10^x"), new RCPosition(3,1));
		cp.add(new UnaryOperator(impl, listener, new Ln(),"ln","e^x"), new RCPosition(4,1));
		cp.add(new UnaryOperator(impl, listener, new Ctg(),"ctg", "arcctg"), new RCPosition(5,2));
	}

	/**
	 * Used to set the digits
	 * @param cp container of the button
	 */
	private void setDigits(Container cp) {
		ActionListener listener = (e) -> {
			Digit digit = (Digit)e.getSource();
			digit.setDigit(impl);
		};
		
		cp.add(new Digit(0,listener), new RCPosition(5,3));
		cp.add(new Digit(1,listener), new RCPosition(4,3));
		cp.add(new Digit(2,listener), new RCPosition(4,4));
		cp.add(new Digit(3,listener), new RCPosition(4,5));
		cp.add(new Digit(4,listener), new RCPosition(3,3));
		cp.add(new Digit(5,listener), new RCPosition(3,4));
		cp.add(new Digit(6,listener), new RCPosition(3,5));
		cp.add(new Digit(7,listener), new RCPosition(2,3));
		cp.add(new Digit(8,listener), new RCPosition(2,4));
		cp.add(new Digit(9,listener), new RCPosition(2,5));
	}

	/**
	 * Used to set the digitholder
	 * @param cp container of the button
	 */
	private void setDigitHolder(Container cp) {
		JLabel digitHolder = new JLabel();
		digitHolder.setBackground(Color.YELLOW);
		digitHolder.setOpaque(true);
		digitHolder.setText("0");
		digitHolder.setHorizontalAlignment(SwingConstants.RIGHT);
		digitHolder.setFont(digitHolder.getFont().deriveFont(30f));
		cp.add(digitHolder, new RCPosition(1,1));
		
		CalcValueListener textListener = new TextValueChange(digitHolder);
		impl.addCalcValueListener(textListener);
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() ->{
			JFrame calc = new Calculator();
			calc.setVisible(true);
		});
	}
}
