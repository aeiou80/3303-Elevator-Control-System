import java.net.InetAddress;
import java.net.UnknownHostException;

public class AddressFinder {
	public static void getAddress() {
		try {
			InetAddress local = InetAddress.getLocalHost();
			System.out.println("Your IP Address is " + local);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static void main(String args[]) {
		AddressFinder.getAddress();
		
	}

}
