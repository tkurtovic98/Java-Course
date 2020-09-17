package hr.fer.zemris.java.crypto.Crypto;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Scanner;

import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import hr.fer.zemris.java.crypto.util.Util;

/**
 * 
 * Class used to decrypt, encrypt and check wheater a file has been
 * altered during transfer. 
 * 
 * @author Tomislav Kurtović
 *
 */
public class Crypto {
	
	/**
	 * Method that gets the arguments from the command line 
	 * and checks whether it should check the sha of the
	 * file or whether it should encrypt or decrypt the 
	 * given file 
	 * If 2 arguments are passed a {@link chechSha} operation is expected
	 * and if 3 a {@link FileProcessor} operation is expected. 
	 * 
	 * @param args arguments passed to the function. They can either be checkSha
	 * or encrypt / decrypt and then the file name or names, depending on the operation
	 * 
	 */
	public static void main(String[] args) {
		if(args.length != 2 && args.length != 3) {
			System.out.println("Invalid number of arguments");
			return;
		}
		
		String action;
		String fileToRead;
		String fileToWrite;
		Scanner scanner = new Scanner(System.in);
		
		if(args.length == 2) {
			action = args[0];
			fileToRead = args[1];
			
			if("checksha".equals(action)) {
				System.out.println("Please provide expected sha-256 digest for "+ fileToRead + ":");
				System.out.printf(">");
				String sha;
				while(true) {
					if(scanner.hasNextLine()) {
						sha = scanner.nextLine();
						if(sha.length() != 64) {
							System.out.println("Please provide 64 bit hex encoding");
							continue;
						}
						break;
					}
				}
				String digested = Util.bytetohex(CheckSha.checkSha(fileToRead));
				if(sha.equals(digested)) {
					System.out.println("Digesting completed. Digest of "+ fileToRead + " matches expected digest.");
				} else {
					System.out.println("Digesting completed. Digest of "+ fileToRead +" does not match the expected digest. Digest\r\n" + 
							"was:" + digested );
				}
			}
			scanner.close();
		}
		
		if(args.length == 3) {
			boolean encrypt = args[0].equals("encrypt") ? true : false;
			fileToRead = args[1] ;
			fileToWrite = args[2];
			
			String keyText = "" ;
			String ivText = "" ;
			
			System.out.println("Please provide password as hex-encoded text (16 bytes, i.e. 32 hex-digits):");
			keyText = retrieveData(scanner);
			
			System.out.println("Please provide initialization vector as hex-encoded text (32 hex-digits):");
			ivText = retrieveData(scanner);
		
			SecretKeySpec keySpec = new SecretKeySpec(Util.hextobyte(keyText), "AES");
			AlgorithmParameterSpec paramSpec = new IvParameterSpec(Util.hextobyte(ivText));
			
			try {
			    FileProcessor.processFile(fileToRead,fileToWrite,  keySpec, paramSpec, encrypt);
				System.out.printf("%s completed. Generated file  "+fileToWrite+" based on file "+fileToRead+".",encrypt? "Encription" : "Decription");
			    
			} catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
				System.out.println("Invalid algorithm or padding !");
			} catch (InvalidKeyException e) {
				System.out.println("Invalid key!");
			} catch (InvalidAlgorithmParameterException e) {
				System.out.println("Invalid parameters");
			} finally {
				scanner.close();
			}
		}
	}
	
	
	/**
	 * Method used to retrieve user data. 
	 * It takes the keyboard input and questions if 
	 * the entered values are 32 hex- digits used 
	 * for the encription or decription 
	 * 
	 * @param scanner takes user input
	 * @return returns valid 32 hex-digits
	 */
	private static String retrieveData(Scanner scanner) {
		String text = "";
		while(true) {
			System.out.printf(">");
			if(scanner.hasNextLine()) {
				text = scanner.nextLine();
				if(text.length() != 32) {
					System.out.println("Please provide 32 hex-digits");
					continue;
				}
				break;
			}
		}
		return text;
	}
}
