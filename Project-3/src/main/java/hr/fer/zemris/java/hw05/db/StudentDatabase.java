package hr.fer.zemris.java.hw05.db;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class that represents a database of student record
 * and can generate a list of filtered student records
 * with the specified conditions
 * 
 * @author Tomislav Kurtović
 *
 */
public class StudentDatabase {

	/**
	 * List of student records
	 */
	private List<StudentRecord> studentInfo ;

	/**
	 * Map of student records
	 */
	private Map<String,Integer> fastRecordGetter;

	
	/**
	 * Constructor of student records that gets all the
	 * rows from a database file
	 */
	public StudentDatabase(List<String> infoFromDB) {
		createStudentRecords(infoFromDB);
		mapIndexes();
	}

	/**
	 * Takes one row from the database and converts it 
	 * to a {@link StudentRecord} object
	 * If the grade is out of the range 1 to 5 it will
	 * print an appropriate message and continue the converting
	 * @param infoFromDB
	 */
	private void createStudentRecords(List<String> infoFromDB) {
		studentInfo = new ArrayList<>();
		
		for(String row : infoFromDB) {
			try {
				if(row.isBlank()) continue;
				String[] info = row.split("\\t+");
				int grade = Integer.parseInt(info[3]);
				if(grade < 1 || grade > 5) {
					System.out.println("Grade for ... is not in valid range!");
					continue;
				}
				StudentRecord record = new StudentRecord(info[0], info[1], info[2], grade);
				if(studentInfo.contains(record)) {
					System.out.println("Duplicate jmbag found!");
					continue;
				}
				studentInfo.add(record);
			}catch(IllegalArgumentException ex) {
				System.out.println("Grade is not in valid ");
			}
		}
	}
		
	/**
	 * Maprs the jmbag of a student 
	 */
	private void mapIndexes() {
		fastRecordGetter = new HashMap<>();
		for(int i = 0, len = studentInfo.size(); i < len; i++) {
			fastRecordGetter.put(studentInfo.get(i).getJmbag(), i);
		}
	}
	
	/**
	 * Gets student with the specified jmbag
	 * 
	 * @param jmbag student to retieve 
	 * @return StudentRecord 
	 */
	public StudentRecord forJMBAG(String jmbag) {
		if(fastRecordGetter.get(jmbag) != null) {
			return studentInfo.get(fastRecordGetter.get(jmbag));
		}
		return null;
	}
	
	/**
	 * Filters out all the student records that do not
	 * satisfy some condition
	 * @param filter filter of the records
	 * @return new list of student records
	 */
	public List<StudentRecord> filter(IFilter filter){
		List<StudentRecord> acceptedRecords = new ArrayList<>();
		for(StudentRecord record: studentInfo) {
			if(filter.accepts(record)) {
				acceptedRecords.add(record);
			}
		}
		return acceptedRecords;
	}
}
