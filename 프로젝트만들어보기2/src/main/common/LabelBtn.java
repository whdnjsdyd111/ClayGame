package main.common;

import java.awt.Font;
import java.awt.event.MouseListener;

import javax.swing.JLabel;

public class LabelBtn extends JLabel {
	
	public LabelBtn(int x, int y, String name, MouseListener i) {
		setText(name);
		setBounds(x, y, 250, 100);
		
		this.addMouseListener(i);
	}
}
