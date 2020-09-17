package hr.fer.zemris.java.hw17.jvdraw.objects;

import java.awt.Rectangle;

/**
 * {@link GeometricalObjectVisitor} used to visit all the objects to determine
 * the best size for exporting images of drawings
 * 
 * @author Tomislav Kurtović
 *
 */
public class GeometricalObjectBBCalculator implements GeometricalObjectVisitor {
	/**
	 * MinX of drawing
	 */
	private int minX = -1;
	/**
	 * MaxX of drawing
	 */
	private int maxX = 0;
	/**
	 * MinY of drawing
	 */
	private int minY = -1;
	/**
	 * MaxY of drawing
	 */
	private int maxY = 0;

	@Override
	public void visit(Line line) {
		int minLineX = line.start.x < line.end.x ? line.start.x : line.end.x;
		int minLineY = line.start.y < line.end.y ? line.start.y : line.end.y;

		int maxLineX = line.start.x > line.end.x ? line.start.x : line.end.x;
		int maxLineY = line.start.y > line.end.y ? line.start.y : line.end.y;

		checkValues(minLineX, minLineY, maxLineX, maxLineY);
	}

	@Override
	public void visit(Circle circle) {
		int minCircleX = circle.start.x - circle.radius / 2 - 1;
		int minCircleY = circle.start.y - circle.radius / 2 - 1;

		int maxCircleX = circle.start.x + circle.radius / 2 + 1;
		int maxCircleY = circle.start.y + circle.radius / 2 + 1;

		checkValues(minCircleX, minCircleY, maxCircleX, maxCircleY);
	}

	@Override
	public void visit(FilledCircle filledCircle) {
		int minCircleX = filledCircle.start.x - filledCircle.radius;
		int minCircleY = filledCircle.start.y - filledCircle.radius;

		int maxCircleX = filledCircle.start.x + filledCircle.radius;
		int maxCircleY = filledCircle.start.y + filledCircle.radius;

		checkValues(minCircleX, minCircleY, maxCircleX, maxCircleY);
	}

	/**
	 * Used to update all the values for the exported image
	 * 
	 * @param minDrawingX minimalX of {@link GeometricalObject} on canvas
	 * @param minDrawingY minimalY of {@link GeometricalObject} on canvas
	 * @param maxDrawingX maximalX of {@link GeometricalObject} on canvas
	 * @param maxDrawingY maximalY of {@link GeometricalObject} on canvas
	 */
	private void checkValues(int minDrawingX, int minDrawingY, int maxDrawingX, int maxDrawingY) {
		if (minX == -1) {
			minX = minDrawingX;
		} else {
			minX = Math.min(minDrawingX, minX);
		}

		if (minY == -1) {
			minY = minDrawingY;
		} else {
			minY = Math.min(minDrawingY, minY);
		}

		maxX = Math.max(maxDrawingX, maxX);
		maxY = Math.max(maxDrawingY, maxY);
	}

	/**
	 * Returns a box that represents the size of the exported image
	 * 
	 * @return bounding box
	 */
	public Rectangle getBoundingBox() {
		return new Rectangle(minX, minY, maxX - minX, maxY - minY);
	}

}
