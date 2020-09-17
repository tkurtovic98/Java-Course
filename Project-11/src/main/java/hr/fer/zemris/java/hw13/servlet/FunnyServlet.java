package hr.fer.zemris.java.hw13.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 * {@link HttpServlet} that is used to 
 * send a request to a jsp file that
 * will render a story 
 * @author Tomislav Kurtović
 *
 */
@WebServlet("/funny.jsp")
public class FunnyServlet extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getRequestDispatcher("/stories/funny.jsp").forward(req, resp);
	}
}
