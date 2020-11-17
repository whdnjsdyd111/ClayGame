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
	
	Thread start_infinity = null;	// �������� ������ ������ 
	Thread start_time = null;		// ���� �ð� ������
	int minu = 0;

	public InfinityMode(MainFrame frame) {
		super(frame);
		
		score = 10;	// ó�� ���ھ� 10���� ��� 0�� �Ǹ� ���� ���� ���߱�
		game_time.setSize(200, 50);	// ���� �ð� ������ ������
		
		this.addMouseListener(new MouseListener() {		// ���콺 ������ ���
			
			@Override
			public void mouseReleased(MouseEvent e) {}
			
			@Override
			public void mousePressed(MouseEvent e) {
				Audios.audio(Audios.SHOOT);	// �ѼҸ� ����
				removeClay(e.getX(), e.getY(), claies, game_score);	// �ش� �ڸ��� �÷���Ʈ ���ֱ�
			}
			
			@Override
			public void mouseExited(MouseEvent e) {}
			
			@Override
			public void mouseEntered(MouseEvent e) {}
			
			@Override
			public void mouseClicked(MouseEvent e) {}
		});
		
		time_start = () -> {	// ���� �ð� Runnable
			int i = 0;	// �и� ������ �ð�
			
			while(true) {	// ���� ��忡 �°� �������� ���ư���
				int sec = i / 100;	// ������
				minu = sec / 60;	// ��
				int mili = i % 100;	// �и� ������
				game_time.setText((minu < 10 ? "0" + minu : minu) + ":" + (sec < 10 ? "0" + sec : sec) + ":" + 
						(mili < 10 ? "0" + mili : mili));	// ������ ������ �� �� �и��� ����
				
				try {
					Thread.sleep(10);	// 0.01 ���� ����
				} catch (InterruptedException e) {
					break;
				}
				
				i++;
			}
			
			frame.dialog = new InfinityDialog(frame, "Infinity Mode Rank", game_time.getText());	// ���� ������ ��ŷ ��� ���̾˷α� ����
		};
		
		create_clay = () -> {	// �÷���Ʈ ���� Runnable
			claies_group = new ThreadGroup("clay group");	// ������ �׷� �ʱ�ȭ
			claies_group.setDaemon(true);
			
			while(true) {	// ���� ��忡 �°� �������� �ݺ�
				
				try {
					int ran = (int) (Math.random() * 3) + 2;	// 0.4 ~ 0.8 �� ���̷� �÷���Ʈ ����
					Thread.sleep(200 * ran);
				} catch (InterruptedException e) {
					stopInfinity();	// ���ͷ�Ʈ �ɸ� �� ���� ��� ���ߴ� �޼ҵ� ����
					start_time.interrupt();		// ���� �ð� ������ ���ͷ�Ʈ
					break;	// �ݺ��� ���߱�
				}
				
				new Thread(claies_group, () -> {	// �÷���Ʈ ���� ������ 
					
					int height = (int) (Math.random() * 300);	// ���� 0 ~ 300 ���� ����
					
					Plate plate = new Plate(height);	// ���̿� �°� �÷���Ʈ �ʱ�ȭ
					claies.add(plate);
					add(plate);		// �ؽ��°� ���� ���ο� �÷���Ʈ �߰� 
					
					int ran = (int) (Math.random() * 2);	// ���� �Ǵ� �����ʿ��� ������ ��ġ �������� ����
					float speed = (float) (minu > 0 ? minu * 1.5 : 0);	// 1���� ���� �� ���� ���ǵ� �ø���
					
					if(ran == 0) {	// 0 �̸� -100 ~ 1200 ���� �̵�
						for (float i = -100; i < 1200; i+=(2 + speed)) {
							plate.setLocation((int) i, getHeight(i, height));
							try {
								Thread.sleep(1);
							} catch (InterruptedException e) {
								claies.remove(plate);
								remove(plate);
							}
						}
					} else {	// 1 �̸� 1200 ~ -100 ���� �̵�
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

					if(plate.isVisible() && score != 0) {	// �÷��̾ �÷���Ʈ�� ������ �� ���ھ� 1 ����
						changeText(--score);	// �ؽ�Ʈ �ٲٴ� �޼ҵ�
						
						if(score == 0)	// ���ھ 0�� �Ǿ��� ��
							start_infinity.interrupt();	// ������ ���ͷ�Ʈ �ɱ�
					}
					
					claies.remove(plate);	// �̵��� ��ġ�� �ؽ��°� ���� ���ο��� ����
					remove(plate);
					
				}).start();
			}
		};
	}
	
	@Override
	public void startGame() {	// ���� ���� �޼ҵ�
		frame.setCursor(frame.blankCursor);	// Ŀ�� ���ֱ�
		game_score.setText("Score 10");		// ���ھ� Score 10 ���� �ʱ�ȭ
		start_time = new Thread(time_start, "Time Start");	// ���� �ð� ������ �ʱ�ȭ
		start_infinity = new Thread(create_clay, "Create Clay");	// ���� ���� ������ �ʱ�ȭ
		
		start_time.start();		// �� ������ ����
		start_infinity.start();
		score = 10;	// ���ھ� 10���� �ʱ�ȭ
		minu = 0;	// �� 0 ���� �ʱ�ȭ
	}
	
	private void changeText(int score) {	// �ؽ�Ʈ �ش� ���ھ�� �ٲٴ� �޼ҵ�
		game_score.setText("Score " + score);	
	}
	
	private void stopInfinity() {	// �÷���Ʈ �׷� ���ͷ�Ʈ �ɱ�
		claies_group.interrupt();
		
		repaint();		// �ٽ� �׸��� �޴� �����ֱ�
		showMenu();
	}
}
