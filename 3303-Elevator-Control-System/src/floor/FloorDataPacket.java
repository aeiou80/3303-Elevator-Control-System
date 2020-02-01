package floor;

import java.util.ArrayList;
import Constants.Direction;

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

	public int getFloor() {
		return Integer.parseInt(floor);
	}

	public Direction getFloorButton() {
		if (floorButton.equals("Up")) {
			return Direction.UP;
		} else if (floorButton.equals("Down")) {
			return Direction.DOWN;
		}
		return Direction.IDLE;
	}

	public int getCarButton() {
		return Integer.parseInt(carButton);
	}
}
