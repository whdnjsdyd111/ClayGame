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
		
		JLabel game_title = new JLabel("Clay Game");		// 게임 타이틀 라벨
		game_title.setBounds(500, 100, 200, 50);
		game_title.setFont(new Font("Consolas", Font.BOLD, 40));
		add(game_title);
		
		MyButton single = new MyButton(450, 300, "Single", e -> {
			frame.card.show(frame.getContentPane(), "single");
		});
		add(single);	// 싱글 플레이 장면으로 전환하는 버튼
		
		MyButton multi = new MyButton(450, 400, "Multiple", e -> {
			frame.card.show(frame.getContentPane(), "multiLogin");
		});
		add(multi);		// 멀티 플레이 장면으로 전환하는 버튼
		
		MyButton end = new MyButton(450, 500, "End", e -> {
			frame.dispose();
		});
		add(end);		// 게임을 끝내는 버튼
	}
}