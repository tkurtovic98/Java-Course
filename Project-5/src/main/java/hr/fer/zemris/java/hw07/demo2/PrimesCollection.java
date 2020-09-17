package hr.fer.zemris.java.hw07.demo2;

import java.util.Iterator;

/**
 * Class that is used to print primes to the screen without
 * using any multiple element storage.
 * 
 * @author Tomislav Kurtović
 *
 */
public class PrimesCollection implements Iterable<Integer>{
	/**
	 * Number of primes to print to screen
	 */
	private int numberOfPrimes;
	
	/**
	 * Default constructor that assigns the 
	 * number of primes to print
	 * @param numberOfPrimes number of primes to print
	 */
	public  PrimesCollection(int numberOfPrimes) {
		this.numberOfPrimes = numberOfPrimes;
	}
	
	/**
	 * Nested class used to generate primes 
	 * 
	 * @author Tomislav Kurtović
	 *
	 */
	private class Prime implements Iterator<Integer>{
		/**
		 * number of primes to generate
		 */
		private int numberOfPrimes2 = numberOfPrimes;
		/**
		 * first prime
		 */
		private int primeToCalculate = 2;
		
		@Override
		public boolean hasNext() {
			return  numberOfPrimes2 > 0;
		}

		/**
		 * Returns the next prime.
		 * If current number is not prime it is 
		 * incremented by 1 until it becomes one.
		 */
		@Override
		public Integer next() {
			while(!isPrime(primeToCalculate)) {
				primeToCalculate++;
			}
			numberOfPrimes2--;
			return primeToCalculate++;
		}

		/**
		 * Checks if given number is prime
		 * @param primeToCalculate number to check
		 * @return true if number is prime, false otherwise
		 */
		private boolean isPrime(int primeToCalculate) {
			for(int i = 2; 2*i <= primeToCalculate ; i++) {
				if(primeToCalculate % i == 0) return false;
			}
			return true;
		}
	}
	/**
	 * Returns new iterator 
	 */
	@Override
	public Iterator<Integer> iterator() {
		return new Prime() ;
	}
}
