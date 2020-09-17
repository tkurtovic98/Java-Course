package hr.fer.zemris.java.tecaj_13.web.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.tecaj_13.dao.DAOException;
import hr.fer.zemris.java.tecaj_13.dao.DAOProvider;
import hr.fer.zemris.java.tecaj_13.model.BlogEntry;

/**
 * {@link HttpServlet} used to edit 
 * user blog entries 
 * @author Tomislav Kurtović
 */
@WebServlet("/edit")
public class Edit extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		if(req.getSession().getAttribute("current.user.id") == null) {
			req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
		}
		req.setAttribute("entry", req.getSession().getAttribute("entry"));
		req.setAttribute("formName", "Edit your post below!");
		req.setAttribute("action", "edit");
		req.getRequestDispatcher("/WEB-INF/pages/editBlog.jsp").forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String entryText = req.getParameter("entry"); 
		BlogEntry blogEntry = (BlogEntry) req.getSession().getAttribute("entry");
		
		blogEntry.setLastModifiedAt(blogEntry.getLastModifiedAt());
		blogEntry.setText(entryText);
		
		try{
			DAOProvider.getDAO().updateBlogEntry(blogEntry);
			req.setAttribute("insert", "Entry update successful!");
		}catch(DAOException ex) {
			req.setAttribute("inser", "Error while updating entry");
		}
		
		
	
		DAOProvider.getDAO().setUserEntries(blogEntry.getCreator().getId());
		req.getRequestDispatcher("/WEB-INF/pages/editBlog.jsp").forward(req, resp);
	}
}
