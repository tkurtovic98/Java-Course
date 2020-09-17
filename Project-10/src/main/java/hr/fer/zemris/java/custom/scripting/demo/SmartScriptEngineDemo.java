package hr.fer.zemris.java.custom.scripting.demo;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import hr.fer.zemris.java.custom.scripting.exec.SmartScriptEngine;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.webserver.RequestContext;
import hr.fer.zemris.java.webserver.RequestContext.RCCookie;

public class SmartScriptEngineDemo {
	public static void main(String[] args) throws IOException {
		
		if(args.length != 1) {
			return;
		}
		
		String docBody = Files.readString(Paths.get(args[0]));
		Map<String, String> parameters = new HashMap<>();		
		Map<String, String> persistentParameters = new HashMap<>();
		List<RCCookie> cookies = new ArrayList<>();
		
//		FOR 2nd test
//		parameters.put("a", "4");
//		parameters.put("b", "2");
		
		//FOR 3rd test
//		persistentParameters.put("brojPoziva", "3");
		RequestContext rc = new RequestContext(System.out, parameters, persistentParameters,cookies, "");
		
		
		new SmartScriptEngine(new SmartScriptParser(docBody).getDocumentNode(),
				rc).execute();
		
//		System.out.println("Vrijednosti u mapi : " + rc.getPersistentParameter("brojPoziva"));
	}
}
