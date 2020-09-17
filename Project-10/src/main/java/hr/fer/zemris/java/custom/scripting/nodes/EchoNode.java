package hr.fer.zemris.java.custom.scripting.nodes;

import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.elems.ElementString;

/**
 * Representation of non-null open tag in 
 * the SmartScriptParser which can have
 * many argument types
 * 
 * @author Tomislav Kurtović
 *
 */
public class EchoNode extends Node {

	/**
	 * storage of elements
	 */
	private Element[] elements;
	
	/**
	 * Default constructor
	 * @param elements passed array of elements that form this node
	 */
	public EchoNode (Element[] elements) {
		this.elements = elements;
	}
	
	/**
	 * 
	 * @return elements
	 */
	public Element[] getElements() {
		return elements;
	}
	
	@Override
	public void accept(INodeVisitor visitor) {
		visitor.visitEchoNode(this);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		for(int i = 0, size = elements.length; i < size; i++) {
			if(elements[i] instanceof ElementString) {
				builder.append("\""+elements[i].toString()+ "\" ");
			}else {
				builder.append(elements[i].toString()+ " ");
			}
		}
		return "{$"+builder.toString() + "$}";
	}
}
