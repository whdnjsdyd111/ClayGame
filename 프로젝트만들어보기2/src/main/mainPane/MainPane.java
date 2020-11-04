package main.mainPane;

import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JLayeredPane;

import main.MainFrame;
import main.common.Buttons;
import main.common.MouseShape;

public class MainPane extends JLayeredPane implements MouseShape {
	private MainFrame frame;
	
	public MainPane(MainFrame frame) {
		this.frame = frame;
		setLayout(null);
		
		JLabel game_title = new JLabel("Ŭ���� ��� ����");		// ���� Ÿ��Ʋ ��
		game_title.setBounds(450, 100, 300, 50);
		game_title.setFont(new Font("�޸�����ü", Font.BOLD, 40));
		add(game_title);
		
		Buttons single = new Buttons(450, 300, "�̱� �÷���", e -> {
			frame.card.show(frame.getContentPane(), "single");
		});
		add(single);	// �̱� �÷��� ������� ��ȯ�ϴ� ��ư
		
		Buttons multi = new Buttons(450, 400, "��Ƽ �÷���", e -> {
			frame.card.show(frame.getContentPane(), "multi");
		});
		add(multi);		// ��Ƽ �÷��� ������� ��ȯ�ϴ� ��ư
		
		Buttons end = new Buttons(450, 500, "������", e -> {
			frame.dispose();
		});
		add(end);		// ������ ������ ��ư
	}
}