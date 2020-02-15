/*
 * readFile class is used to read all information from a file.
 */

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class readFile {

	public readFile() {
		
	}
	public ArrayList<String> toArrayByFileRead(String name) {
		ArrayList<String> list = new ArrayList<>();
		try {
			FileReader fr = new FileReader(name);
			BufferedReader br = new BufferedReader(fr);
			String astring;
			
		   br.readLine();
		   while((astring = br.readLine())!= null) {
			list.add(astring);}
			br.close();
			fr.close();
		}catch(IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
		
		return list;
	}
	
		
	}
