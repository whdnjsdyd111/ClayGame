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
	// 싱글 게임의 공통된 개념들을 모은 추상 클래스, GameScene과 MouseShape 인터페이스를 구현
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	MainFrame frame = null;		// 메인 프레임
	Set<Plate> claies = null;	// 플레이트를 저장할 Set
	JLabel game_time = null;	// 게임 시간 라벨
	JLabel game_score = null;	// 게임 점수 라벨
	JButton to_single = null;	// 싱글 메뉴로 돌아가는 버튼 
	JButton restart = null;		// 재시작을 물을 버튼
	int score = 0;				// 스코어 0부터 시작
	boolean isEnd = false;		// 게임이 끝났는지 여부의 불리안
	
	Runnable time_start = null;		//  게임 시작 Runnable
	Runnable create_clay = null;		// 플레이트 생성 Runnable
	ThreadGroup claies_group = null;	// 플레이트 그룹

	private Image image = new ImageIcon(MainFrame.class.getResource("resource/glass_land.jpg")).getImage();	// 게임 배경에 넣을 이미지
	
	public InGame(MainFrame frame) {
		this.frame = frame;
		
		setLayout(null);	// 레이아웃 널로 초기화
		
		game_time = new JLabel("00:00");		// 게임 시간 라벨
		game_time.setBounds(400, 25, 200, 50);	// 게임 시간  위치 설정
		game_time.setFont(new Font("Consolas", Font.BOLD, 40));	// 게임 시간 폰트
		add(game_time);	// 게임 시간 라벨 추가
		
		game_score = new JLabel("Score " + score);	// 게임 점수 라벨
		game_score.setBounds(650, 25, 200, 50);		// 위치 설정
		game_score.setFont(new Font("Consolas", Font.BOLD, 40));	// 게임 점수 라벨 폰트 설정
		add(game_score);	// 게임 점수 라벨 추가
		
		claies = Collections.synchronizedSet(new HashSet<Plate>());	// 플레이트를 저장할 Set 초기화
		
		setMouseShape(this);	// 마우스를 따라다니는 조준 모양 라벨 추가
		
		to_single = new MyButton(450, 200, "Menu", e -> {	// 싱글 메뉴로 돌아가는 버튼 초기화
			frame.card.show(frame.getContentPane(), "single");
			hideMenu();
			frame.setCursor(Cursor.getDefaultCursor());	// 마우스 커서를 다시 디폴트 커서로 되돌리기
		});
		to_single.setLocation(250, 350);	// 위치 설정
		
		restart = new MyButton(600, 200, "Restart", e -> {	// 재시작 버튼 초기화
			hideMenu();
			startGame();
		});
		restart.setLocation(650, 350);	// 위치 설정
		add(to_single);
		add(restart);	// 두 버튼 추가
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
