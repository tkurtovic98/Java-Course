package hr.fer.java.zemris.hw07.observer1;

import java.util.ArrayList;
import java.util.List;

/**
 * Class that represents an integer storage unit.
 * It can add or remove instances of the {@link IntegerStorageObserver}
 * that are used to do a variety of actions upon change 
 * of the value of this storage.
 * @author Tomislav Kurtović
 *
 */
public class IntegerStorage {
	/**
	 * Current value of storage unit
	 */
	private int value;
	/**
	 * List of observers, has no elements at start
	 */
	private List<IntegerStorageObserver> observers;
	
	/**
	 * Default constructor that takes in the initial value
	 * of the storage and instantiates the list of
	 * observers
	 * @param initialValue initial value of storage
	 */
	public IntegerStorage(int initialValue) {
		this.value = initialValue;
		observers = new ArrayList<IntegerStorageObserver>();
	}
	
	/**
	 * Adds new observer to observer list if it does not
	 * already exist
	 * @param observer observer to add
	 */
	public void addObserver(IntegerStorageObserver observer) {
		if(!observers.contains(observer)) {
			observers.add(observer);
		}
	}
	
	/**
	 * Removes new observer from observer list if it exists
	 * @param observer observer to remove
	 */
	public void removeObserver(IntegerStorageObserver observer) {
		if(observers.contains(observer)) {
			observers.remove(observer);
		}
	}
	
	/**
	 * Clears the list of observers
	 */
	public void clearObservers() { 
		observers.clear();
	}
	
	/**
	 * Gets current value 
	 * @return current value
	 */
	public int getValue() {
		return value;
	}

	/**
	 * Sets the current value
	 * only if new value is different than the current value.
	 * It updates the current value and then notifies all 
	 * registered observers of the change
	 * @param value new value
	 */
	public void setValue(int value) {
		if(this.value != value) { 
			this.value = value; 
			if(observers != null) {
				List<IntegerStorageObserver >clone = 
						new ArrayList<IntegerStorageObserver>(observers);
				for(IntegerStorageObserver observer : clone) { 
					observer.valueChanged(this);
				}
			}
		}
	}
}
