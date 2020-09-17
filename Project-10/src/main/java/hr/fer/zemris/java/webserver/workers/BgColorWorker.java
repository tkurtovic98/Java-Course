package hr.fer.zemris.java.webserver.workers;

import java.io.IOException;
import java.util.Set;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * {@link IWebWorker} that is used to change the 
 * background of the {@link Home} worker if the 
 * user sent a valid color 
 * a hello message to the user 
 * @author Tomislav Kurtović
 *
 */
public class BgColorWorker implements IWebWorker {

	@Override
	public void processRequest(RequestContext context) throws Exception {
		Set<String> params = context.getParameterNames();
		
		if(params.contains("bgcolor")) {
			if(context.getParameter("bgcolor").length() == 6) {
				context.setPersistentParameter("bgcolor", context.getParameter("bgcolor"));
				generateHtml(context, true);
				return;
			}
		}
		generateHtml(context, false);
	}

	/**
	 * Used to generate html page that prints information
	 * about the updating of the background color of the
	 * {@link Home} worker.
	 * It also adds a link to the worker itself
	 * @param context context to write to
	 * @param b if true color was updated, if false not 
	 */
	private void generateHtml(RequestContext context, boolean b) {
		try {
			context.write("<html><body>");
			context.write("<a href = /index2.html> Index2.html </a> <br>");
			context.write("<p>Color is : "+ ((b == true) ? "updated" : "not updated") + "</p>");
			context.write("</body></html>");
		} catch (IOException e) {
		}
	}
}
