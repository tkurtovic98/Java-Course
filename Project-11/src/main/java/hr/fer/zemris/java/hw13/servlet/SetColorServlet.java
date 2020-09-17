package hr.fer.zemris.java.hw13.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


/**
 * {@link HttpServlet} that is used to 
 * change the color of the background
 * of each page that user interacts 
 * with 
 * @author Tomislav Kurtović
 *
 */
@WebServlet("/setcolor")
public class SetColorServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
   
	/**
	 * Stores the passed color value in the current session 
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		session.setAttribute("pickedBgCol", req.getParameter("pickedBgCol"));
	}
}
