package main;

import floor.FloorSubSystem;
import scheduler.Scheduler;

public class Start {

	public static void main(String[] args) {
		Thread floor = new Thread(new FloorSubSystem(),"Floor");
		Thread scheduler = new Thread(new Scheduler(),"Scheduler");
		scheduler.start();
		floor.start();
		

	}

}
