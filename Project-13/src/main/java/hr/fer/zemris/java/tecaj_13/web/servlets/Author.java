package hr.fer.zemris.java.tecaj_13.web.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.mchange.lang.StringUtils;

import hr.fer.zemris.java.tecaj_13.dao.DAOProvider;
import hr.fer.zemris.java.tecaj_13.model.BlogEntry;
import hr.fer.zemris.java.tecaj_13.model.BlogUser;

/**
 * {@link HttpServlet} used to add redirect user
 * request.
 * Depending on the url path, the 
 * serlvet can either: 
 * <ol>
 * <li> Show blog entries of specified user
 * <li> Enable user to add new blog entry or edit selected blog entry
 * <li> Show selected entry from user
 * <ol>
 * Different rules apply to logged in and anonymous users.
 * @author Tomislav Kurtović
 */
@WebServlet("/servleti/author/*")
public class Author extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String pathInfo = req.getPathInfo();

		if (pathInfo == null) {
			req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
			return;
		}
		String[] splitInfo = pathInfo.substring(1).split("/");
		
		if (splitInfo.length == 1) {
			BlogUser user = DAOProvider.getDAO().getUser(splitInfo[0]);
			if (user == null) {
				req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
				return;
			}
			if(req.getSession().getAttribute("current.user.id") != null) {
				req.setAttribute("id", "id");
			} else {
				req.setAttribute("id", null);
			}
			
			req.setAttribute("availableEntries",DAOProvider.getDAO().getUserEntries(user.getId()));
			req.setAttribute("nick", splitInfo[0]);
			req.getRequestDispatcher("/WEB-INF/pages/entries.jsp").forward(req, resp);
			return;
		}

		if (isNumber(splitInfo[1])) {
			BlogEntry entry = DAOProvider.getDAO().getBlogEntry(Long.valueOf(splitInfo[1]));
			if (!splitInfo[0].equals(entry.getCreator().getNick())) {
				req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
				return;
			}
			
			if(req.getSession().getAttribute("current.user.id") != null &&
					String.valueOf(req.getSession().getAttribute("current.user.nick")).equals(splitInfo[0])) {
				req.setAttribute("loggedIn", (String) req.getSession().getAttribute("current.user.nick"));
			}
			
			req.setAttribute("comments", DAOProvider.getDAO().getEntryComments(entry));
			req.setAttribute("entry", entry);
			req.getSession().setAttribute("entry", entry);
			req.getRequestDispatcher("/WEB-INF/pages/entry.jsp").forward(req, resp);
			return;
		}

		if (splitInfo[1].contentEquals("new")) {
			if (!IsValidUser(req, splitInfo[0])) {
				req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
				return;
			}
			req.getRequestDispatcher("/new").forward(req, resp);
			return;
		}

		if (splitInfo[1].contentEquals("edit")) {
			if (!IsValidUser(req, splitInfo[0])) {
				req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
				return;
			}
			req.getRequestDispatcher("/edit").forward(req, resp);
			return;
		}
		req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
	}

	/**
	 * Checks if given string is number.
	 * Used to check id of entry 
	 * @param string string representation of id
	 * @return true if passed string is number, false otherwise
	 */
	private boolean isNumber(String string) {
		try {
			Integer.parseInt(string);
			return true;
		}catch(NumberFormatException ex) {
			return false;
		}
	}
	/**
	 * Checks whether the logged in user is correct.
	 * Prevents unauthorized access to blog entries
	 * @param req request of context 
	 * @param name names of user to validate
	 * @return true if user is valid, false otherwise
	 */
	private boolean IsValidUser(HttpServletRequest req, String name) {
		HttpSession session = req.getSession();
		if (session.getAttribute("current.user.id") != null) {
			String currentNick = String.valueOf(session.getAttribute("current.user.nick"));
			if (currentNick.equals(name)) {
				return true;
			}
		}
		
		return false;
	}

}
