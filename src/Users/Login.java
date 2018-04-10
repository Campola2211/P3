package Users;

public class Login {
	//Login variables
	private String userName;
	private String password;
	private Person person;
	private String emailAddress;
		
	//getters and setters
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public void setPerson(Person person) {
		this.person = person;
	}
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}
	public String getUserName() {
		return userName;
	}
	public String getPassword() {
		return password;
	}
	public Person getPerson() {
		return person;
	}
	public String getEmailAddress() {
		return emailAddress;
	}
		
	//constructor 
	public Login(String userName, String password, Person person, String emailAddress) {
		setUserName(userName);
		setPassword(password);
		setPerson(person);
		setEmailAddress(emailAddress);
	}
		
	//toString method
	public String toString() {
		String str = "";
		str += userName;
		return str;
	}
}
