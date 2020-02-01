package scheduler;

import constants.WaitTime;
import floor.FloorData;

public class Scheduler implements Runnable {

	private FloorData info;
	private boolean elevatorRecieve;
	private boolean floorRecieve;
	private WaitTime wait;

	public Scheduler() {
		elevatorRecieve = false;
		floorRecieve = false;
		wait = new WaitTime();
	}

	public synchronized void sendInfo(FloorData info) {
		String threadName = Thread.currentThread().getName();

		if (threadName.equals("Floor"))
			floorRecieve = true;
		else if (threadName.equals("Elevator"))
			elevatorRecieve = true;
		else {
			System.out.println("Invalid thread name.");
			System.exit(0);
		}

		this.info = info;
		System.out.println("Scheduler recieved info from: " + threadName + " [" + info.getTime() + ", "
				+ info.getFloor() + ", " + info.getFloorButton() + ", " + info.getCarButton() + "]");

		wait.defaultTime();
		notifyAll();
	}

	public synchronized FloorData getInfo() {
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

		} else if (threadName.equals("Elevator")) {
			while (!floorRecieve) {
				try {
					wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
					System.exit(0);
				}
			}
		}

		return info;
	}

	@Override
	public void run() {

	}

}