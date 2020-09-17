package hr.fer.zemris.math;

import java.util.Objects;

/**
 * 
 * Class that represents a vector with it's start in
 * the point (0,0) and its end in the point x and y.
 * The class holds some methods that can be used to 
 * translate, rotate, scale a vector and more.
 * It holds two variables x and y that represent the 
 * end point of the vector.
 * 
 * @author Tomislav Kurtović
 *
 */
public class Vector2D {

	/**
	 * X coordinate of end point of vector
	 */
	private double x;
	
	/**
	 *  Y coordinate of end point of vector
	 */
	private double y;

	/**
	 * Constructor of the Vector2D object.
	 * It instantiates a new Vector2D object with the
	 * variables x and y
	 * @param x X coordinate of end point of vector to be constructed
	 * @param y Y coordinate of end point of vector to be constructed
	 */
	public Vector2D(double x, double y){
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Getter of the x variable 
	 * @return variable of X coordinate
	 */
	public double getX(){
		return x;
	}
	
	/**
	 * Getter of the y variable 
	 * @return variable of Y coordinate
	 */
	public double getY(){
		return y;
	}

	/**
	 * Translates this vector given the 
	 * offset vector.
	 * It adds the components.
	 * 
	 * @param offset offset vector
	 */
	public void translate(Vector2D offset){
		Objects.requireNonNull(offset);
		this.x = translateX(offset.getX());
		this.y = translateY(offset.getY());
	}
	
	/**
	 * Instantiates new translated vector
	 * from this vector without changing it.
	 * 
	 * @param offset offset vector
	 * @return new translated vector
	 */
	public Vector2D translated(Vector2D offset){
		Objects.requireNonNull(offset);
		return new Vector2D(translateX(offset.getX()),translateY(offset.getY()));	
	}
	
	/**
	 * Used to translate the x coordinate of the vector
	 * @param x x offset
	 * @return translated x coordinate
	 */
	private double translateX(double x){
		return this.x + x;
	}
	
	/**
	 * Used to translate the y coordinate of the vector
	 * @param y y offset
	 * @return translated y coordinate
	 */
	private double translateY(double y){
		return this.y + y;
	}
	
	/**
	 * Used to rotate this vector for the 
	 * given angle.
	 * The angle should be in radians between 0 and 2pi
	 * @param angle angle to rotate the vector for
	 */
	public void rotate(double angle){
		double xRotated = rotateX(angle);
		double yRotated = rotateY(angle);
		this.x = xRotated;
		this.y = yRotated;
	}
	
	/**
	 * Used to instantiate a new rotated vector
	 * from this vector for the given angle without changing
	 * this vector.
	 * The angle should be in radians between 0 and 2pi
	 * @param angle angle to rotate the vector for
	 * @return new rotated vector
	 */
	public Vector2D rotated(double angle){
		double xRotated = rotateX(angle);
		double yRotated = rotateY(angle);
		return new Vector2D(xRotated,yRotated);
	}
	
	
	/**
	 * Helping method used to translate the x coordinate 
	 * when given the angle to translate for
	 * 
	 * @param angle to translate
	 * @return translated x coordinate
	 */
	private double rotateX(double angle) {
		return x * Math.cos(angle) - y * Math.sin(angle);
	}

	/**
	 * Helping method used to translate the y coordinate when given the angle to
	 * translate for
	 * 
	 * @param angle to translate
	 * @return translated y coordinate
	 */
	private double rotateY(double angle) {
		return x * Math.sin(angle) + y * Math.cos(angle);
	}

	/**
	 * Used to scale this vector for the given scaler. It multiplies the x and y
	 * coordinate with the given scaler.
	 * 
	 * @param scaler value to multiply the x and y coordinates with
	 */
	public void scale(double scaler) {
		this.x = scaleX(scaler);
		this.y = scaleY(scaler);
	}

	/**
	 * 
	 * Used to instantiate scaled vector from this vector for the given scaler. It
	 * multiplies the x and y coordinate from this vector with the given scaler.
	 * 
	 * @param scaler value to multiply the x and y coordinates with
	 * @return new scaled vector
	 */
	public Vector2D scaled(double scaler) {
		return new Vector2D(scaleX(scaler), scaleY(scaler));
	}

	/**
	 * Helping method used to scale the x coordinate.
	 * 
	 * @param scaler value to scale with
	 * @return scaled x coordinate
	 */
	private double scaleX(double scaler) {
		return this.x * scaler;
	}

	/**
	 * Helping method used to scale the y coordinate.
	 * 
	 * @param scaler value to scale with
	 * @return scaled y coordinate
	 */
	private double scaleY(double scaler) {
		return this.y * scaler;
	}

	/**
	 * Used to get a copy of this vector
	 * 
	 * @return new vector that is a copy of this vector
	 */
	public Vector2D copy() {
		return new Vector2D(this.x, this.y);
	}
}
