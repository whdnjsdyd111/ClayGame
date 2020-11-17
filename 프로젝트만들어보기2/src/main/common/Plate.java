package main.common;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import main.MainFrame;

public class Plate extends JLabel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static final String PLATE_PNG = "resource/플레이트.png";
	private static final ImageIcon icon = new ImageIcon(MainFrame.class.getResource(PLATE_PNG));		// 플레이트 이미지 
	
	public Plate(int height) {
		super(icon);	// 아이콘 지정
		setBounds(0, height, 100, 50);	// 위치 설정
	}
	
}
