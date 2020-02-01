package scheduler;

import Something.NotifyFloor;
import Something.WaitTime;
import constants.FloorLevel;
import floor.FloorDataPacket;
import floor.FloorSubSystem;

public class Scheduler implements Runnable {

	private FloorDataPacket info;
	private boolean elevatorRecieve;
	private boolean floorRecieve;
	private WaitTime waitTime;
	private FloorLevel floor;
	private FloorSubSystem floorSystem;
	private NotifyFloor notifyFloor;

	public Scheduler() {
		elevatorRecieve = false;
		floorRecieve = false;
		waitTime = new WaitTime();
	}

	public void floorNotification(FloorLevel floor, FloorSubSystem floorSystem) {
		this.floor = floor;
		this.floorSystem = floorSystem;
		notifyFloor = new NotifyFloor(floorSystem);
		floorRecieve = true;
	}

	public void elevatorNotification() {
		elevatorRecieve = true;
	}

	public synchronized void floorHandle(FloorLevel floor) {
		while (!floorRecieve) {
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				return;
			}
		}
		System.out.println(Thread.currentThread().getName() + " has recived signal from " + floor);
		waitTime.defaultTime();
		notifyFloor.illuminatButton(floor);
		notifyAll();
	}

	public synchronized void sendInfo(FloorDataPacket info) {
		String threadName = Thread.currentThread().getName();
		
		if (threadName.equals("Floor"))
			floorRecieve = true;
		else if (threadName.equals("Elevator"))
			elevatorRecieve = true;
		else {
			System.out.println("Invalid thread name.");
			System.exit(0);
		}
		
		while (info == null) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
				System.exit(0);
			}
		}

		this.info = info;
		System.out.println("Scheduler recieved info from: " + threadName + " [" + info.getTime() + ", "
				+ info.getFloor() + ", " + info.getFloorButton() + ", " + info.getCarButton() + "]");
		notifyAll();
	}
	
	public synchronized FloorDataPacket getInfo() {
		String threadName = Thread.currentThread().getName();
		
		if (threadName.equals("Floor")) {
			while (!elevatorRecieve) {
				try {
					wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
					System.exit(0);
				}
			}
			elevatorRecieve = false;
		} else if (threadName.equals("Elevator")) {
			while (!floorRecieve) {
				try {
					wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
					System.exit(0);
				}
			}
			floorRecieve = false;
		}
		
		return info;
	}

	public void handleFloor() {

	}

	@Override
	public void run() {
		while (true) {
			if (elevatorRecieve) {
				elevatorRecieve = false;
			} else if (floorRecieve) {
				//floorHandle(floor);
				floorRecieve = false;
			}
			//waitTime.defaultTime();
		}
	}

}