package test;

import floor.FloorDataPacket;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import constants.FloorButton;

public class FloorDataPacketTest {

	private ArrayList<String> data;

	@BeforeEach
	void setUp() throws Exception {
		data = new ArrayList<>();
		data.add("05:15:0");
		data.add("2");
		data.add("Up");
		data.add("4");
	}

	@Test
	void testCorrectPacketData() {
		FloorDataPacket floorPacket = new FloorDataPacket();
		floorPacket.setUp(data);
		assertEquals(data.get(0), floorPacket.getTime());
		assertEquals(data.get(1), Integer.toString(floorPacket.getFloor()));
		assertEquals(FloorButton.UP, floorPacket.getFloorButton());
		assertEquals(data.get(3), Integer.toString(floorPacket.getCarButton()));
	}

}
