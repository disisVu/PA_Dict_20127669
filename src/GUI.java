//import java.util.*;
import java.awt.*;
//import java.awt.event.*;
import javax.swing.*;

//import app.dict;

public class GUI {
	
	public final static boolean RIGHT_TO_LEFT = false;
	
	private static void menuPane(Container menu_pane) {
		if (RIGHT_TO_LEFT) {
			menu_pane.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		}
		
		JLabel app_label = new JLabel("Dictionary App");
		app_label.setAlignmentX((float) 0.5);
		menu_pane.add(app_label, BorderLayout.PAGE_START);
		
//		JButton slang_search_btn = new JButton("Search by slang");
//		JButton definition_search_btn = new JButton("Search by definition");
//		JButton slang_history_btn = new JButton ("Slang history");
		
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
