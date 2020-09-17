package hr.fer.zemris.java.custom.scripting.parser;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.nodes.ForLoopNode;
import hr.fer.zemris.java.custom.scripting.nodes.TextNode;

class ParserTest {

	@Test
	void test1() {
		String document = loader("document1.txt");
		SmartScriptParser parser = new SmartScriptParser(document);
		DocumentNode node = parser.getDocumentNode();
		assertEquals(2,node.numberOfChilder());
	}

	@Test
	void test2() {
		String document = loader("document2.txt");
		SmartScriptParser parser = new SmartScriptParser(document);
		DocumentNode node = parser.getDocumentNode();
		assertEquals(3,node.numberOfChilder());
	}

	@Test
	void badEscapeTest() {
		String document = loader("badEscape.txt");
		assertThrows(SmartScriptParserException.class,()-> {SmartScriptParser parser
			= new SmartScriptParser(document);});
	}
	
	@Test
	void sameTokenizingTest() {
		
		String document1 = loader("sameTokenization1.txt");
		String document2 = loader("sameTokenization2.txt");
		
		SmartScriptParser parser1 = new SmartScriptParser(document1);
    	SmartScriptParser parser2 = new SmartScriptParser(document2);
		
		DocumentNode node = parser1.getDocumentNode();
		DocumentNode node2 = parser2.getDocumentNode();
		
		assertTrue(node.equals(node2));
	}
	
	@Test
	void documentTester() {
		String document1 = loader("doc1.txt");
		SmartScriptParser parser1 = new SmartScriptParser(document1);
		
	}
	
	private String loader(String filename) {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try(InputStream is=
				this.getClass().getClassLoader().getResourceAsStream(filename)){
				byte[] buffer = new byte[1024]; 
				while(true) {
					int read = is.read(buffer);
					if (read<1)break;
					bos.write(buffer,0,read);
				}
				return new String(bos.toByteArray(),StandardCharsets.UTF_8);
		} catch(IOException ex) {
			return null;
		}
	}
	
}
