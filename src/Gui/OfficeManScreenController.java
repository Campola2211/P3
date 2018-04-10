package Gui;

//ADD VIEW SALESINVOICE METHOD

import java.io.FileNotFoundException;
import java.io.IOException;

import Parts.Inventory;
import Parts.WareHouse;
import Parts.WhFactory;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class OfficeManScreenController {
	//in Main Window
	//Labels
	@FXML
    private Label welcomeLabel;

	//Buttons
	@FXML
    private Button logoutBTN;

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

	//in Order Parts Tab
	//TextAreas
	@FXML
    private TextArea orderPartsTA;

	//Buttons
	@FXML
    private Button orderPartsBTN;

	//Button Methods
	@FXML
    void doOrderParts(ActionEvent event) {
		for(int i = 0; i < allWh.allWhSize(); i++) {
			WareHouse wh = allWh.getWhByNumber(i);
			ObservableList<Inventory> iList = wh.getIList();
			for(int j = 0; j < iList.size(); j++) {
				Inventory inv = iList.get(j);
				//System.out.println(inv.getQuantity() + "," + inv.getMinQuantity());
				if(inv.getQuantity() < inv.getMinQuantity()) {
					orderPartsTA.setText("Ordered...\n");
					int diff = inv.getMinQuantity() - inv.getQuantity();
					inv.setQuantity(inv.getQuantity() + diff);
				} else {
					orderPartsTA.setText("Ordered...\n");
				}
			}
			try {
				wh.saveWhDB(wh.getWhName() + ".txt");
			} catch (IOException e) {
				//ADD IO ERROR
			}
		}
    }

	//in Examine Parts Tab
	//TextFields
	@FXML
    private TextField partNameTF;
	@FXML
    private TextField partNumberTF;
	@FXML
    private TextField locationTF;

	//TextAreas
	@FXML
    private TextArea examinePartsTA;
	
	//Buttons
	@FXML
    private Button displayBTN;

	//Button Methods
	@FXML
    void doDisplay(ActionEvent event) {
		String location = locationTF.getText();
		String partName = partNameTF.getText();
		long partNumber = 0;
		WareHouse wh = allWh.getWhByName(location);
		if(wh != null) {
			if(partName.equals("") || partName == null) {
				partNumber = Long.parseLong(partNumberTF.getText());
				Inventory part2 = wh.findPartByNumber(partNumber);
				if(part2 == null) {
					examinePartsTA.setText("Part does not exist.\n");
					partNameTF.setText("");
					partNumberTF.setText("");
					locationTF.setText("");
				} else {
					examinePartsTA.setText("PartName: " + part2.getBp().getPartName() + "\n" + "PartNumber: " + part2.getBp().getPartNumber() + "\n" + 
							"ListPrice: " + part2.getBp().getListPrice() + "\n" + "SalePrice: " + part2.getBp().getSalePrice() + 
							"\n" + "OnSale: " + part2.getBp().isOnSale() + "\n" + "Qnty: " + part2.getQuantity());
					partNameTF.setText("");
					partNumberTF.setText("");
					locationTF.setText("");
				}
			} else {
				Inventory part = wh.findPartByName(partName);
				if(part == null) {
					examinePartsTA.setText("Part does not exist.\n");
					partNameTF.setText("");
					partNumberTF.setText("");
					locationTF.setText("");
				} else {
					examinePartsTA.setText("PartName: " + partName + "\n" + "PartNumber: " + part.getBp().getPartNumber() + "\n" + 
							"ListPrice: " + part.getBp().getListPrice() + "\n" + "SalePrice: " + part.getBp().getSalePrice() + 
							"\n" + "OnSale: " + part.getBp().isOnSale() + "\n" + "Qnty: " + part.getQuantity());
					partNameTF.setText("");
					partNumberTF.setText("");
					locationTF.setText("");
				}
			}
		} else {
			examinePartsTA.setText("WareHouse does not exist.\n");
			partNameTF.setText("");
			partNumberTF.setText("");
			locationTF.setText("");
		}
    }

	//in View Sales Invoice Tab
	//TextFields
	@FXML
    private TextField startDateTF;
	@FXML
    private TextField endDateTF;
	@FXML
    private TextField customerTF;

	//Buttons
	@FXML
    private Button viewBTN;

	//Button Methods
	@FXML
    void doView(ActionEvent event) {

    }

	//Initialize method
	public void initialize() {
		welcomeLabel.setText("Welcome, OfficeMan");
		try {
			allWh.initializeAllWh("allWh.txt");
		} catch (FileNotFoundException e) {
			System.out.println("allWh.txt is not found");
		}
	}

	//Variables
	static WhFactory allWh = new WhFactory();
}
