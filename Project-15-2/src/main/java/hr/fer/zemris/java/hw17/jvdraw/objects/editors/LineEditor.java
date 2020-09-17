package hr.fer.zemris.java.hw17.jvdraw.objects.editors;

import java.awt.Color;
import java.awt.Point;

import javax.swing.JLabel;
import javax.swing.JTextField;

import hr.fer.zemris.java.hw17.jvdraw.objects.Circle;
import hr.fer.zemris.java.hw17.jvdraw.objects.Line;
/**
 * {@link GeometricalObjectEditor} used 
 * to edit the {@link Line} components 
 * of start point or end point
 * @author Tomislav Kurtović
 *
 */
public class LineEditor extends GeometricalObjectEditor{
	private static final long serialVersionUID = 1L;
	/**
	 * Reference to {@link GeometricalObject}
	 */
	private Line line;
	/**
	 * Input field used to edit start point of line
	 */
	private JTextField start;
	/**
	 * Input field used to edit end point of line
	 */
	private JTextField end;
	/**
	 * Input field used to edit color of line
	 */
	private JTextField color;
	
	/**
	 * Constructor 
	 * @param line {@link Line} that needs the editor
	 */
	public LineEditor(Line line) {
		this.line = line;
		initGUI();
	}
	/**
	 * Used to initialize graphical components
	 */
	private void initGUI() {
		start= new JTextField(5);
		end = new JTextField(5);
		color = new JTextField(5);
		
		start.setText(line.formatStart());
		end.setText(line.formatEnd());
		color.setText(line.hexColor());
		
		add(new JLabel("start point"));
		add(start);
		add(new JLabel("end point"));
		add(end);
		add(new JLabel("line color"));
		add(color);
	}

	@Override
	public void checkEditing() {
		Validator.checkText(start);
		Validator.checkText(end);
		Validator.checkColor(color.getText());
	}

	@Override
	public void acceptEditing() {
		line.setValues(getPoint(start), getPoint(end),Color.decode(color.getText()));
	}
	/**
	 * Returns the start and end point from input field
	 * @param field input 
	 * @return start or end point
	 */
	private Point getPoint(JTextField field) {
		String[] startSplit = field.getText().split(",");
		return new Point(Integer.parseInt(startSplit[0]),Integer.parseInt(startSplit[1]));
	}

}
