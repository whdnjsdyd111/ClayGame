package main.common;

import java.awt.Container;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import main.MainFrame;

public interface MouseShape {
	String MOUSE_SHAPE_PNG = "resource/mouse_shape.png";	// ���콺 �н�
	
	
	default void setMouseShape(Container cont) {	// �ش� ������Ʈ�� ���콺 ������ �߰����ִ� �޼ҵ�
		Image image = new ImageIcon(MainFrame.class.getResource(MOUSE_SHAPE_PNG)).getImage();	// �̹��� �ʱ�ȭ
		
		Image changeImage = image.getScaledInstance(100, 100, Image.SCALE_SMOOTH);	// �ش� �̹����� 100, 100���� �ε巴�� �ٲ��ֱ�
		ImageIcon icon = new ImageIcon(changeImage);
		JLabel shape = new JLabel(icon);	// ���������� �ٲ��� �� �ش� ���������� �� �ʱ�ȭ
		shape.setSize(100, 100);	// 100, 100 ������� �ʱ�ȭ
		
		cont.add(shape);	// �ش� ���콺�� �߰�
		
		cont.addMouseMotionListener(new MouseMotionListener() {	// �ش� ������Ʈ�� ���콺��� ������ ���
			
			@Override
			public void mouseMoved(MouseEvent e) {
				shape.setBounds(e.getX() - 50, e.getY() - 50, shape.getWidth(), shape.getHeight());
			}
			
			@Override
			public void mouseDragged(MouseEvent e) {	// ���콺�� �����̰ų� �巡�� ���� ��� ���� ���� ��ġ�� ����ٴϵ��� ����
				shape.setBounds(e.getX() - 50, e.getY() - 50, shape.getWidth(), shape.getHeight());
			}
		});
	}
	
	
	/*	������ ���콺 Ŀ���� ���� �̹����� ��ü�� �Ϸ��� ������ ���콺 Ŀ���� �̹��� ũ��� 32, 32 �� �ִ�� ����
	default void setMouseShape(Container cont) {
		Toolkit tk = Toolkit.getDefaultToolkit();
		Image image = tk.getImage(MOUSE_SHAPE_PATH).getScaledInstance(32, 32, Image.SCALE_SMOOTH);
		Point point = new Point(0, 0);
		Cursor cursor = tk.createCustomCursor(image, point, "aim");
		cont.setCursor(cursor);
	}
	 */
}
