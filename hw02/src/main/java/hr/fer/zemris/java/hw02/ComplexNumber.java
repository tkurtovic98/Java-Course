package hr.fer.zemris.java.hw02;
/**
 * The class is used to make different
 * calculations with complex numbers that
 * are: addition, subtraction, division, multiplication
 * getting the power and getting the roots.
 * The class stores the real and imaginary part
 * of a complex number
 * 
 * @author Tomislav Kurtović
 *
 */
public class ComplexNumber {
	
	private static final double MIN_DIFFERENCE = Math.pow(10, -6);
	
	private double real;
	private double imaginary;
	
	/**
	 * The constructor of the class that 
	 * makes a new object with the
	 * values for the real and imaginary part
	 * 
	 *@param real real part of complex number
	 *@param imaginary imaginary part of complex number
	 */
	
	public ComplexNumber(double real,double imaginary) {
		this.real = real;
		this.imaginary = imaginary;
	}
	
	/**
	 * This method makes a new ComplexNumber object
	 * with only the real part and the imaginary part
	 * is set to 0 by default
	 *@param real real part of complex number
	 *@return complex number where real is the argument passed and imaginary = 0
	 */
	
	public static ComplexNumber fromReal(double real) {
		return new ComplexNumber(real,0);
	}
	
	/**
	 * This method makes a new ComplexNumber object
	 * with only the imaginary part and the real part
	 * is set to 0 by default
	 *@param imaginary imaginary part of complex number
	 *@return complex number where imaginary is the argument passed and real = 0
	 */
	public static ComplexNumber fromImaginary(double imaginary) {
		return new ComplexNumber(0,imaginary);
	}

	/**
	 * This method makes a new ComplexNumber object
	 * from the given magnitude and angle.
	 * The angle must be between 0 and 2PI and the magnitude cannot be negative.
	 *@param magnitude magnitude of complex number 
	 *@param angle angle of complex number
	 *@throws IllegalArgumentException
	 *@return complex number with the given magnitude and given angle
	 */
	public static ComplexNumber fromMagnitudeAndAngle(double magnitude,double angle) {
		if(angle<0 || angle >= 2 * Math.PI || magnitude < 0) {
			throw new IllegalArgumentException("Value of either angle or magnitude is invalid!");
		}
		return new ComplexNumber(magnitude * Math.cos(angle), magnitude * Math.sin(angle));
	}
	
	/**
	 * This method constructs a new complex number out of a string.
	 * The string has to be in the format ( real +- imaginary) and
	 * the i has to be at the end or else an exception will be thrown.
	 * The method splits the string from the + or - symbols in it and stores it
	 * into an array. 
	 * It then goes through the array and checks which values are stored so that it can
	 * assign the appropriate values to the new complex number that is being constructed.
	 * If the imaginary part has only i in it the method will give the imaginary part 
	 * the value of 1.0 or -1.0 depending whether the i has a - sign in front.
	 *@param s string that represents a complex number 
	 *@return complex number 
	 */

	public static ComplexNumber parse(String s) {
		if(s.contains("++") || s.contains("+-") || s.contains("-+") || s.contains("--")) {
			throw new IllegalArgumentException("Must not have two operators next to each other!");
		}
		String[] complexParts = s.split("(?=[+-])");
		String real="";
		String imaginary="";
		
	    for(String part : complexParts) {
	    	if(part.contains("i")) {
	    		if(part.endsWith("i")) {
	    			if(part.length() == 1) {
	    				imaginary = "i";
	    			}else {
	    				imaginary = part.substring(0,part.length()-1);
	    			}
	    		}else {
	    			throw new IllegalArgumentException("The symbol i has to be at the end!");
	    		}
	    	} else {
	    		real = part;
	    	}
	    }
	    double realValue = getValue(real);
	    double imaginaryValue = getValue(imaginary);
	    
	    return new ComplexNumber(realValue,imaginaryValue);
	}
		
	private static double getValue(String str) {
		double value = 0;

	    switch(str) {
    		case "-":
    			value = -1.0;
    			break;
    		case "i":
    			value = 1.0;
    			break;
    		case "":
    			break;
    		default:
    			value = Double.parseDouble(str);
    			break;
	    }
		
		return value;
	}

	/**
	 * Gets the real part of the complex number
	 * @return real part of complex number
	 */
	public double getReal() {
		return this.real;
	}
	
	/**
	 * Gets imaginary part of complex number
	 * @return imaginary part of complex number
	 */
	public double getImaginary() {
		return this.imaginary;
	}

	/**
	 * Gets magnitude of complex number
	 * The method square is a private method that helps with visibility
	 * @return magnitude of complex number
	 */
	public double getMagnitude() {
		return Math.sqrt(square(this));
	}
	
	/**
	 * Gets the angle of the complex number
	 * The values of the angle are in the range [0,2Pi]
	 * @return angle of complex number 
	 */
	public double getAngle() {
		double angle = Math.atan2(imaginary,real);
		if(angle < 0) {
			return angle + 2*Math.PI;
		}
		return angle;
	}
	
	
	/**
	 * Adds two complex numbers 
	 * 
	 * @param c complex number to add with
	 * @return new complex number
	 */
	public ComplexNumber add(ComplexNumber c) {
		return new ComplexNumber(this.real + c.getReal(), this.imaginary+c.getImaginary());
	}
	
	/**
	 * Subtracts two complex numbers
	 * @param c complex number to subtract with
	 * @return new complex number
	 */
	public ComplexNumber sub(ComplexNumber c) {
		return new ComplexNumber(this.real - c.getReal(), this.imaginary-c.getImaginary());
	}
	
	/**
	 * 
	 * Multiplies two complex numbers
	 * @param c complex number to multiply with
	 * @return new complex number
	 */
	public ComplexNumber mul(ComplexNumber c) {
		double realResult = this.real*c.getReal() - this.imaginary*c.getImaginary();
		double imaginaryResult = this.real*c.getImaginary() + this.imaginary*c.getReal();
	    return new ComplexNumber(realResult,imaginaryResult);
	}
	
	/**
	 * Divides two complex numbers by multiplying 
	 * the first (calling) complex number with a new complex number
	 * which is a conjugate of the passed complex number divided by the
	 * square value of the real and imaginary parts of that complex number
	 * @param c complex number to divide with
	 * @throws IllegalArgumentException
	 * @return new complex number
	 */
	public ComplexNumber div(ComplexNumber c) {
		double square = square(c);
		if(square == 0) {
			throw new IllegalArgumentException("Denominator cannot be 0");
		}
		ComplexNumber num = conjugate(c);
		return mul(new ComplexNumber(num.getReal()/square,num.getImaginary()/square));
	}
	
	/**
	 * Calculates the power of a complex number
	 * @param n the nth power
	 * @throws IllegalArgumentException
	 * @return new complex number
	 */
	public ComplexNumber power(int n) {
		if(n < 0) {
			throw new IllegalArgumentException("Argument cannot be less than 0");
		}
		double real = Math.pow(getMagnitude(), n)* Math.cos(getAngle()*n);
		double imag = Math.pow(getMagnitude(), n)* Math.sin(getAngle()*n);
		return new ComplexNumber(real,imag);
	}
	
	/**
	 * Calculates all the roots of a complex number and
	 * returns them in the form of an array
	 * @param n the nth root
	 * @return array of complex number roots 
	 */
	
	public ComplexNumber[] root(int n) {
		if(n<1) {
			throw new IllegalArgumentException("Argument cannot be less than 1");
		}
		ComplexNumber[] roots = new ComplexNumber[n] ;
		
		for(int i=0;i<n;i++) {
			roots[i] = evaluateNumber(i,n);
		}
		return roots;
	}
	
	/**
	 * 
	 * Prints the complex number on the screen
	 */
	
	@Override
	public String toString() {
		if(this.real == 0 && this.imaginary == 0) {
			return " ";
		}
		if(this.real == 0) {
			return Double.toString(this.imaginary) + "i";
		}
		if(this.imaginary == 0) {
			return Double.toString(this.real);
		}
		return Double.toString(this.real)  + Double.toString(this.imaginary) + "i";
		
	}
	
	@Override
	public int hashCode() {
		return super.hashCode();
	}
	
	/**
	 * Used to evaluate equality between two complex numbers.
	 * Two complex numbers are equal when their magnitude and angle are 
	 * equal and here it is check by subtracting the magnitudes and angles
	 * and checking if the values are less than 10^-6.
	 */

	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof ComplexNumber)) {
			return false;
		}
		ComplexNumber num = (ComplexNumber) obj;
		double magnitudeCheck = Math.abs(this.getMagnitude()-num.getMagnitude());
		double angleCheck = Math.abs(this.getAngle()-num.getAngle());
		return magnitudeCheck<MIN_DIFFERENCE && angleCheck<MIN_DIFFERENCE;
	}

	/**
	 * Used for the calculation of the roots 
	 * @param i
	 * @param n
	 * @return root of complex number
	 */
	private ComplexNumber evaluateNumber(int i,int n) {
		 double real = Math.pow(getMagnitude(), 1./n) * Math.cos((getAngle()+2*i*Math.PI)/n);
		 double img =  Math.pow(getMagnitude(), 1./n) * Math.sin((getAngle()+2*i*Math.PI)/n);
		 return new ComplexNumber(real,img);
	}

	private ComplexNumber conjugate(ComplexNumber c) {
		return new ComplexNumber(c.getReal(),-c.getImaginary());
	}
	
	private double square(ComplexNumber c) {
		return Math.pow(c.getReal(), 2) + Math.pow(c.getImaginary(), 2);
	}
	
}
