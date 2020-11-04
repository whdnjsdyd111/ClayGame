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
		
		JLabel game_title = new JLabel("클레어 사격 게임");		// 게임 타이틀 라벨
		game_title.setBounds(450, 100, 300, 50);
		game_title.setFont(new Font("휴먼편지체", Font.BOLD, 40));
		add(game_title);
		
		Buttons single = new Buttons(450, 300, "싱글 플레이", e -> {
			frame.card.show(frame.getContentPane(), "single");
		});
		add(single);	// 싱글 플레이 장면으로 전환하는 버튼
		
		Buttons multi = new Buttons(450, 400, "멀티 플레이", e -> {
			frame.card.show(frame.getContentPane(), "multi");
		});
		add(multi);		// 멀티 플레이 장면으로 전환하는 버튼
		
		Buttons end = new Buttons(450, 500, "끝내기", e -> {
			frame.dispose();
		});
		add(end);		// 게임을 끝내는 버튼
	}
}