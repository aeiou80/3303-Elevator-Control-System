package floor;

public class FloorButton {
	//status 0 = nothing, 1 = up, 2 = down
	int floorStatus;
		
	public FloorButton() {
		floorStatus = 0;
	}
	
	public void resetFloorStatus() {
		floorStatus = 0;
	}
	public void pressUp() {
		floorStatus = 1;
	}
	public void pressDown() {
		floorStatus = 2;
	}
	public int getFloorStatus() {
		return floorStatus;
	}
}