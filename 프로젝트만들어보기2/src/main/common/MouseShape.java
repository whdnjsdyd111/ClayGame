package main.common;

import java.awt.Container;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import main.MainFrame;

public interface MouseShape {
	String MOUSE_SHAPE_PNG = "resource/mouse_shape.png";	// 마우스 패스
	
	
	default void setMouseShape(Container cont) {	// 해당 컴포넌트에 마우스 조준을 추가해주는 메소드
		Image image = new ImageIcon(MainFrame.class.getResource(MOUSE_SHAPE_PNG)).getImage();	// 이미지 초기화
		
		Image changeImage = image.getScaledInstance(100, 100, Image.SCALE_SMOOTH);	// 해당 이미지를 100, 100으로 부드럽게 바꿔주기
		ImageIcon icon = new ImageIcon(changeImage);
		JLabel shape = new JLabel(icon);	// 아이콘으로 바꿔준 후 해당 아이콘으로 라벨 초기화
		shape.setSize(100, 100);	// 100, 100 사이즈로 초기화
		
		cont.add(shape);	// 해당 마우스를 추가
		
		cont.addMouseMotionListener(new MouseMotionListener() {	// 해당 컴포넌트에 마우스모션 리스너 등록
			
			@Override
			public void mouseMoved(MouseEvent e) {
				shape.setBounds(e.getX() - 50, e.getY() - 50, shape.getWidth(), shape.getHeight());
			}
			
			@Override
			public void mouseDragged(MouseEvent e) {	// 마우스를 움직이거나 드래그 했을 경우 조준 라벨의 위치가 따라다니도록 설정
				shape.setBounds(e.getX() - 50, e.getY() - 50, shape.getWidth(), shape.getHeight());
			}
		});
	}
	
	
	/*	원래는 마우스 커서를 조준 이미지로 대체를 하려고 했으나 마우스 커서의 이미지 크기는 32, 32 가 최대라서 실패
	default void setMouseShape(Container cont) {
		Toolkit tk = Toolkit.getDefaultToolkit();
		Image image = tk.getImage(MOUSE_SHAPE_PATH).getScaledInstance(32, 32, Image.SCALE_SMOOTH);
		Point point = new Point(0, 0);
		Cursor cursor = tk.createCustomCursor(image, point, "aim");
		cont.setCursor(cursor);
	}
	 */
}
