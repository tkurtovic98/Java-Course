package hr.fer.zemris.java.custom.collections;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;


/**
 * Class that represents an implementation of a hashtable.
 * It takes two parameters K and V and stores their values as
 * key-value pairs. 
 * The class implemets Iterable so it can make a Iterator to iterate
 * over the elements of this collection
 * 
 * @author Tomislav Kurtović
 *
 * @param <K> key parameter of the key-value pair
 * @param <V> value parameter of the key- value pair
 */
public class SimpleHashtable<K,V> implements Iterable <SimpleHashtable.TableEntry<K, V>>{
	
	
	/**
	 * Class used to store the key-value pairs in.
	 * It has a key , value and next variable. 
	 * This class is used by the SimpleHashtable class 
	 * in order to build the table
	 * 
	 * @author Tomislav Kurtović
	 *
	 * @param <K> key 
	 * @param <V> value
	 */
	public static class TableEntry<K,V> {
		
		/**
		 * Key of the entry
		 */
		private K key;
		/**
		 * Value of the entry 
		 */
		private V value;
		/**
		 * next table entry in slot
		 */
		private TableEntry<K,V> next;
		
		/**
		 * Constructor that makes a new TableEntry with 
		 * a non- null key , a value and next TableEntry, which 
		 * is usually null
		 * 
		 * @param key key of the entry
		 * @param value value of the enty
		 * @param next next entry
		 */
		public TableEntry(K key,V value, TableEntry<K,V> next){
			this.key = Objects.requireNonNull(key);
			this.value = value;
			this.next = next;
		}

		/**
		 * Getter for the key
		 * @return key
		 */
		public K getKey(){
			return this.key;
		}
		
		/**
		 * Getter for the value
		 * @return value
		 */
		public V getValue(){
			return this.value;
		}
		
		/**
		 * Setter for the value 
		 * @param v new value to set
		 */
		public void setValue(V v){
			this.value = v;
		}
	}
	
	/**
	 * Implementation of a Iterator.
	 * Used by the SimpleHashtable in order to iterate over its 
	 * elements and remove them if necessary 
	 * @author Tomislav Kurtović
	 *
	 */
	private class IteratorImpl implements Iterator<SimpleHashtable.TableEntry<K,V>>{
		
		/**
		 * checker if the hashtable has been modified
		 */
		private int activeModificationCount;
		/**
		 * position where the iterator is at currently
		 */
		private int position;
		/**
		 * index the iterator is at
		 */
		private int hash;
		/**
		 * getter for the entries
		 */
		private TableEntry<K, V> lastEntry;
		/**
		 * Last returned entry, active entry
		 */
		private TableEntry<K,V> returnEntry ;
		
		/**
		 * Constructor that start by setting all
		 * variables up 
		 */
		public IteratorImpl() {
			lastEntry = referenceTable[hash];
			returnEntry = null;
			activeModificationCount = modificationCount;
		}
		
		@Override
		public boolean hasNext() {
			isModified();
			return position < size;
		}

		@Override
		public TableEntry<K, V> next() {
			isModified();
			if(!hasNext()) {
				throw new NoSuchElementException();
			}
			if(lastEntry != null) {
				returnEntry = lastEntry;
				lastEntry = lastEntry.next;
				position++;
			} else {
				lastEntry = referenceTable[++hash];
				next();
			}
			return returnEntry;
		}
		
		/**
		 * Removes an entry from the table 
		 * if the table has not been modified.
		 * If it has than a ConcurrentModificationException is thrown
		 */
		public void remove() {
			isModified();
			if(!(containsKey(returnEntry.key))) {
				throw new IllegalStateException();
			}
			SimpleHashtable.this.remove(returnEntry.key);
			activeModificationCount++;
			position--;
		}
		
		/**
		 * Checks if the table has been modified while using the iterator
		 */
		private void isModified() {
			if(activeModificationCount != modificationCount) {
				throw new ConcurrentModificationException("Collection has been modified!");
			}
		}
	}
	
	/**
	 * Used to multiply the old array size to get a bigger one 
	 */
	private static int LENGTH_MULTIPLIER = 2;
	/**
	 * Percentage of allowed occupancy 
	 */
	private static double TABLE_PERCENTAGE_FULL = 0.75;

	/**
	 * Default capacity of the table if no initial capacity is provided
	 */
	private static int DEFAULT_CAPACITY = 16;
	/**
	 * Size of the table
	 */
	private int size ;
	/**
	 * Array of references to the entries of the table
	 */
	private TableEntry <K,V>[] referenceTable;
	/**
	 * Checker that is also used by the iterator
	 */
	private int modificationCount;
	
	/**
	 * Default constructor that sets capacity to default
	 */
	public SimpleHashtable(){
		this(DEFAULT_CAPACITY);
	}
	
	/**
	 * Constructor that sets capacity to the provided 
	 * capacity if the value is not less than 1.
	 * 
	 * @param capacity capacity of the table
	 */
	@SuppressWarnings("unchecked")
	public SimpleHashtable(int capacity){
		if(capacity < 1) {
			throw new IllegalArgumentException("Capacity must be greater than 1");
		}
		int tableSize = 1;
		while(tableSize < capacity){
			tableSize *=2;
		}
		referenceTable = new TableEntry[tableSize];
	}
	
	/**
	 * Checks if table is empty
	 * @return true if it is, false otherwise
	 */
	public boolean isEmpty(){
		return size == 0;
	}
	/**
	 * @return number of entries in table
	 */
	public int size(){
		return size;
	}
	
	/**
	 * Method used to generater hashCode
	 * @param key key of entry to generate hash
	 * @param length length of the active table
	 * @return hash for the specified key
	 */
	private int getHash(K key , int length) {
		return Math.abs(key.hashCode())%length;
	}
	
	/**
	 * Used to put the provided key and value into 
	 * the table as a key - value pair.
	 * It first checks if the ocupacy and then 
	 * generates an appropriate hash and adds the 
	 * values to the table
	 * @param key key to store
	 * @param value value to store
	 */
	public void put(K key, V value) {
		checkSize();
		int hash = getHash(key , referenceTable.length);
		TableEntry<K,V> newValue = new TableEntry<>(key,value,null);
		
		if(referenceTable[hash] == null ){
			referenceTable[hash] = newValue;
            size++;			
            modificationCount++;
            return;
		}
		TableEntry<K,V> old = referenceTable[hash];
		while(old.next != null) {
			if(old.key.equals(key)) {
				old.setValue(value);
				return;
			}
			old = old.next;
		}
		old.next = newValue;
		modificationCount++;
	    size++;
	}
	
	/**
	 * Checks if ocupacy is  equals to or greater than 75% 
	 * of the slots available. 
	 * If it is then it calls a method to copy 
	 * the entries 
	 */
	private void checkSize() {
		if((double) this.size / referenceTable.length >= TABLE_PERCENTAGE_FULL ) {
			copy();
		}
	}

	/**
	 * Method used to copy the entries from the old table to the 
	 * new table of twice the capacity than the old one. 
	 * It generates new hashes for the elements as the 
	 * new table is bigger so there are more hashes 
	 * to be generated
	 */
	
	@SuppressWarnings("unchecked")
	private void copy() {
		TableEntry<K,V>[] newReferenceTable = (TableEntry<K,V>[]) new TableEntry[referenceTable.length * LENGTH_MULTIPLIER];
		for(int i = 0 , oldLength = referenceTable.length; i < oldLength ; i++) {
			TableEntry<K,V> entryToCopy = referenceTable[i];
			while(entryToCopy != null) {
				int newHash = getHash(entryToCopy.key, oldLength * LENGTH_MULTIPLIER);
				TableEntry<K, V> newEntry = new TableEntry<K, V>(entryToCopy.key, entryToCopy.value, null);
				if(newReferenceTable[newHash] == null ) {
					newReferenceTable[newHash] = newEntry;
				} else {
					TableEntry<K, V> oldEntry = newReferenceTable[newHash];
					while(oldEntry.next != null) {
						oldEntry = oldEntry.next;
					}
					oldEntry.next = newEntry;
				}
				entryToCopy = entryToCopy.next; 
			}
		}
		referenceTable = newReferenceTable;
	}
	
	/**
	 * Gets the value of the specified 
	 * key if the key exists. 
	 * If not it does nothing.
	 * @param key key to get the value
	 * @return value 
	 */
	@SuppressWarnings("unchecked")
	public V get(Object key) {
		if(containsKey(key)) {
			int hash =  getHash((K)key , referenceTable.length);
			TableEntry<K, V> head = referenceTable[hash];
			while(head != null) {
				if(head.key.equals(key)) {
					return head.value;
				}
			head = head.next;
			}
		}
		return null;
	}
	
	/**
	 * Removes the value with the 
	 * specified key from the table
	 * @param key key to remove value 
	 */
	@SuppressWarnings("unchecked")
	public void remove(Object key) {
		if(containsKey(key)){
			int hash = getHash((K)key , referenceTable.length);
			TableEntry<K,V> head = referenceTable[hash];
			
			if(head.key.equals(referenceTable[hash].key)) {
				referenceTable[hash] = head.next;
				size--;
				modificationCount++;
				return;
			}
			while(!(head.next.key.equals(key))){
				head = head.next;
			}	
			head.next = head.next.next;
			
			modificationCount++;
			size--;
		}
	}
	
	/**
	 * Checks if the table contains the specified 
	 * key
	 * @param key key to check
	 * @return true if key exists , false otherwise
	 */
	@SuppressWarnings("unchecked")
	public boolean containsKey(Object key) {
		if(key == null) {
			return false;
		}
		int i = getHash((K)key, referenceTable.length);
		TableEntry<K,V> finder = referenceTable[i];
		while(finder != null){
		  if(finder.key.equals(key)) return true;
		  finder = finder.next;
		}
		return false;
	}
	
	/**
	 * Checks if table contains value 
	 * @param value value to check
	 * @return true if value exists , false otherwise
	 */
	public boolean containsValue(Object value){
		for(int i = 0; i < referenceTable.length; i++){
			TableEntry<K,V> finder = referenceTable[i];
			while(finder != null){
			  if(finder.value.equals(value)) return true;
			  finder = finder.next;
		  }
		}
		return false;
	}


	/**
	 * Clears the table from all its values, but
	 * does not change its capacity
	 */
	public void clear() {
		for(int i = 0 ; i < referenceTable.length ; i++) {
			while(referenceTable[i] != null) {
				remove(referenceTable[i].key);
			}
		}
		modificationCount++;
	}
	
	@Override
	public String toString() {
		if(size == 0) {
			return "";
		}
		StringBuilder builder = new StringBuilder();
		builder.append("[");
		for(int i = 0 ; i < referenceTable.length ; i++){
			TableEntry<K,V> entry = referenceTable[i];
			if(entry == null) {
				continue;
			}
			
			while(entry != null) {
				builder.append(entry.key + "=" + entry.value +", ");
				entry = entry.next;
			}
			
		}  
		builder.deleteCharAt(builder.length()-2);
		builder.append("]");
		return builder.toString();
	}

	/**
	 * Factory method to get new iterator for 
	 * this table 
	 */
	@Override
	public Iterator<SimpleHashtable.TableEntry<K, V>> iterator() {
		return new IteratorImpl();
	}
}
