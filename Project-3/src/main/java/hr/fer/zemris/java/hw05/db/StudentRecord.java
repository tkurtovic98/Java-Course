package hr.fer.zemris.java.hw05.db;

/**
 * Class that represents one student record
 * with the assigned jmbag, last name, first name 
 * and final grade. 
 * 
 * @author Tomislav Kurtović
 *
 */
public class StudentRecord {
	
	/**
	 * Jmbag field
	 */
	private String jmbag;
	/**
	 * Last name field
	 */
	private String lastName;
	/**
	 * First name field
	 */
	private String firstName;
	/**
	 * Final grade field
	 */
	private int finalGrade;
	
	/**
	 * Constructor that constructs a new student record
	 * 
	 * @param jmbag jmbag of student
	 * @param lastName last name of student
	 * @param firstName first name of student
	 * @param finalGrade final grade of student
	 */
	public StudentRecord(String jmbag, String lastName, String firstName, int finalGrade) {
		this.jmbag = jmbag;
		this.lastName = lastName;
		this.firstName = firstName;
		this.finalGrade = finalGrade;
	}
	
	/**
	 * Jmbag getter
	 * @return jmbag
	 */
	public String getJmbag() {
		return jmbag;
	}
	
	/**
	 * lastName getter
	 * @return lastName
	 */
	public String getLastName() {
		return lastName;
	}
	
	/**
	 * firstName getter
	 * @return firstName
	 */
	public String getFirstName() {
		return firstName;
	}
	
	/**
	 * finalGrade getter
	 * @return finalGrade
	 */
	public int getFinalGrade() {
		return finalGrade;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((jmbag == null) ? 0 : jmbag.hashCode());
		return result;
	}

	/**
	 * Two records are equal if their jmbag fields are equal
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		StudentRecord other = (StudentRecord) obj;
		if (jmbag == null) {
			if (other.jmbag != null)
				return false;
		} else if (!jmbag.equals(other.jmbag))
			return false;
		return true;
	}
}
