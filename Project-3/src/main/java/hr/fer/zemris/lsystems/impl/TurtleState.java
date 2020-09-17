package hr.fer.zemris.lsystems.impl;

import java.awt.Color;

import hr.fer.zemris.math.Vector2D;


/**
 * Representation of the object that
 * uses the current paint point and
 * orientation of the point
 * 
 * @author Tomislav Kurtović
 *
 */
public class TurtleState {
	
	
	/**
	 * Represents the  position of the point
	 */
	private Vector2D activePosition;
	/**
	 * Represents the  facing position of the point
	 */
	private Vector2D facingDirection;
	/**
	 * Represents the  painting color of the point
	 */
	private Color paintingColor;
	/**
	 * Represents the  unit length the point
	 */
	private double effectiveStepUnit;
	
	/**
	 * Constructor that constructs a new point
	 * 
	 * @param activePosition position of point
	 * @param facingDirection facing direction of point
	 * @param paintingColor color of point
	 * @param effectiveStepUnit unit length of point
	 */
	public TurtleState(Vector2D activePosition, Vector2D facingDirection, Color paintingColor,
			double effectiveStepUnit) {
		super();
		this.activePosition = activePosition;
		this.facingDirection = facingDirection;
		this.paintingColor = paintingColor;
		this.effectiveStepUnit = effectiveStepUnit;
	}
	
	/**
	 * Retrieves the  position
	 * @return active position
	 */
	public Vector2D getActivePosition() {
		return activePosition;
	}
	
	/**
	 * Sets the  position
	 */
	public void setActivePosition(Vector2D activePosition) {
		this.activePosition = activePosition;
	}
	
	/**
	 * Retrieves the facing position
	 * @return facing direction
	 */
	public Vector2D getFacingDirection() {
		return facingDirection;
	}
	
	/**
	 * Sets the facing position
	 */
	public void setFacingDirection(Vector2D facingDirection) {
		this.facingDirection = facingDirection;
	}

	/**
	 * Retrieves the painting color
	 * @return painting color
	 */
	public Color getPaintingColor() {
		return paintingColor;
	}
	/**
	 * Sets the  paiting color
	 */
	public void setPaintingColor(Color paintingColor) {
		this.paintingColor = paintingColor;
	}

	/**
	 * Retrieves the step unit
	 * @return step unit
	 */
	public double getEffectiveStepUnit() {
		return effectiveStepUnit;
	}

	/**
	 * Sets the  step unit
	 */
	public void setEffectiveStepUnit(double effectiveStepUnit) {
		this.effectiveStepUnit = effectiveStepUnit;
	}

	/**
	 * Copies this point and returns a new 
	 * point with same values
	 * 
	 * @return point to return
	 */
	public TurtleState copy() {
		return new TurtleState(activePosition.copy(),facingDirection.copy(),paintingColor,effectiveStepUnit);
	}
}
