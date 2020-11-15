package main.common;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JTextField;
import javax.swing.border.LineBorder;

public class MyTextField extends JTextField {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public MyTextField(int x, int y) {
		setBounds(x, y, 400, 50);
		setFont(new Font("D2 Coding", Font.BOLD, 20));
		setForeground(Color.WHITE);
		setBackground(Color.decode("#828282"));
		addFocusListener(new FocusListener() {
			
			@Override
			public void focusLost(FocusEvent e) {
				setBackground(Color.BLACK);
			}
			
			@Override
			public void focusGained(FocusEvent e) {
				setBackground(Color.decode("#505050"));
			}
		});
		setBorder(new LineBorder(Color.decode("#646464"), 3));
	}
	
}
