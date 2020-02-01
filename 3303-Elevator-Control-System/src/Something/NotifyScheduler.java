package Something;


import constants.FloorLevel;
import floor.FloorSubSystem;
import scheduler.Scheduler;

public class NotifyScheduler {
	private Scheduler scheduler;
	private WaitTime waitTime;

	public NotifyScheduler(Scheduler scheduler) {
		this.scheduler = scheduler;
		waitTime = new WaitTime();
	}

	public synchronized void PassengerPos(FloorLevel floor, FloorSubSystem floorSystem) {
		System.out.println("Passenger is on " + floor);
		scheduler.floorNotification(floor, floorSystem);
		waitTime.defaultTime();
		notifyAll();
	}

}