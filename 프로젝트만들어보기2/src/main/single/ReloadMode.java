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
	
	int round = 1;	// ����
	JLabel round_label = null;	// ���� ��
	Bullet bullet[] = new Bullet[5];	// �Ѿ� 5��
	int bullet_count = 4;	// �ε����� ���߱�
	Thread game_start = null;	// ���� ��ŸƮ ������
	
	Runnable reload_run = () -> {	// �������ϴ� Runnable
		bullet_count = -1;
		for (int i = 0; i < bullet.length; i++) {	// ��� �Ѿ� �Ⱥ��̰� �ϱ�
			bullet[i].setVisible(false);
		}
		
		for (int i = 0; i < bullet.length; i++) {	// �ؿ����� ���ʷ� ���̵��� �ؼ� �����ϴ� ��ó�� ���̵��� ��
			bullet[i].setVisible(true);
			bullet_count++;		// �ϳ��� ���̵��� �ϸ鼭 �Ѿ� ���� �����ֱ�
			if(i == 4)	// �Ѿ��� ��� ä���ٸ� ������ ���� ���� �ʰ� �ٷ� ����
				return;
			try {
				Thread.sleep(250);
			} catch (InterruptedException e) {
			}
		}
	};
	
	Thread reload = new Thread(reload_run);	// ������ �ʱ�ȭ ���ѳ��� �ʱ�ȭ ��Ų ������ ������ �����尡 ���� �������� ���� �ʱ�ȭ�� �������� �˾ƾ� �ϱ� ������
											// �����ڷ� ������ �ؾ��ؼ� �̸� �ʱ�ȭ��
	
	public ReloadMode(MainFrame frame) {
		super(frame);
		
		for (int i = 0; i < bullet.length; i++) {		// �Ѿ� ��ġ ���� �� ���ϱ�
			bullet[i] = new Bullet(1100, 600 - i * 80);
			add(bullet[i]);
		}
		
		round_label = new JLabel("1 Round Start");		// ���� �� �ʱ�ȭ
		round_label.setFont(new Font("Consolas", Font.BOLD, 40));	// ��Ʈ ����
		round_label.setBounds(425, 250, 350, 60);		// ��ġ ����
		add(round_label);								// ���� �߰�
		round_label.setVisible(false);					// ���� ������ �˸��� �����忡�� ������� �ϱ� ������ ó������ ������ �ʵ��� ����
		
		this.addMouseListener(new MouseListener() {	// ���콺 ������ ���
			
			@Override
			public void mouseReleased(MouseEvent e) {}
			
			@Override
			public void mousePressed(MouseEvent e) {	// ���콺�� ������ �� �̺�Ʈ
				if(bullet_count != -1 && !(reload.getState() == Thread.State.RUNNABLE || 	// �Ѿ� ������ ���ų� ������ �����尡 ���� ���� �Ǵ� ������ ���¶��
						reload.getState() == Thread.State.TIMED_WAITING)) {					// ���� ���� ���ϵ��� �ϱ�
					Audios.audio(Audios.SHOOT);	// �ѼҸ� ������ �ϱ�
					score = removeClay(e.getX(), e.getY(), claies, game_score, score, round);	// �ش� �ڸ��� �÷���Ʈ�� ������ ����� ���� ����ŭ�� ���� ���ϱ�
					bullet[bullet_count--].setVisible(false);	// �Ѿ� ������ ����鼭 
				} else {
					Audios.audio(Audios.PERCUSSION);	// �Ѿ��� ���ٸ� �ݹ� �Ҹ� ����
				}
			}
			
			@Override
			public void mouseExited(MouseEvent e) {}
			
			@Override
			public void mouseEntered(MouseEvent e) {}
			
			@Override
			public void mouseClicked(MouseEvent e) {}
		});

		setFocusable(true);		// ���� Ű�� ���� �� �ֵ��� �����ϱ�
		requestFocus();
		
		this.addKeyListener(new KeyAdapter() {	// Ű ������ ����ϱ�
			@Override
			public void keyPressed(KeyEvent e) {	// Ű�� ������ �� �̺�Ʈ 
				if(e.getKeyChar() == 'r' && reload.getState() == Thread.State.NEW) {	// r �� ������ �ʱ�ȭ ������ ��
					reload.start();		// ������ ������ �����ϱ�
					Audios.audio(Audios.RELOAD);	// ���� �Ҹ� ����
				}
				if(e.getKeyChar() == 'r' && reload.getState() == Thread.State.TERMINATED) {		// r �� ������ ������ ������ ���°� ���� ������ ���
					reload = new Thread(reload_run);	// ���� �ʱ�ȭ�ϰ� �����ϱ�
					reload.start();
					Audios.audio(Audios.RELOAD);		// ���� �Ҹ� ����
				}
			}
		});
		
		game_time.setBounds(350, 25, 300, 50);
		game_score.setBounds(650, 25, 300, 50);	// ���� �ð��� ���ھ� �� ��ġ ����
		
		time_start = () -> {	// ���� ���� Runnable
			
			for (round = 1; round <= 10; round++) {		// 10���� ���带 ����
				repaint();	// ���� ������ �� ������Ʈ �ϱ�
				claies_group = new ThreadGroup("clay group");	// ���� ������ �� ���Ӱ� ������ �׷� �ʱ�ȭ
				claies_group.setDaemon(true);
				
				game_time.setText(round + " Round");
				
				round_label.setText(round + " Round Start!");	// ���� ������ �˸��� ��
				round_label.setVisible(true);
				try { Thread.sleep(1000); } catch (InterruptedException e) {}
				for (int i = 3; i > 0; i--) {	// 3 2 1 ī��Ʈ�� ���� �����ϱ�
					round_label.setText("       " + i);
					try { Thread.sleep(1000); } catch (InterruptedException e) {}
				}
				round_label.setVisible(false);
				
				Thread round_create =  new Thread(create_clay = () -> {		// ���忡 ���� �÷���Ʈ�� �����ϴ� ������ �ʱ�ȭ
					
					while(true) {	// ���ͷ�Ʈ�� �ɸ��� ���� ����
						try {
							int ran = (int) (Math.random() * 3) + 5;	//	0.5 ~ 0.8 ���̷� �÷���Ʈ �����ϱ�
							Thread.sleep(100 * ran);
						} catch (InterruptedException e) {
							break;
						}
					
						new Thread(claies_group, () -> {	// �÷���Ʈ�� ������ ������
							
							int height = (int) (Math.random() * 300);	// ���̴� 0 ~ 300 ���̷� �������� ����
							
							Plate plate = new Plate(height);	// ���̸� �μ��� �ְ� �÷���Ʈ �����ϱ�
							claies.add(plate);
							add(plate);		// �ؽ��°� ���� ���ο� �÷���Ʈ �߰��ϱ�
							
							int ran = (int) (Math.random() * 2);	// ���ʰ� �������� �������� ������ ���� ����
							// 1 ���� �ӵ� 1.5
							// 10 ���� �ӵ� 3
							
							float speed = (float) 1.5 + (float) 0.15 * round;	// ���尡 ������ ���� �ӵ��� �� ���������� ����
							
							if(ran == 0) {
								for (float i = -100; i < 1200; i+=speed) {	// -100 ~ 1200 ���� �÷���Ʈ�� ������ �ӵ� ��ŭ ���ϱ�
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
				round_create.start();	// ������ ����
				
				try {
					Thread.sleep(10000);	// ���带 �����ϰ� 10�ʰ� ���߱�
					
					claies_group.interrupt();	// 10�ʰ� ���� �� �÷���Ʈ �׷�� ���� ������ ���ͷ�Ʈ �� �ٽ� ������Ʈ
					round_create.interrupt();
					repaint();
				} catch (InterruptedException e) {
					
				}
			}
			repaint();	// 10���尡 ��� ���� �� ������Ʈ �ϰ� �޴� ����
			showMenu();
			
			frame.dialog = new ReloadDialog(frame, "Reload Mode Rank", score + "");	// ��ŷ ��� ���̾ˎ�� ����
			
			score = 0;	// ���ھ� �ʱ�ȭ
		};
	}
	
	@Override
	public void startGame() {	// ���� ���� �޼ҵ�
		game_score.setText("Score 0");	// game_score �� Score 0 ���� �ʱ�ȭ
		game_start = new Thread(time_start, "Time Start");	// ���� ���� ������ ����
		game_start.start();
		frame.setCursor(frame.blankCursor);	// Ŀ�� ���ֱ�
	}
}
