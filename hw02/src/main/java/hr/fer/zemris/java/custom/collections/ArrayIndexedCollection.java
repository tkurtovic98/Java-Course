package hr.fer.zemris.java.custom.collections;

import java.util.Arrays;
import java.util.Objects;

/**
 * 
 * The class is a Collection that represents a
 * dynamic array.
 * It contains all the methods from the Collection class
 * and has some own methods.
 * The variables that the class holds are the size of the
 * collection , the initial capacity and an array of 
 * Objects in which objects can be added or removed
 * The default capacity is set to 16
 * 
 * @author Tomislav Kurtović
 *
 */

public class ArrayIndexedCollection extends Collection {

	private static int DEFAULT_SIZE = 16;
	
	private int size;
	private Object[] elements; 
	
	
	/**
	 * Default constructor that sets the capacity to 16
	 */
	public ArrayIndexedCollection() {
		this(DEFAULT_SIZE);
	}
	
	/**
	 * Constructor that excepts the initialCapacity as
	 * its argument and sets the capacity of the Collection
	 * to it.
	 * If the initialCapacity is less than 1, an exception is thrown
	 * @throws IllegalArgumentException
	 * @param initialCapacity initial capacity of the Collection
	 */
	public ArrayIndexedCollection(int initialCapacity) {
		if(initialCapacity < 1) {
			throw new IllegalArgumentException (" Capacity must be greater than 1");
		}
		elements = new Object[initialCapacity];
		size = 0;
	}
	
	/**
	 * Constructor that excepts another Collection
	 * and sets the initialCapacity to 16.
	 * @param other other collection to copy elements from
	 */
	public ArrayIndexedCollection(Collection other) {
		this(other,DEFAULT_SIZE);
	}
	
	/**
	 * Constructor that excepts another Collection and initialCapacity.
	 * It then constructs a new Collection that has the same elements as
	 * the excepted one
	 * @param other
	 * @param initialCapacity
	 * @throws IllegalArgumentException
	 */
	public ArrayIndexedCollection(Collection other,int initialCapacity) {
		Objects.requireNonNull(other);
		if(initialCapacity < 1) {
			throw new IllegalArgumentException (" Capacity must be greater than 1");
		}
		if(initialCapacity < other.size())
			initialCapacity = other.size();
		elements = new Object[initialCapacity];
		size=0;
		this.addAll(other);
	}
	/**
	 * If the size and capacity are the same then a new collection is 
	 * reallocated and its capacity is double the size of the old one.
	 * The method then goes through the collection and adds the value 
	 * to the first empty space.
	 * If value is null then an exception is thrown.
	 * @param value value to add
	 * @throws NullPointerException
	 */
	@Override
	public void add(Object value) {
		checkArraySize();
		elements[size] = Objects.requireNonNull(value);
		size++;
	}
	
	@Override
	public boolean isEmpty() {
		return size() == 0;
	}

	@Override
	public int size() {
		return this.size;
	}
	
	/**
	 * Checks if the collection contains the value.
	 * Average complexity is O(n).
	 * 
	 */
	@Override
	public boolean contains(Object value) {
		if(value!=null) {
			for(int i=0;i<this.size;i++) {
				if(elements[i].equals(value)) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Removes the value from the collection.
	 * Average complexity is O(n).
	 * 
	 */
	@Override
	public boolean remove(Object value) {
		if(value!=null) {
			for(int i=0;i<this.size;i++) {
				if(elements[i].equals(value)) {
					remove(i);
					return true;
				}
			}
		}
		return false;
	}
	
	@Override
	public Object[] toArray() {
		Object[] newArray = new Object[this.size];
		for(int i=0;i<this.size;i++) {
			newArray[i] = elements[i];
		}
		return newArray;
	}

	@Override
	public void forEach(Processor processor) {
		for(int i=0;i<this.size;i++) {
			processor.process(elements[i]);
		}
	}

	@Override
	public void addAll(Collection other) {
		super.addAll(other);
	}

	/**
	 * Gets the object at the specified index.
	 * If index is invalid exception is thrown
	 * @param index
	 * @throws IndexOutOfBoundsException
	 * @return
	 */
	public Object get(int index) {
		if(index < 0 || index > this.size-1) {
			throw new IndexOutOfBoundsException("Index is invalid"); // Have to add message
		}
		return elements[index];
	}
	
	/**
	 * Clears the collection of all elements by making 
	 * them null values
	 */
	@Override
	public void clear() {
		for(int i = 0 ; i < this.size;i++) {
			elements[i] = null; 
		}
		size=0;
	}
	
	/**
	 * Inserts the object at the specified position.
	 * The old object is not overridden, because all the
	 * elements are shifted to the right
	 * 
	 * @param value object to insert
	 * @param position position to insert object
	 */
	public void insert(Object value , int position) {
		if(position < 0 || position > this.size) {
			throw new IndexOutOfBoundsException(); // Write message here
		}
		checkArraySize();
		if(elements[position] != null) {
			int i = position;
			while(i < size) {
				elements[i+1] = elements[i];
				i++;
			}
		}
		elements[position] = (Object) Objects.requireNonNull(value);
		size++;
	}

	
	/**
	 * Retrieves the index of the specified object if
	 * it exists and if it is not null
	 * @param value object to retrieve
	 * @return index of retrieved object if found, else -1
	 */
	public int indexOf(Object value) {
		if(value != null) {
			for(int i=0;i<size;i++) {
				if(elements[i].equals(value)) {
					return i;
				}
			}
		}
		return -1;
	}
	
	
	/**
	 * Removes the object at the specified index 
	 * @param index
	 */
	
	public void remove(int index) {
		if(index < 0 || index > size-1) {
			throw new IndexOutOfBoundsException();
		}
		int i = index;
		while(i < size) {
			elements[i] = elements[i+1];
			i++; 
		}
		size--;
	}
	
	/**
	 * Checks if array is not too big for the 
	 * add and insert methods
	 * 
	 */
	private void checkArraySize() {
		if(size == elements.length) {
			elements = Arrays.copyOf(elements, elements.length * 2);
		}
	}
}
