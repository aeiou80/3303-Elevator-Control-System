/*
 * abstract class which uses as father state class for elevators states
 */
public abstract class StateE {
	protected static final long FloorToFloorTime = 4000;
	protected static final long OpenDoorTime = 1350;
	protected static final long HoldDoorTime = 5000;
	protected static final long CloseDoorTime = 1650;
    protected Elevator elevator;
    String stateName = null;

    public StateE(Elevator ele) {
        this.elevator = ele;
    }

    public abstract void moveDoor();
    public abstract void moveElevator();
}
