package hr.fer.zemris.java.hw17.jvdraw.color;

import java.awt.Color;

import hr.fer.zemris.java.hw17.jvdraw.JVDraw;

/**
 * Interface used to act
 * when a color change happens in 
 * the {@link JVDraw} program
 * @author Tomislav Kurtović
 *
 */
public interface ColorChangeListener {
	/**
	 * Invoked when the current active color of the {@link JVDraw} program changes 
	 * @param source {@link IColorProvider} that fired the action 
	 * @param oldColor oldColor - either foreground or background 
	 * @param newColor newColor - either foreground or background
	 */
	public void newColorSelected(IColorProvider source, Color oldColor, Color newColor);
}
