import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Scanner;

/**
 * Scheduler class receives a packet from floor and elevator subsystems, then
 * schedules the devices and elevators.
 * 
 * @author Andrew Foster, Eric Vincent
 * @documentation Cameron Davis
 */
public class Scheduler {

	Floor floor;
	InetAddress elevatorAddress;
	String[] PackageInfo = new String[4];
	private int[] portNumber = new int[4];
	DatagramSocket Socket;
	DatagramSocket[] elevatorSockets;
	DatagramPacket sendPacket, receivePacket;
	inforElevatorSystem[] elevators;
	private int clientport;
	InetAddress clientAddress;
	ScheduleStateEnum state;
	boolean testFlag;
	private int chosenElevator;

	public Scheduler(String address) {
		testFlag = false;
		state = ScheduleStateEnum.READINGFROMFLOOR;
		elevators = new inforElevatorSystem[3];
		elevatorSockets = new DatagramSocket[3];

		for (int i = 0; i < this.elevators.length; i++) {
			this.portNumber[i] = 2000 + i;
			this.elevators[i] = new inforElevatorSystem(1, i, portNumber[i]);
			try {
				this.elevatorSockets[i] = new DatagramSocket(portNumber[i]);

			} catch (IOException e) {
				e.printStackTrace();
			}

		}
		try {
			Socket = new DatagramSocket(3000);
			if (address.contentEquals("local")) {
				elevatorAddress = InetAddress.getLocalHost();
			} else {
				try {
					elevatorAddress = InetAddress.getByName(address);
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
	 * Wait to receive a DatagramPacket sent from either the Floor or Elevator
	 * depending on the current state of the Scheduler and parse its data
	 */
	public void receivePacket() {

		switch (state) {

		case READINGFROMFLOOR:
			byte[] data = new byte[100];
			receivePacket = new DatagramPacket(data, data.length);

			try {
				System.out.println("Scheduler waiting for data...");
				Socket.receive(receivePacket);
				clientport = receivePacket.getPort();
				clientAddress = receivePacket.getAddress();
			} catch (IOException e) {
				e.printStackTrace();
				System.exit(1);
			}
			byte newdata[] = new byte[receivePacket.getLength()];
			System.arraycopy(data, 0, newdata, 0, newdata.length);

			System.out.println("Scheduler receiving package from FloorSubsystem: ");
			System.out.println("From " + receivePacket.getAddress() + ", port: " + receivePacket.getPort());
			String ss = new String(receivePacket.getData()).trim();

			System.out.println("Contains: " + ss);

			this.PackageInfo = ss.split("\t");

			System.out.println(PackageInfo[1] + PackageInfo[2] + PackageInfo[3] + PackageInfo[0]);
			// Logic to determine which elevator goes where
			int minimumDistance = Integer.MAX_VALUE;
			int distance = 0;
			chosenElevator = 0;
			String s = "";
			for (int i = 0; i < this.elevators.length; i++) {
				distance = Math.abs(Integer.parseInt(PackageInfo[1]) - elevators[i].getCurrentFloor());
				if (distance < minimumDistance) {
					minimumDistance = distance;
					chosenElevator = i;
				}
			}

			if (distance > 0) {
				s = "Up" + "," + PackageInfo[1] + "," + PackageInfo[2] + "," + PackageInfo[3];
			} else if (distance < 0) {
				s = "Down" + "," + PackageInfo[1] + "," + PackageInfo[2] + "," + PackageInfo[3];
			} else {
				s = "Stop" + "," + PackageInfo[1] + "," + PackageInfo[2] + "," + PackageInfo[3];
			}

			byte msg[] = s.getBytes();
			int sendPort = portNumber[chosenElevator] - 1000;
			sendPacket = new DatagramPacket(msg, msg.length, elevatorAddress, sendPort);
			elevators[chosenElevator].setCurrentFloor(Integer.parseInt(PackageInfo[1]));

			System.out.println("Scheduler sending package to ElevatorSubsystem...");
			System.out.println("To " + sendPacket.getAddress() + ", port: " + sendPacket.getPort());
			System.out.println("Package contains: " + new String(sendPacket.getData()));

			try {
				elevatorSockets[chosenElevator].send(sendPacket);
			} catch (IOException e) {
				e.printStackTrace();
				System.exit(1);
			}

			System.out.println("Message send\n");
			state = ScheduleStateEnum.READINGFROMELEVATOR; // Change States
			this.receivePacket(); // Rerun method

			break;

		case READINGFROMELEVATOR:
			if (testFlag == true) { // for testing purposes
				return;
			}

			// receive2
			byte[] getReply = new byte[100];
			DatagramPacket receivePacket2 = new DatagramPacket(getReply, getReply.length);

			try {
				elevatorSockets[chosenElevator].receive(receivePacket2);
			} catch (IOException e) {
				e.printStackTrace();
				System.exit(1);
			}

			System.out.println("Package received from ElevatorSubSystem...\n");
			System.out.println("from " + receivePacket2.getAddress() + " , port: " + receivePacket2.getPort());
			System.out.println("package contains: " + new String(receivePacket2.getData()));
			elevators[chosenElevator].setCurrentFloor(Integer.parseInt(PackageInfo[3]));
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
				System.exit(1);
			}

			sendPacket = new DatagramPacket(getReply, receivePacket2.getLength(), clientAddress, clientport);

			System.out.println("Package sending to FloorSubSytem...\n");
			System.out.println("to " + receivePacket2.getAddress() + " , host:" + receivePacket2.getPort());
			System.out.println("package contains: " + new String(receivePacket2.getData()));

			// send2
			try {
				Socket.send(sendPacket);
			} catch (IOException e) {
				e.printStackTrace();
				System.exit(1);
			}

			System.out.println("Package sent.");
			state = ScheduleStateEnum.READINGFROMFLOOR; // Change States
			this.receivePacket(); // RerunMethod
			break;
		}

	}

	public static void main(String[] args) {
		boolean correctInput = false;
		String address = "local";
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
		Scheduler s = new Scheduler(address);
		while (true) {
			s.receivePacket();
		}
	}
}