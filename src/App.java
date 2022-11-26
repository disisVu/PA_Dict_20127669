import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class App {
	

	
	private static void createAndShowGUI() {
		JFrame.setDefaultLookAndFeelDecorated(true);
		JFrame frame = new JFrame("Dictionary App");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
	}

	public static void main(String[] args) {
		createAndShowGUI();
	}

}
