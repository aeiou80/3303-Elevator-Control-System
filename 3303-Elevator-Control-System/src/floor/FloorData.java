package floor;

import java.util.ArrayList;
import constants.FloorButton;
import constants.FloorLevel;

/**
 * Data structure class used to store necessary information to be passed between
 * sub systems
 * 
 * @author Eric Vincent, John Warde
 */

public class FloorData {

	private String time;
	private String floor;
	private String floorButton;
	private String carButton;

	/**
	 * method to set up data and convert them to other data structures
	 * 
	 * @param data passed in from csv file
	 */
	public void setUp(ArrayList<String> data) {
		if (data.size() != 4) {
			int difference = 4 - data.size();
			for (int i = 0; i < difference; i++) {
				data.add("");
			}
		}
		this.time = data.get(0);
		this.floor = data.get(1);
		this.floorButton = data.get(2);
		this.carButton = data.get(3);
	}

	public String getTime() {
		return time;
	}

	/**
	 * @return floor level
	 */
	public FloorLevel getFloor() {
		switch (Integer.parseInt(floor)) {
		case (1):
			return FloorLevel.Floor1;
		case (2):
			return FloorLevel.Floor2;
		case (3):
			return FloorLevel.Floor3;
		case (4):
			return FloorLevel.Floor4;
		case (5):
			return FloorLevel.Floor5;
		case (6):
			return FloorLevel.Floor6;
		}
		return null;
	}

	/**
	 * @return up or down
	 */
	public FloorButton getFloorButton() {
		if (floorButton.equals("Up")) {
			return FloorButton.UP;
		} else if (floorButton.equals("Down")) {
			return FloorButton.DOWN;
		}
		return null;
	}

	/**
	 * @return destination floor
	 */
	public FloorLevel getCarButton() {
		switch (Integer.parseInt(carButton)) {
		case (1):
			return FloorLevel.Floor1;
		case (2):
			return FloorLevel.Floor2;
		case (3):
			return FloorLevel.Floor3;
		case (4):
			return FloorLevel.Floor4;
		case (5):
			return FloorLevel.Floor5;
		case (6):
			return FloorLevel.Floor6;
		}
		return null;
	}

	public String toString() {
		return "" + this.time + " " + this.floor + " " + this.floorButton + " " + this.carButton;
	}
}
