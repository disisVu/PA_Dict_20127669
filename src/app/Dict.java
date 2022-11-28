package app;
import java.io.*;
import java.util.*;
import java.util.Map.Entry;
import java.util.regex.Pattern;

public class Dict {
	HashMap<String, ArrayList<String>> data = new HashMap<>();
	
	// sort HashMap by key
	// reference: https://www.geeksforgeeks.org/sorting-hashmap-according-key-value-java/
	public static HashMap<String, ArrayList<String>> sortHashMapByKey(HashMap<String, ArrayList<String>> hm)
    {
        // Create a list from elements of HashMap
        List<Map.Entry<String, ArrayList<String>>> list
            = new LinkedList<Map.Entry<String, ArrayList<String>>>(hm.entrySet());
 
        // Sort the list using lambda expression
        Collections.sort(list, (i1, i2) -> i1.getKey().compareTo(i2.getKey()));
 
        // transfer data from sorted list to HashMap
        HashMap<String, ArrayList<String>> temp
            = new LinkedHashMap<String, ArrayList<String>>();
        
        for (Map.Entry<String, ArrayList<String>> aa : list) {
            temp.put(aa.getKey(), aa.getValue());
        }
        return temp;
    }
	
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
                	String[] line_value = line_data[1].split(Pattern.quote(" | "));
                	ArrayList<String> definitions 
                		= new ArrayList<String>(Arrays.asList(line_value));
                	
                	// store into HashMap
                	// replaceAll("\\s+","") removes white space from String
                	// reference: https://stackoverflow.com/questions/5455794/removing-whitespace-from-strings-in-java
                	this.data.put(line_data[0].replaceAll("\\s+",""), definitions);
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
		// reference: https://www.geeksforgeeks.org/how-to-iterate-hashmap-in-java/
        Iterator<Entry<String, ArrayList<String>>> new_iterator
        	= this.data.entrySet().iterator();
        
        while (new_iterator.hasNext()) {
        	
        	HashMap.Entry<String, ArrayList<String>> new_element
            	= (Map.Entry<String, ArrayList<String>>)new_iterator.next();
        	
        	definitions = new_element.getValue();
        	
        	// if definition list contains keyword (ignores case-sensitive)
        	// reference: https://stackoverflow.com/questions/15824733/option-to-ignore-case-with-contains-method
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
		System.out.println(dict.searchByDefinition("angry"));
		System.out.println((float)(endTime - startTime) / 1000000000);
	}

}
