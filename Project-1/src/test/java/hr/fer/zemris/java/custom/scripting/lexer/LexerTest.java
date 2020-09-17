package hr.fer.zemris.java.custom.scripting.lexer;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import hr.fer.zemris.java.hw03.prob1.LexerException;

class LexerTest {

	
	//Testing constructor
	@Test
	void testLexer() {
		 Lexer lexer = new Lexer("");
		 assertNotNull(lexer);
		 
	}
	
	//Testing for text tokens
	@Test
	void testNextTokenForText() {
		String text = "This is sample text. \n  {$";
		Lexer lexer = new Lexer(text);
		
		lexer.nextToken();
		assertEquals(TokenType.TEXT,lexer.getToken().getType());
		lexer.nextToken();
		assertEquals(TokenType.BEGINOFTAG,lexer.getToken().getType());
		lexer.nextToken();
		assertEquals(TokenType.EOF,lexer.getToken().getType());
		assertThrows(LexerException.class, () -> lexer.nextToken());
		
	}
	
	//Testing for tag tokens
	@Test
	void testNextTokenForTag() {
		String text = "{$ FOR i 1 10 1 $}";
		Lexer lexer = new Lexer(text);
		
		lexer.nextToken();
		assertEquals(TokenType.BEGINOFTAG,lexer.getToken().getType());
		
		lexer.setState(LexerState.TAG);
		
		lexer.nextToken();
		assertEquals(TokenType.TAGNAME,lexer.getToken().getType());
		
		lexer.nextToken();
		assertEquals(TokenType.VARIABLE,lexer.getToken().getType());
		
		lexer.nextToken();
		assertEquals(TokenType.NUMBER,lexer.getToken().getType());
		
		lexer.nextToken();
		assertEquals(TokenType.NUMBER,lexer.getToken().getType());
		
		lexer.nextToken();
		assertEquals(TokenType.NUMBER,lexer.getToken().getType());
		
		lexer.nextToken();
		assertEquals(TokenType.ENDOFTAG,lexer.getToken().getType());
		
		lexer.nextToken();
		assertEquals(TokenType.EOF,lexer.getToken().getType());
		assertThrows(LexerException.class, () -> lexer.nextToken());
		
	}
	
	//Another tag test
	
	@Test
	void testNextTokenOtherTag() {
		String text = "{$ = i i @sin 1 10 1 $}";
		Lexer lexer = new Lexer(text);
		
		lexer.nextToken();
		assertEquals(TokenType.BEGINOFTAG,lexer.getToken().getType());
		assertEquals("{$",lexer.getToken().getValue());
		
		lexer.setState(LexerState.TAG);
		
		lexer.nextToken();
		assertEquals(TokenType.TAGNAME,lexer.getToken().getType());
		assertEquals("=",lexer.getToken().getValue());
		
		lexer.nextToken();
		assertEquals("i",lexer.getToken().getValue());
		assertEquals(TokenType.VARIABLE,lexer.getToken().getType());
		
		lexer.nextToken();
		assertEquals(TokenType.VARIABLE,lexer.getToken().getType());
		
		lexer.nextToken();
		assertEquals(TokenType.FUNCTION,lexer.getToken().getType());
		
		lexer.nextToken();
		assertEquals(TokenType.NUMBER,lexer.getToken().getType());
		
		lexer.nextToken();
		assertEquals(TokenType.NUMBER,lexer.getToken().getType());
		
		lexer.nextToken();
		assertEquals(TokenType.NUMBER,lexer.getToken().getType());
		
		lexer.nextToken();
		assertEquals(TokenType.ENDOFTAG,lexer.getToken().getType());
		
		lexer.nextToken();
		assertEquals(TokenType.EOF,lexer.getToken().getType());
		assertThrows(LexerException.class, () -> lexer.nextToken());
	}

	@Test
	void testCombinationTextAndTag() {
		String text = "This is sample text. \n {$ IF -23 - 1 10 1 $}";
		Lexer lexer = new Lexer(text);
		
		lexer.nextToken();
		assertEquals(TokenType.TEXT,lexer.getToken().getType());
		lexer.nextToken();
		assertEquals(TokenType.BEGINOFTAG,lexer.getToken().getType());
		
		lexer.setState(LexerState.TAG);
		
		lexer.nextToken();
		assertEquals(TokenType.TAGNAME,lexer.getToken().getType());
		
		lexer.nextToken();
		assertEquals(TokenType.NUMBER,lexer.getToken().getType());
		
		lexer.nextToken();
		assertEquals('-',lexer.getToken().getValue());
		assertEquals(TokenType.OPERATOR,lexer.getToken().getType());
		
		lexer.nextToken();
		assertEquals(TokenType.NUMBER,lexer.getToken().getType());
		
		lexer.nextToken();
		assertEquals(TokenType.NUMBER,lexer.getToken().getType());
		
		lexer.nextToken();
		assertEquals(TokenType.NUMBER,lexer.getToken().getType());
		
		lexer.nextToken();
		assertEquals(TokenType.ENDOFTAG,lexer.getToken().getType());
		
		lexer.nextToken();
		assertEquals(TokenType.EOF,lexer.getToken().getType());
		assertThrows(LexerException.class, () -> lexer.nextToken());
	}

	@Test
	void testEscapeInText() {
		String text = "{ bla } hello \\{$=1$}";
		Lexer lexer = new Lexer(text);
		
		lexer.nextToken();
		assertEquals(TokenType.TEXT,lexer.getToken().getType());
		assertEquals("{ bla } hello {$=1$}",lexer.getToken().getValue());
	}
	@Test
	void testQuote() {
		String document2 = "{$=   \" \\\\n 1\"  $}";
		Lexer lexer = new Lexer(document2);
		
		lexer.nextToken();
		assertEquals(TokenType.BEGINOFTAG,lexer.getToken().getType());
		
		lexer.setState(LexerState.TAG);
		
		lexer.nextToken();
		assertEquals(TokenType.TAGNAME,lexer.getToken().getType());
		
		lexer.nextToken();		
		assertEquals(TokenType.STRING,lexer.getToken().getType());
		

	}
	
	@Test
	void invalidEscapeInTagTest() {
		String invalidEscape = "{$= \"\\s 1\" $}";
		final Lexer lexer1 = new Lexer(invalidEscape);
		
		lexer1.nextToken();
		assertEquals(TokenType.BEGINOFTAG,lexer1.getToken().getType());
		
		lexer1.setState(LexerState.TAG);
		
		lexer1.nextToken();
		assertEquals(TokenType.TAGNAME,lexer1.getToken().getType());
		
		assertThrows(LexerException.class,() -> lexer1.nextToken());
	}
	
	@Test
	void testGetToken() {
		String text = "This is sample text. \n {$ IF -23 - 1 10 1 $}";
		Lexer lexer = new Lexer(text);
		
		lexer.nextToken();
		assertNotNull(lexer.getToken());
		
		lexer.nextToken();
		assertNotNull(lexer.getToken());
		
	}
	
	
	
}
