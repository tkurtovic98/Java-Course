package hr.fer.zemris.java.custom.scripting.elems;

/**
 * 
 * Represents Element as String value
 * 
 * @author Tomislav Kurtović
 *
 */
public class ElementString extends Element {

	private String value;
	
	public ElementString(String value) {
		this.value = value;
	}

	@Override
	public String asText() {
  		return value;
	}

	@Override
	public String toString() {
		return value;
	}
	
	
}
