package hr.fer.zemris.math;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Class that is a representation of 
 * a complex polynomial with factors 
 * @author Tomislav Kurtović
 *
 */
public class ComplexPolynomial {

	/**
	 * list of factors used to instantiate complex polynomial
	 */
	List<Complex> complexNumbers = new ArrayList<>();
	
	/**
	 * Constructor that accepts coeficients of polynomial
	 * @param factors
	 */
	public ComplexPolynomial(Complex ...factors) {
		for(Complex c : factors) {
			complexNumbers.add(c);
		}
	}

	/**
	 * Returns order of polynomial
	 * @return order of polynomial
	 */
	public short order() {
		return (short) (complexNumbers.size() -1) ;
	}
	
	/**
	 * Computes new polynomial that the result 
	 * of multiplying the two polynomials
	 * @param p polynomial to multiply
	 * @return computed polynomial
	 */
	public ComplexPolynomial multiply(ComplexPolynomial p) {
		int i=0;
		int j=0;
		Map<Integer,Complex> mapOfMultiples = new TreeMap<>();
		
		for(Complex num : complexNumbers) {
			j=0;
			for(Complex other : p.complexNumbers) {
				Complex complexToAdd = num.multiply(other);
				if(mapOfMultiples.containsKey(i+j)) {
					mapOfMultiples.put(i+j, mapOfMultiples.get(i+j).add(complexToAdd));
				} else {
					mapOfMultiples.put(i+j, complexToAdd);
				}
				j++;
			}
			i++;
		}
		
		Complex[] factors = new Complex[i+1];
		i=0;
		for(Integer key : mapOfMultiples.keySet()) {
			factors[i++] = mapOfMultiples.get(key);
		}
		return new ComplexPolynomial(factors);
	}

	/**
	 * Computes first derivative of this
	 * ComplexPolynomial 
	 * @return derivation of this polynomial
	 */
	public ComplexPolynomial derive() {
		Complex[] factors = new Complex[complexNumbers.size()-1]; 
		for(int index = 0; index < complexNumbers.size()-1; index++) {
			factors[index] = complexNumbers.get(index + 1).multiply(new Complex(index+1,0));
		}
		return new ComplexPolynomial(factors);
	}
	
	/** 
	 * Computes value of polynomial at given point
	 * @param z point to compute value of
	 * @return compute value
	 */
	public Complex apply(Complex z) {
		Complex complex = z.power(0).multiply(complexNumbers.get(0));
		for(int i = 1; i < complexNumbers.size(); i++) {
			complex = complex.add((z.power(i).multiply(complexNumbers.get(i))));
			i++;
		}
		return complex;
	}

	@Override
	public String toString() {
		List<String> numbers = new ArrayList<>();
		numbers.add(complexNumbers.get(0).toString());
		int i=0;
		for(Complex c : complexNumbers) {
			if(i == 0) {
				i++;
				continue;
			}
			numbers.add("("+c.toString()+")*z^"+i+"+");
			i++;
		}
		Collections.reverse(numbers);
		StringBuilder builder = new StringBuilder();
		for(String str : numbers) {
			builder.append(str);
		}
		return builder.toString();
	}
	
}
