package hr.fer.zemris.java.hw11.jnotepadpp;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.Collator;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.text.BadLocationException;
import javax.swing.text.Caret;
import javax.swing.text.Document;
import javax.swing.text.Element;
import javax.swing.text.JTextComponent;

import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationListener;
import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationProvider;

/**
 * Class that represents actions that are supported by the {@link JNotepadPP}
 * program
 * 
 * @author Tomislav Kurtović
 *
 */
public class Actions {
	/**
	 * Instance of a {@link MultipleDocumentModel}
	 */
	private static MultipleDocumentModel model;
	/**
	 * Instance of a {@link JNotepadPP}
	 */
	private static JNotepadPP instance;

	/**
	 * Instance of the {@link Clipboard}
	 */
	private static Clipboard clipboard;

	/**
	 * Instance of the {@link ILocalizationProvider}
	 */
	private static ILocalizationProvider provider;

	private static Map<String, Action> actions;

	/**
	 * Constructor
	 * 
	 * @param clipboard Instance of the {@link Clipboard}
	 * @param model     Instance of a {@link MultipleDocumentModel}
	 * @param instance  Instance of a {@link JNotepadPP}
	 */
	public Actions(Clipboard clipboard, MultipleDocumentModel model, JNotepadPP instance, ILocalizationProvider prov) {
		Actions.model = model;
		Actions.instance = instance;
		Actions.clipboard = clipboard;
		Actions.provider = prov;
		actions = new HashMap<>();
		configureActions();
		populateMap();
		setLanguageChangeListeners();
	}

	/**
	 * Used to put values to the actions defined in this class
	 */
	private void configureActions() {
		LOAD.putValue(Action.NAME, provider.getString("load"));
		LOAD.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control O"));
		LOAD.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_O);

		CREATE.putValue(Action.NAME, provider.getString("create"));
		CREATE.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control N"));
		CREATE.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_N);

		STATS.putValue(Action.NAME, provider.getString("statistics"));
		STATS.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control M"));
		STATS.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_M);

		SAVE.putValue(Action.NAME, provider.getString("save"));
		SAVE.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control S"));
		SAVE.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_S);

		CLOSE.putValue(Action.NAME, provider.getString("close"));
		CLOSE.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control P"));
		CLOSE.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_P);

		EXIT.putValue(Action.NAME, provider.getString("exit"));
		EXIT.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control Q"));
		EXIT.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_Q);

		COPY.putValue(Action.NAME, provider.getString("copy"));
		COPY.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control C"));
		COPY.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_C);

		PASTE.putValue(Action.NAME, provider.getString("paste"));
		PASTE.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control V"));
		PASTE.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_V);

		CUT.putValue(Action.NAME, provider.getString("cut"));
		CUT.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control X"));
		CUT.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_X);

		UPPER_CASE.putValue(Action.NAME, provider.getString("upper"));
		UPPER_CASE.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control U"));
		UPPER_CASE.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_U);

		LOWER_CASE.putValue(Action.NAME, provider.getString("lower"));
		LOWER_CASE.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control L"));
		LOWER_CASE.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_L);

		INVERT_CASE.putValue(Action.NAME, provider.getString("invert"));
		INVERT_CASE.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control I"));
		INVERT_CASE.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_I);

		ASCENDING.putValue(Action.NAME, provider.getString("ascending"));
		ASCENDING.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control A"));
		ASCENDING.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_A);

		DESCENDING.putValue(Action.NAME, provider.getString("descending"));
		DESCENDING.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control D"));
		DESCENDING.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_D);

		UNIQUE.putValue(Action.NAME, provider.getString("unique"));
		UNIQUE.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control U"));
		UNIQUE.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_U);

		SAVE_AS.putValue(Action.NAME, provider.getString("save_as"));
		SAVE_AS.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control B"));
		SAVE_AS.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_B);
	}

	private void setLanguageChangeListeners() {
		for (String key : actions.keySet()) {
			provider.addLocalizationListener(new ILocalizationListener() {
				@Override
				public void localizationChanged() {
					actions.get(key).putValue(Action.NAME, provider.getString(key));
				}
			});
		}
	}

	private void populateMap() {
		actions.put("load", LOAD);
		actions.put("save", SAVE);
		actions.put("create", CREATE);
		actions.put("exit", EXIT);
		actions.put("upper", UPPER_CASE);
		actions.put("lower", LOWER_CASE);
		actions.put("invert", INVERT_CASE);
		actions.put("statistics", STATS);
		actions.put("copy", COPY);
		actions.put("paste", PASTE);
		actions.put("cut", CUT);
		actions.put("close", CLOSE);
		actions.put("unique", UNIQUE);
		actions.put("ascending", ASCENDING);
		actions.put("descending", DESCENDING);
		actions.put("save_as", SAVE_AS);
	}

	/**
	 * Action used to load a document to the {@link JNotepadPP} program
	 */
	public final Action LOAD = new AbstractAction() {
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			openDocument();
		}

		private void openDocument() {
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setDialogTitle("Open file");
			if (fileChooser.showOpenDialog(instance) != JFileChooser.APPROVE_OPTION) {
				JOptionPane.showMessageDialog(instance, "Opening of the file was canceled");
				return;
			}

			Path path = fileChooser.getSelectedFile().toPath();

			if (!Files.isReadable(path)) {
				JOptionPane.showMessageDialog(instance, "Error : Can not read from file", "ERROR",
						JOptionPane.ERROR_MESSAGE);
				return;
			}

			if (model.loadDocument(path) == null) {
				JOptionPane.showMessageDialog(instance, "Error : Reading of file failed", "ERROR",
						JOptionPane.ERROR_MESSAGE);
				return;
			}
		}
	};

	/**
	 * Action used to create a new document in the {@link JNotepadPP} program
	 */
	public final Action CREATE = new AbstractAction() {
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			model.createNewDocument();
		}
	};

	/**
	 * Action used to calculate statistics of the current opened document in the
	 * {@link JNotepadPP} program
	 */
	public final Action STATS = new AbstractAction() {
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			calculateStatistics();
		}

		private void calculateStatistics() {
			try {
				String text = model.getCurrentDocument().getTextComponent().getText();
				int numberOfChars = text.length();
				int numberOfNonBlank = text.replaceAll("\\s+", "").length();
				long lines = text.lines().count();

				JOptionPane.showMessageDialog(instance,
						"The document has " + numberOfChars + " characters," + numberOfNonBlank
								+ " non-blank characters and " + lines + " lines",
						"Calculation", JOptionPane.INFORMATION_MESSAGE);
			} catch (NullPointerException ex) {
				JOptionPane.showMessageDialog(instance, "Can not calculate for null text!", "ERROR",
						JOptionPane.ERROR_MESSAGE);
			}

		}
	};

	/**
	 * Action used to save the current document user is working on in the
	 * {@link JNotepadPP} program
	 */
	public final Action SAVE = new AbstractAction() {
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			saveFile();
		}

		private void saveFile() {
			SingleDocumentModel document = model.getCurrentDocument();
			
			if(document == null) {
				return;
			}
			
			if (document.getFilePath() == null) {
				JFileChooser jfc = new JFileChooser();
				jfc.setDialogTitle("Save file");
				if (jfc.showSaveDialog(instance) != JFileChooser.APPROVE_OPTION) {
					JOptionPane.showMessageDialog(instance, "Nothing saved", "Info", JOptionPane.INFORMATION_MESSAGE);
					return;
				}
				model.saveDocument(document, jfc.getSelectedFile().toPath());
			} else {
				model.saveDocument(document, null);
			}
			JOptionPane.showMessageDialog(instance, "Action completed", "Info", JOptionPane.INFORMATION_MESSAGE);
			return;
		}
	};
	/**
	 * Action used to save the current document user is working on in the
	 * {@link JNotepadPP} program with a new path
	 */
	public final Action SAVE_AS = new AbstractAction() {
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			save_as();
		}

		private void save_as() {
			SingleDocumentModel document = model.getCurrentDocument();
			
			if(document == null) {
				return;
			}

			if (Files.isRegularFile(document.getFilePath())) {

				int x = JOptionPane.showOptionDialog(instance,
						"Document already exists. Would you like to overwrite path?", "Existing document!",
						JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, null);

				if (x == 0) {
					JFileChooser jfc = new JFileChooser();
					jfc.setDialogTitle("Save_as file");
					if (jfc.showSaveDialog(instance) != JFileChooser.APPROVE_OPTION) {
						JOptionPane.showMessageDialog(instance, "Nothing saved", "Info",
								JOptionPane.INFORMATION_MESSAGE);
						return;
					}
					model.saveDocument(document, jfc.getSelectedFile().toPath());
				}
				if (x == 1) {
					JOptionPane.showMessageDialog(instance, "Saving canceled");
				}
			}
		}
	};

	/**
	 * Action used to close the current document user is working on in the
	 * {@link JNotepadPP} program
	 */
	public final Action CLOSE = new AbstractAction() {
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			close();
		}

		private void close() {
			String[] options = { "Save", "Don't save", "Cancel" };

			SingleDocumentModel document = model.getCurrentDocument();
			
			if(document == null) {
				return;
			}

			if (document.isModified()) {
				String docName;

				if (document.getFilePath() != null) {
					docName = document.getFilePath().getFileName().toString();
				} else {
					docName = "unnamed";
				}

				int x = JOptionPane.showOptionDialog(instance,
						"Would you like to save changes made to " + docName + "?", "Unsaved document found!",
						JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

				if (x == 0) {
					SAVE.actionPerformed(null);
					model.closeDocument(document);
				}
				if (x == 1) {
					model.closeDocument(document);
				}

				if (x == 2) {
					JOptionPane.showMessageDialog(instance, "Closing of document canceled !");
					return;
				}
			}
		}
	};

	/**
	 * Action used when the user wants to exit the program by clicking the "exit"
	 * action. It checks if there are some modified but unsaved documents and asks
	 * the user what he wants to do with every document. The user can also cancel
	 * the closing of the window by pressing "cancel" The method that checks the
	 * documents is also used by the window when the user presses the X button
	 */
	public final Action EXIT = new AbstractAction() {
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			checkUnsavedDocuments();
		}

		private void checkUnsavedDocuments() {
			if (model.getNumberOfDocument() == 0) {
				instance.dispose();
				return;
			}

			String[] options = { "Save", "Don't save", "Cancel" };
			instance.getModel().setSelectedIndex(0);

			List<SingleDocumentModel> copyOfDocs = new ArrayList<>();
			for (SingleDocumentModel doc : model) {
				copyOfDocs.add(doc);
			}

			for (SingleDocumentModel document : copyOfDocs) {
				if (document.isModified()) {
					String docName;

					if (document.getFilePath() != null) {
						docName = document.getFilePath().getFileName().toString();
					} else {
						docName = "unnamed";
					}

					int x = JOptionPane.showOptionDialog(instance,
							"Would you like to save changes made to " + docName + "?", "Unsaved document found!",
							JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

					if (x == 0) {
						SAVE.actionPerformed(null);
						model.closeDocument(document);
					}
					if (x == 1) {
						model.closeDocument(document);
					}

					if (x == 2) {
						JOptionPane.showMessageDialog(instance, "Closing of window canceled !");
						return;
					}
				}
			}

			JOptionPane.showMessageDialog(instance, "Closing the program");
			instance.dispose();
		}
	};

	/**
	 * Action used to copy the selected text from the {@link JNotepadPP} program
	 */
	public final Action COPY = new AbstractAction() {

		@Override
		public void actionPerformed(ActionEvent e) {
			copy();
		}

		private void copy() {
			JTextComponent editor = model.getCurrentDocument().getTextComponent();
			Document document = editor.getDocument();
			Caret caret = editor.getCaret();

			try {
				String text = document.getText(Math.min(caret.getDot(), caret.getMark()),
						Math.abs(caret.getDot() - caret.getMark()));
				clipboard.setSelectedText(text);
			} catch (BadLocationException e) {
			}
		}
	};
	/**
	 * Action used to paste the selected text in the {@link JNotepadPP} program
	 */
	public final Action PASTE = new AbstractAction() {

		@Override
		public void actionPerformed(ActionEvent e) {
			paste();
		}

		private void paste() {
			JTextComponent editor = model.getCurrentDocument().getTextComponent();
			Document document = editor.getDocument();

			try {
				if (clipboard.getSelectedText() == null)
					return;
				document.insertString(editor.getCaretPosition(), clipboard.getSelectedText(), null);
			} catch (BadLocationException e) {
			}
		}
	};

	/**
	 * Action used to cut the selected text from the {@link JNotepadPP} program
	 */
	public final Action CUT = new AbstractAction() {

		@Override
		public void actionPerformed(ActionEvent e) {
			cut();
		}

		private void cut() {
			COPY.actionPerformed(null);
			deleteText();
		}

	};

	/**
	 * Used to delete selected text in the editor after for example cutting the text
	 */
	private void deleteText() {
		JTextComponent editor = model.getCurrentDocument().getTextComponent();
		Document document = editor.getDocument();
		Caret caret = editor.getCaret();

		try {
			document.remove(Math.min(caret.getDot(), caret.getMark()), clipboard.getSelectedText().length());
		} catch (BadLocationException e) {
		}
	}

	/**
	 * Action used to change caseing of selected text to upper case in the
	 * {@link JNotepadPP} program
	 */
	public final Action UPPER_CASE = new AbstractAction() {
		@Override
		public void actionPerformed(ActionEvent e) {
			upperCase();
		}

		private void upperCase() {
			changeText(String::toUpperCase);
		}
	};

	/**
	 * Action used to change caseing of selected text to lower case in the
	 * {@link JNotepadPP} program
	 */
	public final Action LOWER_CASE = new AbstractAction() {

		@Override
		public void actionPerformed(ActionEvent e) {
			lowerCase();
		}

		private void lowerCase() {
			changeText(String::toLowerCase);
		}
	};

	/**
	 * Used to change the text to upper or lower casing
	 * 
	 * @param change method that is applied to the text
	 */
	private void changeText(TextChange change) {
		JTextComponent editor = model.getCurrentDocument().getTextComponent();
		Document document = editor.getDocument();
		Caret caret = editor.getCaret();

		try {
			String text = document.getText(Math.min(caret.getDot(), caret.getMark()),
					Math.abs(caret.getDot() - caret.getMark()));
			document.remove(Math.min(caret.getDot(), caret.getMark()), text.length());
			document.insertString(Math.min(caret.getDot(), caret.getMark()), change.getChange(text), null);

		} catch (BadLocationException e) {
		}

	}

	/**
	 * Action used to invert casing of selected text to either upper or lower case
	 * in the {@link JNotepadPP} program
	 */
	public final Action INVERT_CASE = new AbstractAction() {

		@Override
		public void actionPerformed(ActionEvent e) {
			invert();
		}

		private void invert() {
			JTextComponent editor = model.getCurrentDocument().getTextComponent();
			Document document = editor.getDocument();
			Caret caret = editor.getCaret();

			try {
				String text = document.getText(Math.min(caret.getDot(), caret.getMark()),
						Math.abs(caret.getDot() - caret.getMark()));
				text = toggleText(text);
				document.remove(Math.min(caret.getDot(), caret.getMark()), text.length());
				document.insertString(Math.min(caret.getDot(), caret.getMark()), text, null);

			} catch (BadLocationException e) {
			}

		}

		private String toggleText(String text) {
			char[] textToToggle = text.toCharArray();
			for (int i = 0; i < textToToggle.length; i++) {
				char c = textToToggle[i];
				if (Character.isUpperCase(c)) {
					textToToggle[i] = Character.toLowerCase(c);
				} else if (Character.isLowerCase(c)) {
					textToToggle[i] = Character.toUpperCase(c);
				}
			}
			return new String(textToToggle);
		}
	};

	/**
	 * Action used to sort the selected text in ascending order in the
	 * {@link JNotepadPP} program
	 */
	public final Action ASCENDING = new AbstractAction() {

		@Override
		public void actionPerformed(ActionEvent e) {
			ascending();
		}

		private void ascending() {
			sorting(0);
		}
	};

	/**
	 * Action used to sort the selected text in descending order in the
	 * {@link JNotepadPP} program
	 */
	public final Action DESCENDING = new AbstractAction() {

		@Override
		public void actionPerformed(ActionEvent e) {
			descending();
		}

		private void descending() {
			sorting(1);
		}
	};

	/**
	 * Method used to determine what type of sorting should be applied to the
	 * selected lines of the document
	 * 
	 * @param operation 0 is for ascending sort, 1 is for descending sort
	 */
	private void sorting(int operation) {
		JTextComponent editor = model.getCurrentDocument().getTextComponent();
		Document document = editor.getDocument();
		Caret caret = editor.getCaret();
		Element root = document.getDefaultRootElement();

		try {
			int firstLine = root.getElementIndex(Math.min(caret.getDot(), caret.getMark()));
			int lastLine = root.getElementIndex(Math.max(caret.getDot(), caret.getMark()));
			int numberOfLinesSelected = lastLine - firstLine + 1;

			if (numberOfLinesSelected == 1)
				return;

			List<String> lines = new ArrayList<>();
			int start = ((JTextArea) editor).getLineStartOffset(firstLine);

			for (int i = 0; i < numberOfLinesSelected; i++) {
				int lineStart = ((JTextArea) editor).getLineStartOffset(firstLine + i);
				int lineEnd = ((JTextArea) editor).getLineEndOffset(firstLine + i);

				lines.add(document.getText(lineStart, lineEnd - lineStart));
			}

			Locale locale = new Locale(provider.getCurrentLanguage());
			Collator collator = Collator.getInstance(locale);
			lines = lines.stream()
					.sorted((s1, s2) -> operation == 0 ? collator.compare(s1, s2) : collator.compare(s2, s1))
					.collect(Collectors.toList());

			int length = 0;
			for (String str : lines) {
				document.remove(start + length, str.length());
				document.insertString(start + length, str, null);
				length += str.length();
			}
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Action used to delete duplicate lines and retain only first occurrence of a
	 * line {@link JNotepadPP} program
	 */
	public final Action UNIQUE = new AbstractAction() {

		@Override
		public void actionPerformed(ActionEvent e) {
			unique();
		}

		private void unique() {
			JTextComponent editor = model.getCurrentDocument().getTextComponent();
			Document document = editor.getDocument();
			Caret caret = editor.getCaret();
			Element root = document.getDefaultRootElement();
			List<String> unique = new ArrayList<>();

			try {
				int firstLine = root.getElementIndex(Math.min(caret.getDot(), caret.getMark()));
				int lastLine = root.getElementIndex(Math.max(caret.getDot(), caret.getMark()));
				int numberOfLinesSelected = lastLine - firstLine + 1;

				if (numberOfLinesSelected == 1)
					return;

				for (int i = 0; i < numberOfLinesSelected; i++) {
					int lineStart = ((JTextArea) editor).getLineStartOffset(firstLine + i);
					int lineEnd = ((JTextArea) editor).getLineEndOffset(firstLine + i);
					String line = document.getText(lineStart, lineEnd - lineStart);
					if (unique.contains(line))
						continue;
					unique.add(line);
				}

				int start = ((JTextArea) editor).getLineStartOffset(firstLine);
				int end = ((JTextArea) editor).getLineEndOffset(lastLine);
				int length = 0;
				document.remove(start, end - start);
				for (String str : unique) {
					document.insertString(length, str, null);
					length += str.length();
				}

			} catch (BadLocationException e) {
			}
		}

	};

	/**
	 * Interface used by the casing actions to identify which method should be
	 * applied to the specified text
	 * 
	 * @author Tomislav Kurtović
	 *
	 */
	protected interface TextChange {

		/**
		 * Method to return the string with the applied caseing
		 * 
		 * @param string text to apply caseing method
		 * @return new string with applied caseing
		 */
		public String getChange(String string);
	}
}
