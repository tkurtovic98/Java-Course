package hr.fer.zemris.java.hw07.demo3;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ObjectMultistackTest {

	@Test
	void testPush() {
		ObjectMultistack multistack = new ObjectMultistack();
		ValueWrapper point = new ValueWrapper(Integer.valueOf(100));
		multistack.push("point", point);
		assertEquals(100,multistack.peek("point").getValue());
		multistack.peek("point").add("50");
		assertEquals(150,multistack.peek("point").getValue());
		
		ValueWrapper point2 = new ValueWrapper(Integer.valueOf(5000));
		multistack.push("point", point2);
		assertEquals(5000,multistack.peek("point").getValue());
		
		assertThrows(NullPointerException.class, () -> multistack.push(null, point2));
	}

	@Test
	void testPop() {
		ObjectMultistack multistack = new ObjectMultistack();
		
		assertThrows(UnsupportedOperationException.class, () -> multistack.pop("key"));
		
		multistack.push("point", new ValueWrapper(Integer.valueOf(5)));
		multistack.push("grade", new ValueWrapper(Integer.valueOf(1)));
		multistack.push("distance", new ValueWrapper(Double.valueOf(25.4)));
		
		assertEquals(5,multistack.peek("point").getValue());
		assertEquals(1,multistack.peek("grade").getValue());
		assertEquals(25.4,multistack.peek("distance").getValue());
		
		multistack.pop("grade");

		assertEquals(5,multistack.peek("point").getValue());
		assertEquals(25.4,multistack.peek("distance").getValue());
		assertThrows(NullPointerException.class, () -> multistack.pop("grade"));
	}

	@Test
	void testPeek() {
		ObjectMultistack multistack = new ObjectMultistack();
		
		multistack.push("point", new ValueWrapper(Integer.valueOf(5)));
		multistack.push("grade", new ValueWrapper(Integer.valueOf(1)));
		multistack.push("distance", new ValueWrapper(Double.valueOf(25.4)));
		
		assertEquals(5,multistack.peek("point").getValue());
		assertEquals(1,multistack.peek("grade").getValue());
		assertEquals(25.4,multistack.peek("distance").getValue());
	}

	@Test
	void testIsEmpty() {
		ObjectMultistack multistack = new ObjectMultistack();
		assertTrue(multistack.isEmpty("key"));
		
		multistack.push("key", null);
		assertFalse(multistack.isEmpty("key"));
	}

}
