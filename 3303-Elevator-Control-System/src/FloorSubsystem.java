
/**
 * FloorSubsystem class is a simulation of real-world elevator floor subsystem
 * which receives passenger's instructions and sends it to the scheduler.
 * 
 * @author Andrew Foster
 * @documentation Cameron Davis
 */
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Scanner;

public class FloorSubsystem {

	private InetAddress targetAddress;
	DatagramPacket sendPacket, receivePacket;
	DatagramSocket Socket;
	static FloorButton buttons;
	FloorLamp lamp;
	int currentFloor, targetFloor;
	String time, direction;

	boolean testFlag, testFlag2; // for testing purposes

	public FloorSubsystem(String address) {
		testFlag = false;
		testFlag2 = false;
		time = "";
		currentFloor = 0;
		targetFloor = 0;

		try {
			Socket = new DatagramSocket();
			if (address.contentEquals("local")) {
				targetAddress = InetAddress.getLocalHost();
			} else {
				try {
					targetAddress = InetAddress.getByName(address);
				} catch (UnknownHostException e) {
					e.printStackTrace();
				}
			}
		} catch (SocketException e) {
			e.printStackTrace();
			System.exit(1);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Set the current time
	 * 
	 * @param time
	 */
	public void setTime(String time) {
		this.time = time;
	}

	/**
	 * Set the passengers destination floor
	 * 
	 * @param floor the destination floor
	 */
	public void setTargetFloor(int floor) {
		targetFloor = floor;
	}

	/**
	 * Set the floor the passenger is currently at
	 * 
	 * @param floor
	 */
	public void setFloor(int floor) {
		currentFloor = floor;
	}

	/**
	 * Set the direction the passenger wants to go
	 * 
	 * @param direction
	 */
	public void setDirection(String direction) {
		this.direction = direction;
	}

	/**
	 * Return the current floor of the passenger
	 * 
	 * @return currentFloor
	 */
	public int getCurrentFloor() {
		return currentFloor;
	}

	/**
	 * Return passenger destination floor
	 * 
	 * @return targetFloor
	 */
	public int getTargetFloor() {
		return targetFloor;
	}

	/**
	 * Return passenger's desired direction
	 * 
	 * @return direction
	 */
	public String getDirection() {
		return direction;
	}

	public String getTime() {
		return time;
	}

	/**
	 * Send a DatagramPacket to port 3000 containing information of the operations
	 * of a passenger such as their current floor, the direction they want to go,
	 * and the destination floor. Then, wait for an echo packet.
	 */
	public void sendAndReceive() {

		String a = time + "\t" + currentFloor + "\t" + direction + "\t" + targetFloor;

		byte[] msg = a.getBytes();

		sendPacket = new DatagramPacket(msg, msg.length, targetAddress, 3000);

		System.out.println("Floor sending...");
		System.out.println("To " + sendPacket.getAddress() + ", post: " + sendPacket.getPort());
		System.out.println("Contains: " + new String(sendPacket.getData()));

		try {
			Socket.send(sendPacket);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}

		System.out.println("Message sent.");

		byte[] data = new byte[100];
		receivePacket = new DatagramPacket(data, data.length);

		if (testFlag == true) { // for testing purposes
			return;
		}
		try {
			System.out.println("waiting for echo...");
			Socket.receive(receivePacket);
		} catch (IOException e) {
			System.out.println("waiting time out");
			e.printStackTrace();
			System.exit(1);
		}

		System.out.println("Packet received:");
		System.out.println("From " + receivePacket.getAddress() + ", post: " + receivePacket.getPort());
		System.out.println("Contains: " + new String(receivePacket.getData()));

		//Socket.close();
	}

	public static void main(String[] args) {
		boolean correctInput = false;
		String address = "local";
		ArrayList<String> list = new ArrayList<>();
		readFile reader = new readFile();
		list = reader.toArrayByFileRead("ElevatorCSV.txt");
		int acurrent, atarget;
		String time, direction;
		while (!correctInput) {
			System.out.println(
					"Would you like to connect to custom host? Or connect to local host for the scheduler? (custom/local)");
			Scanner in = new Scanner(System.in);
			String input = in.nextLine();
			if (input.toLowerCase().equals("local")) {
				correctInput = true;
			} else if (input.toLowerCase().equals("custom")) {
				System.out.println("Please enter the IP address of the Scheduler Host Machine:");
				in = new Scanner(System.in);
				input = in.nextLine();
				address = input;
				correctInput = true;
			} else {
				System.out.println("Please enter a valid input. (custom/local)");
			}
		}
		FloorSubsystem aclient = new FloorSubsystem(address);
 
		for (int i = 0; i < list.size(); i++) {
			String[] newList = list.get(i).split("\t");
			try {
				acurrent = Integer.parseInt(newList[1]); // set current floor to acurrent
				atarget = Integer.parseInt(newList[3]); // set target floor atarget
				time = newList[0];
				direction = newList[2];
				aclient.setTime(time);
				aclient.setFloor(acurrent);
				aclient.setTargetFloor(atarget);
				aclient.setDirection(direction);

			} catch (Exception e) {
				System.out.println("Invalid Format of Message.... Floor Subsystem now closing....");
				return;

			}
			aclient.sendAndReceive();
		}
	}
}
