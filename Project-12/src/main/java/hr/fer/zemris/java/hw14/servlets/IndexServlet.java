package hr.fer.zemris.java.hw14.servlets;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import hr.fer.zemris.java.hw14.model.Poll;
import hr.fer.zemris.java.p12.dao.DAOProvider;

/**
 * {@link HttpServlet} that is used to generate
 * home page of web application 
 * @author Tomislav Kurtović
 *
 */
@WebServlet("/servleti/index.html")
public class IndexServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * Used to retrieve the list of available pools the user can choose 
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		List<Poll> polls = DAOProvider.getDao().getPolls();
		req.setAttribute("polls", polls);
		req.getRequestDispatcher("/WEB-INF/pages/index.jsp").forward(req, resp);
	}
}
