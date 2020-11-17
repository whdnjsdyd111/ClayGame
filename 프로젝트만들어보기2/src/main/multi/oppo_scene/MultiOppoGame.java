package main.multi.oppo_scene;

import java.awt.Dialog;
import java.awt.Font;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JLabel;

import main.MainFrame;
import main.common.GameScene;
import main.common.Plate;

public abstract class MultiOppoGame extends Dialog implements GameScene  {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	MainFrame frame = null;
	Set<Plate> claies = null;
	public JLabel game_time = null;
	public JLabel game_score = null;
	boolean isEnd = false;
	
	Runnable time_start = null;
	ThreadGroup claies_group = null;
	
	public MultiOppoGame(MainFrame frame) {
		super(frame, "상대 화면", false);
		this.frame = frame;
		
		setBounds(1000, 200, 900, 800);
		setLayout(null);
		
		game_time = new JLabel("00:00");		// 게임 타이틀 라벨
		game_time.setBounds(400, 50, 300, 50);
		game_time.setFont(new Font("Consolas", Font.BOLD, 40));
		add(game_time);
		
		game_score = new JLabel();	// 게임 스코어 라벨
		game_score.setBounds(400, 350, 200, 50);
		game_score.setFont(new Font("Consolas", Font.BOLD, 40));
		game_score.setVisible(false);	// 상대방이 게임 끝나면 보여주기 위해 처음에 안보이도록 하기
		add(game_score);
		
		claies = Collections.synchronizedSet(new HashSet<Plate>());	// 플레이트 해쉬셋 초기화
		claies_group = new ThreadGroup("clay group");	// 쓰레드 그룹 초기화
		claies_group.setDaemon(true);
		
	}
	
	abstract public void create_clay(int height, int ran);	// 플레이트를 생성하는 추상 메소드
	abstract public void endGame(int score);				// 게임이 끝났을때 실행할 추상 메소드
	public void receiveMousePoint(int x, int y) {			// 상대방에게서 마우스 포인터를 얻어 해당 자리의 플레이트를 제거할 메소드
		removeClay(x, y, claies, game_score);
	}
	abstract public void reload();		// 재장전 추상 메소드
	
	@Override
	public void hideMenu() {}
	@Override
	public void showMenu() {}
}
