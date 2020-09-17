package hr.fer.java.zemris.hw07.observer2;

/**
 * Class used to display how many changes occured since 
 * instantiating this observer
 * 
 * @author Tomislav Kurtović
 */
public class ChangeCounter implements IntegerStorageObserver {
	/**
	 * counter of changes
	 */
	private int counter = 0;
	
	/**
	 * When value changes prints the counter value
	 * and increments the counter by 1
	 */
	@Override
	public void valueChanged(IntegerStorageChange istorage) {
		counter++;
		System.out.printf("Number of value changes since tracking: %d%n", counter); 
	}

}
