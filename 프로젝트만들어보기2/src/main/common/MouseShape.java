package main.common;

import java.awt.Container;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public interface MouseShape {
	String MOUSE_SHAPE_PATH = "../resource/mouse_shape.png";
	
	default void setMouseShape(Container cont, String file) {
		Image image = null;
		try {
			image = ImageIO.read(getClass().getResource(file));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Image changeImage = image.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
		ImageIcon icon = new ImageIcon(changeImage);
		JLabel shape = new JLabel(icon);
		shape.setSize(100, 100);
		
		cont.add(shape);
		
		cont.addMouseMotionListener(new MouseMotionListener() {
			
			@Override
			public void mouseMoved(MouseEvent e) {
				shape.setBounds(e.getX() - 50, e.getY() - 50, shape.getWidth(), shape.getHeight());
			}
			
			@Override
			public void mouseDragged(MouseEvent e) {
				shape.setBounds(e.getX() - 50, e.getY() - 50, shape.getWidth(), shape.getHeight());
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
	
	default void setPlayer(Container cont) {
		
	}
}
