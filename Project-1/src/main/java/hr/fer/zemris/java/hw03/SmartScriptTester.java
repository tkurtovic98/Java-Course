package hr.fer.zemris.java.hw03;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.nodes.ForLoopNode;
import hr.fer.zemris.java.custom.scripting.nodes.Node;
import hr.fer.zemris.java.custom.scripting.nodes.TextNode;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParserException;

/**
 * Class used to test whether the parser works or not. 
 * Command-line programme.
 * It has its main method and another method used 
 * for generating parsed document text. 
 * 
 * @author Tomislav Kurtović
 *
 */

public class SmartScriptTester {
	
	/**
	 * Takes a single argument that represents the 
	 * path to the testing document.
	 * It instantiates a new <code>SmartScriptParser</code>
	 * used for the parsing of the document and then
	 * parses the parsed document to check if the newly
	 * parsed document is structurally the same as the first document
	 * 
	 * @param args path to the tested document
	 * @throws IOException if an error occurs during reading from document
	 */

	public static void main(String[] args) throws IOException {
		
		if(args.length != 1) {
			System.out.println("Invalid number of arguments");
			return;
		}
		
		String filepath = args[0];
		
		String docBody = new String(
							Files.readAllBytes(Paths.get(filepath)), 
							StandardCharsets.UTF_8
				);
	
		SmartScriptParser parser = null;
		try {
			parser = new SmartScriptParser(docBody);
		} catch(SmartScriptParserException e){
			System.out.println("Unable to parse document!");
			System.exit(-1);
		} catch(Exception e) {
			System.out.println("If this line ever executes, you have failed this class");
			System.exit(-1);
		}
		DocumentNode document = parser.getDocumentNode();
		String originalDocumentBody = createOriginalDocumentBody(document);
		
		System.out.println(originalDocumentBody);
		
//		SmartScriptParser parser2 = new SmartScriptParser(originalDocumentBody);
//		DocumentNode document2 = parser2.getDocumentNode();
//		if(document.equals(document2)) {
//			System.out.println("TRUE");
//		}
		
	}
	
	/**
	 * Recursive method used to generate a string for the parsed document
	 * 
	 * @param doc parent element in tree struct
	 * @return string format of the parsed document
	 */
	
	private static String createOriginalDocumentBody(Node doc) {
		StringBuilder builder = new StringBuilder();
		for(int i = 0, numberOfChildren = doc.numberOfChilder(); i < numberOfChildren ; i++) {
			if((doc.getChild(i) instanceof ForLoopNode)) {
				builder.append(doc.getChild(i).toString());
				builder.append(createOriginalDocumentBody(doc.getChild(i)));
				builder.append("{$END$}");
				continue;
			} 
			builder.append(doc.getChild(i).toString());
		}
		return builder.toString();
	}
}
