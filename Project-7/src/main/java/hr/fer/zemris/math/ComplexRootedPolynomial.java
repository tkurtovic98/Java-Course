package hr.fer.zemris.math;

import java.util.ArrayList;
import java.util.List;

public class ComplexRootedPolynomial {
	/**
	 * Constant root
	 */
	Complex constant ;
	/**
	 * List of other roots 
	 */
	List<Complex> complexNumbers = new ArrayList<>();
	
	/**
	 * Constructor 
	 * @param constant constant of polynomial
	 * @param roots roots of polynomial
	 */
	public ComplexRootedPolynomial(Complex constant, Complex ... roots) {
		this.constant = constant;
		for(Complex num : roots) {
			complexNumbers.add(num);
		}
	}
	
	/**
	 * Used to compute value of polynomial at 
	 * given z
	 * @param z point to evaluate
	 * @return value of point z 
	 */
	public Complex apply(Complex z) {
		Complex complex = constant;
		for(Complex num : complexNumbers) {
			complex = complex.multiply(z.sub(num));
		}
		return complex;
	}

	/**
	 * used to find index of closest root for given complex number 
	 * z that is withi treshold 
	 * if there is no such root, returns -1 
	 * first root has index 0, second index 1, etc
	 * @param z
	 * @param treshold
	 * @return
	 */
	public int indexOfClosestRootFor(Complex z, double treshold) {
		if(complexNumbers.isEmpty()) {
			return -1;
		}
		int index = -1;
		double close = 1;
		double mod;
		for(Complex c : complexNumbers) {
			mod = z.sub(c).module();
			if(mod < treshold) {
				if(mod < close) {
					close = mod;
					index = complexNumbers.indexOf(c) ;
				}
			}
		}
		return index;
	}
	
//	/**
//	 * Used to check if the complex numbers are in a predefined 
//	 * treshold 
//	 * @param c complex number
//	 * @param z complex number
//	 * @param treshold treshold
//	 * @return true if numbers are in treshold, false otherwise
//	 */
//	private boolean CheckIfInTreshold(Complex z, Complex c, double treshold) {
//		if((z.sub(c).module()) < treshold) return true;
//		return false;
//	}

	/**
	 * Used to convert the {@link ComplexRootedPolynomial} into
	 * a {@link ComplexPolynomial} 
	 * @return new instance of a {@link ComplexPolynomial}
	 */
	public ComplexPolynomial toComplexPolynom() {
		ComplexPolynomial first = new ComplexPolynomial(new Complex[] {constant});
		for(Complex num : complexNumbers) {
			first = first.multiply(new ComplexPolynomial(new Complex[] {num.negate(),Complex.ONE}));
		}
		return first;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(constant.toString());
		for(Complex c : complexNumbers) {
			builder.append("*(z-"+ c.toString()+")");
		}
		return builder.toString();
	}
}
