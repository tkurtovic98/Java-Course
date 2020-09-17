package hr.fer.zemris.java.crypto.Crypto;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;


/**
 * Class used to process decrypt or encrypt 
 * given file names 
 * It has one static method that does 
 * all the work 
 * @author Tomislav Kurtović
 *
 */
public class FileProcessor {
	/**
	 * Method used to decrypt or encrypt the given files. 
	 * It instantiates a new {@link Cipher} object and 
	 * sets its attributes appropriately.
	 * The method then read from the source file and does the 
	 * wanted operation of either decription or encription. 
	 * 
	 * @param readFrom Source file
	 * @param writeTo Destination file
	 * @param keySpec Key used by cipher
	 * @param paramSpec algorithm specifications used by cipher
	 * @param encrypt boolean used to determine cipher mode
	 * @throws InvalidKeyException if key is  not valid
	 * @throws InvalidAlgorithmParameterException if algorithm is not valid
	 * @throws NoSuchAlgorithmException if algorithm does not exist
	 * @throws NoSuchPaddingException if padding does not exist
	 */
	public static void processFile(String readFrom, String writeTo, SecretKeySpec keySpec, AlgorithmParameterSpec paramSpec, boolean encrypt) 
			throws InvalidKeyException, InvalidAlgorithmParameterException, NoSuchAlgorithmException, NoSuchPaddingException  {
		
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		cipher.init(encrypt ? Cipher.ENCRYPT_MODE : Cipher.DECRYPT_MODE, keySpec, paramSpec);
		File file = new File(readFrom);
		String path = file.getAbsolutePath();
		try(InputStream stream = Files.newInputStream(Paths.get(path));
			 OutputStream outStream = Files.newOutputStream(Paths.get(writeTo))){
			byte[] buffer = new byte[4096];
			while(true) {
				int read = stream.read(buffer);
				if(read < 1) {
					byte[] finalByteArray = cipher.doFinal(buffer);
					if(finalByteArray != null) {
						outStream.write(finalByteArray);
					}
					break;
				}
				byte[] finalByteArray = cipher.update(buffer,0,read);
				if(finalByteArray != null) {
					outStream.write(finalByteArray);
				}
			}
		} catch (IOException  | IllegalBlockSizeException | BadPaddingException e ) {
		}
	}
}
