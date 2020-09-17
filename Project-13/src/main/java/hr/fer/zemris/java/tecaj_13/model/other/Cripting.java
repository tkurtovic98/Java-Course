package hr.fer.zemris.java.tecaj_13.model.other;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Class used to hash the user password 
 * that will be stored in the database so 
 * that no plain text password is inserted into
 * datatbase
 * @author Tomislav Kurtović
 *
 */
public class Cripting {

	/**
	 * Used to hash the sent text password
	 * @param password password to hash
	 * @return hex hashed representation of password
	 */
	public static String hashPassword(String password) {
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-1");
			byte[] digested = md.digest(password.getBytes());
			String hashPassword = bytetohex(digested);
			return hashPassword;
		} catch (NoSuchAlgorithmException ex) {
			return null;
		}
	}
	
	/**
	 * Used to convert byte array to hex string 
	 * 
	 * @param byteArray array of bytes to convert to string 
	 * @return hex string of given array
	 */
	private static String bytetohex(byte[] byteArray) {
		if (byteArray == null || byteArray.length == 0) {
			return "";
		}
		StringBuilder builder = new StringBuilder();
		for (byte b : byteArray) {
			builder.append(String.format("%02x", b));
		}
		return builder.toString();
	}
	
}