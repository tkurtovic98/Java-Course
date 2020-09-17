package hr.fer.zemris.java.custom.scripting.exec;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.elems.ElementFunction;
import hr.fer.zemris.java.custom.scripting.elems.ElementOperator;
import hr.fer.zemris.java.custom.scripting.elems.ElementVariable;
import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.nodes.EchoNode;
import hr.fer.zemris.java.custom.scripting.nodes.ForLoopNode;
import hr.fer.zemris.java.custom.scripting.nodes.INodeVisitor;
import hr.fer.zemris.java.custom.scripting.nodes.TextNode;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * Class that is used to execute a
 * smart script that was generated out 
 * of a file which holds the instructions 
 * @author Tomislav Kurtović
 *
 */
public class SmartScriptEngine {
	/**
	 * Document node of generated instructions
	 */
	private DocumentNode documentNode;
	/**
	 * Request context that is used to write data to some output stream
	 */
	private RequestContext requestContext;
	/**
	 * Stack used to hold different objects
	 */
	private ObjectMultistack multistack = new ObjectMultistack();
	
	/**
	 * Implementation of {@link INodeVisitor} that goes
	 * through the document and makes a tree from different
	 * parts of it. 
	 * Each part has its own set of instructions 
	 */
	private INodeVisitor visitor = new INodeVisitor() {
		@Override
		public void visitTextNode(TextNode node) {
			try {
				requestContext.write(node.toString());
			} catch (IOException e) {
				System.out.println("Error while writing ");
			}
		}

		@Override
		public void visitForLoopNode(ForLoopNode node) {
			String varName = node.getVariable().toString();
			Integer step  = node.getStepExpression() == null ? 1 : Integer.parseInt(node.getStepExpression().toString());
			multistack.push(varName, new ValueWrapper(node.getStartExpression()));
			while(true) {
				Integer var = Integer.parseInt(multistack.peek(varName).getValue().toString());
				if(var > Integer.parseInt(node.getEndExpression().toString())) break;
				
				for(int i = 0, children = node.numberOfChildren(); i < children; i ++) {
					node.getChild(i).accept(this);
				}
				
				var+= step;
				multistack.peek(varName).setValue(var);
			}
			multistack.pop(varName);
		}

		@Override
		public void visitEchoNode(EchoNode node) {
			ObjectMultistack stack = new ObjectMultistack();
			String key = "var".intern();
			Functions functions = new Functions(stack, key, requestContext);
			String varName;
			
			for(Element e : node.getElements()) {
				if(e instanceof ElementVariable) {
					varName = e.toString();
					if(!multistack.isEmpty(varName)) {
						stack.push(key,multistack.peek(varName));
					}
					continue;
				}
				
				if(e instanceof ElementOperator) {
					int left = Integer.parseInt(stack.pop(key).getValue().toString());
					int right = Integer.parseInt(stack.pop(key).getValue().toString());
					stack.push(key, new ValueWrapper(Operations.executeOperation(left,right,e.toString())));
					continue;
				}
				
				if(e instanceof ElementFunction) {
					ValueWrapper left = stack.pop(key);
					if(stack.isEmpty(key)) {
						functions.executeFunction(e.asText(), left.getValue(), null);
					} else {
						ValueWrapper right = stack.pop(key);
						functions.executeFunction(e.asText(), left.getValue(), right.getValue());
					}
					continue;
				}
				
				stack.push(key, new ValueWrapper(e.toString()));
			}
			
			List<ValueWrapper> invertedStack = new ArrayList<>();
			while(true) {
				if(stack.isEmpty(key))break;
				invertedStack.add(0, stack.pop(key));
			}
			
			if(invertedStack.size() != 0) {
				invertedStack.forEach(object -> {
					try {
						if(object.getValue() != null) {
							requestContext.write(object.getValue().toString());
						}
					} catch (IOException e1) {
						System.out.println("Error while writing to context");
					}
				});
			}
		}

		@Override
		public void visitDocumentNode(DocumentNode node) {
			for(int i = 0, children = node.numberOfChildren(); i < children; i++) {
				node.getChild(i).accept(this);
			}
		}
	};
	
	/**
	 * Constructor of this engine
	 * @param documentNode document with instructions
	 * @param requestContext context to write output
	 */
	public SmartScriptEngine(DocumentNode documentNode, RequestContext requestContext) {
		this.documentNode = documentNode;
		this.requestContext = requestContext;
	}
	
	/**
	 * Executes the actions defined in the document that
	 * was passed to this engine
	 */
	public void execute() {
		documentNode.accept(visitor);
	}
}
