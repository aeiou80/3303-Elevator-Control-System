package FloorSubsystem;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FloorSubSystem implements Runnable {
	
	private FloorDataPacket newPacket;
	private List<ArrayList<String>> lst;

	public FloorSubSystem() {
		lst = new ArrayList<>();
	}

	public void sendPacket(FloorDataPacket packet) {

	}

	public void readFile(String filename) {
		FileReader fileReader = null;
		BufferedReader bufferReader = null;
		try {
			String row = "";
			fileReader = new FileReader(filename);
			bufferReader = new BufferedReader(fileReader);
			row = bufferReader.readLine();
			while ((row = bufferReader.readLine()) != null) {
				String[] rowArray = row.split(",");
				ArrayList<String> rowArrayList = new ArrayList<>();
				Collections.addAll(rowArrayList, rowArray);
				lst.add(rowArrayList);
				//newPacket = new FloorDataPacket(lst.get(0));
				//System.out.println(newPacket);
				//sendPacket(newPacket);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		finally
        {
            try {
            	bufferReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
		
		System.out.println(lst.get(1).get(2));
		//newPacket = new FloorDataPacket();
	//	newPacket.setUp(lst.get(0));
	}
	
	public void setFloorQueue() {
		newPacket = new FloorDataPacket();
		for(int i = 0; i < lst.size(); i++) {
			newPacket.setUp(lst.get(i));
			
		}
		
	}
	
	public boolean CheckButton() {
		return false;
		
	}

	@Override
	public void run() {
		readFile("inputfile.csv");
		while(true) {
			CheckButton();
		}
		

	}

	//public static void main(String[] args) {
	//	Thread floor = new Thread(new FloorSubSystem());
	//	floor.start();

	//}
}
