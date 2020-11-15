package main.single;

import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;

import main.MainFrame;
import main.common.MyButton;
import main.common.GameScene;
import main.common.MouseShape;
import main.common.Plate;

public abstract class InGame extends JLayeredPane implements GameScene, MouseShape {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	MainFrame frame = null;
	Set<Plate> claies = null;
	JLabel game_time = null;
	JLabel game_score = null;
	JButton to_single = null;
	JButton restart = null;
	int score = 0;
	boolean isEnd = false;
	
	Runnable time_start = null;
	Runnable create_clay = null;
	ThreadGroup claies_group = null;

	private Image image = new ImageIcon(MainFrame.class.getResource("resource/glass_land.jpg")).getImage();
	
	public InGame(MainFrame frame) {
		this.frame = frame;
		
		setLayout(null);
		
		game_time = new JLabel("00:00");		// 게임 타이틀 라벨
		game_time.setBounds(400, 25, 200, 50);
		game_time.setFont(new Font("Consolas", Font.BOLD, 40));
		add(game_time);
		
		game_score = new JLabel("Score " + score);
		game_score.setBounds(650, 25, 200, 50);
		game_score.setFont(new Font("Consolas", Font.BOLD, 40));
		add(game_score);
		
		claies = Collections.synchronizedSet(new HashSet<Plate>());
		
		setMouseShape(this);
		
		to_single = new MyButton(450, 200, "Menu", e -> {
			frame.card.show(frame.getContentPane(), "single");
			hideMenu();
			frame.setCursor(Cursor.getDefaultCursor());
		});
		to_single.setLocation(250, 350);
		
		restart = new MyButton(600, 200, "Restart", e -> {
			hideMenu();
			startGame();
		});
		restart.setLocation(650, 350);
		add(to_single);
		add(restart);
		hideMenu();
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		// TODO Auto-generated method stub
		super.paintComponent(g);
		g.drawImage(image, 0, 0, null);
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
