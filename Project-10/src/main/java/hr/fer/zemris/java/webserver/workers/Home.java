package hr.fer.zemris.java.webserver.workers;

import java.util.Set;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * {@link IWebWorker} that is used to add
 * background color if the color exists 
 * in the server collections  
 * @author Tomislav Kurtović
 *
 */
public class Home implements IWebWorker {

	@Override
	public void processRequest(RequestContext context) throws Exception {
		Set<String> params  = context.getPersistentParameterNames();
		
		String background = "";
		if(params.contains("bgcolor")) {
			background  = context.getPersistentParameter("bgcolor");
		} else {
			background = "7F7F7F";
		}
		context.setTemporaryParameter("background", background);
		
		context.getDispatcher().dispatchRequest("/private/pages/home.smscr");
	}
}
