package main;

import floor.FloorSubSystem;
import scheduler.Scheduler;

public class Start {

	public static void main(String[] args) {
		Scheduler scheduler = new Scheduler();
		Thread floor = new Thread(new FloorSubSystem(scheduler), "Floor");
		Thread scheduler1 = new Thread(scheduler, "Scheduler");
		scheduler1.start();
		floor.start();
	}

}