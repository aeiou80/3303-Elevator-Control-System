import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

class Tests {
	private Scheduler s;

	@Test
	void testSettersGetters() {
		FloorSubsystem floor = new FloorSubsystem("local");
		floor = new FloorSubsystem("local");
		floor.setTime("1:00.0");
		floor.setFloor(1);
		floor.setDirection("Up");
		floor.setTargetFloor(4);

		assertTrue(floor.getTime() == "1:00.0");
		assertTrue(floor.getCurrentFloor() == 1);
		assertTrue(floor.getDirection() == "Up");
		assertTrue(floor.getTargetFloor() == 4);
	}

	@Test
	void testFloorSend() {
		FloorSubsystem floor = new FloorSubsystem("local");
		floor = new FloorSubsystem("local");
		floor.setTime("1:00.0");
		floor.setFloor(1);
		floor.setDirection("Up");
		floor.setTargetFloor(4);

		floor.testFlag = true;
		floor.sendAndReceive();
		assertFalse(floor.sendPacket == null); // packet is sent
	}

	@Test
	void testSchedulerSendReceive() {
		FloorSubsystem floor = new FloorSubsystem("local");
		floor = new FloorSubsystem("local");
		floor.setTime("1:00.0");
		floor.setFloor(1);
		floor.setDirection("Up");
		floor.setTargetFloor(4);

		floor.testFlag = true;
		s = new Scheduler("local");
		s.testFlag = true;
		floor.sendAndReceive();
		s.receivePacket();
		assertFalse(s.receivePacket == null); // packet received by scheduler
		assertFalse(s.sendPacket == null); // packet sent by scheduler
	}
	
	@Test
	void testPickUpElevatorState() {
		Elevator e = new Elevator(1000);
		assertEquals("The elevator didn't start stopped", "stopped", e.getState());
		assertEquals("The elevator didn't start on floor 1", 1, e.getFloorSensor());
		ArrayList<String> arr = new ArrayList<String>();
		arr.add("Up");
		arr.add("5");
		e.pickUpPassenger(arr);
		assertEquals("The elevator didn't stop!", "stopped", e.getState());
		assertEquals("The elevator didn't end on floor 5", 5, e.getFloorSensor());
	}
	
	@Test
	void testDeliverElevatorState() {
		Elevator e = new Elevator(1001);
		assertEquals("The elevator didn't start stopped", "stopped", e.getState());
		assertEquals("The elevator didn't start on floor 1", 1, e.getFloorSensor());
		ArrayList<String> arr = new ArrayList<String>();
		arr.add("Up");
		arr.add("5");
		arr.add("Up");
		arr.add("8");
		e.deliverPassenger(arr);
		assertEquals("The elevator didn't stop!", "stopped", e.getState());
		assertEquals("The elevator didn't end on floor 8", 8, e.getFloorSensor());
	}

}
