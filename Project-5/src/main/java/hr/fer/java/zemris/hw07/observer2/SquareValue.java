package hr.fer.java.zemris.hw07.observer2;

/**
 * Class used to display the square value of the current
 * value of the IntegerStorage
 *  
 * @author Tomislav Kurtović
 */
public class SquareValue implements IntegerStorageObserver {
	
	/**
	 * Squares current value of the {@link IntegerStorage} 
	 * and prints it to the screen.
	 * The value is not modified
	 */
	@Override
	public void valueChanged(IntegerStorageChange istorage) {
		int value = istorage.getAfterChange();
		System.out.printf("Provided new value: %d, square is %d%n",value, value * value );
	}
}
