package hr.fer.zemris.java.hw05.db;

import java.util.ArrayList;
import java.util.List;

/**
 * Class used to format the results of the filtering and parsing
 * of the query.
 * It has a static method that formats everything 
 * 
 * @author Tomislav Kurtović
 *
 */
public class MyRecordFormatter {

	/**
	 * Method used to format the results.
	 * It first checks what the longest first and last names are,
	 * so that it can determine the width of the cells.
	 * When it does that it adds every formated student record
	 * to a list which is then printed on the screen
	 * 
	 * @param filteredRecords list of student records to format
	 * @return list of formated student records
	 */
	public static List<String> format (List<StudentRecord> filteredRecords){
		List<String> rows = new ArrayList<String>();
		int jmbagLength = 10;
		
		int longestFirst = 0;
		int longestLast = 0;
		//Getting longest first/lastname
		for(StudentRecord record : filteredRecords) {
			if(record.getFirstName().length() > longestFirst) {
				longestFirst = record.getFirstName().trim().length();
			}
			if(record.getLastName().length() > longestLast) {
				longestLast = record.getLastName().trim().length();
			}
		}
		
		rows.add(getFirstOrLastRow(jmbagLength,longestFirst,longestLast));
		
		for(StudentRecord record: filteredRecords) {
			StringBuilder builder = new StringBuilder();
			builder.append("| ");
			builder.append(record.getJmbag());
			builder.append(" | ");
			builder.append(record.getLastName());
			builder.append(addBlanks(record.getLastName(),longestLast));
			builder.append(" | ");
			builder.append(record.getFirstName());
			builder.append(addBlanks(record.getFirstName(),longestFirst));
			builder.append(" | ");
			builder.append(record.getFinalGrade());
			builder.append(" |");
			rows.add(builder.toString());
		}
		rows.add(getFirstOrLastRow(jmbagLength, longestFirst, longestLast));
		return rows;
	}
	
	/**
	 * Adds necessary blanks so that all the cells are aligned
	 * @param lastName
	 * @param longestLast
	 * @return
	 */
	private static String addBlanks(String lastName, int longestLast) {
		StringBuilder builder = new  StringBuilder();
		
		for(int i = lastName.length(); i < longestLast; i++) {
			builder.append(" ");
		}
		return builder.toString();
	}

	/**
	 * Generates the first and last row of the formated result
	 * @param jmbagLength length of jmbag string
	 * @param longestFirst length of longest first name
	 * @param longestLast length of longest last name
	 * @return first and last row 
	 */
	private static String getFirstOrLastRow(int jmbagLength, int longestFirst, int longestLast) {
		StringBuilder builder = new StringBuilder();

		builder.append("+");
		builder.append(evaluate(jmbagLength));
		builder.append(evaluate(longestLast));
		builder.append(evaluate(longestFirst));
		builder.append("===+");
		return builder.toString();
	}

	private static String evaluate(int length) {
		StringBuilder builder = new StringBuilder();
		int i = 0;
		
		while(i <= length) {
			builder.append("=");
			i++;
		}
		builder.append("=+");
		return builder.toString();
	}
}
