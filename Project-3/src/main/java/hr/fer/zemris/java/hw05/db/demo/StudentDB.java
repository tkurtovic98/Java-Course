package hr.fer.zemris.java.hw05.db.demo;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;

import hr.fer.zemris.java.hw05.db.ConditionalExpression;
import hr.fer.zemris.java.hw05.db.MyRecordFormatter;
import hr.fer.zemris.java.hw05.db.QueryFilter;
import hr.fer.zemris.java.hw05.db.QueryParser;
import hr.fer.zemris.java.hw05.db.QueryParserException;
import hr.fer.zemris.java.hw05.db.StudentDatabase;
import hr.fer.zemris.java.hw05.db.StudentRecord;

/**
 * Class used to simulate the query filtering
 * It reads all the rows from the database and 
 * then waits for user input in form of a query.
 * It then evaluates the given query and prints the
 * results
 * @author Tomislav Kurtović
 *
 */
public class StudentDB {

	/**
	 * Main method used to get user input and generate formated result
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		Scanner scanner = new Scanner(System.in);
		
		List<String> database = Files.readAllLines(Paths.get("./src/test/resources/database.txt"),StandardCharsets.UTF_8);
		StudentDatabase DB = new StudentDatabase(database);
		
		System.out.println("Write your query here and press enter or write exit to close the programme");
		while(scanner.hasNext()) {
			System.out.printf("> ");
			if(scanner.hasNextLine()) {
				String query = scanner.nextLine();
				
				System.out.println(query);
				
				if("exit".equals(query)) {
					System.out.println("Goodbye!");
					break;
				}
				
				if(!query.startsWith("query")) {
					System.out.println("Must write query for command to be valid!");
					continue;
				}
	
				try {
					QueryParser parser = new QueryParser(query);
					List<ConditionalExpression> list = parser.getQuery();
					QueryFilter filter = new QueryFilter(list);
					
					List<StudentRecord> filteredRecords = DB.filter(filter);
					List<String> output = MyRecordFormatter.format(filteredRecords);
					if(filteredRecords.size() != 0) {
						if(parser.isDirectQuery()) {
							System.out.println("Using index for retrieval");
						}
						output.forEach(System.out::println);
					}
					System.out.println("Records selected: " + filteredRecords.size() );
				} catch(QueryParserException e){
					System.out.println(e.getMessage());
				}
			}
		}
		scanner.close();
	}

}
