package Parts;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class WareHouse {
	//Variables
	private ObservableList<Inventory> iList;
	private String whName;

	//Constructor
	public WareHouse(String whName) {
		setWhName(whName);
		iList = FXCollections.observableArrayList();
	}

	//Getters and Setters Methods
	public ObservableList<Inventory> getIList() {
		return iList;
	}
	public String getWhName() {
		return whName;
	}
	public void setIList(ObservableList<Inventory> iList) {
		this.iList = iList;
	}
	public void setWhName(String whName) {
		this.whName = whName;
	}

	//Add Inventory Method
	public void addInventory(BikePart bp, int quantity) {
		Inventory found = findInventory(bp);
		System.out.println("found is " + found);
		if(found != null) {
			updateInventory(found, bp, quantity);
		} else {
			iList.add(new Inventory(bp, quantity));
		}
	}

	//Sell Part Method
	public void sellPart(long partNumber) {
		Inventory inv = null;
		for(Inventory i : iList) {
			if(i.getBp().getPartNumber() == partNumber) {
				inv = i;
				break;
			}
		}
		if(inv != null) {
			updateInventory(inv, inv.getBp(), -1);
		}
	}

	//Find Inventory method (BikePart)
	public Inventory findInventory(BikePart bp) {
		for(Inventory inv : iList) {
			if(inv.getBp().equals(bp)) {
				return inv;
			}
		}
		return null;
	}

	//Update Inventory Method
	public void updateInventory(Inventory inv, BikePart bp, int quantity) {
		System.out.println("This is the quantity of updateInventory parameter " + quantity);
		inv.getBp().setListPrice(bp.getListPrice());
		inv.getBp().setSalePrice(bp.getSalePrice());
		inv.getBp().setOnSale(bp.isOnSale());
		System.out.println("Before update: " + inv.getQuantity());
		inv.setQuantity(inv.getQuantity() + quantity);
		System.out.println("After update: " + inv.getQuantity());
	}

	//Find Part by Name method
	public Inventory findPartByName(String partName) {
		for(Inventory inv : iList) {
			if(inv.getBp().getPartName().equals(partName)) {
				return inv;
			}
		}
		return null;
	}

	//find part by number
	public Inventory findPartByNumber(long partNumber) {
		for(Inventory inv : iList) {
			if(inv.getBp().getPartNumber() == partNumber) {
				return inv;
			}
		}
		return null;
	}

	//Size Method
	public int whSize() {
		return iList.size();
	}

	//read WhDB method
	public void readWhDB(String whFileName) throws FileNotFoundException {
		File file = new File(whFileName);
		Scanner read = new Scanner(file);
		while(read.hasNextLine()) {
			String line = read.nextLine();
			String regExp = "\\s*(\\s|,)\\s*";
			String bpi[] = line.split(regExp);
			String partName = bpi[0];
			//System.out.println("hello" + " " + line + " " + line.length() );
			long partNumber = Long.parseLong(bpi[1]);
			double listPrice = Double.parseDouble(bpi[2]);
			double salePrice = Double.parseDouble(bpi[3]);
			boolean onSale = Boolean.parseBoolean(bpi[4]);
			int quantity = Integer.parseInt(bpi[5]);
			BikePart bp = new BikePart(partName, partNumber, listPrice, salePrice, onSale);
			addInventory(bp, quantity);
		}
		read.close();
	}

	//Save WhDB method
	public void saveWhDB(String fileName) throws IOException {
		PrintWriter writer = new PrintWriter(fileName, "UTF-8");
		for(Inventory i : iList) {
			writer.println(i.getBp().getPartName() + "," + i.getBp().getPartNumber() +
					"," + i.getBp().getListPrice() + "," + i.getBp().getSalePrice() +
					"," + i.getBp().isOnSale() + "," + i.getQuantity());
		}
		writer.close();
	}

	//contains part method
	public boolean contains(Inventory inv) {
		for(Inventory i : iList) {
			if(i.getBp().equals(inv.getBp())) {
				return true;
			}
		}
		return false;
	}

}
