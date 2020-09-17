package hr.fer.zemris.lsystems.impl.commands;

import java.awt.Color;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;

/**
 * 
 * Class that represents the draw command for
 * the LSystem.
 * It works the same as {@link DrawCommand}, but
 * does not draw a line
 * 
 * @author Tomislav Kurtović
 *
 */

public class SkipCommand implements Command {
	/**
	 * Number to multiply with
	 */
	private double step;
	
	/**
	 * Constructor that instantiates a new 
	 * Object of type Command which
	 * skips units on the screen
	 */
	public SkipCommand(double step) {
		this.step = step;
	}
	

    /**
     * Executes the skipping command.
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
		DrawAndSkipCommandHelper.executeHelper(ctx, step, new Color(0,0,0,0), painter);
	}
}
