package hr.fer.zemris.java.hw11.jnotepadpp;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.swing.JTextArea;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;

/**
 * Implementation of the {@link SingleDocumentModel} 
 * which is used to store information of a document
 * @author Tomislav Kurtović
 *
 */
public class DefaultSingleDocumentModel implements SingleDocumentModel {

	/**
	 * Modification flag of this document
	 */
	private static boolean isModified ;
	
	/**
	 * Path of document
	 */
	private Path path;
	/**
	 * Text area of document
	 */
	private JTextArea textArea;
	
	/**
	 * List of listeners of this document
	 */
	private List<SingleDocumentListener> singleDocumentListeners;
	
	/**
	 * Constructor of the document
	 * @param path path of the document
	 * @param content content of the document
	 */
	public DefaultSingleDocumentModel(Path path, String content) {
		super();
		this.path = path;
		singleDocumentListeners = new ArrayList<>();
		textArea = new JTextArea(content);
		addTextAreaListener();
	}

	/**
	 * Used to add a listener to the text area 
	 */
	private void addTextAreaListener() {
		textArea.addCaretListener(new CaretListener() {
			@Override
			public void caretUpdate(CaretEvent e) {
				isModified = true;
				notifiyListenersOfMod();
			}
		});
	}

	/**
	 * Used to notify all listeners of modification
	 */
	protected void notifiyListenersOfMod() {
		for(SingleDocumentListener l : singleDocumentListeners) {
			l.documentModifyStatusUpdated(this);
		}
	}

	/**
	 * Used to notify all listeners of path change
	 */
	private void notifyListenersOnPathChange() {
		for(SingleDocumentListener l : singleDocumentListeners) {
			l.documentFilePathUpdated(this);
		}
	}
	
	@Override
	public JTextArea getTextComponent() {
		return this.textArea;
	}

	@Override
	public Path getFilePath() {
		return this.path;
	}

	@Override
	public void setFilePath(Path path) {
		this.path = Objects.requireNonNull(path);
		isModified = false;
		notifiyListenersOfMod();
		notifyListenersOnPathChange();
	}


	@Override
	public boolean isModified() {
		return isModified;
	}

	@Override
	public void addSingleDocumentListener(SingleDocumentListener l) {
		if(!singleDocumentListeners.contains(l)) {
			singleDocumentListeners.add(l);
		}
	}

	@Override
	public void removeSingleDocumentListener(SingleDocumentListener l) {
		if(singleDocumentListeners.contains(l)) {
			singleDocumentListeners.remove(l);
		}
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((path == null) ? 0 : path.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DefaultSingleDocumentModel other = (DefaultSingleDocumentModel) obj;
		if (path == null) {
			if (other.path != null)
				return false;
		} else if (!path.equals(other.path))
			return false;
		return true;
	}
}
