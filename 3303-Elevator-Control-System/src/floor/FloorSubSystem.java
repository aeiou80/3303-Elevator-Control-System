package floor;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import Something.NotifyScheduler;
import Something.WaitTime;
import scheduler.Scheduler;

public class FloorSubSystem implements Runnable {

	private FloorDataPacket newPacket;
	private List<ArrayList<String>> lst;
	private FloorQueue fQ;
	private NotifyScheduler notifySched;
	private WaitTime waitTime;
	private FloorButton buttons;
	private FloorLamp lamp;
	private boolean light;
	private Scheduler scheduler;

	public FloorSubSystem(Scheduler s) {
		scheduler = s;
		buttons = new FloorButton();
		lamp = new FloorLamp();
		newPacket = new FloorDataPacket();
		lst = new ArrayList<>();
		fQ = new FloorQueue();
		notifySched = new NotifyScheduler(s);
		waitTime = new WaitTime();
		light = false;
	}

	public void schedulerNotif() {
		light = true;
	}

	public synchronized void illuminateBtn() {
		while (!light) {
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out.println(Thread.currentThread().getName());
		System.out.println("The " + fQ.getDirectionQ().peek() + " Button on " + fQ.getBaseFloorQ().peek()
				+ " has been illuminated");
		notifyAll();
	}

	public FloorDataPacket readFile(String filename) {
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
		} finally {
			try {
				bufferReader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		FloorDataPacket info = new FloorDataPacket();
		info.setUp(lst.get(0));
		return info;
		// setFloorQueue();
	}

	public void setFloorQueue() {
		for (int i = 0; i < lst.size(); i++) {
			newPacket.setUp(lst.get(i));
			fQ.setTime(newPacket.getTime());
			fQ.setBaseFloor(newPacket.getFloor());
			fQ.setFloorBtn(newPacket.getFloorButton());
			fQ.setDestFloor(newPacket.getCarButton());
		}
	}

	public boolean buttonPressed() {
		if (fQ.getBaseFloorQ().isEmpty()) {
			return false;
		}
		return true;
	}

	public void handlePress() {
		System.out.println("Time is " + fQ.getTimeQ().poll());
		waitTime.defaultTime();
		notifySched.PassengerPos(fQ.getBaseFloorQ().peek(), this);

	}

	@Override
	public void run() {
		FloorDataPacket sendInfo = readFile("inputfile.csv");
		System.out.println(sendInfo.getFloor() + " requested at time: " + sendInfo.getTime() + " to go "
				+ sendInfo.getFloorButton() + " to " + sendInfo.getCarButton());
		scheduler.sendInfo(sendInfo);
		FloorDataPacket recieveInfo = scheduler.getInfo();
		System.out.println("Elevator has arrived at " + recieveInfo.getFloor());
		// while (true) {

//			if (buttonPressed()) {
//				handlePress();
//				illuminateBtn();
//			}
		// }

	}
}