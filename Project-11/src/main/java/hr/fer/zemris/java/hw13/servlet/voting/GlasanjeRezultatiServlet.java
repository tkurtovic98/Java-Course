package hr.fer.zemris.java.hw13.servlet.voting;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * {@link HttpServlet} that is used to 
 * evaluate the voting results 
 * @author Tomislav Kurtović
 *
 */

@WebServlet("/glasanje-rezultati")
public class GlasanjeRezultatiServlet extends HttpServlet{

	/**
	 * USed to obtain voting results and then form a collection
	 * that stores the number of votes and the band name 
	 * assosiated with those votes
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String fileName = req.getServletContext().getRealPath(("/WEB-INF/glasanje-rezultati.txt"));
		Path filePath = Paths.get(fileName);
		@SuppressWarnings("unchecked")
		Map<String, String> bandMap  = (Map<String, String>) req.getSession().getAttribute("bandMap");
		
		if(!Files.exists(filePath)) {
			Files.createFile(filePath);
		}
		
		List<String> lines = Files.readAllLines(filePath);
		Map<String, String> voteMap = new LinkedHashMap<>();
		
		for(String line : lines) {
			String[] splitedLine = line.split("\t");
			voteMap.put(bandMap.get(splitedLine[0]), splitedLine[1]);
		}
		
		Map<String, String> winnersMap = setWinner(req, voteMap);

		req.setAttribute("voteMap", voteMap);
		req.getSession().setAttribute("voteMap", voteMap);
		req.setAttribute("winnersMap", winnersMap);
		req.getRequestDispatcher("/WEB-INF/pages/glasanjeRez.jsp").forward(req, resp);
	}

	private Map<String, String> setWinner(HttpServletRequest req, Map<String, String> voteMap) {
		Map<String,String> winners = new HashMap<>();
		@SuppressWarnings("unchecked")
		Map<String,String> linkMap = (Map<String, String>) req.getSession().getAttribute("linkMap");
		
		int maxVote = 0;
		for(String key : voteMap.keySet()) {
			if(Integer.parseInt(voteMap.get(key)) >= maxVote) {
				maxVote = Integer.parseInt(voteMap.get(key));
				winners.put(key, linkMap.get(key));
			}
		}
		
		return winners;
	}
}
