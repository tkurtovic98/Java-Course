package hr.fer.zemris.java.custom.scripting.nodes;

/**
 * Class that represents a concrete implementation
 * of the {@link INodeVisitor} interface. 
 * Specifically, it defines what the visit methods
 * do when the visitor visits {@link ForLoopNode},
 * {@link TextNode}, {@link EchoNode}, {@link DocumentNode}
 * @author Tomislav Kurtović
 *
 */
public class NodeVisitorImpl implements INodeVisitor {

	/**
	 * {@link StringBuilder} used to remake the parsed smart script
	 */
	StringBuilder builder = new StringBuilder();
	
	@Override
	public void visitTextNode(TextNode node) {
		builder.append(node.toString());
		System.out.printf(node.toString());
	}

	@Override
	public void visitForLoopNode(ForLoopNode node) {
		builder.append(node.toString());
		System.out.printf(node.toString());
		for(int i = 0; i < node.numberOfChildren(); i++) {
			node.getChild(i).accept(this);
		}
		builder.append("{$END$}");
		System.out.printf("{$END$}");
		
	}

	@Override
	public void visitEchoNode(EchoNode node) {
		builder.append(node.toString());
		System.out.printf(node.toString());
	}

	@Override
	public void visitDocumentNode(DocumentNode node) {
		for(int i = 0; i < node.numberOfChildren(); i++) {
			node.getChild(i).accept(this);
		}
	}
	
	/**
	 * Used to return the remade smart script
	 * @return remade smart script
	 */
	public String getText() {
		return builder.toString();
	}
}
