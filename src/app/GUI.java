package app;

//import java.util.*;
import java.awt.*;
//import java.awt.event.*;
import javax.swing.*;

//import app.dict;

public class GUI {
	
	public final static boolean RIGHT_TO_LEFT = false;
	
	private static void addButton(Container pane, String button_text) {
		JButton button = new JButton(button_text);
		pane.add(button);
	}
	
	private static void menuPane(Container menu_pane) {
		
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
		
		JLabel app_label = new JLabel("Dictionary App");
		app_label.setAlignmentX((float) 0.5);
		top_panel.add(app_label);
		
		menu_pane.add(top_panel, BorderLayout.PAGE_START);
		
		// Menu CENTER
		JPanel center_panel = new JPanel();
		center_panel.setLayout(new GridLayout(4,2));
		
		addButton(center_panel, "Search by Slang");
		addButton(center_panel, "Search by Definition");
		addButton(center_panel, "View search history");
		addButton(center_panel, "View Dictionary");
		addButton(center_panel, "Random Slang");
		addButton(center_panel, "Slang Quiz");
		addButton(center_panel, "Definition Quiz");
		
		menu_pane.add(center_panel, BorderLayout.CENTER);		
	}
	
	private static void createAndShowGUI() {
		
		JFrame.setDefaultLookAndFeelDecorated(true);
		
		JFrame frame = new JFrame("Dictionary App");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		menuPane(frame.getContentPane());
		
		frame.pack();
		frame.setVisible(true);
	} 

	public static void main(String[] args) {
		createAndShowGUI();
	}

}
