/*
 * test class for elevatorSubsystem
 */
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.*;



public class test {
	

	public static void main(String[] args) throws UnknownHostException, IOException {
		test test = new test();
		DatagramSocket receiveSocket = new DatagramSocket();
		DatagramPacket packet = null;
		String s = "Up,2,Up,5";
		test.sendMsg(s.getBytes(), s.getBytes().length, InetAddress.getLocalHost(), packet, receiveSocket, 10000);

		byte data[] = new byte[20];
		DatagramPacket receivePacket = new DatagramPacket(data, data.length);

		try {
			receiveSocket.receive(receivePacket);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
		data = test.fixByteArrLength(receivePacket.getLength(), data);
		s = new String(data);
		test.log(s);
		
		///////////////////////////////////////////////////////////
		
		

	}
	public ArrayList<String> stringToList(String msg) {
		ArrayList<String>arrayList = new ArrayList<String>(Arrays.asList(msg.split(",")));
		return arrayList;
	}
	public void log(String msg) {
		System.out.println("Log: " + msg);
	}
	public void sendMsg(byte[] data, int len, InetAddress address, DatagramPacket sendPacket,
			DatagramSocket sendReceiveSocket, int port) throws IOException {

		log("--------------------------------");
		log("Server: sending a packet containing(String):\n" + new String(data));
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

	public byte[] receiveMsg(DatagramSocket receiveSocket, DatagramPacket receivePacket) {
		byte data[] = new byte[20];
		receivePacket = new DatagramPacket(data, data.length);
		log("--------------------------------");
		log("Server: Waiting for Packet.\n");
		try {
			log("Waiting...");
			receiveSocket.receive(receivePacket);
		} catch (IOException e) {
			log("IO Exception: likely: " + "Receive Socket Timed Out.\n" + e);
			e.printStackTrace();
			System.exit(1);
		}
		data = fixByteArrLength(receivePacket.getLength(), data);
		log("Server: Packet received:");
		log("From host: " + receivePacket.getAddress());
		log("Host port: " + receivePacket.getPort());
		int len = receivePacket.getLength();
		log("Length: " + len);
		log("Containing: " + new String(data, 0, len));
		return data;
	}
	public byte[] fixByteArrLength(int len, byte[] arr) {
		byte newdata[] = new byte[len];
		System.arraycopy(arr, 0, newdata, 0, newdata.length);
		return newdata;

	}
}
