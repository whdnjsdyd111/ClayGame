package main.single;

import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JLayeredPane;

import main.MainFrame;

public class InfinityMode extends JLayeredPane {

	MainFrame frame = null;
	
	public InfinityMode(MainFrame frame) {
		this.frame = frame;
		
		JLabel game_title = new JLabel("���Ѹ��");		// ���� Ÿ��Ʋ ��
		game_title.setBounds(450, 100, 300, 50);
		game_title.setFont(new Font("�޸�����ü", Font.BOLD, 40));
		add(game_title);
		
	}
}
