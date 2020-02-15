/*
 * EndState class indicates what will the elevator in end mode do 
 * when it receives signal of a activity.
 */
public class EndState extends StateE {

	public EndState (Elevator ele) {
		super(ele);
	}
	public void moveDoor() {
		elevator.log("Door Doesn't Move");
		
	}
	public void moveElevator() {
		elevator.log("Elevator Doesn't Move");
	}
	

}
