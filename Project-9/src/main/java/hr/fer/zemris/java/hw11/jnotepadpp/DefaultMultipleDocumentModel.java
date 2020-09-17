package hr.fer.zemris.java.hw11.jnotepadpp;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import hr.fer.zemris.java.hw11.jnotepadpp.listeners.ModificationListener;

/**
 * Implementation of the {@link MultipleDocumentModel} 
 * which is used to store {@link SingleDocumentModel}s 
 * in form of a {@link JTabbedPane} 
 * @author Tomislav Kurtović
 *
 */
public class DefaultMultipleDocumentModel extends JTabbedPane implements MultipleDocumentModel {
	private static final long serialVersionUID = 1L;
	
	/**
	 * List of {@link SingleDocumentModel}s
	 */
	private List<SingleDocumentModel> singleDocumentModels;
	/**
	 * Current active {@link SingleDocumentModel}
	 */
	private SingleDocumentModel current;
	/**
	 * List of {@link MultipleDocumentListener}s
	 */
	private List<MultipleDocumentListener> multipleDocumentListeners;
	
	/**
	 * Modified image icon
	 */
	private ImageIcon modifiedImageIcon;
	
	/**
	 * Unmodified image icon
	 */
	private ImageIcon unmodifiedImageIcon;
	
	/**
	 * Constructor that instantiates the fields
	 */
	public DefaultMultipleDocumentModel() {
		singleDocumentModels = new ArrayList<>();
		multipleDocumentListeners = new ArrayList<>();
		getImageIcons();
		setListener();
	}
	
	/**
	 * Sets the listener for the tabbed pane.
	 * When the tabs change so does the title of the window
	 * and also the information printed in the status bar
	 */
	private void setListener() {
		addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				if(getCurrentDocument() == null) return;
				current = singleDocumentModels.get(getSelectedIndex());
				for(MultipleDocumentListener l : multipleDocumentListeners) {
					l.currentDocumentChanged(null, getCurrentDocument());
				}
			}
		});
	}

	/**
	 * Used to generate images of modified and not modified icons
	 */
	private void getImageIcons() {
		IconLoader imageLoader = new IconLoader();
			
		modifiedImageIcon = imageLoader.getIcons("icons/mod.png");
		unmodifiedImageIcon = imageLoader.getIcons("icons/notmod.png");
	}

	@Override
	public Iterator<SingleDocumentModel> iterator() {
		return new Iterator<SingleDocumentModel>() {
			int counter = 0;
			@Override
			public boolean hasNext() {
				return counter < singleDocumentModels.size();
			}
			@Override
			public SingleDocumentModel next() {
				return singleDocumentModels.get(counter++);
			}
		};
	}

	@Override
	public SingleDocumentModel createNewDocument() {
		return addTextArea(new DefaultSingleDocumentModel(null, ""));
	}

	/**
	 * Used for adding a new document to the editor and also
	 * adding a new tab with name unnamed if the document is new 
	 * or the name of the document if the document has
	 * been loaded from the disk
	 * @param defaultSingleDocumentModel document to add 
	 * @return new added document that is now the current document
	 */
	private SingleDocumentModel addTextArea(SingleDocumentModel defaultSingleDocumentModel) {
		JScrollPane scrollPane = new JScrollPane(defaultSingleDocumentModel.getTextComponent());
		Path path = defaultSingleDocumentModel.getFilePath();
		addTab(path == null ? "unnamed" : path.getFileName().toString(), 
				unmodifiedImageIcon,
				scrollPane,
				path == null ? null : path.toString()
				);
		singleDocumentModels.add(defaultSingleDocumentModel);
		defaultSingleDocumentModel.addSingleDocumentListener(new ModificationListener(modifiedImageIcon, unmodifiedImageIcon, this));
		this.current = defaultSingleDocumentModel;
		setSelectedIndex(singleDocumentModels.indexOf(defaultSingleDocumentModel));
		notifyNewAdded();
		return getCurrentDocument();
	}

	/**
	 * Used to notifiy all listener that new document has been added
	 */
	private void notifyNewAdded() {
		for(MultipleDocumentListener l : multipleDocumentListeners) {
			l.documentAdded(getCurrentDocument());
		}
	}

	/**
	 * Used to notifiy all listener that document has been removed
	 */
	private void notifyRemoved() {
		for(MultipleDocumentListener l : multipleDocumentListeners) {
			l.documentRemovde(getCurrentDocument());
		}
	}

	@Override
	public SingleDocumentModel getCurrentDocument() {
		if(getNumberOfDocument() == 0) return null;
//		this.current = singleDocumentModels.get(getSelectedIndex());
		return this.current;
	}

	@Override
	public SingleDocumentModel loadDocument(Path path) {
		try {
			String text = Files.readString(Objects.requireNonNull(path));
			SingleDocumentModel model = new DefaultSingleDocumentModel(path, text);
			if(singleDocumentModels.contains(model)) {
				setSelectedIndex(singleDocumentModels.indexOf(model));
				notifyNewAdded();
				return getCurrentDocument();
			} else {
				return addTextArea(model);
			}
		} catch (IOException e) {
		}
		return null;
	}

	@Override
	public void saveDocument(SingleDocumentModel model, Path newPath) {
		try {
			if(singleDocumentModels.size() == 0) {
				JOptionPane.showMessageDialog(this, "Invalid operation! No documents found");
				return;
			}
			if(checkIfPathExists(newPath)) return;
			Path path = newPath == null ? model.getFilePath() : newPath;
			Files.write(path, model.getTextComponent().getText().getBytes());
			model.setFilePath(path);
		} catch (IOException e) {
			System.out.println("Error while writing to file!");
		}
	}

	/**
	 * Checks if given document with given path is already opened
	 * and prints appropriate message
	 * @param model 
	 * @param newPath new path of the document
	 * @return true if path exists, false otherwise
	 */
	private boolean checkIfPathExists(Path newPath) {
		for(SingleDocumentModel doc : singleDocumentModels) {
			if(doc.getFilePath() == null) continue;
			if(doc.getFilePath().equals(newPath)) {
				JOptionPane.showMessageDialog(this, "Path already exists!");
				return true;
			}
		}
		return false;
	}

	@Override
	public void closeDocument(SingleDocumentModel model) {
		if(singleDocumentModels.contains(model)) {
			singleDocumentModels.remove(model);
			remove(getSelectedIndex());
			notifyRemoved();
		} 
	}

	@Override
	public void addMultipleDocumentListener(MultipleDocumentListener l) {
		if(!multipleDocumentListeners.contains(l)) {
			multipleDocumentListeners.add(l);
		}
	}

	@Override
	public void removeMultipleDocumentListener(MultipleDocumentListener l) {
		if(multipleDocumentListeners.contains(l)) {
			multipleDocumentListeners.remove(l);
		}
	}

	@Override
	public int getNumberOfDocument() {
		return singleDocumentModels.size();
	}

	@Override
	public SingleDocumentModel getDocument(int index) {
		return singleDocumentModels.get(index);
	}

}
