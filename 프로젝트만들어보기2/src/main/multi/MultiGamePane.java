package main.multi;

import javax.swing.JLabel;
import javax.swing.JLayeredPane;

import main.MainFrame;

public class MultiGamePane extends JLayeredPane {
	MainFrame frame = null;
	
	public MultiGamePane(MainFrame frame) {
		this.frame = frame;
		
		JLabel lblNewLabel = new JLabel("MultiGame~");
		lblNewLabel.setBounds(388, 189, 62, 18);
		add(lblNewLabel);
		
	}
}
