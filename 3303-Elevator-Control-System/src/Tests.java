import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.Test;

class Tests {
	private Scheduler s;

	@Test
	void testSettersGetters() {
		FloorSubsystem floor = new FloorSubsystem();
		floor = new FloorSubsystem();
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
		FloorSubsystem floor = new FloorSubsystem();
		floor = new FloorSubsystem();
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
		FloorSubsystem floor = new FloorSubsystem();
		floor = new FloorSubsystem();
		floor.setTime("1:00.0");
		floor.setFloor(1);
		floor.setDirection("Up");
		floor.setTargetFloor(4);

		floor.testFlag = true;
		s = new Scheduler();
		s.testFlag = true;
		floor.sendAndReceive();
		s.receivePacket();
		assertFalse(s.receivePacket == null); // packet received by scheduler
		assertFalse(s.sendPacket == null); // packet sent by scheduler
	}

}
