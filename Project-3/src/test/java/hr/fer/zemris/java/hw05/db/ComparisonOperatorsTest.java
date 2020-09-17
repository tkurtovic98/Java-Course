package hr.fer.zemris.java.hw05.db;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ComparisonOperatorsTest {

	@Test
	void lessTest() {
		IComparisonOperator oper = ComparisonOperators.LESS;
		
		assertEquals(true,oper.satisfied("Ana", "Jasna"));
		assertEquals(false,oper.satisfied("Tomo", "Ivan"));
		assertEquals(true,oper.satisfied("K", "Z"));
	}

	@Test
	void lessOrEqualsTest() {
		IComparisonOperator oper = ComparisonOperators.LESS_OR_EQUALS;
		
		assertEquals(true,oper.satisfied("Ana", "Jasna"));
		assertEquals(true,oper.satisfied("Tomo", "Tomo"));
		assertEquals(true,oper.satisfied("Č", "Čiča"));
		assertEquals(false,oper.satisfied("AAA", "AA"));
	}
	
	@Test
	void greaterTest() {
		IComparisonOperator oper = ComparisonOperators.GREATER;
		
		assertEquals(false,oper.satisfied("Ana", "Jasna"));
		assertEquals(false,oper.satisfied("Tomo", "Tomo"));
		assertEquals(false,oper.satisfied("Č", "Čiča"));
		assertEquals(true,oper.satisfied("AAA", "AA"));
	}
	
	@Test
	void greaterOrEqualsTest() {
		IComparisonOperator oper = ComparisonOperators.GREATER_OR_EQUALS;
		
		assertEquals(false,oper.satisfied("Ana", "Jasna"));
		assertEquals(true,oper.satisfied("Tomo", "Tomo"));
		assertEquals(false,oper.satisfied("Č", "Čiča"));
		assertEquals(true,oper.satisfied("AAA", "AA"));
	}
	
	@Test
	void equalsTest() {
		IComparisonOperator oper = ComparisonOperators.EQUALS;
		
		assertEquals(false,oper.satisfied("Ana", "Jasna"));
		assertEquals(true,oper.satisfied("Tomo", "Tomo"));
		assertEquals(false,oper.satisfied("Č", "Čiča"));
		assertEquals(true,oper.satisfied("AAA", "AAA"));
	
	}
	
	@Test
	void notEqualsTest() {
		IComparisonOperator oper = ComparisonOperators.NOT_EQUALS;
		
		assertEquals(true,oper.satisfied("Ana", "Jasna"));
		assertEquals(false,oper.satisfied("Tomo", "Tomo"));
		assertEquals(true,oper.satisfied("Č", "Čiča"));
		assertEquals(false,oper.satisfied("AAA", "AAA"));
		assertEquals(true,oper.satisfied("1023", "100"));
	}
	
	@Test
	void likeTest() {
		IComparisonOperator oper = ComparisonOperators.LIKE;
		
		assertEquals(false,oper.satisfied("Zagreb", "Aba*"));
		assertEquals(false,oper.satisfied("AAA", "AA*AA"));
		assertEquals(true,oper.satisfied("AAAA", "AA*AA"));
		assertEquals(true,oper.satisfied("Tomislav", "To*av"));
		assertEquals(false,oper.satisfied("Neboder", "Ne*l"));
	}
}
