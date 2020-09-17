package hr.fer.zemris.java.custom.scripting.parser;

import hr.fer.zemris.java.custom.collections.EmptyStackException;
import hr.fer.zemris.java.custom.collections.ObjectStack;
import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.elems.ElementConstantDouble;
import hr.fer.zemris.java.custom.scripting.elems.ElementConstantInteger;
import hr.fer.zemris.java.custom.scripting.elems.ElementFunction;
import hr.fer.zemris.java.custom.scripting.elems.ElementOperator;
import hr.fer.zemris.java.custom.scripting.elems.ElementString;
import hr.fer.zemris.java.custom.scripting.elems.ElementVariable;
import hr.fer.zemris.java.custom.scripting.lexer.Lexer;
import hr.fer.zemris.java.custom.scripting.lexer.LexerException;
import hr.fer.zemris.java.custom.scripting.lexer.LexerState;
import hr.fer.zemris.java.custom.scripting.lexer.TokenType;
import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.nodes.EchoNode;
import hr.fer.zemris.java.custom.scripting.nodes.ForLoopNode;
import hr.fer.zemris.java.custom.scripting.nodes.Node;
import hr.fer.zemris.java.custom.scripting.nodes.TextNode;

/**
 * Used to parse text using a lexer to group
 * the characters by various types.
 * It makes a tree-like structure with different
 * elements placed in a parent and child manner.
 * More about the elements can be found in their
 * respective classes
 * 
 * @author Tomislav Kurtović
 *
 */

public class SmartScriptParser {
	
	/**
	 * Lexer that is used to group the characters
	 */
	private Lexer lexer;
	
	/**
	 * Object that is used to make the connection
	 * of the elements possible
	 */
	private ObjectStack stack;
	
	/**
	 * Object that represents the document body
	 * 
	 */
	private int validNumberOfEnd;
	DocumentNode documentNode;

	/**
	 * 
	 * Constructor of the parser.
	 * It instantiates a new lexer object and 
	 * passes the document body to it.
	 * After that it calls <code>startParser()<code>
	 * so that the actual parsing can
	 * 
	 * @param text body of the document being parsed
	 */
	public SmartScriptParser(String text) {
		try {
			lexer = new Lexer(text);
		}catch(NullPointerException ex) {
			throw new SmartScriptParserException("Null can not be sent to parser");
		}
		
		documentNode = new DocumentNode();
		stack = new ObjectStack();
		startParser();
	}

	/**
	 * Returns the top Parent that holds
	 * all other elements of the parsed document
	 * 
	 * @return body of parsed document
	 */
	public DocumentNode getDocumentNode() {
		return documentNode;
	}
	
	/**
	 * 
	 * Method that is responsible for getting the tokens
	 * from the lexer that generates them.
	 * When it finds the appropriate token type the 
	 * method calls other methods who then do the
	 * task assigned.
	 * The elements are stacked on top of each other so
	 * that they can easily be in a parent child relationship if
	 * necessary.
	 * If an error occurs during the parsing process an
	 * <code>SmartScriptParserException </code>is thrown
	 * It does not return anything, it only makes the 
	 * tree structure and stores it in the <code>documentNode</code>
	 * 
	 * 
	 */
	private void startParser() {
		
		stack.push(documentNode);

	 try {
		lexer.nextToken();
		while(true) {
				Node lastNodePushed = (Node) stack.peek();
				
				if(tokenTypeEquals(TokenType.EOF)) {
					if(validNumberOfEnd != 0) {
						throw new SmartScriptParserException("Invalid number of END tags!");
					}
					break;
				}
				
				//Parse text while token type is TEXT
				if(tokenTypeEquals(TokenType.TEXT)) {
					TextNode textNode = parseText();
					lastNodePushed.addChildNode(textNode);
				}
				
				//Beginning of tag
				if(tokenTypeEquals(TokenType.BEGINOFTAG)) {
					lexer.setState(LexerState.TAG);
					lexer.nextToken();
				}
				
				//True if we get a tag name
				if(tokenTypeEquals(TokenType.TAGNAME)) {
					String value = lexer.getToken().getValue().toString().toUpperCase().trim();
					
					if(value.equals("FOR")){
						ForLoopNode forNodeToPush = parseForTag();
						lastNodePushed = pushNode(forNodeToPush,lastNodePushed);
						validNumberOfEnd++;
					} else if(value.equals("END")){
						try {
							stack.pop();
							lexer.nextToken();
							validNumberOfEnd--;
						}catch (EmptyStackException ex) {
							throw new SmartScriptParserException("END statement is invalid");
						}
					}else {
						EchoNode echoNodeToPush = parseOtherTags();
						lastNodePushed.addChildNode(echoNodeToPush);
					}
				}
					
				if(tokenTypeEquals(TokenType.ENDOFTAG)) {
					lexer.setState(LexerState.TEXT);
					lexer.nextToken();
				}
				if(tokenTypeEquals(TokenType.UNKNOWN)) {
					throw new SmartScriptParserException("Unknown tokentype found!");
				}
			}
	 	}catch(LexerException ex) {
		 throw new SmartScriptParserException(ex.getMessage());
	 	}
	}
	
	/**
	 * Used to push non-empty tag (for example FOR-tag)
	 * to the element stack.
	 * The pushed node becomes the parent node until the
	 * "END" tag is reached.
	 * After that it is popped from the stack.
	 * 
	 * @param nodeToPush node to become the new parent
	 * @param lastNodePushed the new parents parent
	 * @return new parent
	 */
	private Node pushNode(Node nodeToPush, Node lastNodePushed) {
		lastNodePushed.addChildNode(nodeToPush);
		stack.push(nodeToPush);
		lastNodePushed = nodeToPush;
		
		return lastNodePushed;
	}

	/**
	 * Gets the TokenType of a token
	 * 
	 * @param type
	 * @return <code>true</code> if the token types match, <code>false</code> otherwise
	 */
	
	private boolean tokenTypeEquals(TokenType type) {
		if(lexer.getToken().getType().equals(type)) return true;
		return false;
	}
	
	/**
	 * Used to construct the ForLoopNode.
	 * When the parser gets a TAGNAME with
	 * the value of "FOR", this method is called.
	 * It iterates through the tag elements until it
	 * hits the end of the tag ("$}").
	 * The counter variable is used to check whether
	 * the argument number is valid since it can be 3 or 4, if
	 * not an <code>SmartScriptParserException</code> is thrown.
	 * All the variables are initialized to null so that it can be 
	 * checked whether the main elements have been assigned, if not 
	 * an <code>SmartScriptParserException</code> is thrown.
	 * The valid types are Variable,String and Number, anything else 
	 * and an exception is thrown
	 * 
	 * @return ForLoopNode with 3 or 4 variables assigned
	 */
	
	private ForLoopNode parseForTag() {
		lexer.nextToken();
		if(!tokenTypeEquals(TokenType.VARIABLE)) {
			throw new SmartScriptParserException("Invalid VARIABLE name found!");
		}
		ElementVariable variable = getElementVariable();
		lexer.nextToken();
		Element startExpression = getElementVariable();
		lexer.nextToken();
		Element endExpression =  getElementVariable();
		lexer.nextToken();
		Element stepExpression = tokenTypeEquals(TokenType.ENDOFTAG) ? null : new ElementVariable(getTokenValueToString());
		
		if(stepExpression != null) {
			lexer.nextToken();
		}
		
		if(tokenTypeEquals(TokenType.ENDOFTAG)) {
			return new ForLoopNode(variable, startExpression, endExpression, stepExpression);
		}
		throw new SmartScriptParserException("Invalid number of FOR tag variables"); 	
	}
	
	/**
	 * Checks wheather the current token type is valid
	 * for the FOR tag
	 * @return ElementVariable if valid
	 */
	private ElementVariable getElementVariable() {
		if(tokenTypeEquals(TokenType.STRING) ||
				tokenTypeEquals(TokenType.NUMBER) || tokenTypeEquals(TokenType.VARIABLE)) {
			return new ElementVariable(getTokenValueToString());
		}
		throw new SmartScriptParserException("Invalid input for FOR tag!");
	}

	/**
	 * Helping method to generate tokens value
	 * 
	 * @return value of token generated by lexer
	 */
	
	private String getTokenValueToString() {
		return (String) lexer.getToken().getValue();
	}
	
	
	/**
	 * Used to construct the EchoNode.
	 * When the parser gets a TAGNAME with
	 * the value of other than "FOR" or "END",this method is called.
	 * It iterates through the tag elements until it
	 * hits the end of the tag ("$}").
	 * A stack is used to instantiate the EchoNode, as the elements are
	 * being pushed onto it as the method iterates through the tag.
	 * Any number of arguments is valid.
	 * The valid types are Variable,String, Number, Operator, Function, anything else 
	 * and an exception is thrown
	 * 
	 * @return EchoNode with no strict number of elements
	 */
	
	private EchoNode parseOtherTags() {
		ObjectStack echoNodeStack = new ObjectStack();
		echoNodeStack.push(new ElementVariable(getTokenValueToString()));
		
		while(true) {
			lexer.nextToken();
			if(tokenTypeEquals(TokenType.ENDOFTAG)) {
				break;
			}
			
			if(tokenTypeEquals(TokenType.VARIABLE)) {
				echoNodeStack.push(new ElementVariable(getTokenValueToString()));
			}

			if(tokenTypeEquals(TokenType.STRING)) {
				echoNodeStack.push(new ElementString(getTokenValueToString()));
			}
			if(tokenTypeEquals(TokenType.OPERATOR)) {
				echoNodeStack.push(new ElementOperator(getTokenValueToString()));
			}
			if(tokenTypeEquals(TokenType.FUNCTION)) {
				echoNodeStack.push(new ElementFunction(getTokenValueToString()));
			}
			if(tokenTypeEquals(TokenType.NUMBER)) {
				if(getTokenValueToString().contains(".")) {
					echoNodeStack.push(new ElementConstantDouble(Double.parseDouble(getTokenValueToString())));
				} else {
					echoNodeStack.push(new ElementConstantInteger(Integer.parseInt(getTokenValueToString())));
				}
			}
		}
		
		Element[] elements = new Element[echoNodeStack.size()];
		for(int i = elements.length-1 ; i >= 0 ; i--) {
			elements[i] = (Element) echoNodeStack.pop();
		}
		return new EchoNode(elements);
	}

	/**
	 * Used for generating a TextNode element.
	 * It goes through every character until it
	 * gets to a beginning tag.
	 * 
	 * 
	 * @return TextNode with its text value
	 */
	
	private TextNode parseText() {
		StringBuilder nodeTextToAdd = new StringBuilder();
		nodeTextToAdd.append(lexer.getToken().getValue());
		lexer.nextToken();
		while(tokenTypeEquals(TokenType.TEXT)) {
			nodeTextToAdd.append(lexer.nextToken().getValue());
		}
		
		return new TextNode(nodeTextToAdd.toString());
	}
	
}
