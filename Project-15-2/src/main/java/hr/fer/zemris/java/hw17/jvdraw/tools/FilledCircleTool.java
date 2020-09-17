package hr.fer.zemris.java.hw17.jvdraw.tools;

import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

import javax.swing.border.StrokeBorder;

import hr.fer.zemris.java.hw17.jvdraw.color.IColorProvider;
import hr.fer.zemris.java.hw17.jvdraw.drawing.DrawingModel;
import hr.fer.zemris.java.hw17.jvdraw.drawing.JDrawingCanvas;
import hr.fer.zemris.java.hw17.jvdraw.objects.FilledCircle;
import hr.fer.zemris.java.hw17.jvdraw.objects.Line;

/**
 * {@link Tool} used to generate
 * line drawings on canvas 
 * @author Tomislav Kurtović
 *
 */
public class FilledCircleTool extends ToolParent {

	/**
	 * Constructor of filled circle tool
	 * @param fg  reference to current selected foreground color
	 * @param bg reference to current selected background color
	 * @param canvas reference to canvas on which user can paint on 
	 * @param drawingModel reference to drawing model
	 */
	public FilledCircleTool(IColorProvider fg, IColorProvider bg, JDrawingCanvas canvas, DrawingModel drawingModel) {
		super(fg, bg, canvas, drawingModel);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		super.mouseClicked(e);
		if(clickCount == 2) {
			drawingModel.add(new FilledCircle(start, CircleRadiusCalc.calcRadius(start, end),
					fg.getCurrentColor(), bg.getCurrentColor()));
			clickCount = 0;
		}
	}
	
	@Override
	public void paint(Graphics2D g2d) {
		if(clickCount == 0) return;
		g2d.setColor(fg.getCurrentColor());
		int r = CircleRadiusCalc.calcRadius(start, end);
		g2d.fillOval(start.x -  r/2, start.y - r/2, r, r);
		g2d.setColor(bg.getCurrentColor());
		g2d.drawOval(start.x -  r/2, start.y - r/2 , r + 1, r + 1);
	}
}
