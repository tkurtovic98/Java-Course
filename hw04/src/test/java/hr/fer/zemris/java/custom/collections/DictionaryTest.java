package hr.fer.zemris.java.custom.collections;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class DictionaryTest {

	private Dictionary<String,Integer> dictionary;
	
	@Test
	void testDictrionary() {
		dictionary = new Dictionary<String,Integer>();
		
		assertEquals(0,dictionary.size());
		assertEquals(true,dictionary.isEmpty());
	}

	@Test
	void testIsEmpty() {
		dictionary = new Dictionary<String,Integer>();
		
		assertEquals(true,dictionary.isEmpty());
		dictionary.put("Hello", 5);
		assertEquals(false,dictionary.isEmpty());
	}

	@Test
	void testSize() {
		dictionary = new Dictionary<String,Integer>();
		
		assertEquals(0,dictionary.size());
		dictionary.put("Hello", 5);
		assertEquals(1,dictionary.size());
	}

	@Test
	void testClear() {
		dictionary = new Dictionary<String,Integer>();
		
		assertEquals(0,dictionary.size());
		dictionary.put("Hello", 5);
		dictionary.put("Hey", null);
		dictionary.put("Hi", 10);
		
		assertEquals(3,dictionary.size());
		
		dictionary.clear();
		
		assertEquals(0,dictionary.size());
	}

	@Test
	void testPut() {
		dictionary = new Dictionary<String,Integer>();
		
		assertEquals(0,dictionary.size());
		dictionary.put("Hello", 5);
		dictionary.put("Hey", null);
		dictionary.put("Hi", 10);
		assertEquals(3,dictionary.size());
		
		assertThrows(NullPointerException.class,()->{
			dictionary.put(null, 10);
		});
	}
	
	@Test
	void testPut2() {
		Dictionary<Integer,Integer> newDictionary = new Dictionary<Integer,Integer>();
		
		assertEquals(0,newDictionary.size());
		newDictionary.put(0, 5);
		newDictionary.put(1, null);
		newDictionary.put(2, 10);
		assertEquals(3,newDictionary.size());
		
		assertThrows(NullPointerException.class,()->{
			newDictionary.put(null, 10);
		});
	}

	@Test
	void testGet() {
		dictionary = new Dictionary<String,Integer>();
		
		dictionary.put("Hello", 5);
		dictionary.put("Hey", null);
		dictionary.put("Hi", 10);
		
		assertEquals(5,dictionary.get("Hello"));
		assertEquals(null,dictionary.get("Hey"));
		assertEquals(10,dictionary.get("Hi"));
		assertEquals(null,dictionary.get("HOWDY"));
	}

}
