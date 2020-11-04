package main.single;

import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JLayeredPane;

import main.MainFrame;

public class InfinityMode extends JLayeredPane {

	MainFrame frame = null;
	
	public InfinityMode(MainFrame frame) {
		this.frame = frame;
		
		JLabel game_title = new JLabel("무한모드");		// 게임 타이틀 라벨
		game_title.setBounds(450, 100, 300, 50);
		game_title.setFont(new Font("휴먼편지체", Font.BOLD, 40));
		add(game_title);
		
	}
}
