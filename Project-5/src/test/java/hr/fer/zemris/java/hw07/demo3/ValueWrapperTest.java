package hr.fer.zemris.java.hw07.demo3;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ValueWrapperTest {
	
	///TEST FROM HW ///
	@Test
	void test1() {
		ValueWrapper v1 = new ValueWrapper(null); 
		ValueWrapper v2 = new ValueWrapper(null); 
		v1.add(v2.getValue());     // v1 now stores Integer(0); v2 still stores null. 
		assertEquals(0,v1.getValue());
		assertNull(v2.getValue());
	}
	
	@Test
	void test2() {
		ValueWrapper v3 = new ValueWrapper("1.2E1");
		ValueWrapper v4 = new ValueWrapper(Integer.valueOf(1));
		v3.add(v4.getValue());  // v3 now stores Double(13); v4 still stores Integer(1). 
		assertEquals(Double.valueOf(13),v3.getValue());
		assertEquals(1,v4.getValue());
	}
	
	@Test
	void test3() {
		ValueWrapper v5 = new ValueWrapper("12"); 
		ValueWrapper v6 = new ValueWrapper(Integer.valueOf(1)); 
		v5.add(v6.getValue());   // v5 now stores Integer(12); v6 still stores Integer(1). 
		
		assertEquals(Integer.valueOf(13),v5.getValue());
		assertEquals(1,v6.getValue());
	}
	
	@Test
	void test4() {
		ValueWrapper v7 = new ValueWrapper("Ankica");
		ValueWrapper v8 = new ValueWrapper(Integer.valueOf(1)); 
		assertThrows(RuntimeException.class,() -> v7.add(v8.getValue()));     // throws RuntimeException
	}
	/////
	

	@Test
	void testValueWrapper() {
		ValueWrapper wrapper = new ValueWrapper(Integer.valueOf(10));
		assertNotNull(wrapper);
		assertEquals(10,wrapper.getValue());
		
		wrapper = new ValueWrapper(true);
		assertEquals(true,wrapper.getValue());
	}
	
	@Test
	void testWithBadArgs() {
		ValueWrapper wrapper = new ValueWrapper(Integer.valueOf(10));
		assertThrows(RuntimeException.class, ()-> wrapper.add(true));
		assertThrows(RuntimeException.class, ()-> wrapper.subtract("fault"));
		assertThrows(RuntimeException.class, ()-> wrapper.multiply("1.2.3"));
		assertThrows(ArithmeticException.class, ()-> wrapper.divide(0));
	}

	@Test
	void testAdd() {
		ValueWrapper wrapper = new ValueWrapper(Integer.valueOf(10));
		wrapper.add(10);
		assertEquals(20,wrapper.getValue());

		wrapper.add(23);
		assertEquals(43,wrapper.getValue());
	}
	
	@Test
	void testAdd2() {
		ValueWrapper wrapper = new ValueWrapper(Integer.valueOf(5));
		wrapper.add(4.3);
		assertEquals(9.3, wrapper.getValue());
		
		wrapper.add("3");
		assertEquals(12.3, wrapper.getValue());
		
		wrapper.add("3.5");
		assertEquals(15.8, wrapper.getValue());
	}
	
	@Test
	void testAdd3() {
		ValueWrapper wrapper = new ValueWrapper("2");
		wrapper.add("4");
		assertEquals(6, wrapper.getValue());
		wrapper.add(3);
		assertEquals(9, wrapper.getValue());
		wrapper.add("3.5");
		assertEquals(12.5, wrapper.getValue());
	}
	

	@Test
	void testSubtract() {
		ValueWrapper wrapper = new ValueWrapper(Integer.valueOf(10));
		wrapper.subtract(10);
		assertEquals(0,wrapper.getValue());
		wrapper.subtract(23);
		assertEquals(-23,wrapper.getValue());
	}
	
	@Test
	void testSubtract2() {
		ValueWrapper wrapper = new ValueWrapper(Integer.valueOf(5));
		wrapper.subtract(4.3);
		assertTrue(((Double)wrapper.getValue() - 0.7 < 1./1000000) );
		wrapper.subtract("3");
		assertTrue(((Double)wrapper.getValue() + 2.3 < 1./1000000) );
		wrapper.subtract(10);
		assertTrue(((Double)wrapper.getValue() + 12.3  < 1./1000000) );
	}
	
	@Test
	void testSubtract3() {
		ValueWrapper wrapper = new ValueWrapper("200");
		wrapper.subtract("20");
		assertEquals(180, wrapper.getValue());
		wrapper.subtract(40);
		assertEquals(140, wrapper.getValue());
		wrapper.subtract("30.5");
		assertEquals(109.5, wrapper.getValue());
	}
	
	@Test
	void testMultiply() {
		ValueWrapper wrapper = new ValueWrapper(Integer.valueOf(10));
		wrapper.multiply(10);
		assertEquals(100,wrapper.getValue());
		wrapper.multiply(2.5);
		assertEquals(250.0,wrapper.getValue());
	}
	
	@Test 
	void testMultiply2() {
		ValueWrapper wrapper = new ValueWrapper("5.0");
		wrapper.multiply(1);
		assertTrue(((Double)wrapper.getValue() - 5 < 1./1000000) );
		wrapper.multiply("3");
		assertTrue(((Double)wrapper.getValue() - 15 < 1./1000000) );
		wrapper.multiply(10.0);
		assertTrue(((Double)wrapper.getValue() - 150  < 1./1000000) );
	}
	
	
	@Test
	void testDivide() {
		ValueWrapper wrapper = new ValueWrapper(Integer.valueOf(10));
		wrapper.divide(10);
		assertEquals(1,wrapper.getValue());
		wrapper.divide("0.5");
		assertEquals(2.0,wrapper.getValue());
	}
	
	@Test
	void testDivide2() {
		ValueWrapper wrapper = new ValueWrapper("5.0");
		wrapper.divide(1);
		assertTrue(((Double)wrapper.getValue() - 5 < 1./1000000) );
		wrapper.divide("2");
		assertTrue(((Double)wrapper.getValue() - 2.5 < 1./1000000) );
		wrapper.divide(0.1);
		assertTrue(((Double)wrapper.getValue() - 25  < 1./1000000) );
	}

	@Test
	void testNumCompare() {
		ValueWrapper wrapper = new ValueWrapper("5.0");
		int r = wrapper.numCompare("3.4");
		assertTrue(r > 0);
		r = wrapper.numCompare(10);
		assertTrue(r < 0);
		r = wrapper.numCompare(0);
		assertTrue(r > 0);
		wrapper = new ValueWrapper(null);
		assertEquals(0,wrapper.numCompare(null));
	}

	@Test
	void testSetValue() {
		ValueWrapper wrapper = new ValueWrapper(Integer.valueOf(10));
		wrapper.setValue(5);
		assertEquals(5, wrapper.getValue());
		wrapper.setValue("String");
		assertEquals("String", wrapper.getValue());
		wrapper.setValue(true);
		assertEquals(true, wrapper.getValue());
	}

	@Test
	void testGetValue() {
		ValueWrapper wrapper = new ValueWrapper(Integer.valueOf(10));
		assertEquals(10,wrapper.getValue());
		wrapper = new ValueWrapper("10.5");
		assertEquals("10.5",wrapper.getValue());
		wrapper = new ValueWrapper(10.0);
		assertEquals(Double.class, wrapper.getValue().getClass());
		wrapper = new ValueWrapper(null);
		assertNull(wrapper.getValue());
	}
}
