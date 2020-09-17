package hr.fer.zemris.java.hw13.servlet;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpServlet;

/**
 * {@link ServletContextListener} that is used to listen 
 * when the application has been started so 
 * that it can store that value which can later
 * be displayed to the user 
 * @author Tomislav Kurtović
 *
 */

@WebListener
public class ContextListener implements ServletContextListener {
	
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		sce.getServletContext().setAttribute("startTime", System.currentTimeMillis());
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
	}
}
