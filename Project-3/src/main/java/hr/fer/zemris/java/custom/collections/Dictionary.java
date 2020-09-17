package hr.fer.zemris.java.custom.collections;

import java.util.Objects;

/**
 * Represents a map that stores key-value pairs
 * The key of this collection cannot be null, but 
 * it's value can. 
 * The class uses the implementation
 * of <code>ArrayIndexedCollection</code> to store values
 * 
 * @author Tomislav Kurtović
 *
 */
public class Dictionary<K,V> {
	
	
	/**
	 * 
	 * Generic class used to store key and value information
	 * that are later transfered to the collection where they are 
	 * stored as key-value pairs 
	 * 
	 * @author Tomislav Kurtović
	 *
	 * @param <K> key of the entry - usally a String
	 * @param <V> value of the entry
	 */
	private static class Entry<K,V>{
		
		/**
		 * key of the entry
		 */
		private K key;
		
		/**
		 * value of the entry
		 */
		private V value;
		
		/**
		 * Constructor of the entry that 
		 * takes in a non-null key and a value
		 * 
		 * @param key key of entry
		 * @param value value of entry
		 */
		public Entry(K key, V value) {
			this.key = Objects.requireNonNull(key);
			this.value = value;
		}
	}
	
    private ArrayIndexedCollection<Entry<K,V>> instance;
    
    public Dictionary() {
    	instance = new ArrayIndexedCollection<Entry<K,V>>();
    }
	
    public boolean isEmpty() {
    	return instance.isEmpty();
    }
    
    public int size() {
    	return instance.size();
    }
    
    public void clear() {
    	instance.clear();
    }
    
    
    /**
     * Adds the entry to the collection. 
     * If the key already exists in the collection,
     * the value of the entry is overwritten by the 
     * new value passed to this method and if 
     * not then a new entry is added.
     * 
     * @param key key of the entry
     * @param value value of the netry
     */
    public void put(K key, V value) {
    	Entry<K,V> entry = new Entry<K,V>(key,value);
    	int i = instance.indexOf(entry);
    	if(i != -1) {
    		instance.get(i).value = entry.value;
    		return;
    	}
    	instance.add(entry);
    }
    
    /**
     * Returns the value of the entry with the passed key if
     * such an entry exists, otherwise it returns null
     * 
     * @param key key of the entry to find
     * @return value of the entry if found, null otherwise
     */
    public V get (Object key) {
    	for(int i = 0; i < instance.size(); i++ ) {
    		if(instance.get(i).key.equals(key)) {
    			return instance.get(i).value;
    		}
    	}
		return null;
    }
}
