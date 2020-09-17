package hr.fer.zemris.java.custom.scripting.elems;

/**
 * Represents Element with double as its value
 * 
 * @author Tomislav Kurtović
 *
 */

public class ElementConstantDouble extends Element {

	private double value;
	
	public ElementConstantDouble(double value) {
		this.value = value;
	}

	@Override
	public String asText() {
		return String.valueOf(value);
	}
}
