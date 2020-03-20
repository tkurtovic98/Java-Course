package hr.fer.zemris.java.custom.collections;
import static org.junit.jupiter.api.Assertions.*;
import hr.fer.zemris.java.custom.collections.ArrayIndexedCollection;
import org.junit.jupiter.api.Test;


class ArrayIndexedCollectionTest {

	
	@Test
	void testAdd() {
		ArrayIndexedCollection col = new ArrayIndexedCollection(2);
		col.add(Integer.valueOf(20));
		col.add("New York");
		col.add("San Francisco"); 
		
		assertEquals(20,col.get(0));
		assertEquals("New York",col.get(1));
		assertEquals("San Francisco",col.get(2));
		assertEquals(3,col.size());
		assertThrows(NullPointerException.class,()->col.add(null));
	}

	@Test
	void testClear() {
		ArrayIndexedCollection col = new ArrayIndexedCollection();
		col.add(Integer.valueOf(20));
		col.add("New York");
		col.add("San Francisco"); 
		col.add("San Francisco"); 
		col.add("Zagreb"); 
		
		assertEquals(20,col.get(0));
		assertEquals("New York",col.get(1));
		assertEquals("San Francisco",col.get(2));
		assertEquals("San Francisco",col.get(3));
		assertEquals("Zagreb",col.get(4));
		assertEquals(5,col.size());
		
		col.clear();
		
		assertEquals(0,col.size());
	}

	@Test
	void testArrayIndexedCollection() {
		ArrayIndexedCollection col = new ArrayIndexedCollection();	
		assertEquals(true,col.isEmpty());
	}

	@Test
	void testArrayIndexedCollectionInt() {
		assertThrows(IllegalArgumentException.class,()-> {ArrayIndexedCollection col  = new ArrayIndexedCollection(0);});	
	}

	@Test
	void testArrayIndexedCollectionCollection() {
		assertThrows(NullPointerException.class,()-> {ArrayIndexedCollection col  = new ArrayIndexedCollection(null);});	
	}

	@Test
	void testArrayIndexedCollectionCollectionInt1() {
		assertThrows(NullPointerException.class,()-> {ArrayIndexedCollection col  = new ArrayIndexedCollection(null,1);});	
	}
	
	@Test
	void testArrayIndexedCollectionCollectionInt2() {
		ArrayIndexedCollection col  = new ArrayIndexedCollection(5);
		col.add("Hey");
		col.add("Hello");
		col.add(Integer.valueOf(20));
		
		assertThrows(IllegalArgumentException.class,()-> {ArrayIndexedCollection newCol  = new ArrayIndexedCollection(col,0);});	
	}
	
	@Test
	void testArrayIndexedCollectionCollectionInt3() {
		ArrayIndexedCollection col  = new ArrayIndexedCollection(5);
		col.add("Hey");
		col.add("Hello");
		col.add(Integer.valueOf(20));
		
		ArrayIndexedCollection newCol  = new ArrayIndexedCollection(col,6);	
		assertEquals(3,newCol.size());
		assertEquals("Hey",newCol.get(0));
		assertEquals("Hello",newCol.get(1));
		assertEquals(20,newCol.get(2));
	}

	@Test
	void testGet() {
		ArrayIndexedCollection col = new ArrayIndexedCollection(5);
		col.add(Integer.valueOf(20));
		col.add("New York");
		
		assertEquals(20,col.get(0));
		assertEquals("New York",col.get(1));
	
	}

	@Test
	void testInsert() {
		ArrayIndexedCollection col = new ArrayIndexedCollection(4);
		col.add(Integer.valueOf(20));
		col.add("SF");
		col.add(Integer.valueOf(50));
		col.add("HELLO");
		
		col.insert("HEY", 3);
		
		assertEquals(20,col.get(0));
		assertEquals("SF",col.get(1));
		assertEquals(50,col.get(2));
		assertEquals("HEY",col.get(3));
		assertEquals("HELLO",col.get(4));
		
	}

	@Test
	void testIndexOf() {
		ArrayIndexedCollection col = new ArrayIndexedCollection();
		col.add(Integer.valueOf(20));
		col.add("SF");
		col.add(Integer.valueOf(50));
		col.add("HELLO");
		
		assertEquals(0,col.indexOf(20));
		assertEquals(1,col.indexOf("SF"));
		assertEquals(2,col.indexOf(50));
		assertEquals(3,col.indexOf("HELLO"));
	}

	@Test
	void testRemoveInt() {
		ArrayIndexedCollection col = new ArrayIndexedCollection(5);
		col.add(Integer.valueOf(20));
		col.add("SF");
		col.add(Integer.valueOf(50));
		col.add("HELLO");
		
		assertEquals(4,col.size());
		
		col.remove(1);
		
		assertEquals(20,col.get(0));
		assertEquals(50,col.get(1));
		assertEquals("HELLO",col.get(2));
		assertEquals(3, col.size());
	}
	
	@Test 
	void testAddAll() {
		
		ArrayIndexedCollection col = new ArrayIndexedCollection(5);
		col.add(Integer.valueOf(20));
		col.add("SF");
		col.add(Integer.valueOf(50));
		col.add("HELLO");
		
		assertEquals(20,col.get(0));
		assertEquals("SF",col.get(1));
		assertEquals(50,col.get(2));
		assertEquals("HELLO",col.get(3));
		assertEquals(4, col.size());
		
		ArrayIndexedCollection col2 = new ArrayIndexedCollection();
		col2.addAll(col);
		
		assertEquals(4,col.size());
		assertEquals(4,col2.size());
		
		assertEquals(20,col.get(0));
		assertEquals("SF",col.get(1));
		assertEquals(50,col.get(2));
		assertEquals("HELLO",col.get(3));

	}
	
	@Test
	void testToArray() {
		
		ArrayIndexedCollection col = new ArrayIndexedCollection(5);
		col.add(Integer.valueOf(20));
		col.add("SF");
		col.add(Integer.valueOf(50));
		col.add("HELLO");
		
		Object[] array = col.toArray();
		
		assertEquals(20,array[0]);
		assertEquals("SF",array[1]);
		assertEquals(50,array[2]);
		assertEquals("HELLO",array[3]);
		assertEquals(4, array.length);
		
	}
	
	@Test
	void testContains() {
		ArrayIndexedCollection col = new ArrayIndexedCollection(5);
		col.add(Integer.valueOf(20));
		col.add("SF");
		col.add(Integer.valueOf(50));
		col.add("HELLO");
		
		assertTrue(col.contains("SF"));
		assertTrue(col.contains("HELLO"));
		assertEquals(4,col.size());
	}

}
