package floor;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import constants.WaitTime;
import scheduler.Scheduler;

/**
 * Floor sub system class that produces instructions for the scheduler
 * 
 * @author Eric Vincent, Andrew Foster
 */

public class FloorSubSystem implements Runnable {

	private Scheduler scheduler;
	private List<ArrayList<String>> dataLst;

	private FloorData sentInfo;
	private FloorData receivedInfo;

	private WaitTime wait;

	/**
	 * Constructor method for FloorSubSystem
	 * @param s scheduler
	 */
	public FloorSubSystem(Scheduler s) {
		scheduler = s;

		sentInfo = new FloorData();
		dataLst = new ArrayList<>();

		sentInfo = new FloorData();
		dataLst = new ArrayList<>();

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

		sentInfo.setUp(dataLst.get(0));
	}
	
	/**
	 * @return FloorData that was sent to the scheduler
	 */
	public FloorData getsentInfo() {
		return this.sentInfo;
	}
	
	/**
	 * @return FloorData that was received from the scheduler
	 */
	public FloorData getreceivedInfo() {
		return this.receivedInfo;
	}
	

	@Override
	public void run() {
		readFile("inputfile.csv");
		System.out.println(sentInfo.getFloor() + " requested at time: " + sentInfo.getTime() + " to go " + sentInfo.getFloorButton()
				+ " to " + sentInfo.getCarButton()); // prints data from list of csv inputs

		
		wait.defaultTime();

		scheduler.sendInfo(sentInfo); // send info to scheduler
		receivedInfo = scheduler.getInfo(); // try to get info back from scheduler
		
		System.out.println(receivedInfo.getFloor() + " has recieved info from Scheduler: [" + receivedInfo.getTime()
				+ ", " + receivedInfo.getFloor() + ", " + receivedInfo.getFloorButton() + ", "
				+ receivedInfo.getCarButton() + "]");


		wait.defaultTime();

		System.out.println("Elevator has arrived at " + receivedInfo.getCarButton());
		

	}
}