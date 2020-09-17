package hr.fer.zemris.java.tecaj_13.web.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.tecaj_13.dao.DAOProvider;

/**
 * {@link HttpServlet} used as redirection
 * when user has successfully logged into 
 * his blog and displays appropriate message on screen
 * @author Tomislav Kurtović
 */
@WebServlet("/servleti/loginSuccess.jsp")
public class LoginSucces extends HttpServlet {
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setAttribute("nick", req.getSession().getAttribute("current.user.nick"));
		req.setAttribute("availableEntries",DAOProvider.getDAO().getUserEntries((Long)(req.getSession().getAttribute("current.user.id"))));
		req.getRequestDispatcher("/WEB-INF/pages/loginSuccess.jsp").forward(req, resp);
	}
}
