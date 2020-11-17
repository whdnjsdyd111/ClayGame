package main.common;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import main.MainFrame;

public class Bullet extends JLabel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static final String BULLET_PNG = "resource/bullet.png";
	private static final ImageIcon icon = new ImageIcon(MainFrame.class.getResource(BULLET_PNG));	// 총알 이미지 지정
	
	public Bullet(int x, int y) {
		super(icon);				// 아이콘 지정
		setBounds(x, y, 50, 75);	// 지정된 자리에 위치 설정
	}
}
