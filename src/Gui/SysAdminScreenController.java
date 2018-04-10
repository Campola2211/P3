package Gui;

//SYSADMIN CONTROLLER IS ALL DONE

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import Parts.WareHouse;
import Parts.WhFactory;
import Users.Login;
import Users.OfficeMan;
import Users.Person;
import Users.SalesAsso;
import Users.SysAdmin;
import Users.UserFactory;
import Users.WhMan;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class SysAdminScreenController {
	//in Main Window
	//Labels
	@FXML
    private Label welcomeLabel;
	@FXML
    private Label saveLabel;
	
	//Buttons
	@FXML
    private Button logoutButton;
	@FXML
    private Button saveAllButton;
	
	//Button Methods
	@FXML
    void doLogout(ActionEvent event) throws IOException {
		Parent loginParent = FXMLLoader.load(getClass().getResource("LoginScreen.fxml"));
		Scene loginScene = new Scene(loginParent);
		loginScene.getStylesheets().add(getClass().getResource("loginScreen.css").toExternalForm());
		Stage loginStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		loginStage.hide();
		loginStage.setScene(loginScene);
		loginStage.show();
    }
	@FXML
    void doSaveAll(ActionEvent event) {
		try {
			userList.saveUserInfo("allUsers.txt");
			WhFactory allWh = new WhFactory();
			for(int i = 0; i < userList.userListSize(); i++) {
				Login user = userList.getUserList().get(i);
				if(user instanceof SalesAsso) {
					WareHouse saleVan = ((SalesAsso) user).getSaleVan();
					allWh.addWareHouse(saleVan);
				} else if(user instanceof WhMan) {
					WareHouse mainWh = ((WhMan) user).getMainWh();
					allWh.addWareHouse(mainWh);
				}
				try {
					allWh.saveAllWhNames("allWh.txt");
				} catch (FileNotFoundException e) {
					//ADD FILE NOT FOUND ERROR
				}
			}
			//ADD SAVE CONFIRMATION
			saveLabel.setVisible(true);
		} catch (IOException e) {
			//ADD FILE NOT FOUND ERROR
		}
		
    }
	
	//in Make User Tab
	//TextFields
	@FXML
    private TextField userNameTF;
	@FXML
    private TextField passwordTF;
	@FXML
    private TextField firstNameTF;
	@FXML
    private TextField lastNameTF;
	@FXML
    private TextField emailTF;
	
	//ComboBox
	@FXML
    private ComboBox<String> accountTypeCBOX;
	
	//Buttons
	@FXML
    private Button addUserBTN;
	
	//List
	@FXML
    private ListView<String> allUsersList;
	
	//Button Methods
	@FXML
    void doAddUser(ActionEvent event) {
		String userName = userNameTF.getText();
		String password = passwordTF.getText();
		Person person = new Person(firstNameTF.getText(), lastNameTF.getText());
		String email = emailTF.getText();
		String accountType = accountTypeCBOX.getSelectionModel().getSelectedItem();
		Login found = userList.findUser(userName);
		if(found == null) {
			switch(accountType) {
			case "WhMan":
				WhMan whMan = new WhMan(userName, password, person, email);
				String whFileName = "mainWh.txt";
				try {
					whMan.getMainWh().saveWhDB(whFileName);
				} catch (IOException e1) {
					// ADD IOEXCEPTION ERROR
				}
				userList.addUser(whMan);
				users1.add(whMan.getUserName());
				users2.add(whMan.getUserName());
				userNames.add(whMan.getUserName() + ", WhMan");
				break;
			case "SalesAsso":
				SalesAsso salesAsso = new SalesAsso(userName, password, person, email);
				String whFileName2 = salesAsso.getUserName() + "DB.txt";
				try {
					salesAsso.getSaleVan().saveWhDB(whFileName2);
				} catch (IOException e) {
					//ADD IOEXCEPTION ERROR
				}
				userList.addUser(salesAsso);
				users2.add(salesAsso.getUserName());
				users1.add(salesAsso.getUserName());
				userNames.add(salesAsso.getUserName() + ", SalesAsso");
				break;
			case "OfficeMan":
				OfficeMan officeMan = new OfficeMan(userName, password, person, email);
				userList.addUser(officeMan);
				users1.add(officeMan.getUserName());
				users2.add(officeMan.getUserName());
				userNames.add(officeMan.getUserName() + ", OfficeMan");
				break;
			default:
				//DO NOTHING
		}
		selectUserCBOX.setItems(users1);
		selectUser2CBOX.setItems(users2);
		allUsersList.setItems(userNames);
		allUsersList.refresh();
		userNameTF.setText("");
		passwordTF.setText("");
		firstNameTF.setText("");
		lastNameTF.setText("");
		emailTF.setText("");
		accountTypeCBOX.getSelectionModel().clearSelection();
		} else {
			//ADD USER EXISTS ALREADY ERROR
			selectUserCBOX.setItems(users1);
			selectUser2CBOX.setItems(users2);
			allUsersList.setItems(userNames);
			allUsersList.refresh();
			userNameTF.setText("");
			passwordTF.setText("");
			firstNameTF.setText("");
			lastNameTF.setText("");
			emailTF.setText("");
			accountTypeCBOX.getSelectionModel().clearSelection();
		}
		saveLabel.setVisible(false);
    }
	
	//in Delete User Tab
	//TextAreas
	@FXML
    private TextArea deleteTA;
	
	//Buttons
	@FXML
    private Button deleteUserBTN;
	
	//ComboBox
	@FXML
    private ComboBox<String> selectUserCBOX;
	
	//Button Methods
	@FXML
    void doDeleteUser(ActionEvent event) {
		String userName = selectUserCBOX.getSelectionModel().getSelectedItem();
		Login user = userList.findUser(userName);
		if((user instanceof SysAdmin) == false) {
			for(String uName : userNames) {
				if(uName.contains(userName)) {
					userNames.remove(uName);
					break;
				}
			}
			if(user instanceof SalesAsso) {
				String fileName = userName + "DB.txt";
				File file = new File(fileName);
				file.delete();
			} else if(user instanceof WhMan) {
				File file = new File("mainWh.txt");
				file.delete();
			}
			users1.remove(userName);
			users2.remove(userName);
			userList.delUser(user);
			selectUserCBOX.getItems().remove(userName);
			selectUser2CBOX.getItems().remove(userName);
			allUsersList.setItems(userNames);
			deleteTA.appendText("Deleted user: " + userName + "\n");
			selectUserCBOX.getSelectionModel().clearSelection();
		} else {
			deleteTA.appendText("Cannot delete SysAdmin\n");
			selectUserCBOX.getSelectionModel().clearSelection();
		}
		saveLabel.setVisible(false);
    }
	
	//in Reset User Password Tab
	//TextAreas
	@FXML
    private TextArea resetTA;
	
	//Buttons
	@FXML
    private Button resetPasswordBTN;
	@FXML
    private Button resetAllBTN;
	
	//ComboBox
	@FXML
    private ComboBox<String> selectUser2CBOX;
	
	//Button Methods
	@FXML
    void doResetPassword(ActionEvent event) {
		String userName = selectUser2CBOX.getSelectionModel().getSelectedItem();
		Login user = userList.findUser(userName);
		SysAdmin sysAdmin = null;
		for(int i = 0; i < userList.userListSize(); i++) {
			Login u = userList.getUserList().get(i);
			if(u instanceof SysAdmin) {
				sysAdmin = (SysAdmin) u;
				break;
			}
		}
		sysAdmin.resetPassword(user);
		selectUser2CBOX.getSelectionModel().clearSelection();
		resetTA.appendText("Reset password for: \n" + user.getUserName() + "\n");
		saveLabel.setVisible(false);
    }
	@FXML
    void doResetAll(ActionEvent event) {
		SysAdmin sysAdmin = null;
		for(int i = 0; i < userList.userListSize(); i++) {
			Login u = userList.getUserList().get(i);
			if(u instanceof SysAdmin) {
				sysAdmin = (SysAdmin) u;
				break;
			}
		}
		for(int i = 0; i < userList.userListSize(); i++) {
			Login user = userList.getUserList().get(i);
			sysAdmin.resetPassword(user);
		}
		resetTA.appendText("Reset password for: \nAll Users\n");
		saveLabel.setVisible(false);
    }
	
	//Initialize Method
	public void initialize() throws FileNotFoundException {
		welcomeLabel.setText("Welcome, SysAdmin");
		saveLabel.setVisible(false);
		userList.readUserFile("allUsers.txt");
		for(int i = 0; i < userList.userListSize(); i++) {
			Login user = userList.getUserList().get(i);
			String userName = user.getUserName();
			String accountType = "";
			if(user instanceof SysAdmin) {
				accountType = "SysAdmin";
			} else if(user instanceof WhMan) {
				accountType = "WhMan";
			} else if(user instanceof SalesAsso) {
				accountType = "SalesAsso";
			} else if(user instanceof OfficeMan) {
				accountType = "OfficeMan";
			}
			String str = userName + ", " + accountType;
			userNames.add(str);
			users1.add(userName);
			users2.add(userName);
		}
		accountType.add("OfficeMan");
		accountType.add("WhMan");
		accountType.add("SalesAsso");
		allUsersList.setItems(userNames);
		selectUserCBOX.setItems(users1);
		selectUser2CBOX.setItems(users2);
		accountTypeCBOX.setItems(accountType);
	}
	
	//Variables
	UserFactory userList = new UserFactory();
	ObservableList<String> userNames = FXCollections.observableArrayList();
	ObservableList<String> users1 = FXCollections.observableArrayList();
	ObservableList<String> users2 = FXCollections.observableArrayList();
	ObservableList<String> accountType = FXCollections.observableArrayList();
}
