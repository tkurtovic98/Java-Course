package hr.fer.zemris.java.hw17.jvdraw.tools;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.nio.file.Files;
import java.nio.file.Path;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

import hr.fer.zemris.java.hw17.jvdraw.JVDraw;
import hr.fer.zemris.java.hw17.jvdraw.color.IColorProvider;
import hr.fer.zemris.java.hw17.jvdraw.drawing.DrawingModel;
import hr.fer.zemris.java.hw17.jvdraw.drawing.JDrawingCanvas;

/**
 * Class that is used to define and change 
 * all the states the {@link JVDraw} program
 * has. 
 * It also chages the current state of the 
 * program upon request
 * @author Tomislav Kurtović
 *
 */
public class States {
	/**
	 * Reference to {@link IColorProvider} that holds information about current foreground
	 */
	private static IColorProvider fg;
	/**
	 * Reference to {@link IColorProvider} that holds information about current background
	 */
	private static IColorProvider bg;
	/**
	 * Reference to canvas where user can draw on 
	 */
	private static JDrawingCanvas canvas;
	/**
	 * Reference to current running instance of {@link JVDraw}
	 */
	private static JVDraw instance;
	/**
	 * Reference to drawing model
	 */
	private static DrawingModel drawingModel;
	
	/**
	 * Constructor.
	 * It configures all the necessary states upon creation
	 * @param instance instance of {@link JVDraw}
	 */
	public States(JVDraw instance) {
		States.instance = instance;
		configureStates();
	}
	/**
	 * Sets the {@link IColorProvider}s 
	 * @param fg foreground {@link IColorProvider}
	 * @param bg background {@link IColorProvider}
	 */
	public void setColorProviders(IColorProvider fg, IColorProvider bg) {
		States.fg = fg;
		States.bg = bg;
	}
	/**
	 * Sets the {@link JDrawingCanvas}
	 * @param canvas {@link JDrawingCanvas}
	 */
	public void setCanvas(JDrawingCanvas canvas) {
		States.canvas = canvas;
	}
	/**
	 * Sets the {@link DrawingModel} 
	 * @param drawingModel {@link DrawingModel}
	 */
	public void setDrawingModel(DrawingModel drawingModel) {
		States.drawingModel = drawingModel;
	}
	/**
	 * Configures all the states 
	 */
	private void configureStates() {
		LINE.putValue(Action.NAME, "Line");
		CIRCLE.putValue(Action.NAME, "Circle");
		FILL_CIRCLE.putValue(Action.NAME, "Filled circle");
	}
	
	/**
	 * {@link Action} that sets the current state to {@link LineTool}
	 */
	public final Action LINE = new AbstractAction() {
		private static final long serialVersionUID = 1L;
		private Tool line = null;
		
		@Override
		public void actionPerformed(ActionEvent e) {
			line();
		}

		private void line() {
			if(line == null) {
				line = new LineTool(fg, bg, canvas,drawingModel);
			}
			instance.setCurrentState(line);
		}
	};
	/**
	 * {@link Action} that sets the current state to {@link CircleTool}
	 */
	public final Action CIRCLE = new AbstractAction() {
		private static final long serialVersionUID = 1L;

		private Tool circle = null;
		
		@Override
		public void actionPerformed(ActionEvent e) {
			circle();
		}

		private void circle() {
			if(circle == null) {
				circle = new CircleTool(fg, bg, canvas,drawingModel);
			}
			instance.setCurrentState(circle);
		}
	};
	/**
	 * {@link Action} that sets the current state to {@link FilledCircleTool}
	 */
	public final Action FILL_CIRCLE = new AbstractAction() {
		private static final long serialVersionUID = 1L;

		private Tool fill_circle = null;
		
		@Override
		public void actionPerformed(ActionEvent e) {
			fill_circle();
		}

		private void fill_circle() {
			if(fill_circle == null) {
				fill_circle = new FilledCircleTool(fg, bg, canvas,drawingModel);
			}
			instance.setCurrentState(fill_circle);
		}
	};
}
