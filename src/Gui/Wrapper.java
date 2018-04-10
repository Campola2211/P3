package Gui;

import Parts.Inventory;

public class Wrapper {
	//Variables
	private String partName;
	private String partNumber;
	private String listPrice;
	private String salePrice;
	private String onSale;
	private String quantity;
	
	//Constructor
	public Wrapper(Inventory i) {
		String pName = i.getBp().getPartName();
		String pNum = Long.toString(i.getBp().getPartNumber());
		String pLP = Double.toString(i.getBp().getListPrice());
		String pSP = Double.toString(i.getBp().getSalePrice());
		String pBo = Boolean.toString(i.getBp().isOnSale());
		String pQ = Integer.toString(i.getQuantity());
		this.partName = pName;
		this.partNumber = pNum;
		this.listPrice = pLP;
		this.salePrice = pSP;
		this.onSale = pBo;
		this.quantity = pQ;
	}
	//Setters and Getter methods
	public String getPartName() {
		return partName;
	}
	public String getPartNumber() {
		return partNumber;
	}
	public String getListPrice() {
		return listPrice;
	}
	public String getSalePrice() {
		return salePrice;
	}
	public String getOnSale() {
		return onSale;
	}
	public String getQuantity() {
		return quantity;
	}
	public void setPartName(String partName) {
		this.partName = partName;
	}
	public void setPartNumber(long partNumber) {
		this.partNumber = String.valueOf(partNumber);
	}
	public void setListPrice(double listPrice) {
		this.listPrice = String.valueOf(listPrice);
	}
	public void setSalePrice(double salePrice) {
		this.salePrice = String.valueOf(salePrice);
	}
	public void setOnSale(boolean onSale) {
		this.onSale = String.valueOf(onSale);
	}
	public void setQuantity(int quantity) {
		this.quantity = String.valueOf(quantity);
	}
	
	//toString method
	public String toString() {
		String str = "";
		str += partName + "," + partNumber + "," + listPrice + "," + salePrice + "," + onSale + "," + quantity;
		return str;
	}
}
