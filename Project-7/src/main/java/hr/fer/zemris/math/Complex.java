package hr.fer.zemris.math;

import java.util.ArrayList;
import java.util.List;

/**
 * Class that represents a complex number 
 * @author Tomislav Kurtović
 *
 */
public class Complex {

	/**
	 * Real part of complex number
	 */
	private double re;
	/**
	 * Real part of complex number
	 */
	private double im;

	/**
	 * Representation of Complex number (0,i0)
	 */
	public static final Complex ZERO = new Complex(0, 0);
	/**
	 * Representation of Complex number (1,i0)
	 */
	public static final Complex ONE = new Complex(1, 0);
	/**
	 * Representation of Complex number (-1,i0)
	 */
	public static final Complex ONE_NEG = new Complex(-1, 0);
	/**
	 * Representation of Complex number (0,i1)
	 */
	public static final Complex IM = new Complex(0, 1);
	/**
	 * Representation of Complex number (0,-i1)
	 */
	public static final Complex IM_NEG = new Complex(0, -1);

	/**
	 * Default consturctor
	 */
	public Complex() {
		re = 0;
		im = 0;
	}

	/**
	 * Constructor that assignes real and imaginary part of complex number
	 * @param re
	 * @param im
	 */
	public Complex(double re, double im) {
		this.re = re;
		this.im = im;
	}
	
	/**
	 * Gets real part of complex number
	 * @return real part of complex number
	 */
	public double getRe() {
		return re;
	}

	/**
	 * Gets imaginary part of complex number
	 * @return imaginary part of complex number
	 */
	public double getIm() {
		return im;
	}

	/**
	 * Gets module of complex number
	 * 
	 * @return module of complex number
	 */
	public double module() {
		return Math.sqrt(square(this));
	}

	/**
	 * Multiplies two complex numbers
	 * 
	 * @param c complex number to multiply with
	 * @return new complex number
	 */
	public Complex multiply(Complex c) {
		double realResult = this.re * c.re - this.im * c.im;
		double imaginaryResult = this.re * c.im + this.im * c.re;
		return new Complex(realResult, imaginaryResult);
	}

	/**
	 * Divides two complex numbers by multiplying the first (calling) complex number
	 * with a new complex number which is a conjugate of the passed complex number
	 * divided by the square value of the real and imaginary parts of that complex
	 * number
	 * 
	 * @param c complex number to divide with
	 * @throws IllegalArgumentException
	 * @return new complex number
	 */
	public Complex divide(Complex c) {
		double square = square(c);
		if (square == 0) {
			throw new IllegalArgumentException("Denominator cannot be 0");
		}
		Complex num = conjugate(c);
		return multiply(new Complex(num.re / square, num.im / square));
	}

	/**
	 * Adds two complex numbers
	 * 
	 * @param c complex number to add with
	 * @return new complex number
	 */
	public Complex add(Complex c) {
		return new Complex(this.re + c.re, this.im + c.im);
	}

	/**
	 * Subtracts two complex numbers
	 * 
	 * @param c complex number to subtract with
	 * @return new complex number
	 */
	public Complex sub(Complex c) {
		return new Complex(this.re - c.re, this.im - c.im);
	}

	/**
	 * Returns negation of this vector
	 * 
	 * @return -this
	 */
	public Complex negate() {
		return new Complex(-re, -im);
	}

	/**
	 * Calculates the power of a complex number
	 * 
	 * @param n the nth power
	 * @throws IllegalArgumentException
	 * @return new complex number
	 */
	public Complex power(int n) {
		if (n < 0) {
			throw new IllegalArgumentException("Argument cannot be less than 0");
		}
		double real = Math.pow(module(), n) * Math.cos(getAngle() * n);
		double imag = Math.pow(module(), n) * Math.sin(getAngle() * n);
		return new Complex(real, imag);
	}

	/**
	 * Calculates all the roots of a complex number and returns them in the form of
	 * an array
	 * 
	 * @param n the nth root
	 * @return array of complex number roots
	 */

	public List<Complex> root(int n) {
		if (n < 1) {
			throw new IllegalArgumentException("Argument cannot be less than 1");
		}
		List<Complex> roots = new ArrayList<>();

		for (int i = 0; i < n; i++) {
			roots.add(evaluateNumber(i, n));
		}
		return roots;
	}

	@Override
	public String toString() {
		return (im >= 0) ?"(" + re + "+i" + im + ")":"(" + re + "-i" + -1*im + ")";
	}

	/**
	 * Used for the calculation of the roots
	 * 
	 * @param i ith root
	 * @param n number of roots
	 * @return root of complex number
	 */
	private Complex evaluateNumber(int i, int n) {
		double real = Math.pow(module(), 1. / n) * Math.cos((getAngle() + 2 * i * Math.PI) / n);
		double img = Math.pow(module(), 1. / n) * Math.sin((getAngle() + 2 * i * Math.PI) / n);
		return new Complex(real, img);
	}

	/**
	 * Gets the angle of the complex number The values of the angle are in the range
	 * [0,2Pi]
	 * 
	 * @return angle of complex number
	 */
	private double getAngle() {
		double angle = Math.atan2(im, re);
		if (angle < 0) {
			return angle + 2 * Math.PI;
		}
		return angle;
	}

	/**
	 * Returns conjugate of the passed complex number
	 * 
	 * @param c complex number to conjugate
	 * @return conjguate of complex number
	 */
	private Complex conjugate(Complex c) {
		return new Complex(c.re, -c.im);
	}

	private double square(Complex c) {
		return Math.pow(c.re, 2) + Math.pow(c.im, 2);
	}

}
