import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

import hr.fer.zemris.java.hw06.shell.scripting.NameBuilderParser;
import hr.fer.zemris.java.hw06.shell.scripting.NameBuilderParserException;

class NameBuilderParserTest {

	@Test
	void testNameBuilderParser() {
		NameBuilderParser parser =
				new NameBuilderParser("gradovi-${2}-${1,03}.jpg");
		assertNotNull(parser.getNameBuilder());
	}

	@Test
	void testNameBuilderParser1() {
		assertThrows(NameBuilderParserException.class, () -> 
				new NameBuilderParser("gradovi-${2}-${1,0    3}.jpg"));
	}
	@Test
	void testNameBuilderParser2() {
		assertThrows(NameBuilderParserException.class, () -> 
		new NameBuilderParser("gradovi-${-2}-${1,03}.jpg"));
	}
	@Test
	void testNameBuilderParser3() {
		assertThrows(NameBuilderParserException.class, () -> 
		new NameBuilderParser("gradovi-${$2}-${1,03}.jpg"));
	}
	@Test
	void testNameBuilderParser4() {
		assertThrows(NameBuilderParserException.class, () -> 
		new NameBuilderParser("gradovi-${2}-${1,0.3}.jpg"));
	}
	
	@Test
	void testNameBuilderParser5() {
		assertThrows(NameBuilderParserException.class, () -> 
		new NameBuilderParser("gradovi-${2}-${1,03,2}.jpg"));
	}
	@Test
	void testNameBuilderParser6() {
		assertThrows(NameBuilderParserException.class, () -> 
		new NameBuilderParser("gradovi-${2}-${1,032}.jpg"));
	}
}
