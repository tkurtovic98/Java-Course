package hr.fer.zemris.java.custom.collections.demo;

import hr.fer.zemris.java.custom.collections.SimpleHashtable;

public class SimpleHashTableDemo {

	public static void main(String[] args) {
		SimpleHashtable<String, Integer> examMarks = new SimpleHashtable<String, Integer>(2);
		
		examMarks.put("Ivana", 2);
		
		System.out.println("Ivana's exam grade is : " + examMarks.get("Ivana")); // Writes 2
		
		examMarks.put("Ante", 2);
		examMarks.put("Jasna", 2);
		examMarks.put("Kristina", 5);
		examMarks.put("Ivana", 5);

		try {
			examMarks.put(null, 1);
		}catch(NullPointerException ex) {
			System.out.println("Null key found!");
		}
		
		Integer kristinaGrade = examMarks.get("Kristina");
		System.out.println("Kristina's exam grade is : " + kristinaGrade); // Writes 5
		System.out.println("Ivana's exam grade is : " + examMarks.get("Ivana")); // Writes 5
		
		System.out.println("Number of stored pairs: " + examMarks.size()); // Writes 4
		
		System.out.println(examMarks.toString());
	}
}
	
	


