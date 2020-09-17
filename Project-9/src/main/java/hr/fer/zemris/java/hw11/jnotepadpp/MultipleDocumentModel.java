package hr.fer.zemris.java.hw11.jnotepadpp;

import java.nio.file.Path;

/**
 * Interface that represents model capable
 * of holding zero, one or more documents. 
 * Each document has the same concept and one
 * document is shown to the user and 
 * the used can work on the shown document
 * 
 * @author Tomislav Kurtović
 *
 */
public interface MultipleDocumentModel extends Iterable<SingleDocumentModel>{
	
	/**
	 * Used to create new {@link SingleDocumentModel}
	 * @return new {@link SingleDocumentModel}
	 */
	SingleDocumentModel createNewDocument();
	
	/**
	 * Used to get current {@link SingleDocumentModel}
	 * @return current {@link SingleDocumentModel}
	 */
	SingleDocumentModel getCurrentDocument();
	
	/**
	 * Used to load document from the specified path
	 * @param path path of document to be loaded. Can not be {@code null}
	 * @return new {@link SingleDocumentModel} from path
	 */
	SingleDocumentModel loadDocument(Path path);

	/**
	 * Used to save document to specified path
	 * @param model document to save
	 * @param newPath path to save document to. Can be {@code null}, but then
	 * the document is saved at the last associated path
	 */
	void saveDocument(SingleDocumentModel model, Path newPath);
	
	/**
	 * Used to close the specified document
	 * @param model document to close
	 */
	void closeDocument(SingleDocumentModel model);
	
	/**
	 * Used to add document listener to the model
	 * @param l {@link MultipleDocumentListener} to add
	 */
	void addMultipleDocumentListener(MultipleDocumentListener l);
	
	/**
	 * Used to remove document listener from the model
	 * @param l {@link MultipleDocumentListener} to remove
	 */
	void removeMultipleDocumentListener(MultipleDocumentListener l);
	
	
	/**
	 * Returns the number of currently opened documents
	 * @return number of opened documents
	 */
	int getNumberOfDocument();
	
	/**
	 * Returns the {@link SingleDocumentModel} at the specified index
	 * @param index position of the document to be retrieved
	 * @return document at specified position
	 */
	SingleDocumentModel getDocument(int index);
	
}
