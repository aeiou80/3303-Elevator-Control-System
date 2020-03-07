import java.io.*;

/**
 * Elevator subsystem that creates and starts 3 different elevator threads which
 * operate concurrently.
 * 
 * @author Eric Vincent
 */
public class ElevatorSubsystem {

	public static void main(String args[]) throws Exception, IOException {
		Thread a, b, c;
		a = new Thread(new Elevator(1000), "Elevator NO.1");
		b = new Thread(new Elevator(1001), "Elevator NO.2");
		c = new Thread(new Elevator(1002), "Elevator NO.3");
		a.start();
		b.start();
		c.start();
	}
}
