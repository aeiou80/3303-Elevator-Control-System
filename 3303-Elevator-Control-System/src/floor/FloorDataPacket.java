package floor;

import java.util.ArrayList;
import constants.FloorButton;
import constants.FloorLevel;

public class FloorDataPacket {

	private String time;
	private String floor;
	private String floorButton;
	private String carButton;

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

	public FloorButton getFloorButton() {
		if (floorButton.equals("Up")) {
			return FloorButton.UP;
		} else if (floorButton.equals("Down")) {
			return FloorButton.DOWN;
		}
		return FloorButton.IDLE;
	}

	public FloorLevel getCarButton() {
		int temp = Integer.parseInt(carButton);
		if (temp == 1) {
			return FloorLevel.Floor1;
		} else if (temp == 2) {
			return FloorLevel.Floor2;
		} else if (temp == 3) {
			return FloorLevel.Floor3;
		} else if (temp == 4) {
			return FloorLevel.Floor4;
		} else if (temp == 5) {
			return FloorLevel.Floor5;
		} else if (temp == 6) {
			return FloorLevel.Floor6;
		}
		return null;
	}
}
