package main.single;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import main.MainFrame;
import main.common.Plate;
import main.databases.InfinityDialog;
import main.resource.Audios;

public class InfinityMode extends InGame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	Thread start_infinity = null;	// 무한으로 진행할 쓰레드 
	Thread start_time = null;		// 게임 시간 쓰레드
	int minu = 0;

	public InfinityMode(MainFrame frame) {
		super(frame);
		
		score = 10;	// 처음 스코어 10으로 잡고 0이 되면 게임 진행 멈추기
		game_time.setSize(200, 50);	// 게임 시간 사이즈 재조정
		
		this.addMouseListener(new MouseListener() {		// 마우스 리스너 등록
			
			@Override
			public void mouseReleased(MouseEvent e) {}
			
			@Override
			public void mousePressed(MouseEvent e) {
				Audios.audio(Audios.SHOOT);	// 총소리 내기
				removeClay(e.getX(), e.getY(), claies, game_score);	// 해당 자리의 플레이트 없애기
			}
			
			@Override
			public void mouseExited(MouseEvent e) {}
			
			@Override
			public void mouseEntered(MouseEvent e) {}
			
			@Override
			public void mouseClicked(MouseEvent e) {}
		});
		
		time_start = () -> {	// 게임 시간 Runnable
			int i = 0;	// 밀리 세컨드 시간
			
			while(true) {	// 무한 모드에 맞게 무한으로 돌아가기
				int sec = i / 100;	// 세컨드
				minu = sec / 60;	// 분
				int mili = i % 100;	// 밀리 세컨드
				game_time.setText((minu < 10 ? "0" + minu : minu) + ":" + (sec < 10 ? "0" + sec : sec) + ":" + 
						(mili < 10 ? "0" + mili : mili));	// 위에서 지정한 분 초 밀리초 지정
				
				try {
					Thread.sleep(10);	// 0.01 마다 쉬기
				} catch (InterruptedException e) {
					break;
				}
				
				i++;
			}
			
			frame.dialog = new InfinityDialog(frame, "Infinity Mode Rank", game_time.getText());	// 게임 끝나면 랭킹 등록 다이알로그 띄우기
		};
		
		create_clay = () -> {	// 플레이트 생성 Runnable
			claies_group = new ThreadGroup("clay group");	// 스레드 그룹 초기화
			claies_group.setDaemon(true);
			
			while(true) {	// 무한 모드에 맞게 무한으로 반복
				
				try {
					int ran = (int) (Math.random() * 3) + 2;	// 0.4 ~ 0.8 초 사이로 플레이트 생성
					Thread.sleep(200 * ran);
				} catch (InterruptedException e) {
					stopInfinity();	// 인터럽트 걸릴 시 무한 모드 멈추는 메소드 실행
					start_time.interrupt();		// 게임 시간 스레드 인터럽트
					break;	// 반복문 멈추기
				}
				
				new Thread(claies_group, () -> {	// 플레이트 생성 쓰레드 
					
					int height = (int) (Math.random() * 300);	// 높이 0 ~ 300 사이 생성
					
					Plate plate = new Plate(height);	// 높이에 맞게 플레이트 초기화
					claies.add(plate);
					add(plate);		// 해쉬셋과 현재 페인에 플레이트 추가 
					
					int ran = (int) (Math.random() * 2);	// 왼쪽 또는 오른쪽에서 생성할 위치 랜덤으로 지정
					float speed = (float) (minu > 0 ? minu * 1.5 : 0);	// 1분이 지날 때 마다 스피드 올리기
					
					if(ran == 0) {	// 0 이면 -100 ~ 1200 으로 이동
						for (float i = -100; i < 1200; i+=(2 + speed)) {
							plate.setLocation((int) i, getHeight(i, height));
							try {
								Thread.sleep(1);
							} catch (InterruptedException e) {
								claies.remove(plate);
								remove(plate);
							}
						}
					} else {	// 1 이면 1200 ~ -100 으로 이동
						for (float i = 1200; i > -100; i-=(2 + speed)) {
							plate.setLocation((int) i, getHeight(i, height));
							try {
								Thread.sleep(1);
							} catch (InterruptedException e) {
								claies.remove(plate);
								remove(plate);
							}
						}
					}

					if(plate.isVisible() && score != 0) {	// 플레이어가 플레이트를 놓쳤을 시 스코어 1 빼기
						changeText(--score);	// 텍스트 바꾸는 메소드
						
						if(score == 0)	// 스코어가 0이 되었을 시
							start_infinity.interrupt();	// 쓰레드 인터럽트 걸기
					}
					
					claies.remove(plate);	// 이동을 마치고 해쉬셋과 현재 페인에서 제거
					remove(plate);
					
				}).start();
			}
		};
	}
	
	@Override
	public void startGame() {	// 게임 시작 메소드
		frame.setCursor(frame.blankCursor);	// 커서 없애기
		game_score.setText("Score 10");		// 스코어 Score 10 으로 초기화
		start_time = new Thread(time_start, "Time Start");	// 게임 시간 쓰레드 초기화
		start_infinity = new Thread(create_clay, "Create Clay");	// 무한 시작 쓰레드 초기화
		
		start_time.start();		// 두 쓰레드 시작
		start_infinity.start();
		score = 10;	// 스코어 10으로 초기화
		minu = 0;	// 분 0 으로 초기화
	}
	
	private void changeText(int score) {	// 텍스트 해당 스코어로 바꾸는 메소드
		game_score.setText("Score " + score);	
	}
	
	private void stopInfinity() {	// 플레이트 그룹 인터럽트 걸기
		claies_group.interrupt();
		
		repaint();		// 다시 그리고 메뉴 보여주기
		showMenu();
	}
}
