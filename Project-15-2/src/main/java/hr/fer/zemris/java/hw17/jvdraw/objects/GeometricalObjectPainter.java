package hr.fer.zemris.java.hw17.jvdraw.objects;

import java.awt.Graphics2D;

/**
 * {@link GeometricalObjectVisitor} that is used
 * to paint al the {@link GeometricalObject}s upon
 * visting them
 * @author Tomislav Kurtović
 *
 */
public class GeometricalObjectPainter implements GeometricalObjectVisitor {
	/**
	 * component used for painting
	 */
	private Graphics2D g2d;
	
	/**
	 * Constructor of object
	 * @param g2d {@link Graphics2D} component used for painting
	 */
	public GeometricalObjectPainter(Graphics2D g2d) {
		this.g2d = g2d;
	}
	
	@Override
	public void visit(Line line) {
		g2d.setColor(line.main);
		g2d.drawLine(line.start.x,line.start.y,line.end.x,line.end.y);
	}

	@Override
	public void visit(Circle circle) {
		g2d.setColor(circle.main);
		g2d.drawOval(circle.start.x - circle.radius/2, circle.start.y - circle.radius/2, circle.radius, circle.radius);
	}

	@Override
	public void visit(FilledCircle filledCircle) {
		int r = filledCircle.radius;
		g2d.setColor(filledCircle.main);
		g2d.fillOval(filledCircle.start.x -  r/2, filledCircle.start.y - r/2, r, r);
		g2d.setColor(filledCircle.border);
		g2d.drawOval(filledCircle.start.x -  r/2, filledCircle.start.y - r/2 , r + 1, r + 1);
	}
}
