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
	
	public MyTextField(int x, int y) {	// Ŀ���� �ؽ�Ʈ �ʵ�
		setBounds(x, y, 400, 50);		// ��ġ ����
		setFont(new Font("D2 Coding", Font.BOLD, 20));	// ��Ʈ ����
		setForeground(Color.WHITE);		// ���ڻ� �Ͼ������
		setBackground(Color.decode("#828282"));	// ��� ���� ȸ������ ����
		addFocusListener(new FocusListener() {
			
			@Override
			public void focusLost(FocusEvent e) {	// ��Ŀ���� �Ҿ��� �� ��� ������
				setBackground(Color.BLACK);
			}
			
			@Override
			public void focusGained(FocusEvent e) {		// ��Ŀ���Ǿ��� �� ��� ���� ȸ�� ����
				setBackground(Color.decode("#505050"));
			}
		});
		setBorder(new LineBorder(Color.decode("#646464"), 3));	// ��輱 �ſ� ���� ȸ������ ����
	}
	
}
