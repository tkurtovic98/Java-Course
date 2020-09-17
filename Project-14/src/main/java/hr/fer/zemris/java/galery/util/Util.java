package hr.fer.zemris.java.galery.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import hr.fer.zemris.java.galery.model.PictureDesc;

/**
 * Util class that contains only static methods used
 * in generating information for rendered images 
 * @author Tomislav Kurtović
 *
 */
public class Util {

	/**
	 * Used to parse the document with all the descriptions 
	 * in order to generate {@link PictureDesc} objects 
	 * that are added to the list 
	 * @return list of {@link PictureDesc} objects
 	 * @throws IOException if something goes wrong while reading from file
	 */
	public static List<PictureDesc> getPictureDescriptions(String contextPath) throws IOException {
		List<String> allLines = Files.readAllLines(Paths.get(contextPath));
		List<PictureDesc> desc = new ArrayList<>();

		for (int i = 0, size = allLines.size(); i < size; i += 3) {
			if (allLines.get(i).isEmpty())
				break;
			desc.add(new PictureDesc(allLines.get(i), allLines.get(i + 1), allLines.get(i + 2)));
		}
		return desc;
	}
	
	/**
	 * Used to get the base64 encoded string of the 
	 * image 
	 * reference from stack overflow
	 * @param realPath path to image
	 * @return base64 encoded string of image 
	 * @throws IOException if something goes wrong while reading image
	 */
	public static String getEncodedImage(String realPath) throws IOException {
		return Base64.getEncoder().encodeToString(Files.readAllBytes(Paths.get(realPath)));
	}
}
