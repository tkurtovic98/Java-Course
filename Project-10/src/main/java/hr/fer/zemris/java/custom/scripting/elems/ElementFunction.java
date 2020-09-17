package hr.fer.zemris.java.custom.scripting.elems;

/**
 * Represents element as function name
 * 
 * @author Tomislav Kurtović
 */
public class ElementFunction extends Element {

	private String name;
	
	public ElementFunction(String name) {
		this.name = name;
	}

	@Override
	public String asText() {
		return name;
	}

	@Override
	public String toString() {
		return "@" + name;
	}
	
	
}
