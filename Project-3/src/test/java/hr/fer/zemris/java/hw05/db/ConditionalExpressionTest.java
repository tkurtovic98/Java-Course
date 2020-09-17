package hr.fer.zemris.java.hw05.db;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ConditionalExpressionTest {

	
	
	@Test
	void testConditionalExpression() {
		ConditionalExpression expr = new ConditionalExpression(
				FieldValueGetters.LAST_NAME,
				"Bos*",
				ComparisonOperators.LIKE
				);
		assertNotNull(expr);
	}

	@Test
	void testGetFieldValueGetter() {
		ConditionalExpression expr = new ConditionalExpression(
				FieldValueGetters.LAST_NAME,
				"Bos*",
				ComparisonOperators.LIKE
				);
		
		StudentRecord record = new StudentRecord("007", "Bond", "James", 3);
		
		boolean recordSatisfies = expr.getComparisonOperator().satisfied(
				expr.getFieldValueGetter().get(record),
				expr.getLiteral()
				);
				
		assertFalse(recordSatisfies);
		assertEquals("Bond",expr.getFieldValueGetter().get(record));
		assertEquals(false,expr.getComparisonOperator().satisfied("James", expr.getLiteral()));
		assertEquals("Bos*",expr.getLiteral());
	}

	
}
