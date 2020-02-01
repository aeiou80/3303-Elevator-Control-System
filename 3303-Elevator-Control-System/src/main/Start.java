package main;

import elevator.ElevatorSubSystem;
import floor.FloorSubSystem;
import scheduler.Scheduler;

/**
 * Run this
 * 
 * @author John Warde
 */
public class Start {

	public static void main(String[] args) {
		Scheduler s = new Scheduler();
		Thread floor = new Thread(new FloorSubSystem(s), "Floor");
		Thread elevator = new Thread(new ElevatorSubSystem(s), "Elevator");
		Thread scheduler = new Thread(s, "Scheduler");
		elevator.start();
		floor.start();
		scheduler.start();
	}

}