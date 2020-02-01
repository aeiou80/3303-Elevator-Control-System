package main;

import floor.FloorSubSystem;

public class Start {

	public static void main(String[] args) {
		Thread floor = new Thread(new FloorSubSystem(),"floor");
		floor.start();

	}

}
