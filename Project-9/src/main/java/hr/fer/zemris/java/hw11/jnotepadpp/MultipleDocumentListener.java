package hr.fer.zemris.java.hw11.jnotepadpp;

/**
 * Interface that represents the listener
 * of the {@link MultipleDocumentModel} and
 * that is used when changes in the editor occure
 * @author Tomislav Kurtović
 *
 */
public interface MultipleDocumentListener {

	/**
	 * Used to change the current active 
	 * documnets in the editor.
	 * Either previousModel or currentModel can be {@code null}, but not both
	 * @param previousModel previous active model
	 * @param currentModel new/current active model
	 */
	void currentDocumentChanged(SingleDocumentModel previousModel, SingleDocumentModel currentModel);
	
	/**
	 * Called when new document is added to the editor window
	 * @param model new added document
	 */
	void documentAdded(SingleDocumentModel model);
	
	/**
	 * Called when document is removed from the editor window
	 * @param model removed document
	 */
	void documentRemovde(SingleDocumentModel model);
	
}
