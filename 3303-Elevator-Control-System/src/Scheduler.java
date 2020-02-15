/*
 * Scheduler class receive massage from floor subsystem, then schedule 
 * the devices and elevators.
 */

import java.io.IOException;
import java.net.*;

public class Scheduler {
	
	Floor floor;
	
	String[] PackageInfo = new String[4];
	private int[] portNumber = new int[4];
	DatagramSocket Socket;
	DatagramSocket[] elevatorSockets;
	DatagramPacket sendPacket, receivePacket;
	inforElevatorSystem[] elevators;
	private int clientport;
	ScheduleStateEnum state;
	
	/*
	 * constructor of scheduler
	 */
	public Scheduler() {
		state = ScheduleStateEnum.READINGFROMFLOOR;
		elevators = new inforElevatorSystem[4];
		elevatorSockets = new DatagramSocket[4];
		
		for(int i = 0; i < this.elevators.length; i++) {
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
		} catch (SocketException e) {
			e.printStackTrace();
			System.exit(1);
		}
		
	}
	
	/*
	 * receive massage from a port and send a massage to destination port.
	 */
	public void receivePacket() {
		int chosenElevator = 0;
		switch(state) {
		
		case READINGFROMFLOOR:
		byte[] data = new byte[100];
		receivePacket = new DatagramPacket(data, data.length);
		
		try {
			System.out.println("Scheduler waiting for data...");
			Socket.receive(receivePacket);
			clientport = receivePacket.getPort();
		}catch(IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
		byte newdata[] = new byte[receivePacket.getLength()];
		System.arraycopy(data, 0, newdata, 0, newdata.length);
		
		System.out.println("Scheduler receiving package from FloorSubsystem: ");
		System.out.println("From " + receivePacket.getAddress()+ ", port: " + receivePacket.getPort());
		String ss = new String(receivePacket.getData()).trim();
		
		System.out.println("Contains: " + ss);
		
		this.PackageInfo = ss.split("\t");
		
		System.out.println(PackageInfo[1] + PackageInfo[2] + PackageInfo[3] + PackageInfo[0]);
		String s = "" ;
		/*Logic to determine which elevator goes where, not applicable until ITERATION 3
				int minimumDistance = Integer.MAX_VALUE;
				int distance = 0;
				int chosenElevator = 0;
				String s = "";
				for(int i = 0; i < this.elevators.length; i++) {
					distance =  Math.abs(Integer.parseInt(PackageInfo[1]) - elevators[i].getCurrentFloor());
					if( distance < minimumDistance) {
						minimumDistance = distance;
						chosenElevator = i;
					}
				}
				
			
		*/
		int distance =  Math.abs(Integer.parseInt(PackageInfo[1]) - elevators[0].getCurrentFloor()); //Subtract elevators current floor from the suggested floor
		
		if(distance > 0) {
			s = "Up" + "," + PackageInfo[1] + "," + PackageInfo[2] + "," +  PackageInfo[3]; //If suggested floor is higher than elevators current floor
		} else if(distance < 0) {
			s = "Down" + "," + PackageInfo[1] + "," + PackageInfo[2] + "," +  PackageInfo[3]; //If suggested floor lower than current floor
		} else {
			s = "Stop" + "," + PackageInfo[1] + "," + PackageInfo[2] + "," +  PackageInfo[3]; //If at the requested floor
		}
		
		byte msg[] = s.getBytes();
		int sendPort = portNumber[chosenElevator] - 1000;
		try {
			sendPacket = new DatagramPacket(msg,msg.length,InetAddress.getLocalHost(), sendPort);
			
		}catch(UnknownHostException e) {
			e.printStackTrace();
			System.exit(1);
		}
		elevators[chosenElevator].setCurrentFloor(Integer.parseInt(PackageInfo[1]));
		
		System.out.println("Scheduler sending package to ElevatorSubsystem...");
		System.out.println("To " + sendPacket.getAddress() + ", port: " + sendPacket.getPort());
		System.out.println("Package contains: " + new String(sendPacket.getData()));
		
		try {
			elevatorSockets[chosenElevator].send(sendPacket);
		}catch(IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
		
		System.out.println("Message send\n");
		state = ScheduleStateEnum.READINGFROMELEVATOR; //Change States
		this.receivePacket(); //Rerun method
		break;
		
		case READINGFROMELEVATOR:
		
		//receive2 
		byte[] getReply = new byte[100];
		DatagramPacket receivePacket2 = new DatagramPacket(getReply,getReply.length);
		
		try {
			elevatorSockets[chosenElevator].receive(receivePacket2);
			}catch(IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
			
		
		System.out.println("Package received from ElevatorSubSystem...\n");
		System.out.println("from " + receivePacket2.getAddress() + " , port: " + receivePacket2.getPort());
		System.out.println("package contains: " + new String(receivePacket2.getData()));
		elevators[chosenElevator].setCurrentFloor(Integer.parseInt(PackageInfo[3]));
		try {
			Thread.sleep(3000);
		}catch(InterruptedException e) {
			e.printStackTrace();
			System.exit(1);
		}
		
	
		sendPacket = new DatagramPacket(getReply,receivePacket2.getLength(),receivePacket2.getAddress(),clientport);
		
		System.out.println("Package sending to FloorSubSytem...\n");
		System.out.println("to " + receivePacket2.getAddress() + " , host:" + receivePacket2.getPort());
		System.out.println("package contains: " + new String(receivePacket2.getData()));
		
		
		//send2
		try {
			Socket.send(sendPacket);
		}catch(IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
		
		System.out.println("Package sent.");
		state = ScheduleStateEnum.READINGFROMFLOOR; //Change States
		this.receivePacket(); //RerunMethod
		break;
		}
		
		
		
	}
	
	public static void main(String[] args) { 
		Scheduler s = new Scheduler();
		while(true) {
		s.receivePacket();
		}
	}
}