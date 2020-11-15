package main.common;

import java.awt.Container;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import main.MainFrame;

public interface MouseShape {
	String MOUSE_SHAPE_PNG = "resource/mouse_shape.png";
	
	
	default void setMouseShape(Container cont) {
		Image image = new ImageIcon(MainFrame.class.getResource(MOUSE_SHAPE_PNG)).getImage();
		
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
