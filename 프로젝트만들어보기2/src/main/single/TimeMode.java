package main.single;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import main.MainFrame;
import main.common.Plate;
import main.databases.TimeDialog;
import main.resource.Audios;

public class TimeMode extends InGame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public TimeMode(MainFrame frame) {
		super(frame);
		
		this.addMouseListener(new MouseListener() {	// 마우스 리스너 등록
			
			@Override
			public void mouseReleased(MouseEvent e) {}
			
			@Override
			public void mousePressed(MouseEvent e) {	// 마우스를 눌렀을 때
				Audios.audio(Audios.SHOOT);	// 총 쏘는 소리 시작
				score = removeClay(e.getX(), e.getY(), claies, game_score, score);	// 해당 포인트의 플레이트를 지우고 지웠다면 점수 올리기
			}
			
			@Override
			public void mouseExited(MouseEvent e) {}
			
			@Override
			public void mouseEntered(MouseEvent e) {}
			
			@Override
			public void mouseClicked(MouseEvent e) {}
		});
		
		time_start = () -> {	// 시간을 제는 Runnable
			for (int i = 60; i >= 0; i--) {		// 시간 제한은 1분
				int sec = i % 60;
				game_time.setText("0" + (i / 60) + ":" + (sec < 10 ? "0" + sec : sec));	// 각 초마다 글자 바꾸기
				
				try {
					Thread.sleep(1000);	// 1초 동안 스레드 멈추기
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
			isEnd = true;	// 게임이 끝났음을 알림
		};
		
		create_clay = () -> {	// 플레이트를 생성해내는 Runnable
			claies_group = new ThreadGroup("clay group");	// 쓰레드 그룹 초기화
			claies_group.setDaemon(true);
			
			while(!isEnd) {	// 게임이 끝나기 전까지 플레이트를 생성
				
				try {
					int ran = (int) (Math.random() * 4) + 1;	// 랜덤으로 0.1 ~ 0.4 초 사이로 플레이트 생성
					Thread.sleep(100 * ran);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				new Thread(claies_group, () -> {	// 플레이트를 움직이게 하는 쓰레드
					
					int height = (int) (Math.random() * 300);	// 0 ~ 300 사이의 높이를 랜덤으로 지정
					
					Plate plate = new Plate(height);	// 해당 높이로 플레이트 생성하기
					claies.add(plate);
					add(plate);		// 해쉬 셋 과 현재 페인에 플레이트 추가하기
					
					int ran = (int) (Math.random() * 2);	// 왼쪽 또는 오른쪽에서 랜덤으로 생성할 자리 지정
					
					if(ran == 0) {	// ran이 0 이었을 때 왼쪽에서 날아가기
						for (int i = -100; i < 1200; i+=2) {	// -100 ~ 1200 까지 날아가기
							plate.setLocation(i, getHeight(i, height));		// 해당 플레이트의 위치를 바꾸기
							try {
								Thread.sleep(1);
							} catch (InterruptedException e) {	// 쓰레드가 인터럽트 걸렸을 시 해쉬 셋과 현재 페인에서 해당 플레이트 삭제
								claies.remove(plate);
								remove(plate);
							}
						}
					} else {
						for (int i = 1200; i > -100; i-=2) {	// ran이 1 이었을 경우 1200 에서 -100 까지 날아가기
							plate.setLocation(i, getHeight(i, height));
							try {
								Thread.sleep(1);
							} catch (InterruptedException e) {	// 쓰레드가 인터럽트 걸렸을 시 해쉬 셋과 현재 페인에서 해당 플레이트 삭제
								claies.remove(plate);
								remove(plate);
							}
						}
					}
					claies.remove(plate);	// 해당 플레이트가 다 날아갔다면 해쉬 셋과 현재 페인에서 해당 플레이트 삭제
					remove(plate);
					
				}).start();	// 플레이트 날아가는 쓰레드 실행
			}
			claies_group.interrupt();	// 게임이 끝나면  쓰레드 그룹 인터럽트 걸기
			
			repaint();
			showMenu();		// 다시 그리고 메뉴 보여주기
			isEnd = false;	// 다시 불리안 false로 되돌려주기
			frame.dialog = new TimeDialog(frame, "Time Mode Rank", score + "");		// 랭킹 등록 다이알로그 띄우기
			score = 0;	// 스코어 다시 0으로 초기화
		};
	}
	
	@Override
	public void startGame() {	// 게임 시작 하는 메소드
		game_score.setText("Score 0");	// 게임 스코어 텍스트 Score 0 으로 초기화
		new Thread(time_start, "Time Start").start();		// 게임 시간 쓰레드 시작
		new Thread(create_clay, "Create Clay").start();		// 플레이트 생성 쓰레드 시작
		frame.setCursor(frame.blankCursor);					// 커서 투명 커서로 바꾸기
	}
	
}
