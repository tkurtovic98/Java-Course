package hr.fer.zemris.java.hw13.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.usermodel.HSSFCell;

/**
 * {@link HttpServlet} that is used to 
 * make an xls document which contains 
 * different objects in its cells and then
 * enables user to download it 
 * @author Tomislav Kurtović
 *
 */
@WebServlet ("/powers")
public class ExcelServlet extends HttpServlet {

	/**
	 * Used to retrieve sent paramenters in order to make an excel spreadsheet 
	 * that contains n pages, with each page having 2 columns.
	 * The first column are values from paramenter a to parameter b (integers)
	 * and the second column is the value in the first column to the power of 
	 * the current page (from 1 to n)
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String a = req.getParameter("a");
		String b = req.getParameter("b");
		String n = req.getParameter("n");
		
		boolean invalid = false;
		
		int A= 0;
		int B= 0;
		int N= 0;
		try {
			if(a !=null) {
				A = Integer.parseInt(a);
				if(A > 100 || A < -100) {
					invalid = true;
				}
			}else {
				invalid = true;
			}
		}catch(Exception ignorable) {}
		
		try {
			if(b !=null) {
				B = Integer.parseInt(b);
				if(B > 100 || B < -100) {
					invalid = true;
				}
			}else {
				invalid = true;
			}
		}catch(Exception ignorable) {}
		
		try {
			if(n !=null) {
				N = Integer.parseInt(n);
				if(N > 5 || N < 1) {
					invalid = true;
				}
			}else {
				invalid = true;
			}
		}catch(Exception ignorable) {}
		
		if(invalid) {
			req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
			return;
		}
		
		resp.setContentType("application/vnd.ms-excel");
		resp.setHeader("Content-Disposition", "attachment; filename=tablica.xls"); 
		
		HSSFWorkbook workbook = new HSSFWorkbook();
		for(int i = 1; i <= N; i++) {
			HSSFSheet sheet = workbook.createSheet(""+i+"");
			int rowNum = 0;
			for(int j = A; j <= B ; j++) {
				HSSFRow row = sheet.createRow(rowNum);
				HSSFCell cell = row.createCell(0);
				cell.setCellValue(j);
				cell = row.createCell(1);
				cell.setCellValue(""+(int)Math.pow(j, i));
				rowNum++;
			}
		}
		workbook.write(resp.getOutputStream());
		workbook.close();
	}
}