
/**
 * StopState class indicates how the elevator will react in its stop state when
 * it receives signal of passenger activity.
 * 
 * @author Andrew Foster
 */
public class StopState extends StateE {

	/**
	 * StopState constructor
	 * 
	 * @param ele an elevator
	 */
	public StopState(Elevator ele) {
		super(ele);
		stateName = "stopped";
	}

	/**
	 * Elevator door reacts accordingly in the Stop state
	 */
	public void moveDoor() {
		try {
			elevator.log(Thread.currentThread().getName() + " Open the Door");
			Thread.sleep(OpenDoorTime);
			elevator.log(Thread.currentThread().getName() + " Hold the Door");
			Thread.sleep(HoldDoorTime);
			elevator.log(Thread.currentThread().getName() + " Close the Door");
			Thread.sleep(CloseDoorTime);
		} catch (InterruptedException e) {
			elevator.log("Door Movement: Sleep() is Going Wrong");
			e.printStackTrace();
		}
	}

	/**
	 * Elevator movement reacts accordingly in the Stop state
	 */
	public void moveElevator() {
		elevator.log(Thread.currentThread().getName() + " Don't Move");
	}

}
