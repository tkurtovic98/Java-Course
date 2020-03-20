package hr.fer.zemris.java.hw01;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class FactorialTest {
	@Test
	void calculateFactorialTest1() {
		assertThrows(IllegalArgumentException.class, ()-> Factorial.calculateFactorial(21));
		assertThrows(IllegalArgumentException.class, ()-> Factorial.calculateFactorial(346));
	}
	@Test
	void calculateFactorialTest2() {
		assertThrows(IllegalArgumentException.class, ()-> Factorial.calculateFactorial(-1));
		assertThrows(IllegalArgumentException.class, ()-> Factorial.calculateFactorial(-100));

	}
	@Test
	void desiredResultTest() {
		long result = Factorial.calculateFactorial(10);
		assertEquals(3628800,result);
	}
	@Test
	void lessThanThree() {
		long result = Factorial.calculateFactorial(2);
		assertEquals(2,result);
		result = Factorial.calculateFactorial(0);
		assertEquals(1,result);
	}
}
