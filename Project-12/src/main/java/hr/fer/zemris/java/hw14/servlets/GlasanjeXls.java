package hr.fer.zemris.java.hw14.servlets;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import hr.fer.zemris.java.hw14.model.Poll;
import hr.fer.zemris.java.hw14.model.PollOption;
import hr.fer.zemris.java.p12.dao.DAOProvider;

/**
 * {@link HttpServlet} that is used to 
 * make an xls file with the results 
 * from the voting
 * @author Tomislav Kurtović
 *
 */
@WebServlet("/servleti/glasanje-xls")
public class GlasanjeXls extends HttpServlet {

	/**
	 * Used to make new spreadsheet with vote counts and option names for that poll 
	 * as cell values which can be downloaded by the user 
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Poll currentPoll = (Poll)(req.getSession().getAttribute("currentPoll"));
		List<PollOption> pollOptions = DAOProvider.getDao().getPollOptions(currentPoll.getPollID());
		resp.setContentType("application/vnd.ms-excel");
		resp.setHeader("Content-Disposition", "attachment; filename=pollOption"+currentPoll.getPollID()+".xls");
	
		
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("PollOption"+currentPoll.getPollID());

		HSSFRow firstRow = sheet.createRow(0);
		HSSFCell cell = firstRow.createCell(0);
		cell.setCellValue("ID");
		cell = firstRow.createCell(1);
		cell.setCellValue("NAME");
		cell = firstRow.createCell(2);
		cell.setCellValue("# OF VOTES");

		int rowNum = 1;
		for (PollOption pollOption : pollOptions) {
			HSSFRow row = sheet.createRow(rowNum);
			row.createCell(0).setCellValue(pollOption.getPollID());
			row.createCell(1).setCellValue(pollOption.getName());
			row.createCell(2).setCellValue(pollOption.getVotesCount());
			rowNum++;
		}

		workbook.write(resp.getOutputStream());
		workbook.close();
	}

}