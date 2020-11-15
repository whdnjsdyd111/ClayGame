package main.common;

import java.awt.Image;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class Plate extends JLabel {
	public static final String PLATE_PNG = "../resource/플레이트.png";
	
	public Plate(int height, String file) {
		setBounds(0, height, 100, 50);
		Image image = null;
		try {
			image = ImageIO.read(getClass().getResource(file));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Image changeImage = image.getScaledInstance(100, 50, Image.SCALE_SMOOTH);
		ImageIcon icon = new ImageIcon(changeImage);
		setIcon(icon);
	}
}
