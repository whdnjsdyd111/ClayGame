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
		
		JLabel game_title = new JLabel("시간 모드");		// 게임 타이틀 라벨
		game_title.setBounds(450, 100, 300, 50);
		game_title.setFont(new Font("휴먼편지체", Font.BOLD, 40));
		add(game_title);
		
		clay = new HashSet<>();
		
	}
	
	Thread create_clay = new Thread(() -> {
		
	});
	
	Thread time = new Thread(() -> {
		
	});
}
