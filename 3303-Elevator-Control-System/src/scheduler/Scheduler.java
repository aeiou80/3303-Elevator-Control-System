package scheduler;

import constants.WaitTime;
import floor.FloorData;

/**
 * Scheduler class used as a communication pipe between the floor and elevator
 * sub systems
 * 
 * @author Cameron Davis, John Warde
 */
public class Scheduler implements Runnable {

	private FloorData info;
	private boolean elevatorRecieve;
	private boolean floorRecieve;
	private WaitTime wait;

	/**
	 * constructor method for Scheduler
	 */
	public Scheduler() {
		elevatorRecieve = false;
		floorRecieve = false;
		wait = new WaitTime();
	}

	/**
	 * method to send info to elevator or floor
	 * 
	 * @param info
	 */
	public synchronized void sendInfo(FloorData info) {
		String threadName = Thread.currentThread().getName();

		if (threadName.equals("Floor"))
			floorRecieve = true;
		else if (threadName.equals("Elevator"))
			elevatorRecieve = true;
		else {
			System.out.println("Invalid thread name.");
			// System.exit(0);
		}

		this.info = info;
		System.out.println("Scheduler recieved info from: " + threadName + " [" + info.getTime() + ", "
				+ info.getFloor() + ", " + info.getFloorButton() + ", " + info.getCarButton() + "]");

		wait.defaultTime();
		notifyAll();
	}

	/**
	 * @return floor data info
	 */
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
				elevatorRecieve = false;
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
			floorRecieve = false;
		}

		return info;
	}

	@Override
	public void run() {

	}

}