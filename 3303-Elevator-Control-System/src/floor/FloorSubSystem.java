package floor;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.Collections;

public class FloorSubSystem implements Runnable{
	
	DatagramPacket sendPacket, receivePacket;
	DatagramSocket socket;
	public String  currentFloor;
	public String targetFloor;
	public String userOperation;
	public String time;
	FloorButton buttons;
	FloorLamp lamp;
	

	public FloorSubSystem() {
		time = "";
		currentFloor = "0";
		targetFloor = "0";
		userOperation = "UP";
		buttons = new FloorButton();
		lamp = new FloorLamp();
		
		try {
			socket = new DatagramSocket();			
		}catch(SocketException e) {
			e.printStackTrace();
			System.exit(1);
		}	
	}
	public void setTime(String t) {
		time = t;
	}
	
	public void setTargetFloor(String tfloor) {
		targetFloor = tfloor;
	}
	
	public void setCurrentFloor(String cfloor) {
		currentFloor = cfloor;
	}
	
	public String getCurrentFloor() {
		return currentFloor;
	}
	
	public void userSelectsFloor() {
		userOperation = "4";
	}
	public void userSelectsOpen() {
		userOperation = "3";
	}
	public void userSelectsClose() {
		userOperation = "2";
	}
	public void userSelectsStop() {
		userOperation = "1";
	}
	
	public void sendAndReceivePacket() {
		//Sending Packet
		//creates string with all relevant information
		String s = "user at " + currentFloor + " going to -> "+ targetFloor + " at:  "+ time;
		byte[] message = s.getBytes();
		
		//converts string into datagram packet to send to server
		try {
			sendPacket = new DatagramPacket(message, message.length, InetAddress.getLocalHost(), 3000);
		}catch(UnknownHostException e) {
			e.printStackTrace();
			System.exit(1);
		}
		//Information of sent packet
		System.out.println("Client = FloorSubSystem: Sending Info");
		System.out.println("To ->"+ sendPacket.getAddress() + ", port: "+ sendPacket.getPort());
		System.out.println("Contains: " + new String(sendPacket.getData()));
		
		//sending packet
		try {
			socket.send(sendPacket);
		}catch(IOException e) {
			e.printStackTrace();
			System.exit(1);
		}		
		System.out.println("Message Sent Successfully");
		
		//Receiving Packet
		//creates packet to receive information
		byte[] data = new byte[100];
		receivePacket = new DatagramPacket(data, data.length);
		
		try {
			System.out.println("Waiting to receive Packet");
			socket.receive(receivePacket);
		}catch(IOException e) {
			System.out.println("Error: receiving Packet");
			e.printStackTrace();
			System.exit(1);
		}
		
		System.out.println("Packet received.");
		System.out.println("From " + receivePacket.getAddress()+ ", port: " + receivePacket.getPort());
		System.out.println("Contains: " + new String(receivePacket.getData()));	
	}
	
	/*public void sendPacket(FloorDataPacket packet) {

	}
	*/
	public void readFile(String filename) {
		try {
			String row = "";
			FileReader fileReader = new FileReader(filename);
			BufferedReader bufferReader = new BufferedReader(fileReader);
			row = bufferReader.readLine();
			while ((row = bufferReader.readLine()) != null) {
				String[] rowArray = row.split(",");
				ArrayList<String> rowArrayList = new ArrayList<>();
				Collections.addAll(rowArrayList, rowArray);
				if (rowArrayList.size() != 4) {
					int difference = 4 - rowArrayList.size();
					for (int i = 0; i < difference; i++) {
						rowArrayList.add("");
					}
				}

				this.time = rowArrayList.get(0);
				this.currentFloor = rowArrayList.get(1);
				this.targetFloor = rowArrayList.get(3);
				sendAndReceivePacket();
				
				
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}
	}
	
	@Override
	public void run() {
		readFile("inputfile.csv");

	}
	/*
	public static void main(String[] args) {
		
		FloorSubSystem fss_client = new FloorSubSystem();
		fss_client.readFile("inputfile.csv");
		}*/
	}

