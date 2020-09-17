package hr.fer.zemris.java.hw17.jvdraw.color;

import java.awt.Color;

import hr.fer.zemris.java.hw17.jvdraw.JVDraw;

/**
 * Interface used to supply all the 
 * {@link JVDraw} components with the 
 * active selected color 
 * @author Tomislav Kurtović
 *
 */
public interface IColorProvider {
	/**
	 * Retrieves active selected foreground or background color
	 * @return foreground or background color
	 */
	public Color getCurrentColor();
	/**
	 * Adds {@link ColorChangeListener} to the provider so that
	 * it can notify the listeners when the color has changed
 	 * @param l {@link ColorChangeListener} to add
	 */
	public void addColorChangeListener(ColorChangeListener l);
	/**
	 * Removes {@link ColorChangeListener} from the provider
	 * @param l {@link ColorChangeListener} to remove
	 */
	public void removeColorChangeListener(ColorChangeListener l);
}
