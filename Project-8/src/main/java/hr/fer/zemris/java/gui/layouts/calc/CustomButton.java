package hr.fer.zemris.java.gui.layouts.calc;

import java.awt.Dimension;
import java.awt.event.ActionListener;
import javax.swing.JButton;

/**
 * Abstract class that acts as a superclass for all the
 * buttons of the {@link Calculator}
 * @author Tomislav Kurtović
 *
 */
public abstract class CustomButton extends JButton {
	private static final long serialVersionUID = 1L;

	/**
	 * Construcotr
	 * @param listener listener to add 
	 */
	public CustomButton(ActionListener listener) {
		addActionListener(listener);
		initButton();
	}
	
	/**
	 * Initializes button
	 */
	private void initButton() {
		setPreferredSize(new Dimension(30, 10));
		setFont(getFont().deriveFont(15f));
		setVisible(true);
	}
}
