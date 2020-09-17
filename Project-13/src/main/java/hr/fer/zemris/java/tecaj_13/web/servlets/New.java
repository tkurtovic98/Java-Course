package hr.fer.zemris.java.tecaj_13.web.servlets;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.tecaj_13.dao.DAOException;
import hr.fer.zemris.java.tecaj_13.dao.DAOProvider;
import hr.fer.zemris.java.tecaj_13.model.BlogEntry;

/**
 * {@link HttpServlet} used to add new 
 * user blog entry
 * @author Tomislav Kurtović
 */
@WebServlet("/new")
public class New extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		if(req.getSession().getAttribute("current.user.id") == null) {
			req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
		}
		req.setAttribute("formName", "Write new post below!");
		req.setAttribute("action", "new");
		req.getRequestDispatcher("/WEB-INF/pages/editBlog.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String title = req.getParameter("title");
		String entryText = req.getParameter("entry"); 
		
		BlogEntry blogEntry = new BlogEntry();
		blogEntry.setCreatedAt(new Date());
		blogEntry.setLastModifiedAt(blogEntry.getCreatedAt());
		blogEntry.setTitle(title);
		blogEntry.setText(entryText);
		Long creatorId = (Long) req.getSession().getAttribute("current.user.id");
		
		try {
			DAOProvider.getDAO().insertNewEntry(blogEntry, creatorId);
			req.setAttribute("insert", "Insertion of entry was successful!");
		} catch(DAOException ex) {
			req.setAttribute("insert", "Failed to insert new entry");
		}
		
		DAOProvider.getDAO().setUserEntries(creatorId);
		req.getRequestDispatcher("/WEB-INF/pages/editBlog.jsp").forward(req, resp);
	}
}
