package Parts;

public class BikePart {
	//variables
	private String partName;
	private long partNumber;
	private double listPrice;
	private double salePrice;
	private boolean onSale;
		
	//getter and setter methods
	public void setPartName(String partName) {
		this.partName = partName;
	}
	public void setPartNumber(long partNumber) {
		this.partNumber = partNumber;
	}
	public void setListPrice(double listPrice) {
		this.listPrice = listPrice;
	}
	public void setSalePrice(double salePrice) {
		this.salePrice = salePrice;
	}
	public void setOnSale(boolean onSale) {
		this.onSale = onSale;
	}
	public String getPartName() {
		return partName;
	}
	public long getPartNumber() {
		return partNumber;
	}
	public double getListPrice() {
		return listPrice;
	}
	public double getSalePrice() {
		return salePrice;
	}
	public boolean isOnSale() {
		return onSale;
	}
		
	//constructor
	public BikePart(String partName, long partNumber, double listPrice, double salePrice, boolean onSale) {
		setPartName(partName);
		setPartNumber(partNumber);
		setListPrice(listPrice);
		setSalePrice(salePrice);
		setOnSale(onSale);
	}
		
	//toString method
	public String toString() {
		String str = "";
		str += partName + ",";
		str += partNumber + ",";
		str += listPrice + ",";
		str += salePrice + ",";
		str += onSale + ",";
		return str;
	}
		
	//equals method override
	@Override
	public boolean equals(Object o) {
		if(this == o) {
			return true;
		}
		if(o == null || getClass() != o.getClass()) {
			return false;
		}
		BikePart bp = (BikePart) o;
		if(partNumber != bp.getPartNumber()) {
			return false;
		}
		return partName != null ? partName.equals(bp.getPartName()) : bp.partName == null;
	}
	
}
