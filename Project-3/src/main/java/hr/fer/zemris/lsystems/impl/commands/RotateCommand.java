package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;


/**
 * 
 * Class that represents the rotate command for
 * the LSystem.
 * It rotates the view by the given angle and
 * updates other variables
 *
 * @author Tomislav Kurtović
 *
 */
public class RotateCommand implements Command {
	
	/**
	 * Angle to rotate by
	 */
	private double angle;
	
	/**
	 * Constructor that instantiates an object
	 * to rotate the view
	 * 
	 * @param angle angle to rotate by
	 */
	public  RotateCommand(double angle) {
		this.angle = angle;
	}

	   /**
     * Executes the rotate command.
     * It takes in the current Context and
     * painter object responsible for painting on
     * the screen.
     * It then calls the method to 
     * retrieve the active state and 
     * alter its facing direction, which is used as 
     * the view
     * 
     * @param ctx current context
     * @param painter painter to paint on the screen
     */
	@Override
	public void execute(Context ctx, Painter painter) {
		ctx.getCurrentState().getFacingDirection().rotate(angle*Math.PI/180.);
	}
}
