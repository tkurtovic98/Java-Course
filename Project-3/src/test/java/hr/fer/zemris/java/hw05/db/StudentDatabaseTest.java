package hr.fer.zemris.java.hw05.db;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

class StudentDatabaseTest {

	
	@Test
	void constructorTestValid() {
		StudentDatabase DB = new StudentDatabase(getList());
		assertNotNull(DB);
	}

	@Test
	void testForJMBAG() {
		StudentDatabase DB = new StudentDatabase(getList());
		
		StudentRecord s1=new StudentRecord("0000000001", "Akašamović", "Marin", 2);
		StudentRecord s2=new StudentRecord("0000000002", "Bakamović", "Petra", 3);
		StudentRecord s3=new StudentRecord("0000000006", "Cvrlje", "Ivan", 3);
		StudentRecord s4=new StudentRecord("0000000009", "Dean", "Nataša", 2);
		
		assertEquals(s1,DB.forJMBAG("0000000001"));
		assertEquals(s2,DB.forJMBAG("0000000002"));
		assertEquals(s3,DB.forJMBAG("0000000006"));
		assertEquals(s4,DB.forJMBAG("0000000009"));
	}

	@Test
	void testFilter() {
		StudentDatabase DB = new StudentDatabase(getList());
		
		List<StudentRecord> emptyList = DB.filter(record -> record.equals(null) );
		List<StudentRecord> fullList = DB.filter(record -> record.getClass().equals(StudentRecord.class));
		
		assertEquals(0,emptyList.size());
		assertEquals(10,fullList.size());
	}
	
	private List<String> getList(){
		List<String> list = new ArrayList<>();
		
		try {
			list =  Files.readAllLines(Paths.get("database.txt"),StandardCharsets.UTF_8);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return list;
	}
}
