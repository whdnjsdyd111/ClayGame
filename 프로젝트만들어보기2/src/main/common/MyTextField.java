package main.common;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JTextField;
import javax.swing.border.LineBorder;

public class MyTextField extends JTextField {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public MyTextField(int x, int y) {	// 커스텀 텍스트 필드
		setBounds(x, y, 400, 50);		// 위치 설정
		setFont(new Font("D2 Coding", Font.BOLD, 20));	// 폰트 설정
		setForeground(Color.WHITE);		// 글자색 하얀색으로
		setBackground(Color.decode("#828282"));	// 배경 연한 회색으로 지정
		addFocusListener(new FocusListener() {
			
			@Override
			public void focusLost(FocusEvent e) {	// 포커스를 잃었을 때 배경 검은색
				setBackground(Color.BLACK);
			}
			
			@Override
			public void focusGained(FocusEvent e) {		// 포커스되었을 때 배경 진한 회색 지정
				setBackground(Color.decode("#505050"));
			}
		});
		setBorder(new LineBorder(Color.decode("#646464"), 3));	// 경계선 매우 연한 회색으로 지정
	}
	
}
