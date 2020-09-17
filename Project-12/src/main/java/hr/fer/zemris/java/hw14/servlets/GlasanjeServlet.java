package hr.fer.zemris.java.hw14.servlets;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.hw14.model.Poll;
import hr.fer.zemris.java.hw14.model.PollOption;
import hr.fer.zemris.java.p12.dao.DAOProvider;


/**
 * {@link HttpServlet} that is used to 
 * store values used for voting
 * @author Tomislav Kurtović
 *
 */
@WebServlet("/servleti/glasanje")
public class GlasanjeServlet extends HttpServlet {

	/**
	 * Used to read from file that contains information
	 * about the voting candidates and store them 
	 * in appropriate collections
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Long id = Long.valueOf(req.getParameter("pollID"));
		List<PollOption> pollOptions = DAOProvider.getDao().getPollOptions(id);
		Poll poll = DAOProvider.getDao().getPoll(id);
		
		Map<String, String> pollMap = new LinkedHashMap<>();
		Map<String, String> linkMap = new LinkedHashMap<>();
		
		for(PollOption pollOption : pollOptions) {
			pollMap.put(String.valueOf(pollOption.getPollID()), pollOption.getName());
			linkMap.put(String.valueOf(pollOption.getPollID()), pollOption.getLink());
		}
		
		req.setAttribute("title", poll.getTitle());
		req.setAttribute("message", poll.getMessage());
		req.setAttribute("pollMap", pollMap);
		req.getSession().setAttribute("currentPoll", poll);
		req.getRequestDispatcher("/WEB-INF/pages/glasanjeIndex.jsp").forward(req, resp);
	}
}
