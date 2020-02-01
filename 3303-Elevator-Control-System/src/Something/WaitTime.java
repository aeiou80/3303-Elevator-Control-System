package Something;

import constants.TimeConst;

public class WaitTime {
	
	private TimeConst time;
	
	public WaitTime() {
		time = new TimeConst();
	}
	
	public void defaultTime() {
		try {
			Thread.sleep(time.DEFAULT);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void loadTime(){
		try {
			Thread.sleep(time.LOAD);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void MoveTime() {
		try {
			Thread.sleep(time.MOVE);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void openCloseTime() {
		try {
			Thread.sleep(time.OPEN_CLOSE);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
