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
	private FloorData info;
	private List<ArrayList<String>> dataLst;
	private WaitTime wait;

	/**
	 * Constructor method for FloorSubSystem
	 * @param s scheduler
	 */
	public FloorSubSystem(Scheduler s) {
		scheduler = s;
		info = new FloorData();
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
		info.setUp(dataLst.get(0));
	}

	@Override
	public void run() {
		readFile("inputfile.csv");

		System.out.println(info.getFloor() + " requested at time: " + info.getTime() + " to go " + info.getFloorButton()
				+ " to " + info.getCarButton()); // prints data from list of csv inputs

		wait.defaultTime();

		scheduler.sendInfo(info); // send info to scheduler
		FloorData recieveInfo = scheduler.getInfo(); // try to get info back from scheduler

		System.out.println(recieveInfo.getFloor() + " has recieved info from Scheduler: [" + recieveInfo.getTime()
				+ ", " + recieveInfo.getFloor() + ", " + recieveInfo.getFloorButton() + ", "
				+ recieveInfo.getCarButton() + "]");

		wait.defaultTime();

		System.out.println("Elevator has arrived at " + recieveInfo.getCarButton());

	}
}