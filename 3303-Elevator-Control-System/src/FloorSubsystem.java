/*
 * floorSubsystem class is a simulation of real-world floorSubsytem
 * which receives passenger's instruct and send it to scheduler.
 */

import java.io.IOException;
import java.net.*;
import java.util.ArrayList;

public class FloorSubsystem {

	DatagramPacket sendPacket, receivePacket;
	DatagramSocket Socket;
    static FloorButton buttons;
	FloorLamp lamp;
	int currentFloor,targetFloor ;
    String time, direction;
   
    /*
     * constructor of floor subsystem
     */
    public FloorSubsystem() {
		
		time = "";
		currentFloor = 0;
		targetFloor = 0;
	
		
		try {
			Socket = new DatagramSocket();			
		}catch(SocketException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
	
    /*
     * set the current time 
     * @param a :current time in string form
     */
	public void setTime(String a) {
		time = a;
	}
	
	/*
	 * set the target floor which passenger want to reach
	 * @param floor :target floor in integer form
	 */
	public void setTargetFloor(int floor) {
		targetFloor = floor;
	}
	
	/*
	 * set the current floor which passenger is at 
	 * @param floor :current floor in integer form
	 */
	public void setFloor(int floor) {
		currentFloor = floor;
	}
	
	/*
	 * set the direction which passenger want to go 
	 * @param direction :direction in string form
	 */
	public void setDirection(String direction) {
		this.direction = direction;
	}
	
	/*
	 * get the current floor information 
	 * @return current floor 
	 */
	public int getCurrentFloor() {
		return currentFloor;
	}
	
	/*
	 * send message and receive message
	 */
	public void sendAndReceive() {
	
	
		
		String a = time + "\t" + currentFloor + "\t" + direction + "\t" + targetFloor;
		
		byte[] msg = a.getBytes();
		 
		try {
			sendPacket = new DatagramPacket(msg,msg.length,InetAddress.getLocalHost(),3000);
			
		}catch(UnknownHostException e) {
			e.printStackTrace();
			System.exit(1);
		}
		
		System.out.println("Client sending...");
		System.out.println("To " + sendPacket.getAddress() + ", post: " + sendPacket.getPort());
		System.out.println("Contains: " + new String(sendPacket.getData()));
		
		try {
			Socket.send(sendPacket);
		}catch(IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
		
		System.out.println("Message send");
		
		byte[] data = new byte[100];
		receivePacket = new DatagramPacket(data,data.length);
		
		try {
			System.out.println("waiting for echo...");
			Socket.receive(receivePacket);
		}catch(IOException e) {
			System.out.println("waiting time out");
			e.printStackTrace();
			System.exit(1);
		}
		
		System.out.println("Packet received:");
		System.out.println("From " + receivePacket.getAddress()+ ", post: " + receivePacket.getPort());
		System.out.println("Contains: " + new String(receivePacket.getData()));
	
		//Socket.close();
	}
	
	public static void main(String[] args) {
		ArrayList<String> list = new ArrayList<>();
		readFile reader = new readFile();
		list = reader.toArrayByFileRead("ElevatorCSV.txt");
		int acurrent , atarget;
		String time, direction;
		
		FloorSubsystem aclient = new FloorSubsystem();
		
		for(int i =0; i<list.size();i++) {
			String[] newList = list.get(i).split("\t");
			try {
			acurrent = Integer.parseInt(newList[1]);				//set current floor to acurrent 
			atarget = Integer.parseInt(newList[3]);					//set target floor atarget
			time = newList[0];									    
			direction = newList[2];
			aclient.setTime(time);
			aclient.setFloor(acurrent);
			aclient.setTargetFloor(atarget);
			aclient.setDirection(direction);
			
			}
			catch(Exception e) {
				System.out.println("Invalid Format of Message.... Floor Subsystem now closing....");
				return;
				
			}
			aclient.sendAndReceive();
			
		
	}
	}
}
