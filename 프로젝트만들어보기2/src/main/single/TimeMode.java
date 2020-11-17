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
		
		this.addMouseListener(new MouseListener() {	// ���콺 ������ ���
			
			@Override
			public void mouseReleased(MouseEvent e) {}
			
			@Override
			public void mousePressed(MouseEvent e) {	// ���콺�� ������ ��
				Audios.audio(Audios.SHOOT);	// �� ��� �Ҹ� ����
				score = removeClay(e.getX(), e.getY(), claies, game_score, score);	// �ش� ����Ʈ�� �÷���Ʈ�� ����� �����ٸ� ���� �ø���
			}
			
			@Override
			public void mouseExited(MouseEvent e) {}
			
			@Override
			public void mouseEntered(MouseEvent e) {}
			
			@Override
			public void mouseClicked(MouseEvent e) {}
		});
		
		time_start = () -> {	// �ð��� ���� Runnable
			for (int i = 60; i >= 0; i--) {		// �ð� ������ 1��
				int sec = i % 60;
				game_time.setText("0" + (i / 60) + ":" + (sec < 10 ? "0" + sec : sec));	// �� �ʸ��� ���� �ٲٱ�
				
				try {
					Thread.sleep(1000);	// 1�� ���� ������ ���߱�
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
			isEnd = true;	// ������ �������� �˸�
		};
		
		create_clay = () -> {	// �÷���Ʈ�� �����س��� Runnable
			claies_group = new ThreadGroup("clay group");	// ������ �׷� �ʱ�ȭ
			claies_group.setDaemon(true);
			
			while(!isEnd) {	// ������ ������ ������ �÷���Ʈ�� ����
				
				try {
					int ran = (int) (Math.random() * 4) + 1;	// �������� 0.1 ~ 0.4 �� ���̷� �÷���Ʈ ����
					Thread.sleep(100 * ran);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				new Thread(claies_group, () -> {	// �÷���Ʈ�� �����̰� �ϴ� ������
					
					int height = (int) (Math.random() * 300);	// 0 ~ 300 ������ ���̸� �������� ����
					
					Plate plate = new Plate(height);	// �ش� ���̷� �÷���Ʈ �����ϱ�
					claies.add(plate);
					add(plate);		// �ؽ� �� �� ���� ���ο� �÷���Ʈ �߰��ϱ�
					
					int ran = (int) (Math.random() * 2);	// ���� �Ǵ� �����ʿ��� �������� ������ �ڸ� ����
					
					if(ran == 0) {	// ran�� 0 �̾��� �� ���ʿ��� ���ư���
						for (int i = -100; i < 1200; i+=2) {	// -100 ~ 1200 ���� ���ư���
							plate.setLocation(i, getHeight(i, height));		// �ش� �÷���Ʈ�� ��ġ�� �ٲٱ�
							try {
								Thread.sleep(1);
							} catch (InterruptedException e) {	// �����尡 ���ͷ�Ʈ �ɷ��� �� �ؽ� �°� ���� ���ο��� �ش� �÷���Ʈ ����
								claies.remove(plate);
								remove(plate);
							}
						}
					} else {
						for (int i = 1200; i > -100; i-=2) {	// ran�� 1 �̾��� ��� 1200 ���� -100 ���� ���ư���
							plate.setLocation(i, getHeight(i, height));
							try {
								Thread.sleep(1);
							} catch (InterruptedException e) {	// �����尡 ���ͷ�Ʈ �ɷ��� �� �ؽ� �°� ���� ���ο��� �ش� �÷���Ʈ ����
								claies.remove(plate);
								remove(plate);
							}
						}
					}
					claies.remove(plate);	// �ش� �÷���Ʈ�� �� ���ư��ٸ� �ؽ� �°� ���� ���ο��� �ش� �÷���Ʈ ����
					remove(plate);
					
				}).start();	// �÷���Ʈ ���ư��� ������ ����
			}
			claies_group.interrupt();	// ������ ������  ������ �׷� ���ͷ�Ʈ �ɱ�
			
			repaint();
			showMenu();		// �ٽ� �׸��� �޴� �����ֱ�
			isEnd = false;	// �ٽ� �Ҹ��� false�� �ǵ����ֱ�
			frame.dialog = new TimeDialog(frame, "Time Mode Rank", score + "");		// ��ŷ ��� ���̾˷α� ����
			score = 0;	// ���ھ� �ٽ� 0���� �ʱ�ȭ
		};
	}
	
	@Override
	public void startGame() {	// ���� ���� �ϴ� �޼ҵ�
		game_score.setText("Score 0");	// ���� ���ھ� �ؽ�Ʈ Score 0 ���� �ʱ�ȭ
		new Thread(time_start, "Time Start").start();		// ���� �ð� ������ ����
		new Thread(create_clay, "Create Clay").start();		// �÷���Ʈ ���� ������ ����
		frame.setCursor(frame.blankCursor);					// Ŀ�� ���� Ŀ���� �ٲٱ�
	}
	
}
