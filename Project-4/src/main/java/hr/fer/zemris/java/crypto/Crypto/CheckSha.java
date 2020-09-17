package hr.fer.zemris.java.crypto.Crypto;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Class that has a static method used to
 * check if the file was altered during download
 * or transfer.
 * It uses a {@link MessageDigest} in order
 * to chech the file
 * 
 * @author Tomislav Kurtović
 *
 */

public class CheckSha {
	
	/**
	 * Method used to generate sha for this file
	 * and then returns the byte array of the sha 
	 * which can later be checked if the file was altered
	 * 
	 * @param readFrom file to digest
	 * @return byte array of diggested file 
	 */
	public static byte[] checkSha(String readFrom) {
		byte[] digested = null;
		File file = new File(readFrom);
		String path = file.getAbsolutePath();
		try {
			MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
			try (InputStream stream = Files.newInputStream(Paths.get(path))) {
				byte[] buffer = new byte[16];
				while (true) {
					int read = stream.read(buffer);
					if (read < 1) {
						digested = messageDigest.digest();
						break;
					}
					messageDigest.update(buffer);
				}
			} catch (IOException e) {
				return null;
			}
		} catch (NoSuchAlgorithmException e) {
			return null;
		} 
		return digested;
	}
}

