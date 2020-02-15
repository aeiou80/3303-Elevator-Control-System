/* 
 * FloorButton class receives information of passenger's choice,
 * up, or down. There are two buttons for each floor.
 */
 
public class FloorButton extends Floor {
	private boolean up;
	private boolean down;
	
	/**
	 *  This default constructor is for creating a FloorButton object on floor floorNumber,
	 *  Sets both directions to false, making the FloorButton initially inactive.
	 * @param floorNumber The floor on which the buttons are on
	 */
	public FloorButton(int floorNumber) {
		super(floorNumber);
		this.up = false;
		this.down = false;
	}
	/**
	 * This constructor creates a floorButton object, giving the use an option to set the initial direction of 
	 * the button to either up, down, or both in order to test for errors.
	 * @param floorNumber The floor on which the buttons are on.
	 * @param up The up button, true for on, false for off.
	 * @param down The down button, true for on, false for off. 
	 */
	public FloorButton(int floorNumber, boolean up, boolean down) {
		super(floorNumber);
		this.up = up;
		this.down = down;
	}
	
	/**
	 * Allows the user to set the down button on or off.
	 * @param down The down button, true for on, false for off.
	 */
	public void setDown(boolean down) {
		this.down = down;
	}
	/**
	 * Allows the user to modify the current state of the up button to on or off.
	 * @param up The up button, true for on, false for off.
	 */
	public void setUp(boolean up) {
		this.up = up;
	}
	/**
	 * Allows the user to check the current state of the down button.
	 * @return The current state of the down button, true for on, false for off.
	 */
	public boolean getDown() {
		return this.down;
	}
	/**
	 * Allows the user to check the current state of the up button.
	 * @return The current state of the up button, true for on, false for off.
	 */
	public boolean getUp() {
		return this.up;
	}
	/**
	 * Allows the user to check if either light is active, need to check for errors in 
	 * later iterations.
	 * @return The current state of either light, true for on, false for off.
	 */
	public boolean isActive() {
		if((this.up == true) || (this.down == true)) {
			return true;
		}
		return false;
		
	}
}
