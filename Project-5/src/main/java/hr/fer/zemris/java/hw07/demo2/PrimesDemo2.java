package hr.fer.zemris.java.hw07.demo2;

/**
 * Class that prints prime pairs
 * @author Tomislav Kurtović
 *
 */
public class PrimesDemo2 {

	/**
	 * Prints 2 prime pairs 
	 * @param args null in this case
	 */
	public static void main(String[] args) {
		PrimesCollection primesCollection = new PrimesCollection(2);
		for(Integer prime : primesCollection) {
			for(Integer prime2 : primesCollection) {
				System.out.println("Got prime pair: " + prime + ", " + prime2);
			}
		}
	}
}
