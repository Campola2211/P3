package Gui;

import java.io.FileNotFoundException;
import java.io.IOException;
import Users.Login;
import Users.OfficeMan;
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
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginScreenController {
	//Labels
	@FXML
    private Label wrongPasswordLabel;
	@FXML
    private Label userNotFoundLabel;
	
	//TextFields
	@FXML
    private TextField userNameTF;
	@FXML
    private TextField passwordTF;
	
	//Buttons
	@FXML
    private Button loginButton;
	
	//Button Methods
	@FXML
    void doLogin(ActionEvent event) throws IOException {
		String userName = userNameTF.getText();
		String password = passwordTF.getText();
		Login found = userList.findUser(userName);
		
		//for SysAdmin window
		Parent sysAdminParent = FXMLLoader.load(getClass().getResource("SysAdminScreen.fxml"));
		Scene sysAdminScene = new Scene(sysAdminParent);
		sysAdminScene.getStylesheets().add(getClass().getResource("sysAdminScreen.css").toExternalForm());
		Stage sysAdminStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		
		//for WhMan Window
		Parent whManParent = FXMLLoader.load(getClass().getResource("WhManScreen.fxml"));
		Scene whManScene = new Scene(whManParent);
		whManScene.getStylesheets().add(getClass().getResource("whManScreen.css").toExternalForm());
		Stage whManStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		
		//for salesAsso Window
		Parent salesAssoParent = FXMLLoader.load(getClass().getResource("SalesAssoScreen.fxml"));
		Scene salesAssoScene = new Scene(salesAssoParent);
		salesAssoScene.getStylesheets().add(getClass().getResource("salesAssoScreen.css").toExternalForm());
		Stage salesAssoStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		
		//for OfficeMan window
		Parent officeManParent = FXMLLoader.load(getClass().getResource("OfficeManScreen.fxml"));
		Scene officeManScene = new Scene(officeManParent);
		officeManScene.getStylesheets().add(getClass().getResource("officeManScreen.css").toExternalForm());
		Stage officeManStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		if(found != null) {
			if(found instanceof SysAdmin) {
				if(password.equals(found.getPassword())) {
					sysAdminStage.hide();
					sysAdminStage.setScene(sysAdminScene);
					sysAdminStage.show();
				} else {
					wrongPasswordLabel.setVisible(true);
					userNotFoundLabel.setVisible(false);
					passwordTF.setText("");
				}
			} else if(found instanceof WhMan) {
				if(password.equals(found.getPassword())) {
					whManStage.hide();
					whManStage.setScene(whManScene);
					whManStage.show();
				} else {
					wrongPasswordLabel.setVisible(true);
					userNotFoundLabel.setVisible(false);
					passwordTF.setText("");
				}
			} else if(found instanceof SalesAsso) {
				if(password.equals(found.getPassword())) {
					salesAssoStage.hide();
					salesAssoStage.setScene(salesAssoScene);
					salesAssoStage.show();
					for(SalesAsso user : salesAssoList) {
						if(user.getUserName().equals(userName)) {
							FXMLLoader loader = new FXMLLoader();
							loader.setLocation(getClass().getResource("SalesAssoScreen.fxml"));
							loader.load();
							SalesAssoScreenController controller = loader.getController();
							controller.initSalesAsso(user);
						}
					}
				} else {
					wrongPasswordLabel.setVisible(true);
					userNotFoundLabel.setVisible(false);
					passwordTF.setText("");
				}
			} else if(found instanceof OfficeMan) {
				if(password.equals(found.getPassword())) {
					officeManStage.hide();
					officeManStage.setScene(officeManScene);
					officeManStage.show();
				} else {
					wrongPasswordLabel.setVisible(true);
					userNotFoundLabel.setVisible(false);
					passwordTF.setText("");
				}
			}
		} else {
			wrongPasswordLabel.setVisible(false);
			userNotFoundLabel.setVisible(true);
			userNameTF.setText("");
			passwordTF.setText("");
		}
    }
	
	//Initialize Method
	public void initialize() {
		try {
			userList.readUserFile("allUsers.txt");
		} catch (FileNotFoundException e) {
			//ADD FILE NOT FOUND ERROR
		}
		wrongPasswordLabel.setVisible(false);
		userNotFoundLabel.setVisible(false);
		for(int i = 0; i < userList.userListSize(); i++) {
			Login user = userList.getUserList().get(i);
			if(user instanceof SalesAsso) {
				SalesAsso salesAsso = new SalesAsso(user.getUserName(), user.getPassword(), user.getPerson(), user.getEmailAddress());
				salesAssoList.add(salesAsso);
			}
		}
		
	}
	
	//Variables
	UserFactory userList = new UserFactory();
	ObservableList<SalesAsso> salesAssoList = FXCollections.observableArrayList();
}
