
/**
 * EndState class indicates how the elevator will react in its "end" state
 * 
 * @author Eric Vincent
 */
public class EndState extends StateE {

	public EndState(Elevator ele) {
		super(ele);
		stateName = "end";
	}

	public void moveDoor() {
		elevator.log("Door Doesn't Move");
	}

	public void moveElevator() {
		elevator.log("Elevator Doesn't Move");
	}

}
