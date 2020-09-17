package hr.fer.zemris.java.hw05.db.Lexer;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class QueryLexerTest {

	
	private String query1 = "jmbag=\"0000000003\"";
	private String query2 = "lastName = \"Blažić\"";
	private String query3 = " firstName>\"A\" and lastName LIKE \"B*ć\"";
	private String query4 = " firstName!=\"A\"";
	
	@Test
	void testQueryLexer() {
		QueryLexer lexer = new QueryLexer(query1);
		assertNotNull(lexer);
		assertNull(lexer.getToken());
	}

	@Test
	void testGetToken() {
		QueryLexer lexer = new QueryLexer(query2);
		
		lexer.nextToken();
		assertEquals(TokenType.FIELD,lexer.getToken().getType());
		assertEquals("lastName",lexer.getToken().getValue());
		
		lexer.nextToken();
		assertEquals(TokenType.OPERATOR,lexer.getToken().getType());
		assertEquals("=", lexer.getToken().getValue());
		
		lexer.nextToken();
		assertEquals(TokenType.STRING,lexer.getToken().getType());
		assertEquals("Blažić", lexer.getToken().getValue());
	}
	
	@Test
	void testGetToken2() {
		QueryLexer lexer = new QueryLexer(query3);
		
		lexer.nextToken();
		assertEquals(TokenType.FIELD,lexer.getToken().getType());
		assertEquals("firstName",lexer.getToken().getValue());
		
		lexer.nextToken();
		assertEquals(TokenType.OPERATOR,lexer.getToken().getType());
		assertEquals(">", lexer.getToken().getValue());
		
		lexer.nextToken();
		assertEquals(TokenType.STRING,lexer.getToken().getType());
		assertEquals("A", lexer.getToken().getValue());
		
		lexer.nextToken();
		assertEquals(TokenType.OPERATOR,lexer.getToken().getType());
		assertEquals("and", lexer.getToken().getValue().toLowerCase());
		
		
		lexer.nextToken();
		lexer.nextToken();
		assertEquals(TokenType.OPERATOR,lexer.getToken().getType());
		assertEquals("LIKE", lexer.getToken().getValue());
	}
	
	@Test
	void testGetToken3() {
		QueryLexer lexer = new QueryLexer(query4);
		
		lexer.nextToken();
		assertEquals(TokenType.FIELD,lexer.getToken().getType());
		assertEquals("firstName",lexer.getToken().getValue());
		
		lexer.nextToken();
		assertEquals(TokenType.OPERATOR,lexer.getToken().getType());
		assertEquals("!=", lexer.getToken().getValue());
		
		lexer.nextToken();
		assertEquals(TokenType.STRING,lexer.getToken().getType());
		assertEquals("A", lexer.getToken().getValue());
	}
	
	@Test
	void testGetToken4() {
		String invalidQuery = " firstName!=\"A\" ! s";
		QueryLexer lexer = new QueryLexer(invalidQuery);
		
		lexer.nextToken();
		assertEquals(TokenType.FIELD,lexer.getToken().getType());
		assertEquals("firstName",lexer.getToken().getValue());
		
		lexer.nextToken();
		assertEquals(TokenType.OPERATOR,lexer.getToken().getType());
		assertEquals("!=", lexer.getToken().getValue());
		
		lexer.nextToken();
		assertEquals(TokenType.STRING,lexer.getToken().getType());
		assertEquals("A", lexer.getToken().getValue());
		
		assertThrows(QueryLexerExeption.class, ()-> lexer.nextToken());
	}


}
