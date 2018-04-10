package Users;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class UserFactory {
	//Variables
	private ObservableList<Login> userList;
	
	//Constructor
	public UserFactory() {
		userList = FXCollections.observableArrayList();
	}
	
	//Getter and Setter Methods
	public ObservableList<Login> getUserList() {
		return userList;
	}
	public void setUserList(ObservableList<Login> userList) {
		this.userList = userList;
	}
	
	//Find User Method
	public Login findUser(String userName) {
		for(Login user : userList) {
			if(user.getUserName().equals(userName)) {
				return user;
			}
		}
		return null;
	}
	
	//Add User Method
	public void addUser(Login user) {
		userList.add(user);
	}
	
	//Delete User Method
	public void delUser(Login user) {
		userList.remove(user);
	}
	
	//UserList Size Method
	public int userListSize() {
		return userList.size();
	}
	
	//ToString method
	public String toString() {
		String str = "";
		for(Login user : userList) {
			str += user.getUserName() + "\n";
		}
		return str;
	}
	
	//read userList file Method
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
					userList.add(whMan);
					break;
				case "OfficeMan":
					OfficeMan officeMan = new OfficeMan(userName, password, person, email);
					userList.add(officeMan);
					break;
				case "SalesAsso":
					SalesAsso salesAsso = new SalesAsso(userName, password, person, email);
					userList.add(salesAsso);
					break;
				case "SysAdmin":
					SysAdmin sysAdmin = new SysAdmin(userName, password, person, email);
					userList.add(sysAdmin);
					break;
				default:
					break;
			}
		}
		read.close();
	}
	
	//Save All users Method
	public void saveUserInfo(String fileName) throws IOException {
		File file = new File(fileName);
		PrintWriter writer = new PrintWriter(file, "UTF-8");
		for(Login user : userList) {
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
