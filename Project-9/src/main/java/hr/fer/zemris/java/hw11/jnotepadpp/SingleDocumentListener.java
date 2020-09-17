package hr.fer.zemris.java.hw11.jnotepadpp;

/**
 * Interface that represents the listener
 * of the {@link SingleDocumentModel} and
 * that is used when changes in the editor occure
 * @author Tomislav Kurtović
 *
 */
public interface SingleDocumentListener {

	/**
	 * Called when the documents status has been 
	 * modified due to a change in text in its
	 * text area
	 * @param model moddified document
	 */
	void documentModifyStatusUpdated(SingleDocumentModel model);

	/**
	 * Called when the documents path has 
	 * been modified
	 * @param model document whoose path has been modified
	 */
	void documentFilePathUpdated(SingleDocumentModel model);
}
