package hr.fer.zemris.java.hw05.db;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class QueryParserTest {
	
	private String query1 = "query jmbag=\"0000000003\"";
	private String query2 = "query lastName = \"Blažić\"";
	private String query3 = "query firstName>\"A\" and lastName LIKE \"B*ć\"";
	private String query4 = "query firstName!=\"A\"";
	
	@Test
	void testQueryParser() {
		assertThrows(NullPointerException.class,()-> {QueryParser parser = new QueryParser("");});
	}

	@Test
	void testIsDirectQuery1() {
		QueryParser parser = new QueryParser(query1);
		assertEquals(true,parser.isDirectQuery());
	}

	@Test
	void testIsDirectQuery2() {
		QueryParser parser = new QueryParser(query2);
		assertEquals(false,parser.isDirectQuery());
	}
	
	@Test
	void testGetQueriedJMBAG() {
		QueryParser parser = new QueryParser(query1);
		assertEquals("0000000003",parser.getQueriedJMBAG());
	}
	
	@Test
	void testGetQueriedJMBAG2() {
		QueryParser parser = new QueryParser(query2);
		assertThrows(QueryParserException.class, ()-> parser.getQueriedJMBAG());
	}
	
	@Test
	void testGetQuery() {
		QueryParser parser = new QueryParser(query3);
		assertEquals(2, parser.getQuery().size());
		
		parser = new QueryParser(query4);
		assertEquals(1,parser.getQuery().size());
		assertFalse(parser.isDirectQuery());
	}
	
	@Test
	void exampleFromHWTest() {
		QueryParser qp1 = new QueryParser(" jmbag =\"0123456789\" ");
		assertTrue(qp1.isDirectQuery()); // true
		assertEquals("0123456789", qp1.getQueriedJMBAG()); // 0123456789
		assertEquals(1, qp1.getQuery().size()); // 1
		
		QueryParser qp2 = new QueryParser("jmbag=\"0123456789\" and lastName>\"J\"");
		assertFalse(qp2.isDirectQuery()); // false
		assertThrows(QueryParserException.class, () -> qp2.getQueriedJMBAG()); // would throw!
		assertEquals(2, qp2.getQuery().size()); // 2
	}

}
