package hr.fer.zemris.math;


/**
 * 
 * Class that represents a 3D vector with it's start in
 * the point (0,0,0) and its end in the point x , y and z.
 * The class holds some methods that can be used to 
 * scale, add, subtract,multiply a vector and more.
 * It holds three variables x, y and z that represent the 
 * end point of the vector.
 * 
 * @author Tomislav Kurtović
 *
 */
public class Vector3 {

	/**
	 * X coordinate  of vector
	 */
	private double x;
	
	/**
	 *  Y coordinate of vector
	 */
	private double y;
	
	/**
	 *  Z coordinate of vector
	 */
	private double z;

	/**
	 * Constructor of the Vector3 object.
	 * It instantiates a new Vector3 object with the
	 * variables x, y and z
	 * @param x X coordinate of end point of vector to be constructed
	 * @param y Y coordinate of end point of vector to be constructed
	 * @param z Z coordinate of end point of vector to be constructed
	 */
	public Vector3(double x, double y, double z){
		this.x = x;
		this.y = y;
		this.z = z;
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
	 * Getter of the z variable 
	 * @return variable of Y coordinate
	 */
	public double getZ() {
		return z;
	}

	
	/**
	 * Used to calculate norm of vector
	 * @return norm of this vector
	 */
	public double norm() {
		return Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2) + Math.pow(z, 2));
	} 

	/**
	 * Used to normalize the vector
	 * @return normalized vector
	 */
	public Vector3 normalized() {
		return new Vector3(x/norm(), y/norm(), z/norm());
	} 
	
	/**
	 * Adds two vectors 
	 * @param other vector to add
	 * @return new vector
	 */
	public Vector3 add(Vector3 other) {
		return new Vector3(x + other.x, y + other.y, z + other.z);
	} 
	
	/**
	 * Subtracts two vectors 
	 * @param other vector to subtract
	 * @return new vector
	 */
	public Vector3 sub(Vector3 other) {
		return new Vector3(x - other.x, y - other.y, z - other.z);
	} 
	
	/**
	 * Used to calculate dot product of vector with
	 * the passed vector
	 * @param other vector to calculate dot product with
	 * @return dot product
	 */
	public double dot(Vector3 other) {
		return x*other.x + y*other.y + z*other.z;
	} 
	
	/**
	 * Used to calculate vector product of vector with
	 * the passed vector
	 * @param other vector to calculate vector product with
	 * @return vector product
	 */
	public Vector3 cross(Vector3 other) {
		return new Vector3
				(
				y*other.z -  z * other.y,
				z*other.x - x*other.z,
				x*other.y - y*other.x
				);
	}
	
	/**
	 * 
	 * Used to instantiate scaled vector from this vector for the given scale. It
	 * multiplies the x, y and z coordinate from this vector with the given scale.
	 * 
	 * @param scaler value to multiply the x,y and z coordinates with
	 * @return new scaled vector
	 */
	public Vector3 scale(double s) {
		return new Vector3(x*s, y*s, z*s);
		}
	
	/**
	 * 
	 * Used to calculate cosine between this vector and the other one
	 * 
	 * @param vetor to calculate cosine of angle
	 * @return cosine of angle between the vectors
	 */
	public double cosAngle(Vector3 other) {
		return dot(other)/(this.norm() * other.norm());
	}
	
	/**
	 * Converts vector into an array
	 * @return
	 */
	public double[] toArray() {
		return new double[]{x,y,z};
	}

	@Override
	public String toString() {
		return "(" + x + ", " + y + ", " + z + ")";
	}
}
