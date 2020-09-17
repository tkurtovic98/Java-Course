package hr.fer.zemris.java.custom.scripting.demo;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import hr.fer.zemris.java.custom.scripting.nodes.NodeVisitorImpl;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;

public class TreeWriter {

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
		
		SmartScriptParser p = new SmartScriptParser(docBody);
		NodeVisitorImpl visitor = new NodeVisitorImpl();
		p.getDocumentNode().accept(visitor);
		
	}
}
