package hr.fer.zemris.java.crypto.util;

import java.util.Arrays;
import java.util.List;


/**
 * Util class that is used by the {@link Crypto } class in 
 * order to manipulate byte arrays and hex strings
 * 
 * @author Tomislav Kurtović
 *
 */
public class Util {

	/**
	 * Used to convert hex string into a byte 
	 * array 
	 * 
	 * @param keyText hex string
	 * @return byte array of the given string
	 */
	public static byte[] hextobyte(String keyText) {
		if(keyText == null || keyText.length() == 0) {
			return new byte[0];
		}
		if(keyText.length() % 2 == 0) {
			char[] keyData = keyText.toLowerCase().toCharArray();
			int i = 0;
			int keyTextLen = keyText.length();
			byte[] byteArray = new byte[keyTextLen /2];
			
			while(i < keyTextLen) {
				checkValidInput(keyData[i] , keyData[i + 1]);
				byteArray[i / 2] = (byte) ((Character.digit(keyData[i++],16) << 4) + Character.digit(keyData[i++],16));
			}
			return byteArray;
		}
		throw new IllegalArgumentException("Hex input invalid length");
	}
	
	/**
	 * Used to convert byte array to hex string 
	 * 
	 * @param byteArray array of bytes to convert to string 
	 * @return hex string of given array
	 */
	
	public static String bytetohex(byte[] byteArray) {
		if(byteArray == null || byteArray.length == 0)  {
			return "";
		}
		StringBuilder builder = new StringBuilder();
		for(byte b : byteArray) {
			builder.append(String.format("%02x", b));
		}
		
		return builder.toString();
	}
	
	/**
	 * Checks if the hex string contains legal hex digits .
	 * It throws an {@link IllegalArgumentException} if an invalid 
	 * character is found
	 * 
	 * @param first first hex digit
	 * @param second second hex digit
	 */
	private static void checkValidInput(char first, char second) {
		List<Character> validChars = Arrays.asList('a','b','c','d','e','f');
		
		if(validChars.contains(first) || Character.isDigit(first) &&
		validChars.contains(second) || Character.isDigit(second)) {
			return;
		}
		throw new IllegalArgumentException("Hex input invalid!");
	}
	
	
}
