package hr.fer.zemris.java.hw17.jvdraw.actions;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import javax.swing.filechooser.FileFilter;


import hr.fer.zemris.java.hw17.jvdraw.JVDraw;
import hr.fer.zemris.java.hw17.jvdraw.drawing.DrawingModel;
import hr.fer.zemris.java.hw17.jvdraw.drawing.JDrawingCanvas;
import hr.fer.zemris.java.hw17.jvdraw.objects.Circle;
import hr.fer.zemris.java.hw17.jvdraw.objects.FilledCircle;
import hr.fer.zemris.java.hw17.jvdraw.objects.GeometricalObject;
import hr.fer.zemris.java.hw17.jvdraw.objects.GeometricalObjectBBCalculator;
import hr.fer.zemris.java.hw17.jvdraw.objects.GeometricalObjectPainter;
import hr.fer.zemris.java.hw17.jvdraw.objects.Line;

/**
 * Class that is used to instantiate all 
 * the actions supported by the {@link JVDraw} program
 * like opening or saving a drawing
 * @author Tomislav Kurtović
 *
 */
public class Actions {

	/**
	 * Reference to {@link DrawingModel}
	 */
	private static DrawingModel drawingModel;
	/**
	 * Reference to instance of {@link JVDraw}
	 */
	private static JVDraw instance;
	/**
	 * Current drawing, used when saving and opening drawings
	 */
	private static Path activePath = null;

	/**
	 * Constructor
	 * @param instance instance of {@link JVDraw}
	 */
	public Actions(JVDraw instance) {
		Actions.instance = instance;
		configureActions();
	}
	/**
	 * Used to configure all the supported actions
	 */
	private void configureActions() {
		SAVE.putValue(Action.NAME, "Save");
		SAVE.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control S"));
		SAVE.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_S);

		OPEN.putValue(Action.NAME, "Open");
		OPEN.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control O"));
		OPEN.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_O);

		SAVE_AS.putValue(Action.NAME, "Save_As");
		SAVE_AS.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control B"));
		SAVE_AS.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_B);

		EXPORT.putValue(Action.NAME, "Export");
		EXPORT.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control E"));
		EXPORT.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_E);

		EXIT.putValue(Action.NAME, "Exit");
		EXIT.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control X"));
		EXIT.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_X);
	}
	
	/**
	 * Sets the {@link DrawingModel} reference 
	 * @param drawingModel current {@link DrawingModel}
	 */
	public void setDrawingModel(DrawingModel drawingModel) {
		Actions.drawingModel = drawingModel;
	}

	/**
	 * Action used to open a drawing 
	 */
	public final Action OPEN = new AbstractAction() {
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			openJVD();
		}

		private void openJVD() {
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setDialogTitle("Open Drawing");
			if (fileChooser.showOpenDialog(instance) != JFileChooser.APPROVE_OPTION) {
				JOptionPane.showMessageDialog(instance, "Opening of the drawing was canceled");
				return;
			}

			if (!fileChooser.getSelectedFile().toPath().toString().endsWith(".jvd")) {
				JOptionPane.showMessageDialog(instance, "Error : Invalid file format selected", "ERROR",
						JOptionPane.ERROR_MESSAGE);
				return;
			}

			if (!Files.isReadable(fileChooser.getSelectedFile().toPath())) {
				JOptionPane.showMessageDialog(instance, "Error : Can not read from file", "ERROR",
						JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			if(activePath != null) {
				drawingModel.clear();
			}
			activePath = fileChooser.getSelectedFile().toPath();
			
			try {
				for (String str : Files.readAllLines(activePath)) {
					drawingModel.add(getGeometricalObject(str));
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

		private GeometricalObject getGeometricalObject(String str) {
			String[] split = str.split("\\s+");

			switch (split[0]) {
			case "LINE":
				return new Line(new Point(Integer.parseInt(split[1]), Integer.parseInt(split[2])),
						new Point(Integer.parseInt(split[3]), Integer.parseInt(split[4])),
						new Color(Integer.parseInt(split[5]), Integer.parseInt(split[6]), Integer.parseInt(split[7])));
			case "CIRCLE":
				return new Circle(new Point(Integer.parseInt(split[1]), Integer.parseInt(split[2])),
						Integer.parseInt(split[3]),
						new Color(Integer.parseInt(split[4]), Integer.parseInt(split[5]), Integer.parseInt(split[6])));
			case "FCIRCLE":
				return new FilledCircle(new Point(Integer.parseInt(split[1]), Integer.parseInt(split[2])),
						Integer.parseInt(split[3]),
						new Color(Integer.parseInt(split[4]), Integer.parseInt(split[5]), Integer.parseInt(split[6])),
						new Color(Integer.parseInt(split[7]), Integer.parseInt(split[8]), Integer.parseInt(split[9])));
			}
			return null;
		}
	};

	/**
	 * Action used to save a drawing 
	 */
	public final Action SAVE = new AbstractAction() {
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			saveFile();
		}

		private void saveFile() {
			JVDrawSaveVisitor visitor = new JVDrawSaveVisitor();

			if (activePath == null) {
				JFileChooser jfc = new JFileChooser();

				jfc.setFileFilter(new CustomFilter("*.jvd", "jvd"));

				jfc.setDialogTitle("Save file");
				if (jfc.showSaveDialog(instance) != JFileChooser.APPROVE_OPTION) {
					JOptionPane.showMessageDialog(instance, "Nothing saved", "Info", JOptionPane.INFORMATION_MESSAGE);
					return;
				}
				activePath = jfc.getSelectedFile().toPath();
			}

			if (!activePath.toString().endsWith(".jvd")) {
				JOptionPane.showMessageDialog(instance, "Error : Invalid file format, only *.jvd ", "ERROR",
						JOptionPane.ERROR_MESSAGE);
				activePath = null;
				return;
			}

			for (int i = 0, len = drawingModel.getSize(); i < len; i++) {
				drawingModel.getObject(i).accept(visitor);
			}

			visitor.save(activePath);

			drawingModel.clearModifiedFlag();
			JOptionPane.showMessageDialog(instance, "Action completed", "Info", JOptionPane.INFORMATION_MESSAGE);
			return;
		}
	};
	/**
	 * Action used to save a drawing 
	 */
	public final Action SAVE_AS = new AbstractAction() {

		@Override
		public void actionPerformed(ActionEvent e) {
			save_as();
		}

		private void save_as() {
			JVDrawSaveVisitor visitor = new JVDrawSaveVisitor();
			JFileChooser jfc = new JFileChooser();

			jfc.setFileFilter(new CustomFilter("*.jvd", "jvd"));

			jfc.setDialogTitle("Save file");
			if (jfc.showSaveDialog(instance) != JFileChooser.APPROVE_OPTION) {
				JOptionPane.showMessageDialog(instance, "Nothing saved", "Info", JOptionPane.INFORMATION_MESSAGE);
				return;
			}
			activePath = jfc.getSelectedFile().toPath();
			for (int i = 0, len = drawingModel.getSize(); i < len; i++) {
				drawingModel.getObject(i).accept(visitor);
			}

			visitor.save(activePath);

			drawingModel.clearModifiedFlag();
			JOptionPane.showMessageDialog(instance, "Action completed", "Info", JOptionPane.INFORMATION_MESSAGE);
			return;
		}
	};
	/**
	 * Action used to exit the program and save unsaved changes  
	 */
	public final Action EXIT = new AbstractAction() {

		@Override
		public void actionPerformed(ActionEvent e) {
			exit();
		}

		private void exit() {
			if (drawingModel.isModified()) {

				int x = JOptionPane.showConfirmDialog(instance, "Modification found, do you want to save?",
						"Modified drawing", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);

				if (x == 0) {
					SAVE.actionPerformed(null);
					return;
				}
				if (x == 2) {
					return;
				}
			}
			instance.dispose();
		}
	};
	/**
	 * Action used to export a drawing to png, gif or jpg format
	 */
	public final Action EXPORT = new AbstractAction() {

		private List<String> validExtensions = Arrays.asList(new String[] { "png", "gif", "jpg" });

		@Override
		public void actionPerformed(ActionEvent e) {
			export();
		}

		private void export() {
			JFileChooser jfc = new JFileChooser();

			jfc.setFileFilter(new CustomFilter("*.png,*.jpg,*gid", new String[] { "png", "gif", "jpg" }));

			jfc.setDialogTitle("Export file");
			if (jfc.showSaveDialog(instance) != JFileChooser.APPROVE_OPTION) {
				JOptionPane.showMessageDialog(instance, "Nothing exported", "Info", JOptionPane.INFORMATION_MESSAGE);
				return;
			}

			Path exportDest = jfc.getSelectedFile().toPath();

			String extension = exportDest.toString().substring(exportDest.toString().lastIndexOf(".") + 1);

			if (!validExtensions.contains(extension)) {
				JOptionPane.showMessageDialog(instance, "Error : Invalid export format ", "ERROR",
						JOptionPane.ERROR_MESSAGE);
				return;
			}
			GeometricalObjectBBCalculator calc = new GeometricalObjectBBCalculator();

			for (int i = 0, len = drawingModel.getSize(); i < len; i++) {
				drawingModel.getObject(i).accept(calc);
			}

			Rectangle box = calc.getBoundingBox();
			BufferedImage image = new BufferedImage(box.width, box.height, BufferedImage.TYPE_3BYTE_BGR);

			Graphics2D g = image.createGraphics();

			g.translate(-box.x, -box.y);

			GeometricalObjectPainter painter = new GeometricalObjectPainter(g);
			for (int i = 0, len = drawingModel.getSize(); i < len; i++) {
				drawingModel.getObject(i).accept(painter);
			}

			g.dispose();

			File file = exportDest.toFile();
			try {
				ImageIO.write(image, extension, file);
			} catch (IOException e) {
				e.printStackTrace();
			}

			JOptionPane.showMessageDialog(instance, "Action completed", "Info", JOptionPane.INFORMATION_MESSAGE);
			return;
		}
	};
	/**
	 * Class that represents custom filter for 
	 * the {@link JFileChooser} used in some 
	 * actions of the {@link JVDraw} program
	 * @author Tomislav Kurtović
	 *
	 */
	protected static class CustomFilter extends FileFilter {
		private String[] fileExtensions;
		private String extension;
		private String description;

		/**
		 * Constructor
		 * @param description description of filter
		 * @param extensions different extensions
		 */
		protected CustomFilter(String description, String[] extensions) {
			this.fileExtensions = extensions;
		}
		/**
		 * Constructor
		 * @param description description of filter
		 * @param extension extension
		 */
		protected CustomFilter(String description, String extension) {
			this.extension = extension;
		}

		@Override
		public boolean accept(File pathname) {
			if (fileExtensions == null) {
				if (pathname.getName().toLowerCase().endsWith(extension))
					return true;
			} else {
				for (String extension : fileExtensions) {
					if (pathname.getName().toLowerCase().endsWith(extension)) {
						return true;
					}
				}
			}
			return false;
		}

		@Override
		public String getDescription() {
			return description;
		}
	}

}
