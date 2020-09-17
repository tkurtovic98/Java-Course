package hr.fer.zemris.java.hw11.jnotepadpp.listeners;

import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.JTextComponent;

import hr.fer.zemris.java.hw11.jnotepadpp.Actions;
import hr.fer.zemris.java.hw11.jnotepadpp.MultipleDocumentListener;
import hr.fer.zemris.java.hw11.jnotepadpp.SingleDocumentModel;
import hr.fer.zemris.java.hw11.jnotepadpp.StatusBar;

/**
 * Class that is a implementation of the {@link MultipleDocumentListener}
 * interface. 
 * It reacts to the changes that occur when adding new documents to the window
 * or changing tabs
 * @author Tomislav Kurtović
 *
 */
public class DocumentChangedListener implements MultipleDocumentListener {
	/**
	 * Status bar of the editor
	 */
	private StatusBar statusBar;
	/**
	 * Frame of editor
	 */
	private JFrame frame;
	/**
	 * Text area of current document
	 */
	private JTextComponent textArea;
	/**
	 * Current document
	 */
	private SingleDocumentModel model;
	/**
	 * Current carret listener
	 */
	private CaretListener listener;
	
	/**
	 * Actions supported by the editor
	 */
	private Actions actions;
	
	/**
	 * Constructor of the listener
	 * @param statusBar status bar of the editor
	 * @param frame the editor frame
	 */
	public DocumentChangedListener(StatusBar statusBar, JFrame frame, Actions actions) {
		super();
		this.statusBar = statusBar;
		this.frame = frame;
		this.actions = actions;
	}

	@Override
	public void currentDocumentChanged(SingleDocumentModel previousModel, SingleDocumentModel currentModel) {
		if(previousModel == null && currentModel == null) {
			throw new NullPointerException("Both models must not be null");
		}
		change(currentModel);
		listener.caretUpdate(null);
	}

	/**
	 * Removes old text listener and adds a new one to the 
	 * current document 
	 */
	private void addTextAreaListener() {
		textArea.removeCaretListener(listener);
		listener = new CaretListener() {

			@Override
			public void caretUpdate(CaretEvent e) {
				setStatusBarText(model);
				
				boolean isSelected = textArea.getCaret().getDot() != textArea.getCaret().getMark();
				
				actions.COPY.setEnabled(isSelected);
				actions.CUT.setEnabled(isSelected);
				actions.UPPER_CASE.setEnabled(isSelected);
				actions.LOWER_CASE.setEnabled(isSelected);
				actions.INVERT_CASE.setEnabled(isSelected);
				actions.ASCENDING.setEnabled(isSelected);
				actions.DESCENDING.setEnabled(isSelected);
				actions.UNIQUE.setEnabled(isSelected);
			}
		};
		textArea.addCaretListener(listener);
	}

	@Override
	public void documentAdded(SingleDocumentModel model) {
		actions.SAVE.setEnabled(true);
		actions.CLOSE.setEnabled(true);
		actions.STATS.setEnabled(true);
		actions.SAVE_AS.setEnabled(true);
		change(model);
	}

	@Override
	public void documentRemovde(SingleDocumentModel model) {
		change(model);
	}
	
	/**
	 * Used to call all methods responsable
	 * for changing information in the editor
	 * @param model current working document
	 */
	private void change(SingleDocumentModel model) {
		this.model = model;
		changeTitle(model);
		if(model == null) return;
		textArea = model.getTextComponent();
		addTextAreaListener();
	}
	
	/**
	 * Used to change window title depending on
	 * the path of the document being used
	 * @param model current model user is working on
	 */
	private void changeTitle(SingleDocumentModel model) {
		if(model == null) {
			frame.setTitle("");
			return;
		}
		
		if(model.getFilePath() != null) {
			frame.setTitle(model.getFilePath().getFileName().toString() + "- JNotepad++");
		}else {
			frame.setTitle("(unnamed) - JNotepad++");
		}
	}
	
	/**
	 * Used to set the text of the status bar at the end of 
	 * the editor window
	 * @param model current model user is working on
	 */
	private void setStatusBarText(SingleDocumentModel model) {
		if(model == null) {
			statusBar.setLabelText(0, 0, 0);
			statusBar.setLengthText(0);
			return;
		}
		
		try {
			int caretPosition = textArea.getCaretPosition();
			int linePosition  = ((JTextArea) textArea).getLineOfOffset(caretPosition);
			int colPosition = caretPosition - ((JTextArea) textArea).getLineStartOffset(linePosition);
			int selection = Math.abs(textArea.getCaret().getDot() - textArea.getCaret().getMark());
			
			statusBar.setLabelText(colPosition+1, linePosition + 1, selection);
			statusBar.setLengthText(model.getTextComponent().getText().length());
		} catch (BadLocationException e) {
		}
	}
}
