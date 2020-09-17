package hr.fer.zemris.java.hw11.jnotepadpp;

import java.io.IOException;
import java.io.InputStream;

import javax.swing.ImageIcon;

/**
 * Class used to hold method 
 * for loading new image icons that 
 * should be used by the {@link JNotepadPP}
 * @author Tomislav Kurtović
 *
 */
public class IconLoader {

	/**
	 * Method used to get image icons 
	 * as resource stream from the directory
	 * the images are placed at
	 * @param name name of the image 
	 * @return image icon with specified name
	 */
	public ImageIcon getIcons(String name) {
		try (InputStream is = this.getClass().getResourceAsStream(name)) {
			if(is == null) {
				throw new NullPointerException("Input stream is null!");
			}
			return new ImageIcon(is.readAllBytes());
		} catch (IOException ex) {
			return null;
		}
	}
}
