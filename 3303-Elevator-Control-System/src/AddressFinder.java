import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Runnable class that prints out your local IP address
 * 
 * @author Eric Vincent
 */
public class AddressFinder {

	public static void getAddress() {
		try {
			InetAddress local = InetAddress.getLocalHost();
			System.out.println("Your IP Address is " + local);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}

	public static void main(String args[]) {
		AddressFinder.getAddress();
	}
}
