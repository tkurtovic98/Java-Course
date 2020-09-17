package hr.fer.zemris.java.custom.scripting.elems;


/**
 * Represents element as variable
 * 
 * @author Tomislav Kurtović
 *
 */
public class ElementVariable extends Element {

	private String name;
	
	public ElementVariable(String name) {
		this.name = name;
	}
	
	public String asText() {
		return name;
	}

	@Override
	public String toString() {
		return name;
	}
	
	
}
