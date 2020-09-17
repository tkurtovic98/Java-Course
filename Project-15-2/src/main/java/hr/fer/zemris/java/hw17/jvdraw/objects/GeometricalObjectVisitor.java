package hr.fer.zemris.java.hw17.jvdraw.objects;

/**
 * Interface that is used as a visitor
 * for visiting {@link GeometricalObject}s 
 * @author Tomislav Kurtović
 *
 */
public interface GeometricalObjectVisitor {
	
	/**
	 * Invoked when {@link Line} is visited
	 * @param line instance of {@link Line}
	 */
	public abstract void visit(Line line);
	/**
	 * Invoked when {@link Circle} is visited
	 * @param circle instance of {@link Circle}
	 */
	public abstract void visit(Circle circle);
	/**
	 * Invoked when {@link FilledCircle} is visited
	 * @param filledCircle instance of {@link FilledCircle}
	 */
	public abstract void visit(FilledCircle filledCircle);
}
