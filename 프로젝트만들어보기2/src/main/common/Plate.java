package main.common;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import main.MainFrame;

public class Plate extends JLabel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static final String PLATE_PNG = "resource/�÷���Ʈ.png";
	private static final ImageIcon icon = new ImageIcon(MainFrame.class.getResource(PLATE_PNG));
	
	public Plate(int height) {
		super(icon);
		setBounds(0, height, 100, 50);
	}
	
}
