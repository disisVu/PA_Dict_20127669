package app;

import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import app.Dict;

public class GUI implements ItemListener {
	
	JPanel cards;
	static Dict dict = new Dict();
	public final static boolean RIGHT_TO_LEFT = false;
	
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
				ArrayList<String> definitions = dict.searchBySlang(slang);
				
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
	
	public void menuPane(Container menu_pane) {
		
		// check if menu_pane is using BorderLayout
		if (!(menu_pane.getLayout() instanceof BorderLayout)) {
            menu_pane.add(new JLabel("Container doesn't use BorderLayout!"));
            return;
        }
		
		if (RIGHT_TO_LEFT) {
			menu_pane.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		}
		
		// Menu PAGE_START
		
		JPanel top_panel = new JPanel();
		top_panel.setLayout(new BoxLayout(top_panel, BoxLayout.Y_AXIS));
		
		// Menu options drop-down
		String menu_option[] = {"Search by Slang",
								"Search by Definition",
								"View search history",
								"View Dictionary",
								"Random Slang",
								"Slang Quiz",
								"Definition Quiz"};
		JComboBox<String> cb = new JComboBox<String>(menu_option);
		cb.setPreferredSize(new Dimension(200,50));
		cb.setEditable(false);
        cb.addItemListener(this);
        top_panel.add(cb);
		
		menu_pane.add(top_panel, BorderLayout.PAGE_START);
		
		// Menu CENTER
		cards = new JPanel(new CardLayout());
	
		JPanel card1 = searchBySlangPanel();
		cards.add(card1, "Search by Slang");
		
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
		frame.setVisible(true);
	} 

	public static void main(String[] args) {
		dict.importDictionary(".//src/slang.txt");
		createAndShowGUI();
	}

}
