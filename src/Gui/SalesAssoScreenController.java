package Gui;

//GENSALEINVOICE, FIX SELLPART

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import Parts.Inventory;
import Parts.SoldInventory;
import Parts.WareHouse;
import Parts.WhFactory;
import Users.SalesAsso;
import Users.UserFactory;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class SalesAssoScreenController {
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
    void doSaveAll(ActionEvent event) {
		try {
			for(int i = 0; i < allWh.allWhSize(); i++) {
				WareHouse wh = allWh.getWhByNumber(i);
				wh.saveWhDB(wh.getWhName() + ".txt");
			}
			saveLabel.setVisible(true);
		} catch (IOException e) {
			//ADD IO ERROR
		}
    }
    @FXML
    void doLogout(ActionEvent event) throws IOException {
    		salesAsso = null;
    		displayTable.getItems().clear();
    		Parent loginParent = FXMLLoader.load(getClass().getResource("LoginScreen.fxml"));
		Scene loginScene = new Scene(loginParent);
		loginScene.getStylesheets().add(getClass().getResource("loginScreen.css").toExternalForm());
		Stage loginStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		loginStage.hide();
		loginStage.setScene(loginScene);
		loginStage.show();
    }

    //in Sell Tab
    //TextAreas
  	@FXML
      private TextArea sellTA;
  	//TextFields
  	@FXML
    private TextField qtyTF;
  	@FXML
    private TextField partNumTF;
  	@FXML
    private TextField cusTF;

  	//Buttons
  	@FXML
    private Button sellBTN;

	@FXML
    void doSell(ActionEvent event) {
		String customerName = cusTF.getText();
		long partNumber = Long.parseLong(partNumTF.getText());
		int qty = Integer.parseInt(qtyTF.getText());
		System.out.println("Sell quantity is " + qty);
		/*try {
			saleVan.readWhDB(salesAsso.getUserName() + "DB.txt");
		} catch (FileNotFoundException e1) {
			//CATCH
		}*/
		//Inventory found = saleVan.findPartByNumber(partNumber);
		Inventory found = salesAsso.getSaleVan().findPartByNumber(partNumber);
		if(found != null) {
			//System.out.println(saleVan);
			SoldInventory soldPart = salesAsso.sellPart(partNumber, qty, customerName);
			//saleVan = salesAsso.getSaleVan();
			//System.out.println(saleVan);
			/*for(int i = 0; i < saleVan.whSize(); i++) {
				Inventory inv = saleVan.getIList().get(i);
				System.out.println(inv);
			}*/
			//ObservableList<Inventory>iList = saleVan.getIList();
			//ObservableList<Inventory> iList = salesAsso.getSaleVan().getIList();
			ObservableList<Wrapper> wList = FXCollections.observableArrayList();
			for(int j = 0; j < salesAsso.getSaleVan().getIList().size(); j++) {
				Inventory inv2 = salesAsso.getSaleVan().getIList().get(j);
				Wrapper wrapped = new Wrapper(inv2);
				System.out.println("Part after sell: " + salesAsso.getSaleVan().getIList());
				wList.add(wrapped);
			}
			displayTable.getItems().clear();
			soldParts.add(soldPart);
			cusTF.setText("");
			partNumTF.setText("");
			qtyTF.setText("");
			sellTA.appendText("Sold Part: " + soldPart.getBp().getPartName() + ", " + qty + " to " + soldPart.getCustomer() + "\n");
			//System.out.println(saleVan.getIList());
			/*try {
				//saleVan.saveWhDB(saleVan.getWhName() + ".txt");
				String whFile = salesAsso.getUserName() + "DB.txt";
				salesAsso.getSaleVan().saveWhDB(whFile);
			} catch (IOException e) {
				//FILE NOT FOUND ERROR
			}*/
		} else {
			cusTF.setText("");
			partNumTF.setText("");
			qtyTF.setText("");
			sellTA.appendText("Part is not found!\n");
		}
		saveLabel.setVisible(false);
	}

    //in Generate Sales Invoice Tab
    //TextFields
    @FXML
    private TextField customer1TF;
    @FXML
    private TextField date1TF;

    //Buttons
    @FXML
    private Button salesInvoiceBTN;

    //TextAreas
    @FXML
    private TextArea salesInvoiceTA;

    //Button Methods
    @FXML
    void doSalesInvoice(ActionEvent event) {

    }

    //in Load Van Tab
    //TextAreas
    @FXML
    private TextArea fileNameTA;
    @FXML
    private TextArea loadVanTA;

    //Buttons
    @FXML
    private Button selectFileBTN;
    @FXML
    private Button transferBTN;

    //Button Methods
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
    @FXML
    void doTransfer(ActionEvent event) {
    		String fileName = fileNameTA.getText();
    		try{
    			allWh.transfer(fileName);
    			loadVanTA.appendText("Transfer Successful!\n");
    		} catch (IOException e) {
    			//ADD CATCH
    		}
    		System.out.println("Part after transfer: " + salesAsso.getSaleVan().getIList());
    		//saleVan = allWh.getWhByName(salesAsso.getUserName() +"DB");
    		//ObservableList<Inventory>iList = saleVan.getIList();
    		//ObservableList<Inventory> iList = salesAsso.getSaleVan().getIList();
    		//ObservableList<Wrapper> wList = FXCollections.observableArrayList();
    		/*for(int j = 0; j < salesAsso.getSaleVan().getIList().size(); j++) {
    			Inventory inv2 = salesAsso.getSaleVan().getIList().get(j);
    			Wrapper wrapped = new Wrapper(inv2);
    			wList.add(wrapped);
    		}
    		try {
    			String whFile = salesAsso.getUserName() + "DB.txt";
    			salesAsso.getSaleVan().saveWhDB(whFile);
				//saleVan.saveWhDB(saleVan.getWhName() + ".txt");
			} catch (IOException e) {
				//FILE NOT FOUND ERROR
			}*/
    		displayTable.getItems().clear();
    		saveLabel.setVisible(false);
    }

    //in Display Tab
    //Buttons
    @FXML
    private Button showDataBTN;

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
    void doShowData(ActionEvent event) {
    		//System.out.println(salesAsso.getUserName());
    		//System.out.println(salesAsso.getSaleVan().getIList().get(0));
    		ObservableList<Inventory>iList = salesAsso.getSaleVan().getIList();
    		ObservableList<Wrapper> wList = FXCollections.observableArrayList();
    		for(int j = 0; j < iList.size(); j++) {
    			Inventory inv2 = iList.get(j);
    			Wrapper wrapped = new Wrapper(inv2);
    			wList.add(wrapped);
    		}
    		System.out.println(wList);
    		if((displayTable.getItems().containsAll(wList)) == false) {
    			displayTable.setItems(wList);
        		displayTable.refresh();
    		}
    		saveLabel.setVisible(false);
    }

    //Initialize Method
    public void initialize() throws FileNotFoundException {
    	welcomeLabel.setText("Welcome, SalesAsso");
    	saveLabel.setVisible(false);
    	partNameCol.setCellValueFactory(new PropertyValueFactory<>("partName"));
    	partNumberCol.setCellValueFactory(new PropertyValueFactory<>("partNumber"));
    	listPriceCol.setCellValueFactory(new PropertyValueFactory<>("listPrice"));
    	salePriceCol.setCellValueFactory(new PropertyValueFactory<>("salePrice"));
    	onSaleCol.setCellValueFactory(new PropertyValueFactory<>("onSale"));
    	quantityCol.setCellValueFactory(new PropertyValueFactory<>("quantity"));
    	allWh.initializeAllWh("allWh.txt");
    }

    //salesAsso Var
    static SalesAsso salesAsso = null;
    static ObservableList<SoldInventory> soldParts = FXCollections.observableArrayList();
    //static WareHouse saleVan = null;
    static UserFactory userList = new UserFactory();
    static WhFactory allWh = new WhFactory();

    //initSalesAsso Method
    public void initSalesAsso(SalesAsso user) {
    		System.out.println("Controllers are Succesfully linked!");
    		if(user != null) {
    			salesAsso = user;
    			//saleVan = salesAsso.getSaleVan();
    			//System.out.println(user.getUserName());
    			String whFileName = user.getUserName() + "DB.txt";
    			try {
    				userList.readUserFile("allUsers.txt");
    			} catch (FileNotFoundException e) {
    				System.out.println("allUsers.txt is not found.");
    			}
    			salesAsso.setSalesVan(allWh.getWhByName(salesAsso.getUserName() + "DB"));
    			System.out.println("Initial part: " + salesAsso.getSaleVan().getIList());
    			/*for(int i = 0; i < saleVan.whSize(); i++) {
    				Inventory inv = saleVan.getIList().get(i);
    				System.out.println(inv);
    			}*/
    		} else {
    			System.out.println("salesAsso = null");
    		}
    }

}
