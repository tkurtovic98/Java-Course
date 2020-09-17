package hr.fer.zemris.java.webserver.workers;

import java.util.Set;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;
import hr.fer.zemris.java.webserver.SmartHttpServer;
/**
 * {@link IWebWorker} that is used to 
 * calculate the sum of the passed parameters
 * to the {@link SmartHttpServer} 
 * a hello message to the user 
 * @author Tomislav Kurtović
 *
 */
public class SumWorker implements IWebWorker {

	@Override
	public void processRequest(RequestContext context) throws Exception {
		Set<String> params = context.getParameterNames();
		int a = 1;
		int b = 2;

		try {
			if (params.contains("a")) {
				a = Integer.parseInt(context.getParameter("a"));
			}
			if (params.contains("b")) {
				b = Integer.parseInt(context.getParameter("b"));
			}
		} catch (NumberFormatException e) {
		}
		
		String sum = String.valueOf(a + b);
		
		context.setTemporaryParameter("zbroj", sum);
		context.setTemporaryParameter("varA", String.valueOf(a));
		context.setTemporaryParameter("varB", String.valueOf(b));
		context.setTemporaryParameter("imgName", (a+b)%2 == 0 ? "forhw1.png" : "forhw2.png");
		
		context.getDispatcher().dispatchRequest("/private/pages/calc.smscr");
 	}

}
