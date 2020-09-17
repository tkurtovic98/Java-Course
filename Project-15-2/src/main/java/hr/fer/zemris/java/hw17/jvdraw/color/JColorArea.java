package hr.fer.zemris.java.hw17.jvdraw.color;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;
import java.util.PrimitiveIterator.OfDouble;

import javax.swing.JColorChooser;
import javax.swing.JComponent;

import hr.fer.zemris.java.hw17.jvdraw.JVDraw;

/**
 * Class that is used in 
 * defining current color of
 * the {@link JVDraw} program
 * @author Tomislav Kurtović
 *
 */
public class JColorArea extends JComponent implements IColorProvider {
	private static final long serialVersionUID = 1L;
	/**
	 * List of {@link ColorChangeListener}s
	 */
	private List<ColorChangeListener> colorChangeListeners;
	/**
	 * active color of this component
	 */
	private Color selectedColor;
	/**
	 * instance of {@link JVDraw}
	 */
	private JVDraw instance;
	
	/**
	 * Constructor of the {@link JColorArea} 
	 * @param initialColor inital color of the component
	 * @param instance instance of {@link JVDraw}
	 */
	public  JColorArea(Color initialColor, JVDraw instance) {
		selectedColor = initialColor;
		colorChangeListeners = new ArrayList<>();
		this.instance = instance;
		mouseListener();
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		Graphics2D g2d = (Graphics2D)g;
		g2d.setColor(selectedColor);
		g2d.fillRect(0, 0, getPreferredSize().width, getPreferredSize().height);
		
		g2d.dispose();
	}
	
	/**
	 * Called when clicked on.
	 * It displays a {@link JColorChooser} so the user can pick a color
	 * and set it to the current active color
	 */
	public void clicked() {
		Color newColor = JColorChooser.showDialog(
                instance,
                "Choose new color",
                null);
		if(newColor == null) return;
		if(!newColor.equals(selectedColor)) {
			setSelectedColor(newColor);
		}
	}
	
	/**
	 * Used to set selected color from user
	 * @param newColor user selected color
	 */
	private void setSelectedColor(Color newColor) {
		Color tmp = selectedColor;
		selectedColor = newColor;
		notifyColorChangeListeners(tmp, newColor);
	}
	/**
	 * Used to define mouse listener for when user click on it
	 */
	private void mouseListener() {
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				clicked();
				repaint();
			}
		});
	}
	
	/**
	 * Notifies the componenst {@link ColorChangeListener}s of a change in color
	 * @param newColor new color of the component
	 * @param tmp 
	 */
	private void notifyColorChangeListeners(Color newColor, Color tmp) {
		for(ColorChangeListener l : colorChangeListeners) {
			l.newColorSelected(this, tmp, newColor);
		}
	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(15,15);
	}
	
	@Override
	public Color getCurrentColor() {
		return selectedColor;
	}

	@Override
	public void addColorChangeListener(ColorChangeListener l) {
		if(!colorChangeListeners.contains(l)) {
			colorChangeListeners.add(l);
		}
	}

	@Override
	public void removeColorChangeListener(ColorChangeListener l) {
		if(colorChangeListeners.contains(l)) {
			colorChangeListeners.add(l);
		}
	}
}
