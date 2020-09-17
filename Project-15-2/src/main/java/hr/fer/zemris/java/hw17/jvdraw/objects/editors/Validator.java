package hr.fer.zemris.java.hw17.jvdraw.objects.editors;

import javax.swing.JTextField;
/**
 * Class used to validate user input when
 * editing {@link GeometricalObject}s
 * @author Tomislav Kurtović
 *
 */
public class Validator {

	/**
	 * Used to check if the entered text is valid or not 
	 * @param field field to check
	 */
	protected static void checkText(JTextField field) {
		String[] startSplit = field.getText().split(",");
		if(startSplit.length != 2) {
			throw new IllegalArgumentException("Point must have x and y component! \n HINT: WRITE COMPONENTS AS x,y");
		}
		
		try {
			for(String str : startSplit) {
				if(Integer.parseInt(str) < 0) {
					throw new IllegalArgumentException("Value of component cannot be negative");
				}
			}
		}catch(NumberFormatException e) {
			throw new IllegalArgumentException("Illegal arguments for point");
		}
	}
	/**
	 * Used to check whether color is valid. It is valid if it has # infront and is hex valued
	 * @param color color to check
	 */
	protected static void checkColor(String color) {
		if(!color.startsWith("#")) {
			throw new IllegalArgumentException("# has to go before color value");
		}
		if(color.substring(1).length() != 6) {
			throw new IllegalArgumentException("Color value not valid hex");
		}
	}
	/**
	 * Checks if the radius is valid
	 * @param radius radius to chekc
	 */
	protected static  void checkRadius(String radius) {
		try {
			if(Integer.parseInt(radius) < 0) {
				throw new IllegalArgumentException("Radius cannot be negative");
			}
			
		}catch(NumberFormatException e) {
			throw new IllegalArgumentException("Radius is not valid");
		}
	}

	
}
