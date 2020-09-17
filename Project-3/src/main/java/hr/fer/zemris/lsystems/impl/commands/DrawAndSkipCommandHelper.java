package hr.fer.zemris.lsystems.impl.commands;

import java.awt.Color;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.math.Vector2D;

/**
 * 
 * Class that represents the a helper
 * for the {@link DrawCommand} and {@link SkipCommand}.
 * It has a single static method 
 *
 * @author Tomislav Kurtović
 *
 */

public class DrawAndSkipCommandHelper {

	/**
	 * Method used to retrieve the current position of the 
	 * painter and then calculate the new position by
	 * using the facing direction and it's angle 
	 * It rotates the current position for angle.
	 * It takes in the current Context and
     * painter object responsible for painting on
     * the screen.
     * It then draws or skips a line and updates the 
     * current position
	 * 
	 * @param ctx current context
	 * @param step step to multiply with
	 * @param activeColor color that should be painter or  a blank 
	 * @param painter painter to paint on the screen
	 */
	public static void executeHelper(Context ctx, double step, Color activeColor, Painter painter) {
		double currentPositionX = ctx.getCurrentState().getActivePosition().getX();
		double currentPositionY = ctx.getCurrentState().getActivePosition().getY();
		
		double facingDirectonX = ctx.getCurrentState().getFacingDirection().getX();
		double facingDirectonY = ctx.getCurrentState().getFacingDirection().getY();

		double angle = Math.atan2(facingDirectonY, facingDirectonX);
		double unitLength = step * ctx.getCurrentState().getEffectiveStepUnit();
		
		double newPositionX = currentPositionX + unitLength * Math.cos(angle);
		double newPositionY = currentPositionY + unitLength * Math.sin(angle);
		
		ctx.getCurrentState().setActivePosition(new Vector2D(newPositionX,newPositionY));
		
		painter.drawLine(currentPositionX, currentPositionY, newPositionX, newPositionY, activeColor, 1f);
	}
	
}
