package hr.fer.zemris.java.hw17.jvdraw.tools;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.lang.annotation.Inherited;

import hr.fer.zemris.java.hw17.jvdraw.color.IColorProvider;
import hr.fer.zemris.java.hw17.jvdraw.drawing.DrawingModel;
import hr.fer.zemris.java.hw17.jvdraw.drawing.JDrawingCanvas;
import hr.fer.zemris.java.hw17.jvdraw.objects.Line;

/**
 * {@link Tool} used to generate
 * line drawings on canvas 
 * @author Tomislav Kurtović
 *
 */
public class LineTool extends ToolParent {

	/**
	 * Constructor of Line Tool
	 * @param fg  reference to current selected foreground color
	 * @param bg reference to current selected background color
	 * @param canvas reference to canvas on which user can paint on 
	 * @param drawingModel reference to drawing model
	 */
	public LineTool(IColorProvider fg, IColorProvider bg, JDrawingCanvas canvas, DrawingModel drawingModel) {
		super(fg, bg, canvas, drawingModel);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		super.mouseClicked(e);
		if(clickCount == 2) {
			drawingModel.add(new Line(start, end, fg.getCurrentColor()));
			clickCount = 0;
		}
	}
	
	@Override
	public void paint(Graphics2D g2d) {
		if(clickCount == 0) return;
		g2d.setColor(fg.getCurrentColor());
		g2d.drawLine(start.x,start.y,end.x,end.y);
	}
}
