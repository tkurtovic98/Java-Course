package hr.fer.zemris.java.hw11.jnotepadpp;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import hr.fer.zemris.java.hw11.jnotepadpp.listeners.DocumentChangedListener;
import hr.fer.zemris.java.hw11.jnotepadpp.local.FormLocalizationProvider;
import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationListener;
import hr.fer.zemris.java.hw11.jnotepadpp.local.LocalizationProvider;

/**
 * Class that represents a simple Notepad++ alike 
 * program with functionalities such as
 * opening a file, creating a new file, saving files and more
 * @author Tomislav Kurtović
 *
 */
public class JNotepadPP extends JFrame  {
	
	private static final long serialVersionUID = 1L;
	/**
	 * Working model of this frame
	 */
	private DefaultMultipleDocumentModel model;
	
	/**
	 * Field that represents all the actions a user can use
	 */
	private Actions actions;
	
	/**
	 * Status bar of this editor
	 */
	private StatusBar statusBar;
	
	/**
	 * Localization provider of the editor
	 */
	private FormLocalizationProvider flp;
	
	/**
	 * Constructor of the frame
	 */
	public JNotepadPP() {
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		setSize(500,500);
		initGUI();
		setLocationRelativeTo(null);
	}
	
	/**
	 * Used to return the working model of this frame
	 * @return
	 */
	protected DefaultMultipleDocumentModel getModel() {
		return this.model;
	}

	/**
	 * Used to initialize all the GUI elements of the frame
	 */
	private void initGUI() {
		Container cp = getContentPane();
		cp.setLayout(new BorderLayout());
		
		model = new DefaultMultipleDocumentModel();
		cp.add(model, BorderLayout.CENTER);
		
		flp = new FormLocalizationProvider(LocalizationProvider.getInstance(), this);
		
		actions = new Actions(new Clipboard(), model, this, flp);
		
		statusBar = new StatusBar();
		cp.add(statusBar, BorderLayout.PAGE_END);
		model.addMultipleDocumentListener(new DocumentChangedListener(statusBar, this, actions));
		
		disableActionsOnStart();
		windowListener();
		createMenus();
		createToolbar();
	}

	

	/**
	 * Used to add a window listener to this frame
	 * so that a custom exit operation can be implemented
	 * It uses the "exit" action as a way of checking 
	 * the documents. 
	 * @see Actions
	 */
	private void windowListener() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				actions.EXIT.actionPerformed(null);
			}	
			
			@Override
			public void windowClosed(WindowEvent e) {
				statusBar.stopTime();
			}
		});
	}
	
	
	/**
	 * Used to disable all actions upon start when
	 * there is no opened document 
	 */
	private void disableActionsOnStart() {
		actions.CLOSE.setEnabled(false);
		actions.COPY.setEnabled(false);
		actions.CUT.setEnabled(false);
		actions.UPPER_CASE.setEnabled(false);
		actions.LOWER_CASE.setEnabled(false);
		actions.INVERT_CASE.setEnabled(false);
		actions.STATS.setEnabled(false);
		actions.SAVE.setEnabled(false);
		actions.DESCENDING.setEnabled(false);
		actions.ASCENDING.setEnabled(false);
		actions.SAVE_AS.setEnabled(false);
	}
	
	/**
	 *	Used to create all the menus 
	 */
	private void createMenus() {
		JMenuBar mb = new JMenuBar();
		 Map<String, JMenu> menus = new HashMap<>();
		
		JMenu file = new JMenu(flp.getString("file"));
		
		mb.add(file);
		file.add(new JMenuItem(actions.LOAD));
		file.add(new JMenuItem(actions.CREATE));
		file.add(new JMenuItem(actions.SAVE));
		file.add(new JMenuItem(actions.SAVE_AS));
		file.add(new JMenuItem(actions.CLOSE));
		file.addSeparator();
		file.add(new JMenuItem(actions.EXIT));
		
		JMenu edit = new JMenu(flp.getString("edit"));
		mb.add(edit);
		edit.add(new JMenuItem(actions.COPY));
		edit.add(new JMenuItem(actions.CUT));
		edit.add(new JMenuItem(actions.PASTE));
		
		JMenu tools = new JMenu(flp.getString("tools"));
		mb.add(tools);
		JMenu caseing = new JMenu("Change case");
		tools.add(caseing);
		caseing.add(new JMenuItem(actions.UPPER_CASE));
		caseing.add(new JMenuItem(actions.LOWER_CASE));
		caseing.add(new JMenuItem(actions.INVERT_CASE));
		
		JMenu sort = new JMenu(flp.getString("sort"));
		tools.add(sort);
		sort.add(new JMenuItem(actions.ASCENDING));
		sort.add(new JMenuItem(actions.DESCENDING));
		
		tools.add(new JMenuItem(actions.UNIQUE));
		
		JMenu stats = new JMenu(flp.getString("statistics"));
		mb.add(stats);
		stats.add(new JMenuItem(actions.STATS));
		setJMenuBar(mb);
		
		JMenu languages = new JMenu("Languages/Jezici/Sprache");
		JMenuItem hr = new JMenuItem("hr");
		hr.addActionListener(e->LocalizationProvider.getInstance().setLanguage("hr"));
		JMenuItem de = new JMenuItem("de");
		de.addActionListener(e->LocalizationProvider.getInstance().setLanguage("de"));
		JMenuItem en = new JMenuItem("en");
		en.addActionListener(e->LocalizationProvider.getInstance().setLanguage("en"));
		languages.add(hr);
		languages.add(de);
		languages.add(en);
		
		mb.add(languages);
		
		menus.put("file", file );
		menus.put("edit", edit );
		menus.put("statistics", stats );
		menus.put("tools", tools );
		menus.put("sort", sort );
		menus.put("caseing", caseing);
		
		for(String key : menus.keySet()) {
			flp.addLocalizationListener(new ILocalizationListener() {
				@Override
				public void localizationChanged() {
					menus.get(key).setText(flp.getString(key));
				}
			});
		}
	}
	/**
	 * Used to create the toolbar
	 */
	private void createToolbar() {
		JToolBar toolBar = new JToolBar();
		toolBar.setFloatable(true);
		toolBar.add(new JButton(actions.LOAD));
		toolBar.add(new JButton(actions.CREATE));
		toolBar.add(new JButton(actions.SAVE));
		toolBar.add(new JButton(actions.SAVE_AS));
		toolBar.add(new JButton(actions.CLOSE));
		toolBar.addSeparator();
		toolBar.add(new JButton(actions.STATS));
		toolBar.add(new JButton(actions.EXIT));
		getContentPane().add(toolBar, BorderLayout.PAGE_START);
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(()-> {
			new JNotepadPP().setVisible(true);
		});
	}
}
