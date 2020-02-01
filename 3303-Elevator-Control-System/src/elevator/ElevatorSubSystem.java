package elevator;

import Schedular.Scheduler;
import floor.FloorDataPacket;

public class ElevatorSubSystem implements Runnable {

	private int id;
	private Scheduler scheduler;
	private FloorDataPacket info;

	public ElevatorSubSystem(int id, Scheduler s) {
		this.id = id;
		this.scheduler = s;
	}

	@Override
	public void run() {
		// info = scheduler.getInfo();
		System.out.println("Elevator recieved info from Scheduler: [" + info.getTime() + ", " + info.getFloor() + ", "
				+ info.getFloorButton() + ", " + info.getCarButton() + "]");
		System.out.println("Elevator #" + id + " is ready to go to Floor " + info.getFloorButton());
		// scheduler.sendInfo(info);
	}

}
