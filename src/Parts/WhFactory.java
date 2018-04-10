package Parts;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class WhFactory {
	//Variables
	private ObservableList<WareHouse> allWh;

	//Constructor
	public WhFactory() {
		allWh = FXCollections.observableArrayList();
	}

	//Getter Method
	public ObservableList<WareHouse> getAllWh() {
		return allWh;
	}

	//Get WareHouse by Name Method
	public WareHouse getWhByName(String whName) {
		for(WareHouse wh : allWh) {
			if(wh.getWhName().equals(whName)) {
				return wh;
			}
		}
		return null;
	}

	//Get WareHouse by Number Method
	public WareHouse getWhByNumber(int i) {
		return allWh.get(i);
	}

	//Add WareHouse Method
	public void addWareHouse(WareHouse wh) {
		WareHouse found = getWhByName(wh.getWhName());
		if(found == null) {
			allWh.add(wh);
		}
	}

	//AllWh Size Method
	public int allWhSize() {
		return allWh.size();
	}

	//Save AllWhNames Method
	public void saveAllWhNames(String fileName) throws IOException {
		PrintWriter writer = new PrintWriter(fileName, "UTF-8");
		for(WareHouse wh : allWh) {
			String whName = wh.getWhName();
			writer.println(whName);
		}
		writer.close();
	}

	//Save All WareHouses Method
	public void saveAllWh() throws IOException {
		for(WareHouse wh : allWh) {
			String whFileName = wh.getWhName() + ".txt";
			PrintWriter writer = new PrintWriter(whFileName, "UTF-8");
			for(int i = 0; i < wh.getIList().size(); i++) {
				writer.println(wh.getIList().get(i));
			}
			writer.close();
		}
	}

	//initialize allWh method
	public void initializeAllWh(String fileName) throws FileNotFoundException {
		File file = new File(fileName);
		Scanner read = new Scanner(file);
		while(read.hasNextLine()) {
			String whName = read.nextLine();
			WareHouse wh = new WareHouse(whName);
			String whFileName = wh.getWhName() + ".txt";
			//System.out.println(whFileName);
			wh.readWhDB(whFileName);
			allWh.add(wh);
		}
		read.close();
	}

	//Transfer method
	public void transfer(String fileName) throws FileNotFoundException {
		File file = new File(fileName);
		Scanner read = new Scanner(file);
		String nameLine = read.nextLine();
		String whNames[] = nameLine.split(",");
		WareHouse whInitial = getWhByName(whNames[0]);
		WareHouse whDestination = getWhByName(whNames[1]);
		while(read.hasNextLine()) {
			String invLine = read.nextLine();
			String iA[] = invLine.split(",");
			BikePart bp = whInitial.findPartByName(iA[0]).getBp();
			whDestination.addInventory(bp, Integer.parseInt(iA[1]));
			int iQty = whInitial.findInventory(bp).getQuantity();
			System.out.println("Transfer quantity is " + (Integer.parseInt(iA[1])));
			whInitial.findPartByName(bp.getPartName()).setQuantity(iQty - (Integer.parseInt(iA[1])));
		}
		/*try{
			String whInitialFile = whInitial.getWhName() + ".txt";
			String whDestinationFile = whDestination.getWhName() + ".txt";
			whInitial.saveWhDB(whInitialFile);
			whDestination.saveWhDB(whDestinationFile);
		} catch (IOException e) {
			//ADD CATCH
		}*/
		read.close();
	}

}
