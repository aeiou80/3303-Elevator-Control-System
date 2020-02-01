package Something;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.UnknownHostException;

import constants.FloorLevel;
import scheduler.Scheduler;

public class NotifyScheduler {
	private DatagramPacket sendPacket;
	private Scheduler scheduler;
	private WaitTime waitTime;

	public NotifyScheduler(Scheduler s) {
		scheduler = s;
		waitTime = new WaitTime();
	}

	public synchronized void PassengerPos(FloorLevel floor) {
		// Sending Packet
		// creates string with all relevant information
		String s = "Passenger is on " + floor;
		byte[] message = s.getBytes();
		// converts string into datagram packet to send to server
		try {
			sendPacket = new DatagramPacket(message, message.length, InetAddress.getLocalHost(), 3000);
		} catch (UnknownHostException e) {
			e.printStackTrace();
			System.exit(1);
		}

		System.out.println(new String(sendPacket.getData()));
		scheduler.floorNotification(floor);
		waitTime.defaultTime();

		notifyAll();
	}

}