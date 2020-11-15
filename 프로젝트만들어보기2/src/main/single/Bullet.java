package main.single;

import java.awt.Image;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class Bullet extends JLabel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Bullet(int x, int y) {
		setBounds(x, y, 50, 75);
		
		Image image = null;
		try {
			image = ImageIO.read(getClass().getResource("../resource/bullet.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Image changeImage = image.getScaledInstance(50, 75, Image.SCALE_SMOOTH);
		ImageIcon icon = new ImageIcon(changeImage);
		
		setIcon(icon);
	}
}
