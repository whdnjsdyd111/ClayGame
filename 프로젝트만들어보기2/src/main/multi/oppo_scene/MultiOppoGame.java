package main.multi.oppo_scene;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dialog;
import java.awt.Font;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.border.LineBorder;

import main.MainFrame;
import main.common.Buttons;
import main.common.GameScene;

public abstract class MultiOppoGame extends Dialog implements GameScene  {
	
	MainFrame frame = null;
	Set<JLabel> claies = null;
	JLabel game_time = null;
	JLabel game_score = null;
	boolean isEnd = false;
	
	Runnable time_start = null;
	ThreadGroup claies_group = null;
	
	public MultiOppoGame(MainFrame frame) {
		super(frame, "상대 화면", false);
		this.frame = frame;
		
		setBounds(1000, 200, 900, 800);
		setLayout(null);
		
		game_time = new JLabel("00:00");		// 게임 타이틀 라벨
		game_time.setBounds(400, 50, 200, 50);
		game_time.setFont(new Font("휴먼편지체", Font.BOLD, 40));
		add(game_time);
		
		game_score = new JLabel();
		game_score.setBounds(400, 350, 200, 50);
		game_score.setFont(new Font("휴먼편지체", Font.BOLD, 40));
		game_score.setVisible(false);
		add(game_score);
		
		claies = Collections.synchronizedSet(new HashSet<JLabel>());
		claies_group = new ThreadGroup("clay group");
		claies_group.setDaemon(true);
		
	}
	
	abstract public void create_clay(int height, int ran);
	abstract public void endGame(int score);
	public void receiveMousePoint(int x, int y) {
		removeClay(x, y, claies, game_score);
	}
	
	@Override
	public void hideMenu() {}
	@Override
	public void showMenu() {}
}
