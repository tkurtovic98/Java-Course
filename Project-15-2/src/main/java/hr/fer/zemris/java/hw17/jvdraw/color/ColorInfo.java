package hr.fer.zemris.java.hw17.jvdraw.color;

import java.awt.Color;

import javax.swing.JLabel;

import hr.fer.zemris.java.hw17.jvdraw.JVDraw;

/**
 * {@link ColorChangeListener} that is used
 * to display current color in the {@link JVDraw} program
 * @author Tomislav Kurtović
 *
 */
public class ColorInfo extends JLabel implements ColorChangeListener {
	private static final long serialVersionUID = 1L;
	/**
	 * Reference to {@link IColorProvider} that has information about foreground color
	 */
	private IColorProvider fg;
	/**
	 * Reference to {@link IColorProvider} that has information about background color
	 */
	private IColorProvider bg;
	
	/**
	 * Constructor of the object
	 * @param fg Reference to {@link IColorProvider} that has information about foreground color
	 * @param bg Reference to {@link IColorProvider} that has information about background color
	 */
	public ColorInfo(IColorProvider fg, IColorProvider bg) {
		this.fg = fg;
		this.bg = bg;
		printColors();
	}
	
	/**
	 * Used to print the current selected colors to screen
	 */
	private void printColors() {
		setText("Foreground color: " + formatColor(fg.getCurrentColor())+ ", background color: " + formatColor(bg.getCurrentColor()));
	}
	/**
	 * Formats colors  
	 * @param c color to format
	 * @return formatted string with rgb components of color
	 */
	private String formatColor(Color c){
		return "("+c.getRed()+","+c.getGreen()+","+c.getBlue()+")";
	}

	@Override
	public void newColorSelected(IColorProvider source, Color oldColor, Color newColor) {
		printColors();
	}
}
