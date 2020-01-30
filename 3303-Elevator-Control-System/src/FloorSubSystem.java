import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class FloorSubSystem implements Runnable{

	public FloorSubSystem() {
		
	}
	
	public void sendPacket(FloorDataPacket packet) {
		
	}
	

	public void readFile(String filename) {
	try {
			String row = "";
			FileReader fileReader = new FileReader(filename);
			BufferedReader bufferReader = new BufferedReader(fileReader);
			row = bufferReader.readLine();
			while((row = bufferReader.readLine())!= null) {
				String[] rowArray = row.split(",");
				ArrayList<String> rowArrayList = new ArrayList<>();
				Collections.addAll(rowArrayList,rowArray);
				FloorDataPacket newPacket = new FloorDataPacket(rowArrayList);
				System.out.println(newPacket);
				sendPacket(newPacket);
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
	
	public static void main(String[] args) {
		Thread floor = new Thread(new FloorSubSystem());
		floor.start();
		
	}
}
