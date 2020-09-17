package hr.fer.java.zemris.hw07.observer1;

/**
 * Class used to display the square value of the current
 * value of the IntegerStorage
 *  
 * @author Tomislav Kurtović
 *
 */
public class SquareValue implements IntegerStorageObserver {
	
	/**
	 * Squares current value of {@link IntegerStorage}
	 * and prints it on the screen.
	 * The value is not modified
	 */
	@Override
	public void valueChanged(IntegerStorage istorage) {
		int value = istorage.getValue();
		System.out.printf("Provided new value: %d, square is %d%n",value, value * value );
	}
}
