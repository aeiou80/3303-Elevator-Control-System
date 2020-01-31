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
	private FloorQueue fQ;

	public FloorSubSystem() {
		newPacket = new FloorDataPacket();
		lst = new ArrayList<>();
		fQ = new FloorQueue();
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
	}
	
	public void setFloorQueue() {
		for(int i = 0; i < lst.size(); i++) {
			newPacket.setUp(lst.get(i));
			fQ.addTime(newPacket.getTime());
			fQ.addBaseFloor(newPacket.getFloor());
			fQ.addDirecQueue(newPacket.getFloorButton());
			fQ.addDestFloor(newPacket.getCarButton());
		}
	}
	
	public boolean buttonPressed() {
		if(fQ.getBaseFloorQ().isEmpty()) {
			return false;
		}
		return true;
	}
	
	public void handlePress() {
		System.out.println("time is " + fQ.getTimeQ().poll());
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("Passenger is on " + Thread.currentThread().getName() + " " + fQ.getBaseFloorQ().poll());
	}

	@Override
	public void run() {
		readFile("inputfile.csv");
		setFloorQueue();
		while(true) {
			if(buttonPressed()) {
				handlePress();
			}
		}
		

	}
}
