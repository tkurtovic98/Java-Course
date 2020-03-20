package hr.fer.zemris.java.custom.scripting.nodes;


/**
 * Object used by the SmartScriptParser to
 * store the document body.
 * This class is the top parent of the 
 * parser elements
 * 
 * @author Tomislav Kurtović
 *
 */
public class DocumentNode extends Node {

	String documentBody;

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((documentBody == null) ? 0 : documentBody.hashCode());
		return result;
	}

	/**
	 * equals method that returns true when the documentNodes have the 
	 * same number of children
	 * 
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DocumentNode other = (DocumentNode) obj;
		if (documentBody == null) {
			if (other.documentBody != null)
				return false;
		} else if (!documentBody.equals(other.documentBody))
			return false;
		return this.numberOfChilder() == other.numberOfChilder();
	}
	
	
	
	
}
