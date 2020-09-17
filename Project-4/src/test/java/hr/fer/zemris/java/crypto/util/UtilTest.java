package hr.fer.zemris.java.crypto.util;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import hr.fer.zemris.java.crypto.util.Util;

class UtilTest {

	@Test
	void testHextobyte() {
		byte[] array = Util.hextobyte("01aE22");
		
		assertEquals(1,array[0]);
		assertEquals(-82,array[1]);
		assertEquals(34,array[2]);
		
		assertThrows(IllegalArgumentException.class, ()-> Util.hextobyte("01a"));
		assertThrows(IllegalArgumentException.class, ()-> Util.hextobyte("0ZD2"));
	}

	@Test
	void testBytetohex() {
		String hex = Util.bytetohex(new byte[] {1,-82,34});
		String hex2 = Util.bytetohex(new byte[] {});
		
		assertEquals("01ae22", hex);
		assertEquals(0,hex2.length());
	}

}
