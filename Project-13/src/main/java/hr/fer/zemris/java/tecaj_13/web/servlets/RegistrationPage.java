package hr.fer.zemris.java.tecaj_13.web.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.tecaj_13.dao.DAOException;
import hr.fer.zemris.java.tecaj_13.dao.DAOProvider;
import hr.fer.zemris.java.tecaj_13.dao.jpa.JPADAOImpl;
import hr.fer.zemris.java.tecaj_13.model.BlogUser;
import hr.fer.zemris.java.tecaj_13.model.other.Cripting;
import hr.fer.zemris.java.tecaj_13.model.other.Form;

/**
 * {@link HttpServlet} used to register 
 * new users to blog.
 * If the user exists in the database then 
 * the user will not be registered in the database 
 * @author Tomislav Kurtović
 */
@WebServlet("/servleti/register")
public class RegistrationPage extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getRequestDispatcher("/WEB-INF/pages/registration.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Form form = new Form();

		form.setFirstName(req.getParameter("firstName"));
		form.setLastName(req.getParameter("lastName"));
		form.setEmail(req.getParameter("eMail"));
		form.setNick(req.getParameter("nick"));
		form.setPassword(Cripting.hashPassword(req.getParameter("password")));

		if (!form.isValid()) {
			req.setAttribute("form", form);
			doGet(req, resp);
			return;
		}
		
		BlogUser user = form.createBlogUser();
		try {
			DAOProvider.getDAO().registerUser(user);
		} catch(DAOException ex) {
			form.setError("nick", ex.getMessage());
			req.setAttribute("form", form);
			doGet(req, resp);
			return;
		}

		req.getRequestDispatcher("/WEB-INF/pages/successReg.jsp").forward(req, resp);
	}
}
