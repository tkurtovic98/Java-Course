package hr.fer.zemris.java.hw17.jvdraw.tools;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;

import hr.fer.zemris.java.hw17.jvdraw.JVDraw;
import hr.fer.zemris.java.hw17.jvdraw.color.IColorProvider;
import hr.fer.zemris.java.hw17.jvdraw.drawing.DrawingModel;
import hr.fer.zemris.java.hw17.jvdraw.drawing.JDrawingCanvas;

/**
 * Implementation of the {@link Tool} interface
 * used as a top class for all other tools used
 * by the {@link JVDraw} program.
 * Other tools are:
 * <li>{@link LineTool}
 * <li> {@link CircleTool}
 * <li>{@link FilledCircleTool}
 * 
 * @author Tomislav Kurtović
 *
 */
public class ToolParent implements Tool{
	/**
	 * reference to current selected foreground color
	 */
	protected IColorProvider fg;
	/**
	 * reference to current selected background color
	 */
	protected IColorProvider bg;
	/**
	 * reference to canvas on which user can paint on 
	 */
	private JDrawingCanvas canvas;
	/**
	 * number of click counts so that the tool knows when to generate drawing
	 */
	protected int clickCount;
	/**
	 * Starting point of drawing
	 * 
	 */
	protected Point start;
	/**
	 * End point of drawing
	 */
	protected Point end;
	/**
	 * reference to drawing model
	 */
	protected DrawingModel drawingModel;
	
	/**
	 * Constructor of the Tool
	 * @param fg  reference to current selected foreground color
	 * @param bg reference to current selected background color
	 * @param canvas reference to canvas on which user can paint on 
	 * @param drawingModel reference to drawing model
	 */
	public ToolParent(IColorProvider fg, IColorProvider bg, JDrawingCanvas canvas, DrawingModel drawingModel) {
		this.fg = fg;
		this.bg = bg;
		this.canvas = canvas;
		this.drawingModel = drawingModel;
		clickCount = 0;
		start = new Point();
		end = new Point();
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (clickCount == 0) {
			start = e.getPoint();
			clickCount = 1;
			return;
		}
		end = e.getPoint();
		clickCount = 2;
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		if(clickCount != 0) {
			end = e.getPoint();
			canvas.repaint();
		}
	}

	@Override
	public void mouseDragged(MouseEvent e) {
	}

	@Override
	public void paint(Graphics2D g2d) {
	}
}
