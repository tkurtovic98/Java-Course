package hr.fer.zemris.lsystems.impl.commands;

import java.awt.Color;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;

/**
 * 
 * Class that represents the draw command for
 * the LSystem.
 * It takes a step that represents the 
 * coefficient with which the effectiveStep of
 * the drawer is multiplied with
 * 
 * @author Tomislav Kurtović
 *
 */
public class DrawCommand implements Command {
	
	/**
	 * Number to multiply with
	 */
	private double step;
	
	

	/**
	 * Constructor that instantiates a new 
	 * Object of type Command which
	 * draws on the screen
	 */
	public DrawCommand(double step) {
		this.step = step;
	}
	
	
    /**
     * Executes the drawing command.
     * It takes in the current Context and
     * painter object responsible for painting on
     * the screen.
     * It then calls a helper that updates
     * other fields.
     * 
     * @param ctx current context
     * @param painter painter to paint on the screen
     */
	@Override
	public void execute(Context ctx, Painter painter) {
		Color activeColor = ctx.getCurrentState().getPaintingColor();
		DrawAndSkipCommandHelper.executeHelper(ctx, step, activeColor, painter);
	}
}
