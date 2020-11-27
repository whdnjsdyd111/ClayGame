package main.mainPane;

import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JLayeredPane;

import main.MainFrame;
import main.common.MyButton;

public class MainPane extends JLayeredPane {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public MainPane(MainFrame frame) {
		setLayout(null);
		
		JLabel game_title = new JLabel("Clay Game");		// ���� Ÿ��Ʋ ��
		game_title.setBounds(500, 100, 200, 50);
		game_title.setFont(new Font("Consolas", Font.BOLD, 40));
		add(game_title);
		
		MyButton single = new MyButton(450, 300, "Single", e -> {
			frame.card.show(frame.getContentPane(), "single");
		});
		add(single);	// �̱� �÷��� ������� ��ȯ�ϴ� ��ư
		
		MyButton multi = new MyButton(450, 400, "Multiple", e -> {
			frame.card.show(frame.getContentPane(), "multiLogin");
		});
		add(multi);		// ��Ƽ �÷��� ������� ��ȯ�ϴ� ��ư
		
		MyButton end = new MyButton(450, 500, "End", e -> {
			frame.dispose();
		});
		add(end);		// ������ ������ ��ư
	}
}