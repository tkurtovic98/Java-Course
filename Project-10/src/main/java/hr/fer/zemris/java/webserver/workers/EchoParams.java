package hr.fer.zemris.java.webserver.workers;

import java.io.IOException;
import java.util.Set;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

/**
* {@link IWebWorker} that is used to print 
* the parameters from the request to the screen 
* @author Tomislav Kurtović
*
*/
public class EchoParams implements IWebWorker {

	@Override
	public void processRequest(RequestContext context) throws Exception {
		Set<String> params = context.getParameterNames();

		try {
			context.write("<html><body>");
			context.write("<table>");
			for(String param : params) {
				context.write("<tr><td>"+param+"</td></tr>");
			}
			context.write("</table>"); 
			context.write("</body></html>");
		}catch(IOException e) {
			e.printStackTrace();
		}
		
	}
}
