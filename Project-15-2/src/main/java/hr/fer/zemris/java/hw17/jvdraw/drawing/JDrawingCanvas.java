package hr.fer.zemris.java.hw17.jvdraw.drawing;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import javax.swing.JComponent;

import hr.fer.zemris.java.hw17.jvdraw.objects.Circle;
import hr.fer.zemris.java.hw17.jvdraw.objects.FilledCircle;
import hr.fer.zemris.java.hw17.jvdraw.objects.GeometricalObject;
import hr.fer.zemris.java.hw17.jvdraw.objects.GeometricalObjectPainter;
import hr.fer.zemris.java.hw17.jvdraw.objects.GeometricalObjectVisitor;
import hr.fer.zemris.java.hw17.jvdraw.objects.Line;
import hr.fer.zemris.java.hw17.jvdraw.tools.Tool;
/**
 * Class that represents canvas user can draw on.
 * It alows user to draw 
 * {@link GeometricalObject}s such as :
 * <li> {@link Line}
 * <li> {@link Circle}
 * <li> {@link FilledCircle}
 * @author Tomislav Kurtović
 *
 */
public class JDrawingCanvas extends JComponent implements DrawingModelListener {
	private static final long serialVersionUID = 1L;
	/**
	 * Supplier used to get current state of program
	 */
	private Supplier<Tool> supplier;
	private GeometricalObjectVisitor painter;
	private DrawingModel drawingModel = null;

	/**
	 * Constructor of the {@link JDrawingCanvas}
	 * @param supplier supplier of current state ({@link Tool})
	 */
	public JDrawingCanvas(Supplier<Tool> supplier) {
		this.supplier = supplier;
		mouseListeners();
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.fillRect(0, 0, getPreferredSize().width, getPreferredSize().height);

		painter = new GeometricalObjectPainter(g2d);

		if(drawingModel != null) {
			for(int i = 0, len = drawingModel.getSize(); i < len; i++) {
				drawingModel.getObject(i).accept(painter);
			}
		}
		
		if (supplier.get() != null) {
			supplier.get().paint(g2d);
		}
		
		g2d.dispose();
	}

	/**
	 * Used to add all the necessary mouse listeners to the canvas 
	 * so that it can forward all mouse events to appropriate current {@link Tool}
	 */
	private void mouseListeners() {
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (supplier.get() != null)
					supplier.get().mouseClicked(e);
			}

			@Override
			public void mousePressed(MouseEvent e) {
				if (supplier.get() != null)
					supplier.get().mousePressed(e);
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				if (supplier.get() != null)
					supplier.get().mouseReleased(e);
			}
		});

		addMouseMotionListener(new MouseAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				if (supplier.get() != null)
					supplier.get().mouseDragged(e);
			}

			@Override
			public void mouseMoved(MouseEvent e) {
				if (supplier.get() != null)
					supplier.get().mouseMoved(e);
			}
		});
	}

	@Override
	public void objectsAdded(DrawingModel source, int index0, int index1) {
		drawingModel = source;
		repaint();
	}

	@Override
	public void objectsRemoved(DrawingModel source, int index0, int index1) {
		drawingModel = source;
		repaint();
	}

	@Override
	public void objectsChanged(DrawingModel source, int index0, int index1) {
		drawingModel = source;
		repaint();
	}
}
