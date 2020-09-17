package hr.fer.zemris.java.hw05.db;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

class QueryFilterTest {
	private String query1 = "query jmbag=\"0000000003\"";
	private String query3 = "query firstName>\"A\" and lastName LIKE \"B*ć\"";
	
	@Test
	void testAccepts() {
		QueryParser parser = new QueryParser(query1);
		List<ConditionalExpression> list = parser.getQuery();
		QueryFilter filter = new QueryFilter(list);
		StudentDatabase DB = new StudentDatabase(getList());
		List<StudentRecord> l = DB.filter(filter);
		assertEquals(1,l.size());
	}
	
	@Test
	void testAccepts2() {
		QueryParser parser = new QueryParser(query3);
		List<ConditionalExpression> list = parser.getQuery();
		QueryFilter filter = new QueryFilter(list);
		StudentDatabase DB = new StudentDatabase(getList());
		List<StudentRecord> l = DB.filter(filter);
		assertEquals(4,l.size());
	}

	private List<String> getList(){
		List<String> list = new ArrayList<>();
		
		try {
			list =  Files.readAllLines(Paths.get(".\\src\\test\\resources\\database.txt"),StandardCharsets.UTF_8);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return list;
	}
	
}
