import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class GUI {
	
	public final static boolean RIGHT_TO_LEFT = false;
	
	private static void addComponents(Container main_pane) {
		if (RIGHT_TO_LEFT) {
			main_pane.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		}
		
		main_pane.setLayout(new GridLayout(2,2));
		
		JButton slang_search_btn = new JButton("Search by slang");
		JButton definition_search_btn = new JButton("Search by definition");
		JButton slang_history_btn = new JButton ("Slang history");
		
	}
	
	private static void createAndShowGUI() {
		JFrame.setDefaultLookAndFeelDecorated(true);
		JFrame frame = new JFrame("Dictionary App");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		addComponents(frame.getContentPane());
		frame.pack();
		frame.setVisible(true);
	} 

	public static void main(String[] args) {
		createAndShowGUI();
	}

}
