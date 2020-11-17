package main.single;

import java.awt.Font;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JLabel;

import main.MainFrame;
import main.common.Bullet;
import main.common.Plate;
import main.databases.ReloadDialog;
import main.resource.Audios;

public class ReloadMode extends InGame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	int round = 1;	// 라운드
	JLabel round_label = null;	// 라운드 라벨
	Bullet bullet[] = new Bullet[5];	// 총알 5발
	int bullet_count = 4;	// 인덱스에 맞추기
	Thread game_start = null;	// 게임 스타트 쓰레드
	
	Runnable reload_run = () -> {	// 재장전하는 Runnable
		bullet_count = -1;
		for (int i = 0; i < bullet.length; i++) {	// 모든 총알 안보이게 하기
			bullet[i].setVisible(false);
		}
		
		for (int i = 0; i < bullet.length; i++) {	// 밑에부터 차례로 보이도록 해서 장전하는 것처럼 보이도록 함
			bullet[i].setVisible(true);
			bullet_count++;		// 하나씩 보이도록 하면서 총알 개수 더해주기
			if(i == 4)	// 총알을 모두 채웠다면 쓰레드 슬립 하지 않고 바로 리턴
				return;
			try {
				Thread.sleep(250);
			} catch (InterruptedException e) {
			}
		}
	};
	
	Thread reload = new Thread(reload_run);	// 쓰레드 초기화 시켜놓기 초기화 시킨 이유는 재장전 쓰레드가 끝난 상태인지 새로 초기화한 상태인지 알아야 하기 때문에
											// 접근자로 접근을 해야해서 미리 초기화함
	
	public ReloadMode(MainFrame frame) {
		super(frame);
		
		for (int i = 0; i < bullet.length; i++) {		// 총알 위치 설정 및 더하기
			bullet[i] = new Bullet(1100, 600 - i * 80);
			add(bullet[i]);
		}
		
		round_label = new JLabel("1 Round Start");		// 라운드 라벨 초기화
		round_label.setFont(new Font("Consolas", Font.BOLD, 40));	// 폰트 설정
		round_label.setBounds(425, 250, 350, 60);		// 위치 설정
		add(round_label);								// 라운드 추가
		round_label.setVisible(false);					// 라운드 시작을 알리는 쓰레드에서 보여줘야 하기 때문에 처음에는 보이지 않도록 설정
		
		this.addMouseListener(new MouseListener() {	// 마우스 리스너 등록
			
			@Override
			public void mouseReleased(MouseEvent e) {}
			
			@Override
			public void mousePressed(MouseEvent e) {	// 마우스를 눌렀을 때 이벤트
				if(bullet_count != -1 && !(reload.getState() == Thread.State.RUNNABLE || 	// 총알 개수가 없거나 재장전 쓰레드가 실행 가능 또는 웨이팅 상태라면
						reload.getState() == Thread.State.TIMED_WAITING)) {					// 총을 쏘지 못하도록 하기
					Audios.audio(Audios.SHOOT);	// 총소리 내도록 하기
					score = removeClay(e.getX(), e.getY(), claies, game_score, score, round);	// 해당 자리에 플레이트가 있으면 지우고 라운드 수만큼의 점수 더하기
					bullet[bullet_count--].setVisible(false);	// 총알 개수를 지우면서 
				} else {
					Audios.audio(Audios.PERCUSSION);	// 총알이 없다면 격발 소리 내기
				}
			}
			
			@Override
			public void mouseExited(MouseEvent e) {}
			
			@Override
			public void mouseEntered(MouseEvent e) {}
			
			@Override
			public void mouseClicked(MouseEvent e) {}
		});

		setFocusable(true);		// 장전 키를 누를 수 있도록 설정하기
		requestFocus();
		
		this.addKeyListener(new KeyAdapter() {	// 키 리스너 등록하기
			@Override
			public void keyPressed(KeyEvent e) {	// 키를 눌렀을 때 이벤트 
				if(e.getKeyChar() == 'r' && reload.getState() == Thread.State.NEW) {	// r 을 눌렀고 초기화 상태일 때
					reload.start();		// 재장전 스레드 실행하기
					Audios.audio(Audios.RELOAD);	// 장전 소리 내기
				}
				if(e.getKeyChar() == 'r' && reload.getState() == Thread.State.TERMINATED) {		// r 을 눌렀고 재장전 쓰레드 상태가 끝난 상태일 경우
					reload = new Thread(reload_run);	// 새로 초기화하고 실행하기
					reload.start();
					Audios.audio(Audios.RELOAD);		// 장전 소리 내기
				}
			}
		});
		
		game_time.setBounds(350, 25, 300, 50);
		game_score.setBounds(650, 25, 300, 50);	// 게임 시간과 스코어 라벨 위치 설정
		
		time_start = () -> {	// 게임 시작 Runnable
			
			for (round = 1; round <= 10; round++) {		// 10번의 라운드를 실행
				repaint();	// 라운드 시작할 때 리페인트 하기
				claies_group = new ThreadGroup("clay group");	// 라운드 시작할 때 새롭게 쓰레드 그룹 초기화
				claies_group.setDaemon(true);
				
				game_time.setText(round + " Round");
				
				round_label.setText(round + " Round Start!");	// 라운드 시작을 알리는 라벨
				round_label.setVisible(true);
				try { Thread.sleep(1000); } catch (InterruptedException e) {}
				for (int i = 3; i > 0; i--) {	// 3 2 1 카운트를 세고 시작하기
					round_label.setText("       " + i);
					try { Thread.sleep(1000); } catch (InterruptedException e) {}
				}
				round_label.setVisible(false);
				
				Thread round_create =  new Thread(create_clay = () -> {		// 라운드에 따라 플레이트를 생성하는 쓰레드 초기화
					
					while(true) {	// 인터럽트가 걸릴때 까지 진행
						try {
							int ran = (int) (Math.random() * 3) + 5;	//	0.5 ~ 0.8 사이로 플레이트 생성하기
							Thread.sleep(100 * ran);
						} catch (InterruptedException e) {
							break;
						}
					
						new Thread(claies_group, () -> {	// 플레이트를 날리는 스레드
							
							int height = (int) (Math.random() * 300);	// 높이는 0 ~ 300 사이로 랜덤으로 설정
							
							Plate plate = new Plate(height);	// 높이를 인수로 주고 플레이트 생성하기
							claies.add(plate);
							add(plate);		// 해쉬셋과 현재 페인에 플레이트 추가하기
							
							int ran = (int) (Math.random() * 2);	// 왼쪽과 오른쪽을 랜덤으로 지정할 랜덤 숫자
							// 1 라운드 속도 1.5
							// 10 라운드 속도 3
							
							float speed = (float) 1.5 + (float) 0.15 * round;	// 라운드가 진행할 수록 속도가 더 빨라지도록 설정
							
							if(ran == 0) {
								for (float i = -100; i < 1200; i+=speed) {	// -100 ~ 1200 까지 플레이트를 날리며 속도 만큼 더하기
									plate.setLocation((int) i, getHeight(i, height));	// 
									try {
										Thread.sleep(1);
									} catch (InterruptedException e) {
										claies.remove(plate);
										remove(plate);
									}
								}
							} else {
								for (float i = 1200; i > -100; i-=speed) {
									plate.setLocation((int) i, getHeight(i, height));
									try {
										Thread.sleep(1);
									} catch (InterruptedException e) {
										claies.remove(plate);
										remove(plate);
									}
								}
							}
							claies.remove(plate);
							remove(plate);
							
						}).start();
					}
				});
				round_create.start();	// 쓰레드 시작
				
				try {
					Thread.sleep(10000);	// 라운드를 시작하고 10초간 멈추기
					
					claies_group.interrupt();	// 10초가 지난 후 플레이트 그룹과 라운드 쓰레드 인터럽트 후 다시 리페인트
					round_create.interrupt();
					repaint();
				} catch (InterruptedException e) {
					
				}
			}
			repaint();	// 10라운드가 모두 끝난 후 리페인트 하고 메뉴 띄우기
			showMenu();
			
			frame.dialog = new ReloadDialog(frame, "Reload Mode Rank", score + "");	// 랭킹 등록 다이알롣그 띄우기
			
			score = 0;	// 스코어 초기화
		};
	}
	
	@Override
	public void startGame() {	// 게임 시작 메소드
		game_score.setText("Score 0");	// game_score 라벨 Score 0 으로 초기화
		game_start = new Thread(time_start, "Time Start");	// 게임 시작 쓰레드 시작
		game_start.start();
		frame.setCursor(frame.blankCursor);	// 커서 없애기
	}
}
