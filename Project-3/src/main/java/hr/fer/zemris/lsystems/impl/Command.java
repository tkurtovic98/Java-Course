package hr.fer.zemris.lsystems.impl;

import hr.fer.zemris.lsystems.Painter;

/**
 * Interface used to execute all 
 * the commands that the 
 * the LSystem has
 * 
 * @author Tomislav Kurtović
 *
 */
public interface Command {
	
	/**
     * Executes the command.
     * It takes in the current Context and
     * painter object responsible for painting on
     * the screen.
     * 
     * @param ctx current context
     * @param painter painter to paint on the screen
     */
	void execute(Context ctx, Painter painter);

}
