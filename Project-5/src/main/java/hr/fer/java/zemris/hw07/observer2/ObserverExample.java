package hr.fer.java.zemris.hw07.observer2;

/**
 * Class used to demonstrate the {@link IntegerStorage} in action
 * and also check if the registered observers work correctly
 * @author Tomislav Kurtović
 *
 */
public class ObserverExample {
	/**
	 * Main method that instantiates new {@link IntegerStorage}
	 * and then adds some observers and sets the value 
	 * multiple times
	 * @param args null in this case
	 */
	public static void main(String[] args) {
		IntegerStorage istorage = new IntegerStorage(20);
		
		istorage.addObserver(new SquareValue());
		istorage.addObserver(new ChangeCounter());
		istorage.addObserver(new DoubleValue(1));
		istorage.addObserver(new DoubleValue(2));
		istorage.addObserver(new DoubleValue(2));
		
		istorage.setValue(5);
		istorage.setValue(2);
		istorage.setValue(25);
		
		istorage.setValue(13);
		istorage.setValue(22);
		istorage.setValue(15);
	}

}
