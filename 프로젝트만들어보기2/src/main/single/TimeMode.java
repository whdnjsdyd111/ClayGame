package main.single;

import java.awt.Font;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JLabel;
import javax.swing.JLayeredPane;

import main.MainFrame;

public class TimeMode extends JLayeredPane {
	
	MainFrame frame = null;
	Set<JLabel> clay = null;
	
	public TimeMode(MainFrame frame) {
		
		this.frame = frame;
		
		JLabel game_title = new JLabel("�ð� ���");		// ���� Ÿ��Ʋ ��
		game_title.setBounds(450, 100, 300, 50);
		game_title.setFont(new Font("�޸�����ü", Font.BOLD, 40));
		add(game_title);
		
		clay = new HashSet<>();
		
	}
	
	Thread create_clay = new Thread(() -> {
		
	});
	
	Thread time = new Thread(() -> {
		
	});
}
