package main.single;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import main.MainFrame;

public class Bullet extends JLabel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static final String BULLET_PNG = "resource/bullet.png";
	private static final ImageIcon icon = new ImageIcon(MainFrame.class.getResource(BULLET_PNG));
	
	public Bullet(int x, int y) {
		setBounds(x, y, 50, 75);
		setIcon(icon);
	}
}
