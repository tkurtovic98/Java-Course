package hr.fer.zemris.java.hw13.servlet.voting;

import java.io.IOException;
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

/**
 * {@link HttpServlet} that is used to 
 * make an xls file with the results 
 * from the voting
 * @author Tomislav Kurtović
 *
 */
@WebServlet("/glasanje-xls")
public class GlasanjeXls extends HttpServlet {

	/**
	 * Used to make new spreadsheet with vote counts and band names 
	 * as cell values which can be downloaded by the user 
	 */
	@SuppressWarnings("unchecked")
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("application/vnd.ms-excel");
		resp.setHeader("Content-Disposition", "attachment; filename=bands.xls");

		Map<String, String> voteMap = (Map<String, String>) req.getSession().getAttribute("voteMap");
		Map<String, String> bandMap = (Map<String, String>) req.getSession().getAttribute("bandMap");

		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("Bands");

		HSSFRow firstRow = sheet.createRow(0);
		HSSFCell cell = firstRow.createCell(0);
		cell.setCellValue("ID");
		cell = firstRow.createCell(1);
		cell.setCellValue("NAME");
		cell = firstRow.createCell(2);
		cell.setCellValue("# OF VOTES");

		int rowNum = 1;
		for (String key : bandMap.keySet()) {
			HSSFRow row = sheet.createRow(rowNum);
			row.createCell(0).setCellValue(key);
			row.createCell(1).setCellValue(bandMap.get(key));
			row.createCell(2).setCellValue(voteMap.get(bandMap.get(key)));
			rowNum++;
		}

		workbook.write(resp.getOutputStream());
		workbook.close();
	}

}