package hr.fer.zemris.java.fractals;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import hr.fer.zemris.java.fractals.viewer.FractalViewer;
import hr.fer.zemris.math.Complex;

/**
 * Class used to take user input and 
 * then call appropriate methods 
 * to generate fractals 
 * @author Tomislav Kurtović
 */
public class Newton {

	/**
	 * Main method that takes user input and calls 
	 * methods to generate fractals
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println("Welcome to Newton-Raphson iteration-based fractal viewer.");
		System.out.println("Please enter at least two roots, one root per line. Enter 'done' when done.");
		int i = 1;
		Scanner sc = new Scanner(System.in);
		List<Complex> factors = new ArrayList<>();
		while(true) {
			System.out.printf("Root %d >",i);
			if(sc.hasNextLine()) {
				String line = sc.nextLine();
				
				if(line.equals("done")) {
					System.out.println("Image of fractal will appear shortly. Thank you.");
					break;
				}
				
				if(line.isBlank()) {
				  System.out.println("Invalid input!");
				  continue;
				}
				
				try{
					double re = getReal(line);
					double im = getIm(line);
					factors.add(new Complex(re,im));
					i++;
				}catch(IllegalArgumentException ex) {
					System.out.println(ex.getMessage());
				}
			}
		}
		sc.close();
		FractalViewer.show(new IFractalProducerImpl(factors));
	}

	/**
	 * Used to get imaginary part of user input
	 * @param line user input
	 * @return imaginary part of complex number
	 */
	private static double getIm(String line) {
		String[] complexParts = line.trim().split("(?=[+-])");
		
		if(complexParts.length == 2) {
			if(!complexParts[1].contains("i")) {
				throw new IllegalArgumentException("Illegal argument found!");
			}
			return getValue(complexParts[1]);
		}
		
		if(complexParts.length == 1) {
			if(!complexParts[0].contains("i")) return 0.0;
			return getValue(complexParts[0]);
		}
		throw new IllegalArgumentException("Invalid number of arguments");
	}

	/**
	 * Used to get imaginary part of user input
	 * @param line user input
	 * @return imaginary part of complex number
	 */
	private static double getValue(String im) {
		im = im.replace("i", "");
		if (im.trim().equals("-")) {
			return -1.0;
		}
		if(im.trim().equals("+")) {
			return 1.0;
		}
		if(im.isBlank()) {
			return 1.0;
		}
		return Double.parseDouble(im.trim());
	}

	/**
	 * Used to get real part of user input
	 * @param line user input
	 * @return real part of complex number
	 */
	private static double getReal(String line) {
		String[] complexParts = line.trim().split("(?=[+-])");
		
		if(complexParts.length == 1) {
			if(complexParts[0].contains("i")) {
				return 0.0;
			} 
		}
		return Double.parseDouble(complexParts[0]);
	}
}
