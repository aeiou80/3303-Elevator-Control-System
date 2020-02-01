package floor;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import constants.WaitTime;
import scheduler.Scheduler;

public class FloorSubSystem implements Runnable {

	private Scheduler scheduler;
<<<<<<< Upstream, based on origin/iter_1
	private FloorData info;
	private List<ArrayList<String>> dataLst;
=======
	private FloorData sentInfo;
	private FloorData receivedInfo;
	private List<ArrayList<String>> lst;
>>>>>>> e41aa61 Added tests
	private WaitTime wait;

	/**
	 * Constructor method for FloorSubSystem
	 * @param s scheduler
	 */
	public FloorSubSystem(Scheduler s) {
		scheduler = s;
<<<<<<< Upstream, based on origin/iter_1
		info = new FloorData();
		dataLst = new ArrayList<>();
=======
		sentInfo = new FloorData();
		lst = new ArrayList<>();
>>>>>>> e41aa61 Added tests
		wait = new WaitTime();
	}

	/**
	 * method to parse csv file into array list
	 * @param filename 
	 */
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
				dataLst.add(rowArrayList);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				bufferReader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
<<<<<<< Upstream, based on origin/iter_1
		info.setUp(dataLst.get(0));
=======
		sentInfo.setUp(lst.get(0));
>>>>>>> e41aa61 Added tests
	}
	
	public FloorData getsentInfo() {
		return this.sentInfo;
	}
	
	public FloorData getreceivedInfo() {
		return this.receivedInfo;
	}
	

	@Override
	public void run() {
		readFile("inputfile.csv");

<<<<<<< Upstream, based on origin/iter_1
		System.out.println(info.getFloor() + " requested at time: " + info.getTime() + " to go " + info.getFloorButton()
				+ " to " + info.getCarButton()); // prints data from list of csv inputs
=======
		System.out.println(sentInfo.getFloor() + " requested at time: " + sentInfo.getTime() + " to go " + sentInfo.getFloorButton()
				+ " to " + sentInfo.getCarButton());
		
		wait.defaultTime();

		scheduler.sendInfo(sentInfo); // send info to scheduler
		receivedInfo = scheduler.getInfo(); // try to get info back from scheduler
		
		System.out.println(receivedInfo.getFloor() + " has recieved info from Scheduler: [" + receivedInfo.getTime()
				+ ", " + receivedInfo.getFloor() + ", " + receivedInfo.getFloorButton() + ", "
				+ receivedInfo.getCarButton() + "]");
>>>>>>> e41aa61 Added tests

		wait.defaultTime();

		System.out.println("Elevator has arrived at " + receivedInfo.getCarButton());
		

	}
}