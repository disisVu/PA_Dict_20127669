package app;

import java.util.*;
import java.util.Map.Entry;
import java.util.regex.Pattern;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;



public class GUI implements ItemListener {
	
	JPanel cards;
	static Dict dict = new Dict();
	static HashMap<String, String> search_history = new HashMap<>();
	static DefaultTableModel history_model;
	 
	public static void addButton(Container pane, String button_text) {
		JButton button = new JButton(button_text);
		pane.add(button);
	}
	
	public static JPanel searchBySlangPanel() {
		
		JPanel pane = new JPanel();
		pane.setLayout(new BorderLayout());
		
		// search bar
		JTextField text_field = new JTextField(20);
		text_field.setBorder(new LineBorder(Color.GRAY, 2));
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
					// update search history table
					history_model.addRow(new String[] {slang, Arrays.toString(data)});
				}
				else {
					list.setModel(listmodel);
					// update search history table
					history_model.addRow(new String[] {slang, "(none)"});
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
		text_field.setBorder(new LineBorder(Color.GRAY, 2));
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
	
	// slang search history
	public static JPanel slangSearchHistory() {
		JPanel pane = new JPanel();
		pane.setLayout(new BorderLayout());
		
		// feature label
		JLabel label = new JLabel("Table view");
		pane.add(label, BorderLayout.PAGE_START);
		
		// history table
		String[][] matrix = new String[0][2];
		
		String[] columns = {"slang", "definition"};
		
		history_model = new DefaultTableModel(matrix, columns);
		
		JTable table = new JTable();
		table.setModel(history_model);
		table.setBounds(30,40,200,300);          
	    JScrollPane scroll_pane = new JScrollPane(table);
	    
	    pane.add(scroll_pane, BorderLayout.CENTER);
	    
	    pane.revalidate();
		
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
		JLabel label = new JLabel("Table view");
		pane.add(label, BorderLayout.PAGE_START);
		
		// import dictionary to matrix
		String[][] matrix = importDictToMatrix(dict);
        
		// table heading
		String[] columns = {"slang", "definition"};
		
		JTable table = new JTable();
		table.setModel(new DefaultTableModel(matrix, columns));
		table.setBounds(30,40,200,300);          
	    JScrollPane scroll_pane = new JScrollPane(table);
	    pane.add(scroll_pane, BorderLayout.CENTER);
	    
	    // control panel
	    JPanel btn_panel = new JPanel();
	    btn_panel.setLayout(new FlowLayout());
	    
	    JButton add_btn = new JButton("Add slang");
	    // add slang button event
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
	    		
	    		if (add_definition != null) {    			
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
	    				// overwrite definition
	    				case 0:
	    					// update dictionary
	    					dict.data.put(add_slang, new ArrayList<String>(Arrays.asList(
	    							add_definition.split(Pattern.quote(" | ")))));				
	    					// get row index of given slang
	    					int row = 0;
	    					for (int i = table.getModel().getRowCount() - 1; i >= 0; --i) {
    				            if (table.getModel().getValueAt(i, 0).equals(add_slang)) {
    				                // what if value is not unique?
    				                row = i;
    				            }
	    				    }
	    					// update table row
	    					table.getModel().setValueAt(add_definition, row, 1);
	    					
	    					break;
	    				
	    				// duplicate slang
	    				case 1:
	    					// update dictionary
	    					dict.data.put(add_slang + "~", new ArrayList<String>(Arrays.asList(
	    							add_definition.split(Pattern.quote(" | ")))));
	    					// add new row to table
	    					((DefaultTableModel) table.getModel()).addRow(new String[] {add_slang + "~", add_definition});
	    					break;
	    				}
	    			}
	    			// if slang hasn't existed
	    			else {
	    				dict.data.put(add_slang, new ArrayList<String>(Arrays.asList(
	    						add_definition.split(Pattern.quote(" | ")))));
	    				// add new row to table
	    				((DefaultTableModel) table.getModel()).addRow(new String[] {add_slang, add_definition});
	    			}
	    		}
	    	}
	    });
	    
	    JButton edit_btn = new JButton("Edit slang");
	    // edit slang button event
	    edit_btn.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent evt) {  			    		
	    		// edit slang by selecting table row
	    		// reference: https://www.youtube.com/watch?v=Tg62AxNRir4
	    		if (table.getSelectedRowCount() == 1) {
	    			
	    			int row = table.getSelectedRow();
	    			
	    			String edit_slang = table.getModel().getValueAt(row, 0).toString();
	    			
	    			String edit_definition = JOptionPane.showInputDialog(pane,
		    				"new Slang definition: ", null);
	    			
	    			// if definition input field isn't empty
	    			if (edit_definition != null) {
	    				// update dictionary
	    				dict.data.put(edit_slang, new ArrayList<String>(Arrays.asList(
	    						edit_definition.split(Pattern.quote(" | ")))));	
	    				// update table row
	    				table.getModel().setValueAt(edit_definition, row, 1);			
	    			}
	    		}
	    		else {
	    			if (table.getSelectedRowCount() == 0) {
	    				JOptionPane.showMessageDialog(null, "please select one row", 
	    						"InfoBox", JOptionPane.INFORMATION_MESSAGE);
	    			}
	    			else {
	    				JOptionPane.showMessageDialog(null, "can't select multiple row at once", 
	    						"InfoBox", JOptionPane.INFORMATION_MESSAGE);
	    			}
	    		}
	    	}
	    });
	    
	    JButton delete_btn = new JButton("Delete slang");
	    // delete slang button event
	    delete_btn.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent evt) {
	    		// delete slang by selecting table row
	    		if (table.getSelectedRowCount() == 1) {
	    			
	    			// confirm dialog
	    			// reference: https://mkyong.com/swing/java-swing-how-to-make-a-confirmation-dialog/
	    			int confirmation = JOptionPane.showConfirmDialog(null, "Proceed to delete slang?");
	    			
	    			// YES = 0
	    			if (confirmation == 0) {
	    				int row = table.getSelectedRow();
	    				
	    				String delete_slang = table.getModel().getValueAt(row, 0).toString();
	    				
	    				// update dictionary
	    				dict.data.remove(delete_slang);
	    				
	    				// update table row
	    				((DefaultTableModel) table.getModel()).removeRow(row);		  				
	    			}
	    		}
	    		else {
	    			if (table.getSelectedRowCount() == 0) {
	    				JOptionPane.showMessageDialog(null, "please select one row", 
	    						"InfoBox", JOptionPane.INFORMATION_MESSAGE);
	    			}
	    			else {
	    				JOptionPane.showMessageDialog(null, "can't select multiple row at once", 
	    						"InfoBox", JOptionPane.INFORMATION_MESSAGE);
	    			}
	    		}
	    	}
	    });
	    
	    JButton reset_btn = new JButton("Reset table");
	    // reset dictionary button event
	    reset_btn.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent evt) {
	    		int confirmation = JOptionPane.showConfirmDialog(null, "Proceed to reset dictionary?");
    			
    			// YES = 0
    			if (confirmation == 0) {				
    				// reset dictionary
    				dict.importDictionary(".//src/slang.txt");
    				
    				// update table
    				table.setModel(new DefaultTableModel(matrix, columns));  				
    			}
	    	}
	    });
	    
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
								"Slang search history",
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
		
		JPanel card3 = slangSearchHistory();
		cards.add(card3, "Slang search history");
		
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
		dict.importDictionary(".//src/slang.txt");
		createAndShowGUI();
	}

}
