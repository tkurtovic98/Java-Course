package hr.fer.zemris.java.hw11.jnotepadpp;

/**
 * Class that is used as a clipboar by the {@link JNotepadPP}
 * program and its cut/copy/paste actions
 * @author Tomislav Kurtović
 *
 */
public class Clipboard {
	/**
	 * Selected text in the editor
	 */
	private String selectedText;

	/**
	 * Getter of the text
	 * @return copied or cut text that is stored in the clipboard
	 */
	public String getSelectedText() {
		return selectedText;
	}

	/**
	 * Sets the text property of the clipboard 
	 * @param selectedText copied or cut text
	 */
	public void setSelectedText(String selectedText) {
		this.selectedText = selectedText;
	}
}
