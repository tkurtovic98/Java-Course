package hr.fer.zemris.java.hw17.jvdraw.tools;

import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

import hr.fer.zemris.java.hw17.jvdraw.JVDraw;

/**
 * Interface used in processing actions
 * from different  states
 * of the {@link JVDraw} program
 * @author Tomislav Kurtović
 *
 */
public interface Tool {

	/**
	 * Invoked when mouse is pressed
	 * @param e mouse event
	 */
	public void mousePressed(MouseEvent e);
	/**
	 * Invoked when mouse is released
	 * @param e mouse event
	 */
	public void mouseReleased(MouseEvent e);
	/**
	 * Invoked when mouse is clicked
	 * @param e mouse event
	 */
	public void mouseClicked(MouseEvent e);
	/**
	 * Invoked when mouse is moved
	 * @param e mouse event
	 */
	public void mouseMoved(MouseEvent e);
	/**
	 * Invoked when mouse is dragged
	 * @param e mouse event
	 */
	public void mouseDragged(MouseEvent e);
	/**
	 * Invoked when a request to paint object was sent
	 * @param g2d graphics to paint to
	 */
	public void paint(Graphics2D g2d);
}
