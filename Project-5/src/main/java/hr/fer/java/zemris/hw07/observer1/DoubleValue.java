package hr.fer.java.zemris.hw07.observer1;

/**
 * Class used to display the value of the 
 * value stored in {@link IntegerStorage} multiplied 
 * by 2
 * @author Tomislav Kurtović
 *
 */
public class DoubleValue implements IntegerStorageObserver {
	/**
	 * Number of times to write the double value
	 */
	private int timesToWrite;
	
	/**
	 * Default constructor that takes the number of times
	 * to write double value
	 * @param n times to write value
	 */
	public  DoubleValue(int n) {
		timesToWrite = n;
	}

	/**
	 * Used to print the value * 2 of the {@link IntegerStorage} 
	 * value upon change.
	 * After the n-th time the observer prints the value it
	 * removes itself from the {@link IntegerStorage} 
	 */
	@Override
	public void valueChanged(IntegerStorage istorage) {
		System.out.printf("Double value: %d%n", istorage.getValue() * 2);
		timesToWrite--;
		if(timesToWrite == 0) {
			istorage.removeObserver(this);
		}
	}
}
