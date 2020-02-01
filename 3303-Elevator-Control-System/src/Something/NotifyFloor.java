package Something;

import constants.FloorLevel;
import floor.FloorSubSystem;

public class NotifyFloor {
	
	private FloorSubSystem floorSystem;
	private WaitTime waitTime;
	
	public NotifyFloor(FloorSubSystem floorSystem) {
		this.floorSystem = floorSystem;
		waitTime = new WaitTime();
	}
	
	public synchronized void illuminatButton(FloorLevel floor) {
		System.out.println(Thread.currentThread().getName() + " is sending signal back to " + floor + " to illuminate floor Button");
		floorSystem.schedulerNotif();
		notifyAll();
		
	}
	

}
