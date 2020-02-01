package floor;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import Something.NotifyScheduler;
import Something.WaitTime;
import scheduler.Scheduler;

public class FloorSubSystem implements Runnable{
	
	private FloorDataPacket newPacket;
	private List<ArrayList<String>> lst;
	private FloorQueue fQ;
	private NotifyScheduler notifySched;
	private WaitTime waitTime;
	DatagramPacket sendPacket, receivePacket;
	DatagramSocket socket;
	FloorButton buttons;
	FloorLamp lamp;
	private Scheduler scheduler;
	

	public FloorSubSystem() {
		buttons = new FloorButton();
		lamp = new FloorLamp();
		newPacket = new FloorDataPacket();
		lst = new ArrayList<>();
		fQ = new FloorQueue();
		notifySched = new NotifyScheduler();
		waitTime = new WaitTime();
		scheduler = new Scheduler();
		
		try {
			socket = new DatagramSocket();			
		}catch(SocketException e) {
			e.printStackTrace();
			System.exit(1);
		}	
	}
	
	
	public void sendAndReceivePacket() {
		//Sending Packet
		//creates string with all relevant information
		String s = "Passenger at " + Thread.currentThread().getName() + " "+ fQ.getBaseFloorQ().peek() + " going to -> "+ fQ.getDestFloorQ().peek() + " at: "+ fQ.getTimeQ().peek();
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
		FileReader fileReader = null;
		BufferedReader bufferReader = null;
		try {
			String row = "";
			fileReader = new FileReader(filename);
			bufferReader = new BufferedReader(fileReader);
			row = bufferReader.readLine();
			while ((row = bufferReader.readLine()) != null) {
				String[] rowArray = row.split(",");
				ArrayList<String> rowArrayList = new ArrayList<>();
				Collections.addAll(rowArrayList, rowArray);
				lst.add(rowArrayList);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				bufferReader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		setFloorQueue();
	}
	
	public void setFloorQueue() {
		for(int i = 0; i < lst.size(); i++) {
			newPacket.setUp(lst.get(i));
			fQ.setTime(newPacket.getTime());
			fQ.setBaseFloor(newPacket.getFloor());
			fQ.setFloorBtn(newPacket.getFloorButton());
			fQ.setDestFloor(newPacket.getCarButton());
		}
	}
	
	public boolean buttonPressed() {
		if(fQ.getBaseFloorQ().isEmpty()) {
			return false;
		}
		return true;
	}
	
	public void handlePress() {
		
		System.out.println("Time is " + fQ.getTimeQ().poll());
		waitTime.defaultTime();
		notifySched.PassengerPos(fQ.getBaseFloorQ().poll());
		
		
	}
	
	@Override
	public void run() {
		readFile("inputfile.csv");
		scheduler.floorNotification(fQ.getBaseFloorQ().peek());
          		while(true) {
			if(buttonPressed()) {
				
				handlePress();
			}
		}

	}
}
