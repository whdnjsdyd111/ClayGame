package main.mainPane;

import java.awt.Font;
import java.util.Random;

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
		
		JLabel game_title = new JLabel("Clay Game");		// 게임 타이틀 라벨
		game_title.setBounds(500, 100, 200, 50);
		game_title.setFont(new Font("Consolas", Font.BOLD, 40));
		add(game_title);
		
		Buttons single = new Buttons(450, 300, "Single", e -> {
			frame.card.show(frame.getContentPane(), "single");
		});
		add(single);	// 싱글 플레이 장면으로 전환하는 버튼
		
		Buttons multi = new Buttons(450, 400, "Multiple", e -> {
			frame.card.show(frame.getContentPane(), "multiLogin");
		});
		add(multi);		// 멀티 플레이 장면으로 전환하는 버튼
		
		Buttons end = new Buttons(450, 500, "End", e -> {
			frame.dispose();
		});
		add(end);		// 게임을 끝내는 버튼
	}
}