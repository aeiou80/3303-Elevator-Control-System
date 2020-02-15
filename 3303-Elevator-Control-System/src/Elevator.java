/*
 * Elevator class deal with all conditions that elevator may meet.
 * It controls the actions of elevator.
 */

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

public class Elevator implements Runnable {
	private DatagramSocket receiveSocket;
	private DatagramPacket receivePacket;
	private int portNumber;
	private int motor;// -1:down 1:up 0:stop
	private int button;// the floor number chosen by passenger
	private int floorSensor;// the floor number where elevator is
	private Set<Integer> elevatorLamps;// the floor numbers that elevator need to stop by
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

	/*
	 * @override the run function is overridden such that the elevators
	 * can run concurrently and implements what developer want elevators
	 * to do.
	 */
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
			this.pickUpPassager(arrayList);
			this.deliveryPassager(arrayList);
			DatagramSocket newSocket;
			try {
				newSocket = new DatagramSocket();
				DatagramPacket sendPacket = null;
				String s = "Done";
				byte[] result = s.getBytes();
				sendMsg(result, result.length, receivePacket.getAddress(), sendPacket, newSocket, receivePacket.getPort());
				this.log("PortNumber: "+receivePacket.getPort());
				newSocket.close();
			} catch (SocketException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}
	
	/*
	 * decides the state of a elevator when the passengers 
	 * press the button.
	 * @param arr :a list of actions that passengers made
	 */
	public void pickUpPassager(ArrayList<String> arr) {
		if(arr.get(0).equals("Stop")) {
			motor = 0;
		}
		else if(arr.get(0).equals("Up")) {
			motor = 1;
		}
		else if(arr.get(0).equals("Down")) {
			motor = -1;
		}
		else {
			try {
				throw new IllegalAccessException("Message is invalid");
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(motor == 0) {
			state  = new StopState(this);
		}
		else {
			button = Integer.parseInt(arr.get(1));
			state = new MoveState(this);
			
		}
		state.moveDoor();
		state.moveElevator();
		state = new StopState(this);
		state.moveDoor();
		
	}
	
	/*
	 * decides the state of a elevator when the passengers 
	 * enter the elevator.
	 * @param arr :a list of actions that passengers made
	 */
	public void deliveryPassager(ArrayList<String> arr) {
		if(arr.get(2).equals("Up")) {
			motor = 1;
		}
		else if(arr.get(2).equals("Down")) {
			motor = -1;
		}
		else {
			try {
				throw new IllegalAccessException("Message is invalid");
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(motor == 0) {
			try {
				throw new IllegalAccessException("Message is invalid");
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else {
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
	
	/*
	 * return the button pressed
	 * @return int :number in button which is pressed
	 */
	public int getButton() {
		return button;
	}
	
	/*
	 * get the motor status 
	 * @return motor status in integer
	 */
	public int getMotor() {
		return motor;
	}
	
	/*
	 * active and set the floor sensor
	 * @param f :a floor sensor object
	 */
	public void setFloorSensor(int f) {
		floorSensor = f;
	}
	
	/*
	 * get the status of floor sensor
	 * @return floor sensor status in integer
	 */
	public int getFloorSensor() {
		return floorSensor;
	}

	/*
	 * convert a message string to arraylist
	 * @param msg :a msg string 
	 * @return a arraylist which contains strings
	 */
	public ArrayList<String> stringToList(String msg) {
		ArrayList<String> arrayList = new ArrayList<String>(Arrays.asList(msg.split(",")));
		arrayList.get(3).trim();
		return arrayList;
	}

	/*
	 * create a new byte list in limited length
	 * @param len :the limitation of length 
	 * @param arr :a list of byte
	 * @return a list of byte
	 */
	public byte[] fixByteArrLength(int len, byte[] arr) {
		byte newdata[] = new byte[len];
		System.arraycopy(arr, 0, newdata, 0, newdata.length);
		return newdata;

	}

	/*
	 * a boolean function for validating message 
	 * @param a list of byte
	 * @return boolean variable true
	 */
	public boolean validMsg(String msg) {
		ArrayList<String> validDirections = new ArrayList<>();
		validDirections.add("Up");
		validDirections.add("Down");
		validDirections.add("Stop");
		String[] message = msg.split(",");
		if(!validDirections.contains(message[0]) || !validDirections.contains(message[2])) {
			return false;
		}
		try {
			int value = Integer.parseInt(message[1]);
			value = Integer.parseInt(message[3]);
		}
		catch(Exception e) {
			return false;
		}
		return true;
	}

	/*
	 * send the massage 
	 * @param data :a list of byte
	 * @param len :the length of data
	 * @param address :the destination inet address
	 * @param sendPacket :the datagram Packet which is going to be send 
	 * @param sendReceiveSocket :the socket which is used to send and receive message
	 * @param port :the destination port 	 
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

	/*
	 * receive the message 
	 * @param receiveSocket :the socket which is used to receive message
	 * @return a list of bytes
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

	/*
	 * print the bytes in a byte list 
	 * @param arr :a list of byte
	 */
	public static void printByteArr(byte[] arr) {
		for (int i = 0; i < arr.length; i++) {
			System.out.print(arr[i]);
		}
		System.out.print("\n");
	}

	public void log(String msg) {
		System.out.println("Log: " + msg);
	}
	
	//Used for testing
	public String getState() {
		return state.stateName;
	}
}
