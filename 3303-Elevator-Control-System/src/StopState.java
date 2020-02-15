/*
 * StopState class indicates what will the elevator in stop mode do 
 * when it receives signal of a activity.
 */
public class StopState extends StateE {

	/*
	 * constructor of StopState
	 * @param ele :an elevator 
	 */
	public StopState (Elevator ele) {
		super(ele);
	}
	
	/*
	 * action of the door
	 */
	public void moveDoor() {
		elevator.setElevatorMoving(false);
		try {
			elevator.log(Thread.currentThread().getName() + " Open the Door");
			elevator.setDoor(true);
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
	
	/*
	 * action of elevator
	 */
	public void moveElevator() {
		elevator.log(Thread.currentThread().getName() + " Don't Move");
	}
	

}
