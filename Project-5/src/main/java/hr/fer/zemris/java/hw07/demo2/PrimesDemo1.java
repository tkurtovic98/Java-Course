package hr.fer.zemris.java.hw07.demo2;

/**
 * Class that prints the first 5 primes 
 * @author Tomislav Kurtović
 */
public class PrimesDemo1 {

	/**
	 * Prints first 5 primes
	 * @param args
	 */
	public static void main(String[] args) {
		PrimesCollection primesCollection = new PrimesCollection(5);
		for(Integer prime : primesCollection) {
			System.out.println("Got prime: " + prime);
		}
	}
}
