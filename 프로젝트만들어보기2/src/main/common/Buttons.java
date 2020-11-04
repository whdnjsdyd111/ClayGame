package main.common;

import java.awt.event.ActionListener;

import javax.swing.JButton;

public class Buttons extends JButton {

	public Buttons(int x, int y, String name, ActionListener i) {
		setText(name);
		setSize(300, 60);
		setLocation(x, y);
		
		this.addActionListener(i);
	}
}
