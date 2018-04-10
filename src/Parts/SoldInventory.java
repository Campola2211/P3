package Parts;

public class SoldInventory extends Inventory{
	//Variables
	private String formalDate;
	private double totalCost;
	private String customer;
	private long date;

	//constructor
	public SoldInventory(BikePart bp, int quantity, String formalDate, double totalCost, String customer,long date) {
		super(bp, quantity);
		setFormalDate(formalDate);
		setTotalCost(totalCost);
		setCustomer(customer);
		setDate(date);
	}
	//getter and setter methods
	public void setDate(long date) {
		this.date = date;
	}
	public void setTotalCost(double totalCost) {
		this.totalCost = totalCost;
	}
	public void setCustomer(String customer) {
		this.customer = customer;
	}
	public void setFormalDate(String formalDate) {
		this.formalDate = formalDate;
	}
	public long getDate() {
		return date;
	}
	public double getTotalCost() {
		return totalCost;
	}
	public String getCustomer() {
		return customer;
	}
	public String getFormalDate() {
		return formalDate;
	}

	//toString
	public String toString() {
		String str = "";
		str += formalDate;
		return str;
	}
	
}
