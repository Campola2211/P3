package Users;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import Parts.BikePart;
import Parts.Inventory;
import Parts.WareHouse;
import Parts.WhFactory;

public class WhMan extends Login{
	//Variables
	WareHouse mainWh;
	
	//Constructor
	public WhMan(String userName, String password, Person person, String emailAddress) {
		super(userName, password, person, emailAddress);
		mainWh = new WareHouse("mainWh");
	}
	
	//Getter Method
	public WareHouse getMainWh() {
		return mainWh;
	}
	
	//Read and Update Main WH
	public void readMainWh(String fileName) throws FileNotFoundException {
		File file = new File(fileName);
		Scanner read = new Scanner(file);
		while(read.hasNextLine()) {
			String line = read.nextLine();
			String array[] = line.split(",");
			String partName = array[0];
			long partNumber = Long.parseLong(array[1]);
			double listPrice = Double.parseDouble(array[2]);
			double salePrice = Double.parseDouble(array[3]);
			boolean onSale = Boolean.parseBoolean(array[4]);
			int quantity = Integer.parseInt(array[5]);
			BikePart bp = new BikePart(partName, partNumber, listPrice, salePrice, onSale);
			Inventory inv = new Inventory(bp, quantity);
			mainWh.updateInventory(inv, bp, quantity);
		}
		read.close();
	}
	
	//Examine Part by Name Method
	public String examineByName(String whName, WhFactory allWh, String partName) {
		String str = "";
		WareHouse wh = allWh.getWhByName(whName);
		Inventory found = wh.findPartByName(partName);
		if(found != null) {
			str += "Location: " + wh.getWhName() + "\nPartName: " + found.getBp().getPartName()
					+ "\nPartNumber: " + found.getBp().getPartNumber() + "\nListPrice: " + found.getBp().getListPrice()
					+ "\nSalePrice: " + found.getBp().getSalePrice() + "\nOnSale: " + found.getBp().isOnSale();
			return str;
		}
		return null;
	}
	
	//Examine Part by Number Method
	public String examineByNumber(String whName, WhFactory allWh, long partNumber) {
		String str = "";
		for(int i = 0; i < allWh.allWhSize(); i++) {
			WareHouse wh = allWh.getWhByNumber(i);
			for(int j = 0; j < wh.whSize(); j++) {
				Inventory found = wh.getIList().get(j);
				if(found.getBp().getPartNumber() == partNumber) {
					str += "Location: " + wh.getWhName() + "\nPartName: " + found.getBp().getPartName()
							+ "\nPartNumber: " + found.getBp().getPartNumber() + "\nListPrice: " + found.getBp().getListPrice()
							+ "\nSalePrice: " + found.getBp().getSalePrice() + "\nOnSale: " + found.getBp().isOnSale();
					return str;
				}
			}
		}
		return null;
	}
	
	//Examine All Parts by Name Method
	public String examineAllWhByName(WhFactory allWh, String partName) {
		String str = "";
		for(int i = 0; i < allWh.allWhSize(); i++) {
			WareHouse wh = allWh.getWhByNumber(i);
			for(int j = 0; j < wh.whSize(); j++) {
				Inventory found = wh.getIList().get(j);
				if(found.getBp().getPartName().equals(partName)) {
					str += "Location: " + wh.getWhName() + "\nPartName: " + found.getBp().getPartName()
							+ "\nPartNumber: " + found.getBp().getPartNumber() + "\nListPrice: " + found.getBp().getListPrice()
							+ "\nSalePrice: " + found.getBp().getSalePrice() + "\nOnSale: " + found.getBp().isOnSale();
					str += "\n";
					return str;
				}
			}
		}
		return null;
	}
	
	//Examine All Parts by Number Method
	public String examineAllWhByNumber(WhFactory allWh, long partNumber) {
		String str = "";
		for(int i = 0; i < allWh.allWhSize(); i++) {
			WareHouse wh = allWh.getWhByNumber(i);
			for(int j = 0; j < wh.whSize(); j++) {
				Inventory found = wh.getIList().get(j);
				if(found.getBp().getPartNumber() == partNumber) {
					str += "Location: " + wh.getWhName() + "\nPartName: " + found.getBp().getPartName()
							+ "\nPartNumber: " + found.getBp().getPartNumber() + "\nListPrice: " + found.getBp().getListPrice()
							+ "\nSalePrice: " + found.getBp().getSalePrice() + "\nOnSale: " + found.getBp().isOnSale();
					str += "\n";
					return str;
				}
			}
		}
		return null;
	}
	
}
