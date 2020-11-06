package main.common;

import java.awt.Container;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public interface MouseShape {
	String MOUSE_SHAPE_PATH = "../resource/mouse_shape.png";

	default void setMouseShape(Container cont) {
		Image image = null;
		try {
			image = ImageIO.read(getClass().getResource(MOUSE_SHAPE_PATH));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Image changeImage = image.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
		ImageIcon icon = new ImageIcon(changeImage);
		JLabel label = new JLabel(icon);
		label.setSize(100, 100);
		
		cont.add(label);
		
		cont.addMouseMotionListener(new MouseMotionListener() {
			
			@Override
			public void mouseMoved(MouseEvent e) {
				label.setBounds(e.getX() - 50, e.getY() - 50, label.getWidth(), label.getHeight());
			}
			
			@Override
			public void mouseDragged(MouseEvent e) {
				label.setBounds(e.getX() - 50, e.getY() - 50, label.getWidth(), label.getHeight());
			}
		});
	}
	
	
	/*
	default void setMouseShape(Container cont) {
		Toolkit tk = Toolkit.getDefaultToolkit();
		Image image = tk.getImage(MOUSE_SHAPE_PATH).getScaledInstance(32, 32, Image.SCALE_SMOOTH);
		Point point = new Point(0, 0);
		Cursor cursor = tk.createCustomCursor(image, point, "aim");
		cont.setCursor(cursor);
	}
	 */
}
