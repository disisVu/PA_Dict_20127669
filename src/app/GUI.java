package app;

import java.util.*;
import java.util.Map.Entry;
import java.util.regex.Pattern;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;



public class GUI implements ItemListener {
	
	JPanel cards;
	static Dict default_dict = new Dict();
	static Dict dict = new Dict();
	
	public static void addButton(Container pane, String button_text) {
		JButton button = new JButton(button_text);
		pane.add(button);
	}
	
	public static JPanel searchBySlangPanel() {
		
		JPanel pane = new JPanel();
		pane.setLayout(new BorderLayout());
		
		// search bar
		JTextField text_field = new JTextField(20);
		text_field.setPreferredSize(new Dimension(30,20));
		
		// result list
		JList<String> list = new JList<String>();
		DefaultListModel<String> listmodel = new DefaultListModel<String>();
		
		// listen on TextField enter
		text_field.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String slang = text_field.getText();
				ArrayList<String> definitions = dict.searchBySlang(slang.toUpperCase());
				
				if (definitions != null) {
					String[] data = new String[definitions.size()];
					data = definitions.toArray(data);
					list.setListData(data);
				}
				else {
					list.setModel(listmodel);
				}
			}
		});
		pane.add(text_field, BorderLayout.PAGE_START);
		
		list.setLayoutOrientation(JList.VERTICAL);
		list.setVisibleRowCount(-1);	
		pane.add(list, BorderLayout.CENTER);
		
		return pane;
	}
	
	public static JPanel searchByDefinitionPanel() {
		JPanel pane = new JPanel();
		pane.setLayout(new BorderLayout());
		
		// search bar
		JTextField text_field = new JTextField(20);
		text_field.setPreferredSize(new Dimension(30,20));
		
		// result list
		JList<String> list = new JList<String>();
		DefaultListModel<String> listmodel = new DefaultListModel<String>();
		
		// listen on TextField enter
		text_field.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String keyword = text_field.getText();
				ArrayList<String> slangs = dict.searchByDefinition(keyword);
				
				// if definition array is not empty
				if (slangs != null) {
					String[] data = new String[slangs.size()];
					data = slangs.toArray(data);
					list.setListData(data);
				}
				else {
					list.setModel(listmodel);
				}
			}
		});
		pane.add(text_field, BorderLayout.PAGE_START);
		
		list.setLayoutOrientation(JList.VERTICAL);
		list.setVisibleRowCount(-1);	
		pane.add(list, BorderLayout.CENTER);
		
		return pane;
	}
	
	// import dictionary into String matrix
	public static String[][] importDictToMatrix(Dict dict) {
		
		HashMap<String, ArrayList<String>> sorted_dict 
			= Dict.sortHashMapByKey(dict.data);
		
		String[][] table = new String[sorted_dict.size()][2];
		
		// iterator
		int i = 0;
	    Iterator<Entry<String, ArrayList<String>>> new_iterator
	    	= sorted_dict.entrySet().iterator();
	    
	    while (new_iterator.hasNext()) {
	    	
	    	HashMap.Entry<String, ArrayList<String>> new_element
	        	= (Map.Entry<String, ArrayList<String>>)new_iterator.next();
	    	
	    	table[i][0] = new_element.getKey();      	
	    	table[i][1] = String.join("| ", new_element.getValue());
	    	
	    	i++;
	    }
	    return table;
	}
	
	public static JPanel manageDictionary() {
		JPanel pane = new JPanel();
		pane.setLayout(new BorderLayout());
		
		// feature label
		JLabel label = new JLabel("Dictionary Manager");
		pane.add(label, BorderLayout.PAGE_START);
		
		String[][] matrix = importDictToMatrix(dict);
        
		String[] columns = {"slang", "definition"};
		
		DefaultTableModel model = new DefaultTableModel(matrix, columns);
		JTable table = new JTable();
		table.setModel(model);
		table.setBounds(30,40,200,300);          
	    JScrollPane scroll_pane = new JScrollPane(table);
	    pane.add(scroll_pane, BorderLayout.CENTER);
	    
	    // control panel
	    JPanel btn_panel = new JPanel();
	    btn_panel.setLayout(new FlowLayout());
	    
	    JButton add_btn = new JButton("Add slang"); 
	    // popUp window
	    // reference: https://stackoverflow.com/questions/8852560/how-to-make-popup-window-in-java
	    add_btn.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent evt) {  		
	    		String add_slang = (JOptionPane.showInputDialog(pane,
	    				"new Slang: ", null));
	    		if (add_slang != null) {
	    			add_slang.toUpperCase();
	    		}
	    		String add_definition = JOptionPane.showInputDialog(pane,
	    				"new Slang definition: ", null);
	    		
	    		String[] options = {"Overwrite", "Duplicate"};
	    		
	    		// if slang has already existed
	    		if (dict.data.get(add_slang) != null) {
	    			// popUp window: overwrite or duplicate
		    		// reference: https://mkyong.com/swing/java-swing-joptionpane-showoptiondialog-example/
	    			int option_index = JOptionPane.showOptionDialog(pane, 
	    					"Slang has already existed",
	    					"Confirm?",
	    					JOptionPane.DEFAULT_OPTION,
	    					JOptionPane.INFORMATION_MESSAGE,
	    					null,
	    					options,
	    					options[0]);
	    			
	    			switch (option_index) {
	    			case 0:
	    				// overwrite definition
	    				dict.data.put(add_slang, new ArrayList<String>(Arrays.asList(
	    						add_definition.split(Pattern.quote(" | ")))));
	    				model.addRow(new String[] {add_slang, add_definition});
	    				break;
	    			case 1:
	    				// duplicate slang
	    				dict.data.put(add_slang + "~", new ArrayList<String>(Arrays.asList(
	    						add_definition.split(Pattern.quote(" | ")))));
	    				model.addRow(new String[] {add_slang + "~", add_definition});
	    				break;
	    			}
	    		}
	    		else {
	    			dict.data.put(add_slang, new ArrayList<String>(Arrays.asList(
    						add_definition.split(Pattern.quote(" | ")))));
	    			model.addRow(new String[] {add_slang, add_definition});
	    		}
	    	}
	    });
	    
	    JButton edit_btn = new JButton("Edit slang");
	    
	    JButton delete_btn = new JButton("Delete slang");
	    
	    JButton reset_btn = new JButton("Reset table");
	    
	    btn_panel.add(add_btn);
	    btn_panel.add(edit_btn);
	    btn_panel.add(delete_btn);
	    btn_panel.add(reset_btn);
	    
	    pane.add(btn_panel, BorderLayout.PAGE_END);
	    
	    return pane;
	}
	
	public void menuPane(Container menu_pane) {
		
		// check if menu_pane is using BorderLayout
		if (!(menu_pane.getLayout() instanceof BorderLayout)) {
            menu_pane.add(new JLabel("Container doesn't use BorderLayout!"));
            return;
        }
		
		// Menu PAGE_START
		
		JPanel top_panel = new JPanel();
		top_panel.setLayout(new BoxLayout(top_panel, BoxLayout.Y_AXIS));
		
		// Menu options drop-down
		String menu_option[] = {"Search by Slang",
								"Search by Definition",
								"View search history",
								"Manage Dictionary",
								"Random Slang",
								"Slang Quiz",
								"Definition Quiz"};
		JComboBox<String> cb = new JComboBox<String>(menu_option);
		cb.setPreferredSize(new Dimension(200,50));
		cb.setEditable(false);
        cb.addItemListener(this);
        top_panel.add(cb);
		
		menu_pane.add(top_panel, BorderLayout.PAGE_START);
		
		// Cards
		cards = new JPanel(new CardLayout());
	
		JPanel card1 = searchBySlangPanel();
		cards.add(card1, "Search by Slang");
		
		JPanel card2 = searchByDefinitionPanel();
		cards.add(card2, "Search by Definition");
		
		JPanel card4 = manageDictionary();
		cards.add(card4, "Manage Dictionary");
		
		menu_pane.add(cards, BorderLayout.CENTER);
	}
	
	public void itemStateChanged(ItemEvent evt) {
        CardLayout cl = (CardLayout)(cards.getLayout());
        cl.show(cards, (String)evt.getItem());
    }
	
	private static void createAndShowGUI() {
		
		JFrame.setDefaultLookAndFeelDecorated(true);
		
		JFrame frame = new JFrame("Dictionary App");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		GUI gui = new GUI();
		gui.menuPane(frame.getContentPane());

		frame.pack();	
		// center window on screen
		// reference: https://stackoverflow.com/questions/144892/how-to-center-a-window-in-java
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	} 

	public static void main(String[] args) {
		default_dict.importDictionary(".//src/slang.txt");
		dict = default_dict;
		createAndShowGUI();
	}

}
