package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;


/**
 * 
 * Class that represents the push command for
 * the LSystem.
 * It  adds a new state to the memory
 * and makes it the current active one
 *
 * @author Tomislav Kurtović
 *
 */

public class PushCommand implements Command {
	@Override
	
	   /**
     * Executes the push command.
     * It takes in the current Context and
     * painter object responsible for painting on
     * the screen.
     * It then calls the method to 
     * update the activeState 
     * 
     * @param ctx current context
     * @param painter painter to paint on the screen
     */
	public void execute(Context ctx, Painter painter) {
		ctx.pushState(ctx.getCurrentState().copy());
	}
}
