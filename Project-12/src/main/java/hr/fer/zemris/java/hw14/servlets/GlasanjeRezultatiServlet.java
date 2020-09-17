package hr.fer.zemris.java.hw14.servlets;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

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
 * evaluate the voting results 
 * @author Tomislav Kurtović
 *
 */
@WebServlet("/servleti/glasanje-rezultati")
public class GlasanjeRezultatiServlet extends HttpServlet{

	/**
	 * USed to obtain voting results and then form a collection
	 * that stores the number of votes and the band name 
	 * assosiated with those votes
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Poll currentPoll = (Poll)(req.getSession().getAttribute("currentPoll"));
		List<PollOption> pollOptions = DAOProvider.getDao().getPollOptions(currentPoll.getPollID());

		pollOptions.sort((o1,o2)->{
			return o1.getVotesCount()>o2.getVotesCount() ? -1 : o1.getVotesCount() == o1.getVotesCount() ? 0 : 1;
		});
		req.setAttribute("pollOptionsVotes", pollOptions);
		
		long maxVote = pollOptions.get(0).getVotesCount();
		pollOptions = pollOptions.stream().filter(p1 -> p1.getVotesCount()== maxVote).collect(Collectors.toList());
		req.setAttribute("pollWinner", pollOptions);
		
		req.getRequestDispatcher("/WEB-INF/pages/glasanjeRez.jsp").forward(req, resp);
	}
}
