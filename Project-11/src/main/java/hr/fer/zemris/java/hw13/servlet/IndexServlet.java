package hr.fer.zemris.java.hw13.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * {@link HttpServlet} that is used to 
 * display appropriate jsp files and 
 * render its content on the screen.
 * The name of the file to render is 
 * passed through the request params.
 * @author Tomislav Kurtović
 *
 */
@WebServlet("/index.jsp")
public class IndexServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
	
	/**
	 * Used to dispatch requests to different jsp files 
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String jspToRender = req.getParameter("name");
		if(jspToRender == null) {
			jspToRender = "index.jsp";
		}
		req.getRequestDispatcher("/WEB-INF/pages/"+ jspToRender).forward(req, resp);
	}
}
