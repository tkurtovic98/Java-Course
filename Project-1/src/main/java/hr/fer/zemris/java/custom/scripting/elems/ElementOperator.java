package hr.fer.zemris.java.custom.scripting.elems;

/**
 * Represents element as operator
 * 
 * @author Tomislav Kurtović
 *
 */
public class ElementOperator extends Element {

	private String symbol;
	
	public ElementOperator(String symbol) {
		this.symbol = symbol;
	}

	@Override
	public String asText() {
		return symbol;
	}

	@Override
	public String toString() {
		return symbol;
	}
	
	
}
