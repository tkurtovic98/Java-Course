package hr.fer.zemris.java.custom.scripting.nodes;

import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.elems.ElementVariable;


/**
 * Representation of non-null closed tag used
 * by the SmartScriptParser.
 * It stores 4 different elements from which 
 * 3 are obligatory
 * 
 * @author Tomislav Kurtović
 *
 */
public class ForLoopNode extends Node {

	/**
	 * Variable of tag
	 */
	private ElementVariable variable;
	
	/**
	 * String , number or variable
	 */
	private Element startExpression;
	
	/**
	 * String , number or variable
	 */
	private Element endExpression;
	
	/**
	 * String , number or variable
	 */
	private Element stepExpression;
	
	/**
	 * Default constructor 
	 * @param variable
	 * @param startExpression
	 * @param endExpression
	 * @param stepExpression
	 */
	
	public ForLoopNode(ElementVariable variable, Element startExpression, Element endExpression,
			Element stepExpression) {
		super();
		this.variable = variable;
		this.startExpression = startExpression;
		this.endExpression = endExpression;
		this.stepExpression = stepExpression;
	}

	public ElementVariable getVariable() {
		return variable;
	}

	public Element getStartExpression() {
		return startExpression;
	}

	public Element getEndExpression() {
		return endExpression;
	}

	public Element getStepExpression() {
		return stepExpression;
	}

	@Override
	public String toString() {
		String basicVars = "{$FOR" + " " + variable.asText() + " " + startExpression.asText() + " " + endExpression.asText() + " ";
		if(stepExpression == null) {
			return basicVars + "$}";
		}
		return basicVars + stepExpression.asText() + " $}";
	}
	
	
	
}
