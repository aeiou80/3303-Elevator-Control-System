package floor;

import java.util.PriorityQueue;

import constants.FloorButton;
import constants.FloorLevel;

public class FloorQueue {
	
	private PriorityQueue<String> timeQ;
	private PriorityQueue<FloorLevel> baseFloorQ;
	private PriorityQueue<FloorButton> floorBtnQ;
	private PriorityQueue<FloorLevel> destFloorQ;
	
	public FloorQueue() {
		timeQ = new PriorityQueue<String>();
		baseFloorQ = new PriorityQueue<FloorLevel>();
		floorBtnQ = new PriorityQueue<FloorButton>();
		destFloorQ = new PriorityQueue<FloorLevel>();
	}
	
	public void setTime(String time) {
		timeQ.add(time);
	}
	
	public void setBaseFloor(FloorLevel floor) {
		baseFloorQ.add(floor);
	}
	
	public void setFloorBtn(FloorButton direction) {
		floorBtnQ.add(direction);
	}
	
	public void setDestFloor(FloorLevel floor) {
		destFloorQ.add(floor);
	}
	
	public PriorityQueue<String> getTimeQ(){
		return timeQ;
	}
	
	public PriorityQueue<FloorLevel> getBaseFloorQ(){
		return baseFloorQ;
	}
	
	public PriorityQueue<FloorButton> getDirectionQ(){
		return floorBtnQ;
	}
	
	public PriorityQueue<FloorLevel> getDestFloorQ(){
		return destFloorQ;
	}
}
