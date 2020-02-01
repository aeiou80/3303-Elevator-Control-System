package scheduler;

import Something.WaitTime;
import constants.FloorLevel;

public class Scheduler implements Runnable {
	
	private boolean elevatorRecieve;
	private boolean floorRecieve;
	private WaitTime waitTime;
	private FloorLevel floor;
	
	public Scheduler() {
		elevatorRecieve = false;
		floorRecieve = false;
		waitTime = new WaitTime();
	}
	
	public void floorNotification(FloorLevel floor) {
		this.floor =  floor;
		floorRecieve = true;
	}
	
	public void elevatorNotification() {
		elevatorRecieve = true;
	}
	
	public synchronized void floorHandle(FloorLevel floor) {
		while(!floorRecieve) {
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				return;
			}
		}
		System.out.println(Thread.currentThread().getName() + " has recived signal from " + floor);
		notifyAll();
	}
	
	public void handleFloor() {
		
	}

	@Override
	public void run() {
		System.out.println(Thread.currentThread().getName());
		floorHandle(floor);
		while(true) {
			if(elevatorRecieve) {
				
				elevatorRecieve = false;
				
			}
			else if(floorRecieve) {
				
				floorHandle(floor);
				floorRecieve = false;
			}
			
			waitTime.defaultTime();
		}

	}

}
