package hr.fer.zemris.java.hw05.db;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class FieldValueGettersTest {

	private static StudentRecord record = new StudentRecord("35", "V", "Vojko", 5);
	
	@Test
	void firstNameTest() {
		assertEquals("Vojko", FieldValueGetters.FIRST_NAME.get(record) );
	}
	
	@Test
	void lastNameTest() {
		assertEquals("V", FieldValueGetters.LAST_NAME.get(record));
	}
	
	@Test
	void jmbagTest() {
		assertEquals("35", FieldValueGetters.JMBAG.get(record));
	}

}
