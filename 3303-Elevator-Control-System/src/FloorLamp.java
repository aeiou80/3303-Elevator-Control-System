
/**
 * FloorLamp class receives information of passenger's choice, up, or down and
 * light the correct lamp. There are two lamps for each floor.
 * 
 * @author Andrew Foster
 */
public class FloorLamp extends Floor {

	private boolean up;
	private boolean down;

	/**
	 * The default constructor creates an object FloorLamp on floor floorNumber.
	 * Sets both directions to false, making the FloorLamp initially inactive.
	 * 
	 * @param floorNumber The floor the lamp is on.
	 */
	public FloorLamp(int floorNumber) {
		super(floorNumber);
		this.up = false;
		this.down = false;
	}

	/**
	 * This constructor creates an object FloorLamp on floor floorNumber, allowing
	 * the user to set the initial direction of the light to either up, down, or
	 * both to check for errors.
	 * 
	 * @param floorNumber The floor the lamp is on.
	 * @param up          The up lamp, true for on, false for off.
	 * @param down        The down lamp, true for on, false for off.
	 */
	public FloorLamp(int floorNumber, boolean up, boolean down) {
		super(floorNumber);
		this.up = up;
		this.down = down;
	}

	/**
	 * Allows the user to set the down lamp on or off
	 * 
	 * @param down The down lamp, true for on, false for off.
	 */
	public void setDown(boolean down) {
		this.down = down;
	}

	/**
	 * Allows the user to set the up lamp on or off.
	 * 
	 * @param up The up lamp, true for on, false for off.
	 */
	public void setUp(boolean up) {
		this.up = up;
	}

	/**
	 * Allows the user to check the current state of the down button.
	 * 
	 * @return The current state of the down button, true for on, flase for off.
	 */
	public boolean getDown() {
		return this.down;
	}

	/**
	 * Allows the user to check the current state of the up button.
	 * 
	 * @return The current state of the up button, true for on, false for off.
	 */
	public boolean getUp() {
		return this.up;
	}
}
