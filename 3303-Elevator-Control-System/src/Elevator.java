import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Elevator class that handles passenger events and maintains a correct elevator
 * state. Controls the actions of the elevator.
 * 
 * @author Eric Vincent
 * @documentation Jake Cassady, Cameron Davis
 */
public class Elevator implements Runnable {
	private DatagramSocket receiveSocket;
	private DatagramPacket receivePacket;
	private int portNumber;
	private int motor; // -1:down, 1:up, 0:stop
	private int button; // the floor number chosen by passenger
	private int floorSensor; // the floor number where elevator is
	private Set<Integer> elevatorLamps; // the floor numbers that elevator need to stop by
	private StateE state;

	public Elevator(int po) {
		try {
			portNumber = po;

			receiveSocket = new DatagramSocket(portNumber);

		} catch (SocketException se) {
			se.printStackTrace();
			System.exit(1);
		}
		state = new StopState(this);
		button = -1;
		motor = 0;
		floorSensor = 1;
		elevatorLamps = new HashSet<Integer>();
	}

	/**
	 * the run function is overridden such that the elevators can run concurrently
	 * and implements what developer want elevators to do.
	 */
	@Override
	public void run() {

		while (true) {
			byte data[];
			boolean valid;
			ArrayList<String> arrayList;

			data = receiveMsg(receiveSocket);
			data = fixByteArrLength(receivePacket.getLength(), data);
			String msg = new String(data);
			valid = validMsg(msg);
			if (!valid) {
				try {
					throw new IllegalAccessException("Message is invalid");
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
			}
			arrayList = stringToList(msg);
			this.pickUpPassenger(arrayList);
			this.deliverPassenger(arrayList);
			DatagramSocket newSocket;
			try {
				newSocket = new DatagramSocket();
				DatagramPacket sendPacket = null;
				String s = "Done";
				byte[] result = s.getBytes();
				sendMsg(result, result.length, receivePacket.getAddress(), sendPacket, newSocket,
						receivePacket.getPort());
				this.log("PortNumber: " + receivePacket.getPort());
				newSocket.close();
			} catch (SocketException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	/**
	 * Alters the state of the elevator when passengers press a button
	 * 
	 * @param arr a list of actions that passengers made
	 */
	public void pickUpPassenger(ArrayList<String> arr) {
		if (arr.get(0).equals("Stop")) {
			motor = 0;
		} else if (arr.get(0).equals("Up")) {
			motor = 1;
		} else if (arr.get(0).equals("Down")) {
			motor = -1;
		} else {
			try {
				throw new IllegalAccessException("Message is invalid");
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (motor == 0) {
			state = new StopState(this);
		} else {
			button = Integer.parseInt(arr.get(1));
			state = new MoveState(this);

		}
		state.moveDoor();
		state.moveElevator();
		state = new StopState(this);
		state.moveDoor();

	}

	/**
	 * Alters the state of the elevator when the elevator is delivering the
	 * passengers
	 * 
	 * @param arr a list of actions that passengers made
	 */
	public void deliverPassenger(ArrayList<String> arr) {
		if (arr.get(2).equals("Up")) {
			motor = 1;
		} else if (arr.get(2).equals("Down")) {
			motor = -1;
		} else {
			try {
				throw new IllegalAccessException("Message is invalid");
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (motor == 0) {
			try {
				throw new IllegalAccessException("Message is invalid");
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			button = Integer.parseInt(arr.get(3));
			elevatorLamps.add(button);
			state = new MoveState(this);
		}
		state.moveDoor();
		state.moveElevator();
		state = new StopState(this);
		state.moveDoor();
		elevatorLamps.remove(button);
	}

	/**
	 * Returns the floor button number chosen by a passenger
	 * 
	 * @return the button number
	 */
	public int getButton() {
		return button;
	}

	/**
	 * Return the status of the elevator's motor (-1 = down, 0 = stopped, 1 = going
	 * up)
	 * 
	 * @return the status of the motor
	 */
	public int getMotor() {
		return motor;
	}

	/**
	 * Set the value of the floor sensor which indicates what floor the elevator is
	 * currently at
	 * 
	 * @param f the floor number
	 */
	public void setFloorSensor(int f) {
		floorSensor = f;
	}

	/**
	 * Return the floor number where the elevator currently is
	 * 
	 * @return floor number of the elevator
	 */
	public int getFloorSensor() {
		return floorSensor;
	}

	/**
	 * Convert a String to an ArrayList. Splits given String at comma's into
	 * elements of the ArrayList.
	 * 
	 * @param msg the String to convert
	 * @return ArrayList of the String
	 */
	public ArrayList<String> stringToList(String msg) {
		ArrayList<String> arrayList = new ArrayList<String>(Arrays.asList(msg.split(",")));
		arrayList.get(3).trim();
		return arrayList;
	}

	/**
	 * Create and return a new byte list of a given limited length
	 * 
	 * @param len the desired length
	 * @param arr byte list
	 * @return the new length corrected byte list
	 */
	public byte[] fixByteArrLength(int len, byte[] arr) {
		byte newdata[] = new byte[len];
		System.arraycopy(arr, 0, newdata, 0, newdata.length);
		return newdata;

	}

	/**
	 * Validates a given elevator directions message
	 * 
	 * @param msg the directions message
	 * @return true if valid, false otherwise
	 */
	public boolean validMsg(String msg) {
		ArrayList<String> validDirections = new ArrayList<>();
		validDirections.add("Up");
		validDirections.add("Down");
		validDirections.add("Stop");
		String[] message = msg.split(",");
		if (!validDirections.contains(message[0]) || !validDirections.contains(message[2])) {
			return false;
		}
		try {
			int value = Integer.parseInt(message[1]);
			value = Integer.parseInt(message[3]);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	/**
	 * Sends a DatagramPacket provided its byte data, length, destination
	 * InetAddress & port through a given DatagramSocket
	 * 
	 * @param data              the packet's byte data
	 * @param len               the length of the data
	 * @param address           the destination InetAddress
	 * @param sendPacket        an uninitialized sendPacket to be sent
	 * @param sendReceiveSocket the socket used to send the packet
	 * @param port              the destination port
	 * @throws IOException
	 */
	public void sendMsg(byte[] data, int len, InetAddress address, DatagramPacket sendPacket,
			DatagramSocket sendReceiveSocket, int port) throws IOException {

		log("--------------------------------");
		log(Thread.currentThread().getName() + ": sending a packet containing(String): " + new String(data));
		try {
			sendPacket = new DatagramPacket(data, len, address, port);
			sendReceiveSocket.send(sendPacket);
			log("Send Successfully");
		} catch (UnknownHostException e) {
			log("IO Exception: likely: " + "Send Socket Timed Out.\n" + e);
			e.printStackTrace();
			System.exit(1);
		}

	}

	/**
	 * Waits to receive a DatagramPacket on a given DatagramSocket
	 * 
	 * @param receiveSocket the socket
	 * @return the byte data of the received packet
	 */
	public byte[] receiveMsg(DatagramSocket receiveSocket) {
		byte data[] = new byte[20];
		receivePacket = new DatagramPacket(data, data.length);
		log("--------------------------------");
		log(Thread.currentThread().getName() + ": Waiting for Packet.");
		try {

			receiveSocket.receive(receivePacket);
		} catch (IOException e) {
			log("IO Exception: likely: " + "Receive Socket Timed Out.\n" + e);
			e.printStackTrace();
			System.exit(1);
		}
		data = fixByteArrLength(receivePacket.getLength(), data);
		log(Thread.currentThread().getName() + ": Packet received:");
		log("From host: " + receivePacket.getAddress());
		log("Host port: " + receivePacket.getPort());
		int len = receivePacket.getLength();

		log("Containing: " + new String(data, 0, len));
		return data;
	}

	/**
	 * Print a given byte array
	 * 
	 * @param arr the byte array
	 */
	public static void printByteArr(byte[] arr) {
		for (int i = 0; i < arr.length; i++)
			System.out.print(arr[i]);
		System.out.print("\n");
	}

	public void log(String msg) {
		System.out.println("Log: " + msg);
	}

	// Used for testing
	public String getState() {
		return state.stateName;
	}
}
