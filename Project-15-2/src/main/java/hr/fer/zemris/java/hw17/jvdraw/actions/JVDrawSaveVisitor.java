package hr.fer.zemris.java.hw17.jvdraw.actions;

import java.awt.Color;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import hr.fer.zemris.java.hw17.jvdraw.objects.Circle;
import hr.fer.zemris.java.hw17.jvdraw.objects.FilledCircle;
import hr.fer.zemris.java.hw17.jvdraw.objects.GeometricalObject;
import hr.fer.zemris.java.hw17.jvdraw.objects.GeometricalObjectVisitor;
import hr.fer.zemris.java.hw17.jvdraw.objects.Line;

/**
 * {@link GeometricalObjectVisitor} used in 
 * visiting all the {@link GeometricalObject}s 
 * when user requests saving of drawing
 * @author Tomislav Kurtović
 *
 */
public class JVDrawSaveVisitor implements GeometricalObjectVisitor{
	/**
	 * {@link StringBuilder} used in constructing document for saving
	 */
	StringBuilder builder;
	/**
	 * Constructor
	 */
	public JVDrawSaveVisitor() {
		builder = new StringBuilder();
	}
	
	@Override
	public void visit(Line line) {
		String start = line.formatStart().replace(",", " ");
		String end = line.formatEnd().replace(",", " ");
		Color color = line.getColor();
		builder.append("LINE " + start + " " + end + " " + color.getRed() + " " + color.getGreen()+ " " + color.getBlue() + "\n");
	}

	@Override
	public void visit(Circle circle) {
		String start = circle.formatStart().replace(",", " ");
		int radius = circle.getRadius();
		Color color = circle.getColor();
		builder.append("CIRCLE " + start + " " + radius + " " + color.getRed() + " " + color.getGreen()+ " " + color.getBlue() + "\n");
	}

	@Override
	public void visit(FilledCircle filledCircle) {
		String start = filledCircle.formatStart().replace(",", " ");
		int radius = filledCircle.getRadius();
		Color fill = filledCircle.getColor();
		Color border = filledCircle.getBorderColor();
		builder.append("FCIRCLE " + start + " " + radius + " " + fill.getRed() + " " + fill.getGreen()+ " " + fill.getBlue() + " ");
		builder.append(border.getRed() + " " + border.getGreen() + " " + border.getBlue()+ "\n");
	}
	/**
	 * Saves drawing to path
	 * @param path destination
	 */
	public void save(Path path) {
		try {
			Files.writeString(path, builder.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
