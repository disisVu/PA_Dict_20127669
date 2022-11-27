import java.io.*;
import java.util.*;
import java.util.regex.Pattern;

public class dict {
	public static HashMap<String,String[]> data = new HashMap<>();
	
	// import dictionary data into HashMap
	public static void importDictionary(String filename) {
		try {			
			BufferedReader br = new BufferedReader(new FileReader(filename));		
			
			String line = "";
            while ((line = br.readLine()) != null) {
            	
            	// split into Slang and Definitions
                String[] line_data = line.split("`");
                
                // if Slang goes with Definition
                if (line_data.length > 1) {
                	// split Definitions into array
                	String[] line_value = line_data[1].split(Pattern.quote("|"));
                	
                	// store into HashMap
                	data.put(line_data[0], line_value);
				}
            }
			br.close();
		}
		catch (IOException exc) {
			System.out.println(exc);
		}
	}
	
	// search Definition by Slang
	public static void searchBySlang(String slang) {
		
	}

	public static void main(String[] args) {
		long startTime = System.nanoTime();
		importDictionary(".//src/slang.txt");
		long endTime = System.nanoTime();
		System.out.println(Arrays.toString(data.get("ô")));
		System.out.println((float)(endTime - startTime) / 1000000000);
	}

}
