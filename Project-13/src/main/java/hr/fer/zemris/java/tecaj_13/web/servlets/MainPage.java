package hr.fer.zemris.java.tecaj_13.web.servlets;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import hr.fer.zemris.java.tecaj_13.dao.DAOProvider;
import hr.fer.zemris.java.tecaj_13.model.BlogEntry;
import hr.fer.zemris.java.tecaj_13.model.BlogUser;
import hr.fer.zemris.java.tecaj_13.model.other.Form;

/**
 * {@link HttpServlet} used to 
 * display:
 * <ol>
 * <li> Login form for existing users to login
 * <li> Link for new users to register
 * <li> List of existing users who posted on their blogs
 * <ol>
 * When user is logged in appropriate session 
 * parameters are set for later use
 * @author Tomislav Kurtović
 */
@WebServlet("/servleti/main")
public class MainPage extends HttpServlet{
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		if(session.getAttribute("current.user.id") != null) {
			req.setAttribute("log", "");
		}
		
		List<BlogUser> registeredAuthors = DAOProvider.getDAO().getRegisteredUsers()
											.stream()
											.filter(user -> user.getUserEntries().size() > 0)
											.collect(Collectors.toList());
		req.setAttribute("registeredAuthors", registeredAuthors);
		req.getRequestDispatcher("/WEB-INF/pages/main-page.jsp").forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String nick = req.getParameter("nick");
		String password = req.getParameter("password");
		
		BlogUser activeUser = DAOProvider.getDAO().loginUser(nick, password);
		Form form = new Form();
		
		if(activeUser == null) {
			form.setNick(nick);
			form.setError("userPass", "Nick or password are not valid!");
			req.setAttribute("form", form);
			doGet(req, resp);
			return;
		}
		HttpSession session = req.getSession();
		session.setAttribute("current.user.id", activeUser.getId());
		session.setAttribute("current.user.fn", activeUser.getFirstName());
		session.setAttribute("current.user.ln", activeUser.getLastName());
		session.setAttribute("current.user.nick", nick);
		resp.sendRedirect("loginSuccess.jsp");
	}
}
