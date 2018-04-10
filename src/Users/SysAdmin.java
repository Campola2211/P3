package Users;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;


public class SysAdmin extends Login{
	//Variables
	private UserFactory userList;
	
	//Constructor
	public SysAdmin(String userName, String password, Person person, String emailAddress) {
		super(userName, password, person, emailAddress);
	}
	
	//Getter and Setter method
	public UserFactory getUserList() {
		return userList;
	}
	public void setUserList(UserFactory userList) {
		this.userList = userList;
	}
	
	//Create Users Method
	public WhMan makeWhMan(String userName, String password, Person person, String emailAddress) {
		WhMan user = new WhMan(userName, password, person, emailAddress);
		return user;
	}
	public OfficeMan makeOfficeMan(String userName, String password, Person person, String emailAddress) {
		OfficeMan user = new OfficeMan(userName, password, person, emailAddress);
		return user;
	}
	public SalesAsso makeSalesAsso(String userName, String password, Person person, String emailAddress) {
		SalesAsso user = new SalesAsso(userName, password, person, emailAddress);
		return user;
	}

	//Add User Method
	public void addNewUser(Login user) {
		Login found = findUser(user);
		if(found != null) {
			userList.addUser(user);
		}
		//ADD CATCH FOR IF USER DOES EXIST ALREADY
	}
	
	//Delete User Method
	public void deleteUser(Login user) {
		Login found = findUser(user);
		if(found != null) {
			userList.delUser(found);
		}
		//ADD CATCH FOR IF USER DOESN'T EXIST
	}
	
	//Find User Method
	public Login findUser(Login user) {
		for(int i = 0; i < userList.userListSize(); i++) {
			if(userList.getUserList().get(i).getUserName().equals(user.getUserName())) {
				return userList.getUserList().get(i);
			}
		}
		return null;
	}
	
	//Find User by UserName Method
	public Login findUserByUN(String userName) {
		for(int i = 0; i < userList.userListSize(); i++) {
			if(userList.getUserList().get(i).getUserName().equals(userName)) {
				return userList.getUserList().get(i);
			}
		}
		return null;
	}
	
	//Reset Password Method
	public void resetPassword(Login user) {
		user.setPassword("password");
	}
	
	//Read UserFile Method
	public void readUserFile(String fileName) throws FileNotFoundException {
		File file = new File(fileName);
		Scanner read = new Scanner(file);
		while(read.hasNextLine()) {
			String line = read.nextLine();
			String al[] = line.split(" ");
			String userName = al[0];
			String password = al[1];
			String firstName = al[2];
			String lastName = al[3];
			String email = al[4];
			String accountType = al[5];
			Person person = new Person(firstName, lastName);
			switch(accountType) {
				case "WhMan":
					WhMan whMan = new WhMan(userName, password, person, email);
					userList.addUser(whMan);
					break;
				case "OfficeMan":
					OfficeMan officeMan = new OfficeMan(userName, password, person, email);
					userList.addUser(officeMan);
					break;
				case "SalesAsso":
					SalesAsso salesAsso = new SalesAsso(userName, password, person, email);
					userList.addUser(salesAsso);
					break;
				case "SysAdmin":
					SysAdmin sysAdmin = new SysAdmin(userName, password, person, email);
					userList.addUser(sysAdmin);
					break;
				default:
					break;
			}
		}
		read.close();
	}
	
	//Save All Users Method
	public void saveUserInfo(String fileName) throws IOException {
		File file = new File(fileName);
		PrintWriter writer = new PrintWriter(file, "UTF-8");
		for(int i = 0; i < userList.userListSize(); i++) {
			Login user = userList.getUserList().get(i);
			if(user instanceof WhMan) {
				writer.println(user.getUserName() + " " + user.getPassword() + " " + user.getPerson().getFirstName() +
						" " + user.getPerson().getLastName() + " " + user.getEmailAddress() + " " + "WhMan");
			} else if(user instanceof OfficeMan) {
				writer.println(user.getUserName() + " " + user.getPassword() + " " + user.getPerson().getFirstName() +
						" " + user.getPerson().getLastName() + " " + user.getEmailAddress() + " " + "OfficeMan");
			} else if(user instanceof SalesAsso) {
				writer.println(user.getUserName() + " " + user.getPassword() + " " + user.getPerson().getFirstName() +
						" " + user.getPerson().getLastName() + " " + user.getEmailAddress() + " " + "SalesAsso");
			} else if(user instanceof SysAdmin) {
				writer.println(user.getUserName() + " " + user.getPassword() + " " + user.getPerson().getFirstName() +
						" " + user.getPerson().getLastName() + " " + user.getEmailAddress() + " " + "SysAdmin");
			}
		}
		writer.close();
	}
	
}
