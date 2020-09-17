package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;

/**
 * 
 * Class that represents the scale command for
 * the LSystem.
 * It scales the active length of units 
 * by which the painter object paint
 *
 * @author Tomislav Kurtović
 *
 */

public class ScaleCommand implements Command {
	
	/**
	 * Factor used for scaling
	 */
	private double factor;
	
	/**
	 * Constructor that instantiates a object
	 * used to scale the unit length
	 * @param factor factor to scale by 
	 */
	public ScaleCommand(double factor) {
		this.factor = factor;
	}
	

    /**
     * Executes the scaling command.
     * It takes in the current Context and
     * painter object responsible for painting on
     * the screen.
     * It then calls the method to 
     * retrieve the active state and 
     * alter its effectiveStepUnit
     *
     * 
     * @param ctx current context
     * @param painter painter to paint on the screen
     */
	@Override
	public void execute(Context ctx, Painter painter) {
		ctx.getCurrentState().setEffectiveStepUnit(ctx.getCurrentState().getEffectiveStepUnit() * factor);
	}
}
