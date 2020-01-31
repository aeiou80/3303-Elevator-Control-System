package main;

import FloorSubsystem.FloorSubSystem;

public class Start {

	public static void main(String[] args) {
		Thread floor = new Thread(new FloorSubSystem(),"floor");
		floor.start();

	}

}
