package Gui;

//ADD ALL SAVE WINDOW

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import Parts.BikePart;
import Parts.Inventory;
import Parts.WareHouse;
import Parts.WhFactory;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class WhManScreenController {
	//in Main Window
	//Labels
	@FXML
    private Label welcomeLabel;
	@FXML
    private Label saveLabel;
	
	//Buttons
	@FXML
    private Button saveAllBTN;
	@FXML
    private Button logoutBTN;
	
	//Button Methods
	@FXML
    void doSaveAll(ActionEvent event) throws IOException {
		allWh.saveAllWh();
		//Save All Confirmation
		saveLabel.setVisible(true);
    }
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
	
	//in IO Tab
	//TextAreas
	@FXML
    private TextArea fileNameTA;
	@FXML
    private TextArea ioTA;
	
	//Buttons
	@FXML
    private Button readFileBTN;
	@FXML
    private Button selectFileBTN;
	
	//Button Methods
	 @FXML
	 void doReadFile(ActionEvent event) throws FileNotFoundException{
		String fileName = fileNameTA.getText();
		WareHouse mainWh = null;
		for(int i = 0; i < allWh.allWhSize(); i++) {
			WareHouse wh = allWh.getWhByNumber(i);
			if(wh.getWhName().equals("mainWh")) {
				mainWh = wh;
				wh.readWhDB(fileName);
				displayTable.getItems().clear();
			}
		}
		for(int j = 0; j < mainWh.whSize(); j++) {
			Inventory inv = mainWh.getIList().get(j);
			BikePart bp = inv.getBp();
			for(int k = 0; k < allWh.allWhSize(); k++) {
				WareHouse tempWh = allWh.getWhByNumber(k);
				if(tempWh.getWhName() != "mainWh") {
					if(tempWh.contains(inv)) {
						Inventory found = tempWh.findInventory(bp);
						if(found != null) {
							found.getBp().setListPrice(bp.getListPrice());
							found.getBp().setSalePrice(bp.getSalePrice());
							found.getBp().setOnSale(bp.isOnSale());
						}
					}
				}
			}
		}
		fileNameTA.setText("");
		ioTA.appendText("Succesfully read into mainWh!\n");
		saveLabel.setVisible(false);
	 }
	 @FXML
	 void doSelectFile(ActionEvent event) {
		FileChooser fc = new FileChooser();
		fc.setTitle("Select File:");
		File selectedFile = fc.showOpenDialog(null);
		if(selectedFile != null) {
			fileNameTA.setText(selectedFile.getName());
		} else {
			fileNameTA.setText("File is not valid.");
		}
		saveLabel.setVisible(false);
	 }
	 
	 //in Display Tab
	 //ComboBox
	 @FXML
	 private ComboBox<String> displayCBOX;
	 
	 //Buttons
	 @FXML
	 private Button selectButton;
	 
	 //TableView
	 @FXML
	 private TableView<Wrapper> displayTable;
	 
	 //TableColumns
	 @FXML
	 private TableColumn<Wrapper, String> partNameCol;
	 @FXML
	 private TableColumn<Wrapper, String> partNumberCol;
	 @FXML
	 private TableColumn<Wrapper, String> listPriceCol;
	 @FXML
	 private TableColumn<Wrapper, String> salePriceCol;
	 @FXML
	 private TableColumn<Wrapper, String> onSaleCol;
	 @FXML
	 private TableColumn<Wrapper, String> quantityCol;
	 
	 //Button Methods
	 @FXML
	 void doSelect(ActionEvent event) {
		 partNameCol.setCellValueFactory(new PropertyValueFactory<>("partName"));
		 partNumberCol.setCellValueFactory(new PropertyValueFactory<>("partNumber"));
		 listPriceCol.setCellValueFactory(new PropertyValueFactory<>("listPrice"));
		 salePriceCol.setCellValueFactory(new PropertyValueFactory<>("salePrice"));
		 onSaleCol.setCellValueFactory(new PropertyValueFactory<>("onSale"));
		 quantityCol.setCellValueFactory(new PropertyValueFactory<>("quantity"));
		 String whName = displayCBOX.getSelectionModel().getSelectedItem();
		 WareHouse wh = allWh.getWhByName(whName);
		 if(wh != null) {
			 ObservableList<Inventory> iList = wh.getIList();
			 ObservableList<Wrapper> displayList = FXCollections.observableArrayList();
			 for(Inventory i : iList) {
				 Wrapper wrapped = new Wrapper(i);
				 displayList.add(wrapped);
			 }
			 displayTable.setItems(displayList);
			 displayTable.refresh();
			 displayCBOX.getSelectionModel().clearSelection();
		 } else {
			 //Do nothing
			 
		 }
		 saveLabel.setVisible(false);
	 }
	 
	 //in Examine Tab
	 //TextFields
	 @FXML
	 private TextField partNameTF;
	 @FXML
	 private TextField locationTF;
	 
	 //Buttons
	 @FXML
	 private Button examineBTN;
	 
	 //TextAreas
	 @FXML
	 private TextArea examineTA;
	 
	 //Button Methods
	 @FXML
	 void doExamine(ActionEvent event) {
		 String location = locationTF.getText();
		 String partName = partNameTF.getText();
		 WareHouse wh = allWh.getWhByName(location);
		 if(wh != null) {
			 Inventory part = wh.findPartByName(partName);
			 if(part == null) {
				 examineTA.setText("Part does not exist.\n");
				 locationTF.setText("");
				 partNameTF.setText("");
			 } else {
				 examineTA.setText("PartName: " + partName + "\n" + "PartNumber: " + part.getBp().getPartNumber() + "\n" + 
						 "ListPrice: " + part.getBp().getListPrice() + "\n" + "SalePrice: " + part.getBp().getSalePrice() + 
						 "\n" + "OnSale: " + part.getBp().isOnSale() + "\n" + "Qnty: " + part.getQuantity() +
						 "\n" + "Location: " + location + "\n");
				 locationTF.setText("");
				 partNameTF.setText("");
			 }
		 } else {
			 examineTA.setText("WareHouse does not exist.\n");
			 locationTF.setText("");
			 partNameTF.setText("");
		 }
		 saveLabel.setVisible(false);
	 }
	 
	 //Initialize Method
	 public void initialize() throws FileNotFoundException {
		welcomeLabel.setText("Welcome, WhMan");
		saveLabel.setVisible(false);
		allWh.initializeAllWh("allWh.txt");
		for(int i = 0; i < allWh.allWhSize(); i++) {
			WareHouse wh = allWh.getAllWh().get(i);
			String whName = wh.getWhName();
			whNames.add(whName);
		}
		displayCBOX.setItems(whNames);
	 }
	 
	 //Variables
	 WhFactory allWh = new WhFactory();
	 ObservableList<String> whNames = FXCollections.observableArrayList();
}
