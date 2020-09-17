package hr.fer.zemris.java.hw07.demo4;

/**
 * Class used to store information of a student 
 * @author Tomislav Kurtović
 *
 */
public class StudentRecord {

	/**
	 * jmbag of the student
	 */
	private String jmbag;
	/**
	 * last name of the student
	 */
	private String lastName;
	/**
	 * name of the student
	 */
	private String name;
	/**
	 * points scored on mid term exam of the student
	 */
	private double MI;
	/**
	 * points scored on final exam of the student
	 */
	private double ZI;
	/**
	 * points scored during laboratory  exercises
	 */
	private double LAB;
	/**
	 * grade of student
	 */
	private int grade;
	
	/**
	 * Constructor that creates new StudentRecord 
	 * @param jmbag
	 * @param lastName
	 * @param name
	 * @param mI
	 * @param zI
	 * @param lAB
	 * @param grade
	 */
	public StudentRecord(String jmbag, String lastName, String name, double mI, double zI, double lAB,int grade) {
		super();
		this.jmbag = jmbag;
		this.lastName = lastName;
		this.name = name;
		MI = mI;
		ZI = zI;
		LAB = lAB;
		this.grade = grade;
	}

	/**
	 * Returns jmbag
	 * @return jmbag
	 */
	public String getJmbag() {
		return jmbag;
	}
	/**
	 * Returns lastName
	 * @return lastName
	 */
	public String getLastName() {
		return lastName;
	}
	/**
	 * Returns name
	 * @return name
	 */
	public String getName() {
		return name;
	}
	/**
	 * Returns MI
	 * @return MI
	 */
	public double getMI() {
		return MI;
	}
	/**
	 * Returns ZI
	 * @return ZI
	 */
	public double getZI() {
		return ZI;
	}
	/**
	 * Returns LAB
	 * @return LAB
	 */
	public double getLAB() {
		return LAB;
	}
	/**
	 * Returns grade
	 * @return grade
	 */
	public int getGrade() {
		return grade;
	}

	@Override
	public String toString() {
		return "StudentRecord [jmbag=" + jmbag + ", prezime=" + lastName + ", ime=" + name + ", MI=" + MI + ", ZI=" + ZI
				+ ", LAB=" + LAB + "]";
	}
	
	
}
