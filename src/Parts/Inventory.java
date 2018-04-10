package Parts;

public class Inventory {
	//Variables
	private BikePart bp;
	private int quantity;
	private int minQuantity = 5;

	//setters and getters
	public void setBp(BikePart bp) {
		this.bp = bp;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public BikePart getBp() {
		return bp;
	}
	public int getQuantity() {
		return quantity;
	}
	public int getMinQuantity() {
		return minQuantity;
	}

	//constructor
	public Inventory(BikePart bp, int quantity) {
		setBp(bp);
		setQuantity(quantity);
	}

	//toString method
	/*
	public String toString() {
		String str = "";
		str += bp;
		str += quantity;
		return str;
	}
	*/
}
