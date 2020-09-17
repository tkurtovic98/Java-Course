package hr.fer.zemris.java.hw11.jnotepadpp;

import java.nio.file.Path;

import javax.swing.JTextArea;

/**
 * Interface that represents a model of a 
 * single document, having information about
 * the file path from which the document was loaded (can be null for new document).
 * It also holds info about the modification status of the document
 * and a reference to the Swing component used for its editing
 * @author Tomislav Kurtović
 *
 */
public interface SingleDocumentModel {

	/**
	 * Returns the reference to the text area assosiated with
	 * this document
	 * @return reference to text area
	 */
	JTextArea getTextComponent();
	
	/**
	 * Used to return path of this file
	 * @return path of file
	 */
	Path getFilePath();
	
	/**
	 * Used to set file path
	 * @param path new path of file. Can not be {@code null}
	 */
	void setFilePath(Path path);
	
	/**
	 * Returns the modification status of this file
	 * @return true if file was modified, false otherwise
	 */
	boolean isModified();
	
	/**
	 * Used to add single document to this model
	 * @param l {@link SingleDocumentListener} to add
	 */
	void addSingleDocumentListener(SingleDocumentListener l);
	/**
	 * Used to remove single document from this model
	 * @param l {@link SingleDocumentListener} to remove
	 */	
	void removeSingleDocumentListener(SingleDocumentListener l);
}
