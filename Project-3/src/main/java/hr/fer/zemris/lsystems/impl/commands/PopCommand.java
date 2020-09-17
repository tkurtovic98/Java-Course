package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;

/**
 * 
 * Class that represents the pop command for
 * the LSystem.
 * It  removes the current active state from the
 * memory
 *
 * @author Tomislav Kurtović
 *
 */
public class PopCommand implements Command {
	
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
	@Override
	public void execute(Context ctx, Painter painter) {
		ctx.popState();
	}
}
