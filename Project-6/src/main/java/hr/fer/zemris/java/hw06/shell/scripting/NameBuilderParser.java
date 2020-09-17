package hr.fer.zemris.java.hw06.shell.scripting;

import java.util.ArrayList;
import java.util.List;
public class NameBuilderParser {
	private List<NameBuilder> nameBuilders;
	
	public NameBuilderParser(String expression) {
		nameBuilders = new ArrayList<>();
		parseExpression(expression);
	}
	
	private void parseExpression(String expression) {
		CommandsLexer lexer = new CommandsLexer(expression);
		ExpressionToken expressionToken; 
		while(true) {
			expressionToken = lexer.getValueOfExpression();
			if(expressionToken == null) break;
			
			if(expressionToken.getType().equals(ExpressionTokenType.TEXT)) {
				nameBuilders.add(DefaultNameBuilders.text(expressionToken.getValue().trim()));
				continue;
			}
			
			if(expressionToken.getType().equals(ExpressionTokenType.GROUP)) {
				try {
					
					if(expressionToken.getValue().contains(",")) {
						evaluateGroup(expressionToken);
						continue;
					}
					int index = getNumberFromGroup(expressionToken.getValue().trim());
					nameBuilders.add(DefaultNameBuilders.group(index));
				}catch(IllegalArgumentException ex) {
					throw new NameBuilderParserException(ex.getMessage());
				}
				continue;
			}
			
			throw new NameBuilderParserException("Error while parsing expression!");
		}
	}
	
	private void evaluateGroup(ExpressionToken expressionToken) {
		String[] values = expressionToken.getValue().split(",");
		
		if(values.length != 2) {
			throw new IllegalArgumentException("Values of expression for group are invalid!");
		}
		
		try {
			int index = getNumberFromGroup(values[0]);
			char c = ' ';
			int minWidth = 0;
			if(values[1].length() != 2) {
				throw new IllegalArgumentException("Extra grouping info invalid");
			}
			if(values[1].charAt(0) == '0') {
				c = '0';
				minWidth = getNumberFromGroup(values[1].substring(1)); 
			}else {
				minWidth = getNumberFromGroup(values[1]);
			}
			
			nameBuilders.add(DefaultNameBuilders.group(index, c, minWidth));
		}catch(IllegalArgumentException ex) {
			throw new NameBuilderParserException(ex.getMessage());
		}
	}
	
	private int getNumberFromGroup(String string) {
		int number = Integer.parseInt(string);
		if(number < 0) {
			throw new IllegalArgumentException("Group index or minimum width cannot be parsed if value is less than 0!");
		}
		return number;
	}
	
	public NameBuilder getNameBuilder() {
		return new ActualNameBuilder(nameBuilders);
	}
}
