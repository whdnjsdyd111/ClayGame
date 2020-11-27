package main.single;

import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JLayeredPane;

import main.MainFrame;
import main.common.MyButton;

public class SingleGamePane extends JLayeredPane {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	MainFrame frame = null;
	
	public SingleGamePane(MainFrame frame) {
		setLayout(null);
		this.frame = frame;
		
		JLabel game_title = new JLabel("Clay Game");		// 게임 타이틀 라벨
		game_title.setBounds(500, 100, 200, 50);
		game_title.setFont(new Font("Consolas", Font.BOLD, 40));
		add(game_title);
		
		MyButton infinity = new MyButton(100, 250, "Infinity Mode", e -> {	// 무한 모드를 시작하는 버튼
			frame.card.show(frame.getContentPane(), "infinity");
			frame.infinityMode.startGame();
		});
		add(infinity);
		
		MyButton time = new MyButton(450, 250, "Time Mode", e -> {	// 시간 제한 모드를 시작하는 버튼
			frame.card.show(frame.getContentPane(), "time");
			frame.time.startGame();
		});
		add(time);
		
		MyButton reload = new MyButton(800, 250, "Reload Mode", e -> {	// 장전 모드를 시작하는 버튼
			frame.card.show(frame.getContentPane(), "reload");
			frame.reload.startGame();
		});
		add(reload);
		
		MyButton to_main = new MyButton(275, 480, "Main", e -> {	// 메인 화면으로 가는 버튼
			frame.card.show(frame.getContentPane(), "main");
		});
		add(to_main);
		
		MyButton end = new MyButton(625, 480, "End", e -> {		// 게임을 끝내는 버튼
			frame.dispose();
		});
		add(end);
	}
}
