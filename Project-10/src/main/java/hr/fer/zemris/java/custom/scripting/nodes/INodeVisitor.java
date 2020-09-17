package hr.fer.zemris.java.custom.scripting.nodes;

import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;

/**
 * Interface used to visit all 
 * the nodes when remaking a smart script
 * that was parsed with the {@link SmartScriptParser}
 * @author Tomislav Kurtović
 *
 */
public interface INodeVisitor {
	
	/**
	 * Executed when the visitor visits
	 * the {@link TextNode}
	 * @param node visited text node
	 */
	public void visitTextNode(TextNode node);
	/**
	 * Executed when the visitor visits
	 * the {@link ForLoopNode}
	 * @param node visited for loop node
	 */
	public void visitForLoopNode(ForLoopNode node);
	/**
	 * Executed when the visitor visits
	 * the {@link EchoNode}
	 * @param node visited echo node
	 */
	public void visitEchoNode(EchoNode node);
	/**
	 * Executed when the visitor visits
	 * the {@link DocumentNode}
	 * @param node visited document node
	 */
	public void visitDocumentNode(DocumentNode node);
}
