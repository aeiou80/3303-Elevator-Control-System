import java.io.IOException;
import java.net.DatagramSocket;

/**
 * inforElevatorSystem class is used for scheduler to get and set all
 * information of elevator.
 * 
 * @author Andrew Foster
 */
public class inforElevatorSystem {

	private int elevatorNumber;
	private int currentFloor;
	private String direction;
	private int portNumber;
	DatagramSocket elevatorSocket;

	/**
	 * The default constructor sets the floor to 1 on elevator one.
	 */
	public inforElevatorSystem() {
		this.currentFloor = 1;
		this.elevatorNumber = 1;
		this.direction = null;
	}

	/**
	 * Sets the floor to currentFloor on elevator one.
	 * 
	 * @param currentFloor The current floor the elevator is on.
	 */
	public inforElevatorSystem(int currentFloor) {
		this.currentFloor = currentFloor;
		this.elevatorNumber = 1;
		this.direction = null;
	}

	/**
	 * Sets the floor to currentFloor, on elevator elevatorNumber.
	 * 
	 * @param currentFloor   The current floor the elevator is on.
	 * @param elevatorNumber The elevator number being used.
	 */
	public inforElevatorSystem(int currentFloor, int elevatorNumber) {
		this.currentFloor = currentFloor;
		this.elevatorNumber = elevatorNumber;
		this.direction = null;
	}

	public inforElevatorSystem(int currentFloor, int elevatorNumber, String direction) {
		this.currentFloor = currentFloor;
		this.elevatorNumber = elevatorNumber;
		this.direction = direction;
	}

	public inforElevatorSystem(int currentFloor, int elevatorNumber, int portNumber) {
		this.currentFloor = currentFloor;
		this.elevatorNumber = elevatorNumber;
		this.portNumber = portNumber;
		this.direction = null;
	}

	/**
	 * Allows the user to access the current floor number.
	 * 
	 * @return The current floor number.
	 */
	public void run() {

		try {
			elevatorSocket = new DatagramSocket(portNumber);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public int getCurrentFloor() {
		return currentFloor;
	}

	/**
	 * Allows the user to set the current floor number.
	 * 
	 * @param currentFloor The current floor number.
	 */
	public void setCurrentFloor(int currentFloor) {
		this.currentFloor = currentFloor;
	}

	/**
	 * Allows the user to access the current elevator number.
	 * 
	 * @return The current elevator number.
	 */
	public int getElevatorNumber() {
		return elevatorNumber;
	}

	/**
	 * Allows the user to set the current elevator number.
	 * 
	 * @param elevatorNumber The current elevator number.
	 */
	public void setElevatorNumber(int elevatorNumber) {
		this.elevatorNumber = elevatorNumber;
	}

	public String getDirection() {
		return direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}

	public int getPort() {
		return portNumber;
	}
}