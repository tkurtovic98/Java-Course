package hr.fer.zemris.java.hw17.jvdraw;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.function.Supplier;

import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import hr.fer.zemris.java.hw17.jvdraw.actions.Actions;
import hr.fer.zemris.java.hw17.jvdraw.color.ColorInfo;
import hr.fer.zemris.java.hw17.jvdraw.color.JColorArea;
import hr.fer.zemris.java.hw17.jvdraw.drawing.DrawingModel;
import hr.fer.zemris.java.hw17.jvdraw.drawing.DrawingModelImpl;
import hr.fer.zemris.java.hw17.jvdraw.drawing.JDrawingCanvas;
import hr.fer.zemris.java.hw17.jvdraw.list.DrawingObjectListModel;
import hr.fer.zemris.java.hw17.jvdraw.objects.GeometricalObject;
import hr.fer.zemris.java.hw17.jvdraw.objects.editors.GeometricalObjectEditor;
import hr.fer.zemris.java.hw17.jvdraw.tools.States;
import hr.fer.zemris.java.hw17.jvdraw.tools.Tool;

/**
 * Class that represents the drawing program
 * where user can draw different shapes of 
 * differernt colors 
 * @author Tomislav Kurtović
 *
 */
public class JVDraw extends JFrame {
	private static final long serialVersionUID = 1L;
	
	/**
	 * Current state of program
	 */
	private Tool currentState;
	/**
	 * Configurator of the current state 
	 */
	private States statesConfig = new States(this);
	/**
	 * Reference to program drawing model
	 */
	private DrawingModel drawingModel;
	/**
	 * Different actions supported by program
	 */
	private Actions actions;
	
	/**
	 * Constructor 
	 */
	public JVDraw() {
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		setSize(500, 500);
		initGUI();
		setLocationRelativeTo(null);
	}
	
	/**
	 * UPdates current state
	 * @param state new state
	 */
	public void setCurrentState(Tool state) {
		currentState = state;
	}
	/**
	 * Used to initialize all the gui components 
	 */
	private void initGUI() {
		drawingModel = new DrawingModelImpl();
		statesConfig.setDrawingModel(drawingModel);
		setToolbar();
		setDrawingCanvas();
		setSideList();
		setMenu();
		
		windowListener();
	}
	
	/**
	 * Adds a {@link WindowListener} to the frame
	 */
	private void windowListener() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				actions.EXIT.actionPerformed(null);
			}
		});
	}
	/**
	 * Sets the toolbar of the frame by adding 
	 * instances of {@link JColorArea} and also {@link Tool}s that
	 * represent the states
	 */
	private void setToolbar() {
		JToolBar toolBar = new JToolBar();
		toolBar.setFloatable(true);
		
		JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout(FlowLayout.LEFT));
		toolBar.add(panel);
		
		JColorArea foreground = new JColorArea(Color.BLACK, this);
		JColorArea background = new JColorArea(Color.BLACK, this);
		
		statesConfig.setColorProviders(foreground, background);
		
		panel.add(foreground);
		panel.add(background);
		
		getContentPane().add(toolBar, BorderLayout.PAGE_START);
		
		ColorInfo cInfo = new ColorInfo(foreground, background);
		foreground.addColorChangeListener(cInfo);
		background.addColorChangeListener(cInfo);
		getContentPane().add(cInfo, BorderLayout.PAGE_END);
		
		addButtons(toolBar,panel);
	}
	
	/**
	 * Adds the buttons to support state change 
	 * @param tb toolbar of frame
	 * @param panel panel to add buttons to
	 */
	private void addButtons(JToolBar tb, JPanel panel) {
		ButtonGroup buttonGroup = new ButtonGroup();
		
		JRadioButton line = new JRadioButton(statesConfig.LINE);
		JRadioButton circle = new JRadioButton(statesConfig.CIRCLE);
		JRadioButton filled_circle = new JRadioButton(statesConfig.FILL_CIRCLE);
		
		buttonGroup.add(line);
		buttonGroup.add(circle);
		buttonGroup.add(filled_circle);
		
		panel.add(line);
		panel.add(circle);
		panel.add(filled_circle);
	}
	
	/**
	 * Sets the {@link JDrawingCanvas} of the program
	 */
	private void setDrawingCanvas() {
		Supplier<Tool> current = ()-> currentState;
		JDrawingCanvas canvas = new JDrawingCanvas(current);
		canvas.setPreferredSize(getContentPane().getMaximumSize());
		drawingModel.addDrawingModelListener(canvas);
		statesConfig.setCanvas(canvas);
		getContentPane().add(canvas, BorderLayout.CENTER);
	}
	
	/**
	 * Sets the list of the program that contains all drawed objects to {@link JDrawingCanvas}
	 * with added {@link KeyListener}s
	 */
	private void setSideList() {
		DrawingObjectListModel listModel = new DrawingObjectListModel(drawingModel);
		JList<GeometricalObject> list = new JList<>(listModel);
		
		JPanel sidePanel = new JPanel();
		sidePanel.add(new JScrollPane(list));
		
		list.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				super.keyPressed(e);
				
				if(list.getSelectedIndex() < 0) return;
				
				if(e.getKeyCode() == KeyEvent.VK_DELETE) {
					drawingModel.remove(list.getModel().getElementAt(list.getSelectedIndex()));
				}
				
				if(e.getKeyCode() == KeyEvent.VK_PLUS) {
					drawingModel.changeOrder(list.getModel().getElementAt(list.getSelectedIndex()), -1);
				}
				
				if(e.getKeyCode() == KeyEvent.VK_MINUS) {
					drawingModel.changeOrder(list.getModel().getElementAt(list.getSelectedIndex()), 1);
				}
			}
		});
		
		list.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				super.mouseEntered(e);
				list.setEnabled(true);
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				super.mouseExited(e);
				list.setEnabled(false);
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				super.mouseClicked(e);
				if(e.getClickCount() == 2) {
					showDialog(list.getModel().getElementAt(list.locationToIndex(e.getPoint())));
				}
			}
		});
		
		getContentPane().add(sidePanel, BorderLayout.LINE_END);
	}
	
	/**
	 * Shows {@link GeometricalObjectEditor}
	 * @param elementAt selected {@link GeometricalObject} from list
	 */
	protected void showDialog(GeometricalObject elementAt) {
			GeometricalObjectEditor editor = elementAt.createGeometricalObjectEditor();
			if(JOptionPane.showConfirmDialog(this, editor, "Edit", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
				try {
					editor.checkEditing();
					editor.acceptEditing();
				} catch(IllegalArgumentException e) {
					JOptionPane.showMessageDialog(this, e.getMessage());
				}
			}
	}
	/**
	 * Sets the menu of the program
	 */
	private void setMenu() {
		JMenuBar mB = new JMenuBar();
		JMenu menu = new JMenu("File");
		actions = new Actions(this);
		actions.setDrawingModel(drawingModel);
		
		menu.add(new JMenuItem(actions.OPEN));
		menu.add(new JMenuItem(actions.SAVE));
		menu.add(new JMenuItem(actions.SAVE_AS));
		menu.add(new JMenuItem(actions.EXPORT));
		menu.add(new JMenuItem(actions.EXIT));
		
		mB.add(menu);
		setJMenuBar(mB);
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(()-> {
			new JVDraw().setVisible(true);
		});
	}

}
