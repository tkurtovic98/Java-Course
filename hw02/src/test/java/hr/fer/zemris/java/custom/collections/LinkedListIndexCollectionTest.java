package hr.fer.zemris.java.custom.collections;

import static org.junit.jupiter.api.Assertions.*;
import hr.fer.zemris.java.custom.collections.LinkedListIndexedCollection;
import org.junit.jupiter.api.Test;

class LinkedListIndexCollectionTest {

	@Test
	void testAdd() {
		LinkedListIndexedCollection list = new LinkedListIndexedCollection();
		list.add(Integer.valueOf(20));
		list.add("New York");
		list.add("San Francisco"); 
		
		assertEquals(20,list.get(0));
		assertEquals("New York",list.get(1));
		assertEquals("San Francisco",list.get(2));
		assertEquals(3,list.size());
		assertThrows(NullPointerException.class,()->list.add(null));
	}

	@Test
	void testClear() {
		LinkedListIndexedCollection list = new LinkedListIndexedCollection();
		list.add(Integer.valueOf(20));
		list.add("New York");
		list.add("San Francisco"); 
		list.add("San Francisco"); 
		list.add("Zagreb"); 
		
		assertEquals(20,list.get(0));
		assertEquals("New York",list.get(1));
		assertEquals("San Francisco",list.get(2));
		assertEquals("San Francisco",list.get(3));
		assertEquals("Zagreb",list.get(4));
		assertEquals(5,list.size());
		
		list.clear();
		
		assertEquals(0,list.size());
	}

	@Test
	void testLinkedListIndexedCollection() {
		LinkedListIndexedCollection list = new LinkedListIndexedCollection();
		assertEquals(0,list.size());
	}

	@Test
	void testLinkedListIndexedCollectionlistlection() {
		LinkedListIndexedCollection list  = new LinkedListIndexedCollection();
		list.add("Hey");
		list.add("Hello");
		list.add(Integer.valueOf(20));
		
		LinkedListIndexedCollection newList  = new LinkedListIndexedCollection(list);	
		assertEquals(3,newList.size());
		assertEquals("Hey",newList.get(0));
		assertEquals("Hello",newList.get(1));
		assertEquals(20,newList.get(2));
	}

	@Test
	void testGet() {
		LinkedListIndexedCollection list = new LinkedListIndexedCollection();
		list.add(Integer.valueOf(20));
		list.add("New York");
		
		assertEquals(20,list.get(0));
		assertEquals("New York",list.get(1));
	}

	@Test
	void testInsert() {
		LinkedListIndexedCollection list = new LinkedListIndexedCollection();
		list.add(Integer.valueOf(20));
		list.add("SF");
		list.add(Integer.valueOf(50));
		list.add("HELLO");
		
		list.insert("HEY", 3);
		
		assertEquals(20,list.get(0));
		assertEquals("SF",list.get(1));
		assertEquals(50,list.get(2));
		assertEquals("HEY",list.get(3));
		assertEquals("HELLO",list.get(4));
	}

	@Test
	void testIndexOf() {
		LinkedListIndexedCollection list = new LinkedListIndexedCollection();
		list.add(Integer.valueOf(20));
		list.add("SF");
		list.add(Integer.valueOf(50));
		list.add("HELLO");
		
		assertEquals(0,list.indexOf(20));
		assertEquals(1,list.indexOf("SF"));
		assertEquals(2,list.indexOf(50));
		assertEquals(3,list.indexOf("HELLO"));
	}

	@Test
	void testRemoveInt() {
		LinkedListIndexedCollection list = new LinkedListIndexedCollection();
		list.add(Integer.valueOf(20));
		list.add("SF");
		list.add(Integer.valueOf(50));
		list.add("HELLO");
		
		assertEquals(4,list.size());
		
		list.remove(1);
		
		assertEquals(20,list.get(0));
		assertEquals(50,list.get(1));
		assertEquals("HELLO",list.get(2));
		assertEquals(3, list.size());
	}
	
	 @Test
     void testRemoveLast() {
         LinkedListIndexedCollection list = new LinkedListIndexedCollection();
         list.add("SF");
         list.add("HELLO");
 
         list.remove(1);
     }

     @Test
     void testInsertToEnd() {
         LinkedListIndexedCollection list = new LinkedListIndexedCollection();
         list.add("SF");
         list.add("HELLO");

         list.insert("HEY", list.size());
     }

     @Test 
 	void testAddAll() {
 		
    	 LinkedListIndexedCollection list = new LinkedListIndexedCollection();
    	 list.add(Integer.valueOf(20));
    	 list.add("SF");
    	 list.add(Integer.valueOf(50));
    	 list.add("HELLO");
 		
 		assertEquals(20,list.get(0));
 		assertEquals("SF",list.get(1));
 		assertEquals(50,list.get(2));
 		assertEquals("HELLO",list.get(3));
 		assertEquals(4, list.size());
 		
 		LinkedListIndexedCollection list2 = new LinkedListIndexedCollection();
 		list2.addAll(list);
 		
 		assertEquals(4,list.size());
 		assertEquals(4,list2.size());
 		
 		assertEquals(20,list.get(0));
 		assertEquals("SF",list.get(1));
 		assertEquals(50,list.get(2));
 		assertEquals("HELLO",list.get(3));

 	}
 	
 	@Test
 	void testToArray() {
 		
 		LinkedListIndexedCollection list = new LinkedListIndexedCollection();
 		list.add(Integer.valueOf(20));
 		list.add("SF");
 		list.add(Integer.valueOf(50));
 		list.add("HELLO");
 		
 		Object[] array = list.toArray();
 		
 		assertEquals(20,array[0]);
 		assertEquals("SF",array[1]);
 		assertEquals(50,array[2]);
 		assertEquals("HELLO",array[3]);
 		assertEquals(4, array.length);
 		
 	}
 	@Test
 	void testContains() {
 		LinkedListIndexedCollection list = new LinkedListIndexedCollection();
 		list.add(Integer.valueOf(20));
 		list.add("SF");
 		list.add(Integer.valueOf(50));
 		list.add("HELLO");
 		
 		assertTrue(list.contains("SF"));
 		assertTrue(list.contains("HELLO"));
 		assertEquals(4,list.size());
 	}
     
}
