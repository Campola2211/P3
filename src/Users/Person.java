package Users;

public class Person {
	//Person Variables
	private String firstName;
	private String lastName;

	//getter and setter methods
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getFirstName() {
		return firstName;
	}
	public String getLastName() {
		return lastName;
	}

	//constructor
	public Person(String firstName, String lastName) {
		setFirstName(firstName);
		setLastName(lastName);
	}

	//toString method
	public String toString() {
		String str = "";
		str += firstName + " " + lastName;
		return str;
	}
}
