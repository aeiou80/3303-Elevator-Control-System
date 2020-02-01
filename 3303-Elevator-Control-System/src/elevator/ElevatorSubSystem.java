package elevator;

import floor.FloorDataPacket;
import scheduler.Scheduler;

public class ElevatorSubSystem implements Runnable {

	private Scheduler scheduler;
	private FloorDataPacket info;

	public ElevatorSubSystem(Scheduler s) {
		this.scheduler = s;
	}

	@Override
	public void run() {
		//while (true) {
			info = scheduler.getInfo();
			
			System.out.println("Elevator recieved info from Scheduler: [" + info.getTime() + ", " + info.getFloor()
					+ ", " + info.getFloorButton() + ", " + info.getCarButton() + "]");
			System.out.println(Thread.currentThread().getName() + " is going up to " + info.getFloorButton());
			
			scheduler.sendInfo(info);
		//}
	}
}
