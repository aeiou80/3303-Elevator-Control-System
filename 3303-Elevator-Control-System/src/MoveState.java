/*
 * MoveState class indicates what will the elevator in move mode do 
 * when it receives signal of a activity.
 */
public class MoveState extends StateE {

	public MoveState(Elevator ele) {
		super(ele);
		stateName = "moving";
	}

	public void moveDoor() {
		elevator.log("Door Doesn't Move");
	}

	public void moveElevator() {
		if(elevator.getMotor()>0) {
			elevator.log(Thread.currentThread().getName() + " Move Up");
		}
		else {
			elevator.log(Thread.currentThread().getName() + " Move Down");
		}
		try {
			Thread.sleep(FloorToFloorTime/4);
			elevator.log("...");
			Thread.sleep(FloorToFloorTime/4);
			elevator.log("...");
			Thread.sleep(FloorToFloorTime/4);
			elevator.log("...");
			Thread.sleep(FloorToFloorTime/4);
			elevator.setFloorSensor(elevator.getButton());
			elevator.log(Thread.currentThread().getName() + " Move To " + elevator.getFloorSensor() + " Floor");
			Thread.sleep(FloorToFloorTime/2);
		} catch (InterruptedException e) {
			elevator.log(Thread.currentThread().getName() + " Movement: Sleep() is Going Wrong");
			e.printStackTrace();
		}
		
	}
}
