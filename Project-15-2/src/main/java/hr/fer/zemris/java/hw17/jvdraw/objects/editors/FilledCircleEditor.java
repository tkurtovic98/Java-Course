package hr.fer.zemris.java.hw17.jvdraw.objects.editors;

import java.awt.Color;
import java.awt.Point;

import javax.swing.JLabel;
import javax.swing.JTextField;

import hr.fer.zemris.java.hw17.jvdraw.objects.Circle;
import hr.fer.zemris.java.hw17.jvdraw.objects.FilledCircle;

/**
 * {@link GeometricalObjectEditor} used 
 * to edit the {@link FilledCircle} components 
 * of radius ,center point etc.
 * @author Tomislav Kurtović
 *
 */
public class FilledCircleEditor extends GeometricalObjectEditor {
	private static final long serialVersionUID = 1L;
	/**
	 * Reference to {@link GeometricalObject}
	 */
	private FilledCircle filledCircle;
	/**
	 * Input field used to edit center of circle
	 */
	private JTextField start;
	/**
	 * Input field used to edit radius of circle
	 */
	private JTextField radius;
	/**
	 * Input field used to edit fill color of circle
	 */
	private JTextField fillColor;
	/**
	 * Input field used to edit border color of circle
	 */
	private JTextField borderColor;
	
	/**
	 * Constructor
	 * @param filledCircle {@link FilledCircle} that needs this editor
	 */
	public  FilledCircleEditor(FilledCircle filledCircle) {
		this.filledCircle = filledCircle;
		initGUI();
	}
	/**
	 * Used to initialize graphical elements
	 */
	private void initGUI() {
		start= new JTextField(5);
		radius = new JTextField(5);
		fillColor = new JTextField(5);
		borderColor = new JTextField(5);
		
		start.setText(filledCircle.formatStart());
		radius.setText(filledCircle.getRadius()+"");
		fillColor.setText(filledCircle.hexColor());
		borderColor.setText(filledCircle.hexBorderColor());
		
		
		add(new JLabel("start point"));
		add(start);
		add(new JLabel("radius"));
		add(radius);
		add(new JLabel("new fill color"));
		add(fillColor);
		add(new JLabel("new border color"));
		add(borderColor);
	}

	@Override
	public void checkEditing() {
		Validator.checkText(start);
		Validator.checkRadius(radius.getText());
		Validator.checkColor(fillColor.getText());
		Validator.checkColor(borderColor.getText());
	}

	@Override
	public void acceptEditing() {
		filledCircle.setValues(getPoint(start), Integer.parseInt(radius.getText()), 
				Color.decode(fillColor.getText()), Color.decode(borderColor.getText()));
	}
	/**
	 * Returns the center point from input field
	 * @param field input 
	 * @return center point
	 */
	private Point getPoint(JTextField field) {
		String[] startSplit = field.getText().split(",");
		return new Point(Integer.parseInt(startSplit[0]),Integer.parseInt(startSplit[1]));
	}

}
