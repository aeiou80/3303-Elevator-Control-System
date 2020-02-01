package scheduler;

import Something.NotifyFloor;
import Something.WaitTime;
import constants.FloorLevel;
import floor.FloorSubSystem;

public class Scheduler implements Runnable {

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
		this.floorSystem =  floorSystem;
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

	public void handleFloor() {

	}

	@Override
	public void run() {
		while (true) {
			if (elevatorRecieve) {
				elevatorRecieve = false;
			} else if (floorRecieve) {
				floorHandle(floor);
				floorRecieve = false;
			}
			waitTime.defaultTime();
		}
	}

}