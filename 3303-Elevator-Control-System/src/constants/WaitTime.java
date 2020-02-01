package constants;

public class WaitTime {

	private TimeConst time;

	public WaitTime() {
		time = new TimeConst();
	}

	/**
	 * method is called for thread sleep of 1500 ms
	 */
	public void defaultTime() {
		try {
			Thread.sleep(time.DEFAULT);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * method is called for thread sleep of 4000 ms
	 */
	public void loadTime() {
		try {
			Thread.sleep(time.LOAD);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * method is called for thread sleep of 3935 ms
	 */
	public void MoveTime() {
		try {
			Thread.sleep(time.MOVE);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * method is called for thread sleep of 3732 ms
	 */
	public void openCloseTime() {
		try {
			Thread.sleep(time.OPEN_CLOSE);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
