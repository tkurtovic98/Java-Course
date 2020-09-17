package hr.fer.zemris.java.hw14.servlets;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.p12.dao.DAOProvider;
import hr.fer.zemris.java.p12.dao.sql.SQLConnectionProvider;

/**
 * {@link HttpServlet} that is used to 
 * update the voting information 
 * in the voting file
 * @author Tomislav Kurtović
 *
 */
@WebServlet("/servleti/glasanje-glasaj")
public class GlasanjeGlasajServlet extends HttpServlet {

	/**
	 * Used to obtain the voting data and update the same data
	 * accordingly given the user sent values (clicks)
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		long pollOptionsID = Long.valueOf(req.getParameter("id"));
		DAOProvider.getDao().updateVotesCount(pollOptionsID);
		resp.sendRedirect(req.getContextPath() + "/servleti/glasanje-rezultati");
	}
}
