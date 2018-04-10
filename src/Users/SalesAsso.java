package Users;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import Parts.Inventory;
import Parts.SoldInventory;
import Parts.WareHouse;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class SalesAsso extends Login{
	//Variables
	private WareHouse saleVan;
	private ObservableList<SoldInventory> soldParts;
	private ObservableList<Long> dates;

	//Constructor
	public SalesAsso(String userName, String password, Person person, String emailAddress) {
		super(userName, password, person, emailAddress);
		String salevanName = userName + "DB";
		saleVan = new WareHouse(salevanName);
		soldParts = FXCollections.observableArrayList();
		dates = FXCollections.observableArrayList();
	}

	//getter and Setter methods
	public WareHouse getSaleVan() {
		return saleVan;
	}
	public ObservableList<SoldInventory> getSoldParts() {
		return soldParts;
	}
	public ObservableList<Long> getDates() {
		return dates;
	}
	public void setSalesVan(WareHouse wh) {
		saleVan = wh;
	}
	public void setSaleVan(WareHouse saleVan) {
		this.saleVan = saleVan;
	}

	//Sold Part Method
	public SoldInventory sellPart(long partNumber, int quantity, String customerName) {
		for(int i = 0; i < saleVan.whSize(); i++) {
			Inventory inv = saleVan.getIList().get(i);
			System.out.println(inv);
			if(inv.getBp().getPartNumber() == partNumber) {
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH:mm");
				LocalDateTime dateTime = LocalDateTime.now();
				String fullDate = dateTime.format(formatter);
				String formalDate = dateConversion(fullDate);
				String line = fullDate.replace("-", "");
				line = line.replace(":", "");
				long range = Long.parseLong(line);
				System.out.println("Minus quanity is " + -quantity);
				saleVan.updateInventory(inv, inv.getBp(), -quantity);
				double price = 0;
				if(inv.getBp().isOnSale() == true) {
					price = inv.getBp().getSalePrice();
				} else {
					price = inv.getBp().getListPrice();
				}
				double totalCost = price * quantity;
				SoldInventory soldInv = new SoldInventory(inv.getBp(), inv.getQuantity(), formalDate, totalCost, customerName, range);
				soldParts.add(soldInv);
				return soldInv;
			}
		}
		return null;
	}

	//Generate Sales Invoice Method
	//FINISH GENERATE SALES INVOICE METHOD
	public void genSalesInvoice(String customerName, String date) {
		double total = 0;
		System.out.println("Sales Invoice for " + customerName + " " + date);
		System.out.printf("%s        %s    %s   %s  %s     %s\n", "Part Name", "Part Number", "Price", "Sales Price", "Qnty", "Total Cost");
		for(int i = 0; i < soldParts.size(); i++) {
			SoldInventory soldInv = soldParts.get(i);
			String partName = soldInv.getBp().getPartName();
			String partNumber = String.valueOf(soldInv.getBp().getPartNumber());
			String price = String.valueOf(soldInv.getBp().getListPrice());
			String salesPrice = String.valueOf(soldInv.getBp().getSalePrice());
			String qnty = String.valueOf(soldInv.getQuantity());
			String totalCost = String.valueOf(soldInv.getTotalCost());
			System.out.printf("%s              %s     %s     %s     %s        %s\n", partName, partNumber, price, salesPrice, qnty, totalCost);
			total += Double.parseDouble(totalCost);
		}
		System.out.println("\n                                                                " + total);
	}

	//Date Conversion Method
	public String dateConversion(String date) {
		String newDate[] = date.split("-");
		String str = "";
		switch(newDate[1]) {
			case "01":
				str += "January ";
				break;
			case "02":
				str += "Febuary ";
				break;
			case "03":
				str += "March ";
				break;
			case "04":
				str += "April ";
				break;
			case "05":
				str += "May ";
				break;
			case "06":
				str += "June ";
				break;
			case "07":
				str += "July ";
				break;
			case "08":
				str += "August ";
				break;
			case "09":
				str += "September ";
				break;
			case "10":
				str += "October ";
				break;
			case "11":
				str += "November ";
				break;
			case "12":
				str += "December ";
				break;
		}
		str += newDate[2] +", ";
		str += newDate[0] + " at ";
		String clock[] = newDate[3].split(":");
		int hour = Integer.parseInt(clock[0]);
		int min = Integer.parseInt(clock[1]);
		if(hour < 13) {
			str += hour + ":" + min + " AM";
		} else {
			str += hour + ":" + min + "PM";
		}
		return str;
	}

}
