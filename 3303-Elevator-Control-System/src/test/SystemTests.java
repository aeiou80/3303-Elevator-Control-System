package test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import elevator.ElevatorSubSystem;
import floor.FloorSubSystem;
import scheduler.Scheduler;

/**
 * Elevator Control System test
 * 
 * @author Eric Vincent
 */
class SystemTests {
	/**
	 * Testing the FloorSubSystem's ability to read in files
	 */
	@Test
	void testReadFile() {
		Scheduler schedule = new Scheduler();
		FloorSubSystem floor = new FloorSubSystem(schedule);
		floor.readFile("inputfile.csv");
		assertEquals(floor.getsentInfo().toString(),"05:15.0 2 Up 4");
	}
	/**
	 * Ensuring that the data initially received by the Floor SubSystem is successfully send to the elevator subsystem, and that the elevator system
	 * is able to send the data back.
	 */
	@Test
	void testInfoTransfer() {
		Scheduler schedule = new Scheduler();
		FloorSubSystem floor = new FloorSubSystem(schedule);
		ElevatorSubSystem ele = new ElevatorSubSystem(schedule);
		Thread floorThread = new Thread(floor, "Floor");
		Thread eleThread = new Thread(ele,"Elevator");
		floorThread.start();
		eleThread.start();
		while(floorThread.isAlive() && eleThread.isAlive()) {} //Stalls the test until the threads have finished executing
		assertEquals(floor.getsentInfo().toString(),ele.getReceivedInfo().toString()); //Data floor sends compared to data elevator receives
		assertEquals(floor.getreceivedInfo().toString(),ele.getReceivedInfo().toString()); //Data elevator sends compared to data floor receives
	}

}
