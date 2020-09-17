package hr.fer.zemris.lsystems.impl.commands;

import java.awt.Color;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;

/**
 * 
 * Class that represents the color command for
 * the LSystem.
 *
 * @author Tomislav Kurtović
 *
 */
public class ColorCommand implements Command {
	
	/**
	 * new color
	 */
	private Color color;
	
	/**
	 * 
	 * Constructor that instantiates a object
	 * used to change the painting color
	 * 
	 * @param color new color
	 */
	public ColorCommand(Color color) {
		this.color = color;
	}
	
	  /**
     * Executes the color command.
     * It takes in the current Context and
     * painter object responsible for painting on
     * the screen.
     * It then calls the method to 
     * update the painting color
     * 
     * @param ctx current context
     * @param painter painter to paint on the screen
     */
	@Override
	public void execute(Context ctx, Painter painter) {
		ctx.getCurrentState().setPaintingColor(color);
	}
}
