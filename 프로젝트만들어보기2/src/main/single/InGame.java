package main.single;

import java.awt.Cursor;
import java.awt.Font;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;

import main.MainFrame;
import main.common.Buttons;
import main.common.GameScene;
import main.common.MouseShape;

public abstract class InGame extends JLayeredPane implements GameScene, MouseShape {
	
	MainFrame frame = null;
	Set<JLabel> claies = null;
	JLabel game_time = null;
	JLabel game_score = null;
	JButton to_single = null;
	JButton restart = null;
	int score = 0;
	boolean isEnd = false;
	
	Runnable time_start = null;
	Runnable create_clay = null;
	ThreadGroup claies_group = null;
	
	public InGame(MainFrame frame) {
		this.frame = frame;
		
		setLayout(null);
		
		game_time = new JLabel("00:00");		// 게임 타이틀 라벨
		game_time.setBounds(400, 25, 100, 50);
		game_time.setFont(new Font("휴먼편지체", Font.BOLD, 40));
		add(game_time);
		
		game_score = new JLabel("Score " + score);
		game_score.setBounds(650, 25, 200, 50);
		game_score.setFont(new Font("휴먼편지체", Font.BOLD, 40));
		add(game_score);
		
		claies = Collections.synchronizedSet(new HashSet<JLabel>());
		
		setMouseShape(this);
		
		to_single = new Buttons(450, 200, "메뉴로", e -> {
			frame.card.show(frame.getContentPane(), "single");
			hideMenu();
			frame.setCursor(Cursor.getDefaultCursor());
		});
		to_single.setLocation(250, 350);
		
		restart = new Buttons(600, 200, "재시작", e -> {
			hideMenu();
			startGame();
		});
		restart.setLocation(650, 350);
		add(to_single);
		add(restart);
		hideMenu();
	}
	

	@Override
	public void showMenu() {
		to_single.setVisible(true);
		restart.setVisible(true);
		frame.setCursor(Cursor.getDefaultCursor());
	}
	
	@Override
	public void hideMenu() {
		to_single.setVisible(false);
		restart.setVisible(false);
		frame.setCursor(frame.blankCursor);
	}
}
