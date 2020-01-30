import java.util.ArrayList;

public class FloorDataPacket {

		private String time;
		private String floor;
		private String floorButton;
		private String carButton;
		
		public FloorDataPacket(ArrayList<String> data) {
			if(data.size() != 4) {
				int difference = 4 - data.size();
				for(int i = 0; i < difference; i++) {
					data.add("");
				}
			}
			this.time = data.get(0);
			this.floor = data.get(1);
			this.floorButton = data.get(2);
			this.carButton =  data.get(3);
			
			
		}
		
		public String getTime() {
			return time;
		}


		public String getFloor() {
			return floor;
		}


		public String getFloorButton() {
			return floorButton;
		}


		public String getCarButton() {
			return carButton;
		}

		public String toString() {
			String value = "{" + this.time + "," + this.floor + "," + this.floorButton + "," + this.carButton + "}";
			return value;
		}
	}

