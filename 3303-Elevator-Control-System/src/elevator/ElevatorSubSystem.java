package elevator;

import constants.WaitTime;
import floor.FloorData;
import scheduler.Scheduler;

/**
 * Class for the elevator sub system that receives information from the
 * scheduler sub system and sends it back
 * 
 * @author Cameron Davis, Jake Cassady
 */

public class ElevatorSubSystem implements Runnable {

	private Scheduler scheduler;
	private FloorData receivedInfo;
	private WaitTime wait;

	/**
	 * constructor for ElevatorSubSystem
	 * 
	 * @param s
	 */
	public ElevatorSubSystem(Scheduler s) {
		this.scheduler = s;
		wait = new WaitTime();
	}

	public FloorData getReceivedInfo() {
		return this.receivedInfo;
	}

	@Override
	public void run() {
		receivedInfo = scheduler.getInfo(); // try to get info from scheduler
		System.out.println(
				"Elevator recieved info from Scheduler: [" + receivedInfo.getTime() + ", " + receivedInfo.getFloor()
						+ ", " + receivedInfo.getFloorButton() + ", " + receivedInfo.getCarButton() + "]");

		wait.defaultTime(); // sleep to slow things down

		scheduler.sendInfo(receivedInfo); // send info

		System.out.println(Thread.currentThread().getName() + " is going " + receivedInfo.getFloorButton() + " to "
				+ receivedInfo.getCarButton());
	}
}
