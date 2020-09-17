package hr.fer.zemris.java.tecaj_13.web.servlets;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.tecaj_13.dao.DAOProvider;
import hr.fer.zemris.java.tecaj_13.model.BlogComment;
import hr.fer.zemris.java.tecaj_13.model.BlogEntry;
import hr.fer.zemris.java.tecaj_13.model.BlogUser;

/**
 * {@link HttpServlet} used to add
 * user comments to the blog entries 
 * of blog users 
 * @author Tomislav Kurtović
 */
@WebServlet("/servleti/addComment")
public class AddComment extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String comment = req.getParameter("commentToSubmit");
		BlogEntry entry = (BlogEntry) req.getSession().getAttribute("entry");
		BlogUser user = DAOProvider.getDAO().getUser((String)req.getSession().getAttribute("current.user.nick)"));
		
		String email = "";
		if(user != null) {
			email = user.getEmail();
		}
		
		BlogComment blogComment = new BlogComment();
		blogComment.setUsersEMail(email);
		blogComment.setPostedOn(new Date());
		blogComment.setMessage(comment);
		blogComment.setBlogEntry(entry);
		
		DAOProvider.getDAO().setComment(blogComment);
		
		req.setAttribute("commentAdded", "Comment added to entry!");
		req.setAttribute("comments", DAOProvider.getDAO().getEntryComments(entry));
		req.getRequestDispatcher("/WEB-INF/pages/entry.jsp").forward(req, resp);
	}
}
