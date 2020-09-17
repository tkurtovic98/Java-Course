package hr.fer.zemris.java.hw17.jvdraw.objects.editors;

import java.awt.Color;
import java.awt.Point;

import javax.swing.JLabel;
import javax.swing.JTextField;

import hr.fer.zemris.java.hw17.jvdraw.objects.Circle;

/**
 * {@link GeometricalObjectEditor} used 
 * to edit the {@link Circle} components 
 * of radius and center point
 * @author Tomislav Kurtović
 *
 */
public class CircleEditor extends GeometricalObjectEditor {
	private static final long serialVersionUID = 1L;
	/**
	 * Reference to {@link GeometricalObject}
	 */
	private Circle circle;
	/**
	 * Input field used to edit center of circle
	 */
	private JTextField start;
	/**
	 * Input field used to edit radius of circle
	 */
	private JTextField radius;
	/**
	 * Input field used to edit color of circle
	 */
	private JTextField color;
	/**
	 * Constructor 
	 * @param circle circle that need the editor
	 */
	public CircleEditor(Circle circle) {
		this.circle = circle;
		initGUI();
	}
	/**
	 * Initializes all the components of the editor
	 */
	private void initGUI() {
		start= new JTextField(5);
		radius = new JTextField(5);
		color = new JTextField(5);
		
		start.setText(circle.formatStart());
		radius.setText(circle.getRadius()+"");
		color.setText(circle.hexColor());
		
		add(new JLabel("start point"));
		add(start);
		add(new JLabel("radius"));
		add(radius);
		add(new JLabel("new color"));
		add(color);
	}

	@Override
	public void checkEditing() {
		Validator.checkText(start);
		Validator.checkRadius(radius.getText());
		Validator.checkColor(color.getText());
	}
	
	@Override
	public void acceptEditing() {
		circle.setValues(getPoint(start), Integer.parseInt(radius.getText()), Color.decode(color.getText()));
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
