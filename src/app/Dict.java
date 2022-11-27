package app;
import java.io.*;
import java.util.*;
import java.util.Map.Entry;
import java.util.regex.Pattern;

public class Dict {
	HashMap<String, ArrayList<String>> data = new HashMap<>();
	
	// import dictionary data into HashMap
	public void importDictionary(String filename) {
		try {			
			BufferedReader br = new BufferedReader(new FileReader(filename));		
			
			String line = "";
            while ((line = br.readLine()) != null) {
            	
            	// split into Slang and Definitions
                String[] line_data = line.split("`");
                
                // if Slang goes with Definition
                if (line_data.length > 1) {
                	// split Definitions into array
                	String[] line_value = line_data[1].split(Pattern.quote("| "));
                	ArrayList<String> definitions 
                		= new ArrayList<String>(Arrays.asList(line_value));
                	
                	// store into HashMap
                	this.data.put(line_data[0], definitions);
				}
            }
			br.close();
		}
		catch (IOException exc) {
			System.out.println(exc);
		}
	}
	
	// search Definition by exact Slang
	public ArrayList<String> searchBySlang(String slang) {
		return (this.data.get(slang));
	}
	
	// search Slang by keyword in Definition
	public ArrayList<String> searchByDefinition(String keyword) {
		
		ArrayList<String> slangs = new ArrayList<String>();
		ArrayList<String> definitions = new ArrayList<String>();;
		
		// iterator
        Iterator<Entry<String, ArrayList<String>>> new_iterator
        	= this.data.entrySet().iterator();
        
        while (new_iterator.hasNext()) {
        	
        	HashMap.Entry<String, ArrayList<String>> new_element
            	= (Map.Entry<String, ArrayList<String>>)new_iterator.next();
        	
        	definitions = new_element.getValue();
        	
        	// if definition list contains keyword (not case-sensitive)
        	if (definitions.stream().anyMatch(keyword::equalsIgnoreCase)) {
        		// add slang to result list
        		slangs.add(new_element.getKey());
        	}
        }
        return slangs;
	}

	public static void main(String[] args) {
		long startTime = System.nanoTime();
		Dict dict = new Dict();
		dict.importDictionary(".//src/slang.txt");
		long endTime = System.nanoTime();
//		System.out.println((dict.data.get(">.<")));
		System.out.println(dict.searchByDefinition("happy"));
		System.out.println((float)(endTime - startTime) / 1000000000);
	}

}
