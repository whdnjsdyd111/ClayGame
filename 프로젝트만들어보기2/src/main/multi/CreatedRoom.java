package main.multi;

import javax.swing.JLayeredPane;

import main.MainFrame;

public class CreatedRoom extends JLayeredPane {
	
	MainFrame frame = null;
	static final int PORT = 7000;
	
	public CreatedRoom(MainFrame frame, String ip) {
		this.frame = frame;
		setLayout(null);
		
		
	}
}
