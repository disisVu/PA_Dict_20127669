import java.io.*;
import java.util.*;

public class dict {
	public static Map<String,String> data = new HashMap<>();
	
	public static void importFromFile(String filename) {
		try {			
			BufferedReader br = new BufferedReader(new FileReader(filename));			
			String line = "";
            while ((line = br.readLine()) != null) {
                String[] line_data = line.split("`");
                if (line_data.length > 1) {
                	data.put(line_data[0], line_data[1]);
				}
            }
			br.close();        
		}
		catch (IOException exc) {
			System.out.println(exc);
		}
		
	}

	public static void main(String[] args) {
		importFromFile(".//src//slang.txt");
		System.out.println(data);
	}

}
