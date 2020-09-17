package hr.fer.zemris.java.hw07.demo4;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Class that prints some information about the students 
 * from a file 
 * @author Tomislav Kurtović
 *
 */
public class StudentDemo {
	
	/**
	 * Main method that loads the information from the file and converts
	 * all the information to a list of {@link StudentRecord}s.
	 * It then calls methods described in this class and also calls different
	 * print methods
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		List<String> lines = Files.readAllLines(Paths.get("./studenti.txt"),StandardCharsets.UTF_8);
		List<StudentRecord> records = convert(lines);
		
		long brojBodovaViseOd25 = vratiBodovaViseOd25(records);
		printNumber(brojBodovaViseOd25,1);
		long ocjena5 = vratiBrojOdlikasa(records);
		printNumber(ocjena5,2);
		
		
		List<StudentRecord> odlikasi = vratiListuOdlikasa(records);
		printList(odlikasi,3);
		List<StudentRecord> odlikasiSortirano = vratiSortiranuListuOdlikasa(records);
		printList(odlikasiSortirano,4);
		List<String> nepolozeniJMBAGovi = vratiPopisNepolozenih(records);
		printList(nepolozeniJMBAGovi,5);
		
		Map<Integer, List<StudentRecord>> mapaPoOcjenama = razvrstajStudentePoOcjenama(records);
		printMapList(mapaPoOcjenama,6);
		Map<Integer, Integer> mapaPoOcjenama2 = vratiBrojStudenataPoOcjenama(records);
		printMapNum(mapaPoOcjenama2,7);
		Map<Boolean, List<StudentRecord>> prolazNeprolaz = razvrstajProlazPad(records);
		printMapList(prolazNeprolaz,8);
	}

	/**
	 * Static method used to print the map entries of the map
	 * where both key and value are Integers
	 * @param map map to print
	 * @param i number of task to print
	 */
	private static void printMapNum(Map<Integer, Integer> map, int i) {
		System.out.println("Zadatak " + i);
		System.out.println("=========");
		for( Object key : map.keySet()) {
			System.out.println(map.get(key));
		}
		System.out.println("");
	}

	/**
	 * Static method used to print the map entries of the map
	 * where both key and value are Integers
	 * @param map map to print
	 * @param i number of task to print
	 */
	private static void printMapList(Map<? extends Object, List<StudentRecord>> map, int i) {
		System.out.println("Zadatak " + i);
		System.out.println("=========");
		for( Object key : map.keySet()) {
			System.out.println(key);
			for(Object info : map.get(key)) {
				System.out.println(info);
			}
		
		}
		System.out.println("");
	}
	/**
	 * Static method used to print the list values to the screen
	 * @param map list to print
	 * @param i number of task to print
	 */
	private static void printList(List<? extends Object> list, int i) {
		System.out.println("Zadatak " + i);
		System.out.println("=========");
		for(Object info : list) {
			System.out.println(info);
		}
		System.out.println("");
	}
	
	/**
	 * Static method used to print result of long to screen
	 * @param num long value to print
	 * @param i number of task to print
	 */
	private static void printNumber(long num, int i) {
		System.out.println("Zadatak " + i);
		System.out.println("=========");
		System.out.println(num + "\n" );
	}
	
	/**
	 * Converts lines from list of strings to 
	 * a list of {@link StudentRecord}s
	 * @param lines list with lines from file
	 * @return list with {@link StudentRecord}s
	 */
	private static List<StudentRecord> convert(List<String> lines) {
		List<StudentRecord> list = new ArrayList<>();
		for(String s : lines) {
			String[] linesSplit = s.split("\\s+");
			list.add(new StudentRecord(linesSplit[0], linesSplit[1], 
					linesSplit[2], parseToDouble(linesSplit[3]), parseToDouble(linesSplit[4]),
					parseToDouble(linesSplit[5]),parseInt(linesSplit[6])));
		}
		return list;
	}
	/**
	 * Tries to parse string to int value and throws exception if 
	 * the value is invalid
	 * @param string string representation of int value
	 * @return int value of string
	 */
	private static int parseInt(String string) {
		try {
			return Integer.parseInt(string);
		}catch(NumberFormatException ex) {
			System.out.println("Unable to store value!");
			return 0;
		}
	}
	/**
	 * Tries to parse string to double value and throws exception if 
	 * the value is invalid
	 * @param string string representation of double value
	 * @return double value of string
	 */
	private static double parseToDouble(String string) {
		try {
			 return  Double.parseDouble(string);
		}catch(NumberFormatException ex) {
			System.out.println("Unable to store value!");
			return 0;
		}
	}
	
	/**
	 * Returns the number of students whose overall points 
	 * from MI + ZI + LAB are greater than 25
	 * @param list list of {@link StudentRecord}s
	 * @return number of students whose overall is greater than 25
	 */
	private static long vratiBodovaViseOd25(List<StudentRecord> list) {
		return list.stream()
				.filter(student -> (student.getMI() + student.getZI() + student.getLAB())> 25)
				.count();
	}
	
	/**
	 * Returns the number of students who passed with 
	 * a grade of 5
	 * @param list list of {@link StudentRecord}s
	 * @return number of students with grade 5
	 */
	private static long vratiBrojOdlikasa(List<StudentRecord> list) {
		return list.stream()
				.filter(student -> student.getGrade() == 5)
				.count();
		
	}
	/**
	 * Returns a list of {@link StudentRecord}s with students who 
	 * passed with a 5
	 * @param list list of {@link StudentRecord}s
	 * @return list of {@link StudentRecord}s who passed with 5
	 */
	private static List<StudentRecord> vratiListuOdlikasa(List<StudentRecord> list) {
		return list.stream()
				.filter(student -> student.getGrade() == 5)
				.collect(Collectors.toList());
	}
	
	/**
	 * Returns a sorted list of {@link StudentRecord}s with students who 
	 * passed with a 5.
	 * It is sorted by point count with higher values at the beginning
	 * @param list list of {@link StudentRecord}s
	 * @return sorted list of {@link StudentRecord}s who passed with 5
	 */
	private static List<StudentRecord> vratiSortiranuListuOdlikasa(List<StudentRecord>list){
		return list.stream()
				.filter(student -> student.getGrade() == 5)
				.sorted((st1,st2)->{
				return Double.compare(getPoints(st2), getPoints(st1));
				})
				.collect(Collectors.toList());
	}
	
	/**
	 * Returns the points from MI, ZI and LAB added up
	 * @param student student whose points should be added up
	 * @return value of addition
	 */
	private static double getPoints(StudentRecord student) {
		return student.getMI() + student.getZI() + student.getLAB();
	}
	
	/**
	 * Returns a  list of jmbags with students who 
	 * did not pass
	 * @param list list of {@link StudentRecord}s
	 * @return  list of jmbags 
	 */
	private static List<String> vratiPopisNepolozenih(List<StudentRecord> list) {
		return list.stream()
				.filter(st -> st.getGrade() == 1)
				.map(st -> st.getJmbag())
				.sorted((jmbag1,jmbag2) -> jmbag1.compareTo(jmbag2))
				.collect(Collectors.toList());
	}
	
	/**
	 * Returns a  map containing grades as keys and list
	 * of {@link StudentRecord}s. 
	 * The list contains student who have the same grade as the key  
	 * @param list list of {@link StudentRecord}s
	 * @return  map of students grouped by grade 
	 */
	private static Map<Integer, List<StudentRecord>> razvrstajStudentePoOcjenama(List<StudentRecord> list) {
		return list.stream()
				.collect(Collectors.groupingBy(StudentRecord::getGrade));
	}
	
	/**
	 * Returns a  map containing grades as keys and number of students 
	 * with that grade as values
	 * @param list list of {@link StudentRecord}s
	 * @return  map of number of students grouped by grade
	 */
	private static Map<Integer,Integer>vratiBrojStudenataPoOcjenama(List<StudentRecord> list) {
		return list.stream()
				.collect(Collectors.toMap(StudentRecord::getGrade, i->Integer.valueOf(1),(i,j) -> i+j));
	}
	/**
	 * Returns a  map containing true or false values as keys list of
	 * {@link StudentRecord}s as values.
	 * The lists contain students who either all passed or all failed
	 * @param list list of {@link StudentRecord}s
	 * @return  map of number of students grouped by pass
	 */
	private static Map<Boolean, List<StudentRecord>> razvrstajProlazPad(List<StudentRecord> list) {
		return list.stream()
				.collect(Collectors.partitioningBy(student -> student.getGrade() > 1));
	}
}
