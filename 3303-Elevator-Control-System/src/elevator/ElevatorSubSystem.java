package elevator;

import constants.WaitTime;
import floor.FloorData;
import scheduler.Scheduler;

public class ElevatorSubSystem implements Runnable {

	private Scheduler scheduler;
	private FloorData info;
	private WaitTime wait;

	/**
	 * Constructor Class for ElevatorSubSystem
	 * @param s
	 */
	public ElevatorSubSystem(Scheduler s) {
		this.scheduler = s;
		wait = new WaitTime();
	}

	@Override
	public void run() {
		info = scheduler.getInfo();
		System.out.println("Elevator recieved info from Scheduler: [" + info.getTime() + ", " + info.getFloor() + ", "
				+ info.getFloorButton() + ", " + info.getCarButton() + "]");  // prints data passed in from scheduler

		wait.defaultTime();

		scheduler.sendInfo(info);

		System.out.println(
				Thread.currentThread().getName() + " is going " + info.getFloorButton() + " to " + info.getCarButton());
	}
}
