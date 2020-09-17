package hr.fer.zemris.java.custom.scripting.nodes;

/**
 * Representation of non-null text node
 * used in SmartScriptParser
 *
 * @author Tomislav Kurtović
 *
 */
public class TextNode extends Node {

	private String text;
	
	@Override
	public void accept(INodeVisitor visitor) {
		visitor.visitTextNode(this);
	}
	
	/**
	 * Default constructor
	 * @param text text of node
	 */
	public TextNode(String text) {
		this.text = text;
	}
	
	public String getText() {
		return text;
	}

	@Override
	public String toString() {
		return getText();
	}
	
	
	
}
