package hr.fer.zemris.java.gui.layouts.calc;

import javax.swing.JLabel;

import hr.fer.zemris.java.gui.calc.model.CalcModel;
import hr.fer.zemris.java.gui.calc.model.CalcValueListener;

/**
 * {@link CalcValueListener} that is in charge of 
 * the label responsible for text printing 
 * @author Tomislav Kurtović
 *
 */
public class TextValueChange implements CalcValueListener {

	/**
	 * Label to notify
	 */
	private JLabel label;
	
	/**
	 * constructor
	 * @param label
	 */
	public  TextValueChange(JLabel label) {
		this.label = label;
	}
	
	@Override
	public void valueChanged(CalcModel model) {
		label.setText(model.toString());
	}

}
