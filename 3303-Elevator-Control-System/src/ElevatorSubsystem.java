/*
 * ElevatorSubsystem implements concurrent, it makes 
 * 4 elevators in system work in same time.
 */
import java.io.*;

public class ElevatorSubsystem {


	public static void main(String args[]) throws Exception, IOException {
		Thread a, b, c, d;
		a = new Thread(new Elevator(1000),"Elevator NO.1");
		//b = new Thread(new Elevator(1001),"Elevator NO.2");
		//c = new Thread(new Elevator(1002),"Elevator NO.3");
		//d = new Thread(new Elevator(1003),"Elevator NO.4");
		a.start();
		//b.start();
		//c.start();
		//d.start();
		
	}

}

