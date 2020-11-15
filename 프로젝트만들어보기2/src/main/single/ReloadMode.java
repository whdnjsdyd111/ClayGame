package main.single;

import java.awt.Font;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JLabel;

import main.MainFrame;
import main.common.Plate;
import main.databases.ReloadDialog;
import main.resource.Audios;

public class ReloadMode extends InGame {
	
	int round = 1;
	JLabel round_label = null;
	Bullet bullet[] = new Bullet[5];	// 총알 5발
	int bullet_count = 4;	// 인덱스에 맞추기
	Thread game_start = null;
	
	Runnable reload_run = () -> {
		bullet_count = -1;
		for (int i = 0; i < bullet.length; i++) {
			bullet[i].setVisible(false);
		}
		
		for (int i = 0; i < bullet.length; i++) {
			bullet[i].setVisible(true);
			bullet_count++;
			if(i == 4)
				return;
			try {
				Thread.sleep(250);
			} catch (InterruptedException e) {
			}
		}
	};
	
	Thread reload = new Thread(reload_run);
	
	public ReloadMode(MainFrame frame) {
		super(frame);
		
		for (int i = 0; i < bullet.length; i++) {
			bullet[i] = new Bullet(1100, 600 - i * 80);
			add(bullet[i]);
		}
		
		round_label = new JLabel("1 Round Start");
		round_label.setFont(new Font("Consolas", Font.BOLD, 40));
		round_label.setBounds(425, 250, 350, 60);
		add(round_label);
		round_label.setVisible(false);
		
		this.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				if(bullet_count != -1 && !(reload.getState() == Thread.State.RUNNABLE || reload.getState() == Thread.State.TIMED_WAITING)) {
					Audios.audio(Audios.SHOOT);
					score = removeClay(e.getX(), e.getY(), claies, game_score, score, round);
					bullet[bullet_count--].setVisible(false);
				} else {
					Audios.audio(Audios.PERCUSSION);
				}
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				
			}
		});

		setFocusable(true);
		requestFocus();
		
		this.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyChar() == 'r' && reload.getState() == Thread.State.NEW) {
					reload.start();
					Audios.audio(Audios.RELOAD);
				}
				if(e.getKeyChar() == 'r' && reload.getState() == Thread.State.TERMINATED) {
					reload = new Thread(reload_run);
					reload.start();
					Audios.audio(Audios.RELOAD);
				}
			}
		});
		
		game_time.setBounds(350, 25, 300, 50);
		game_score.setBounds(650, 25, 300, 50);
		
		time_start = () -> {
			
			for (round = 1; round <= 10; round++) {
				repaint();
				claies_group = new ThreadGroup("clay group");
				claies_group.setDaemon(true);
				
				game_time.setText(round + " Round");
				
				round_label.setText(round + " Round Start!");
				round_label.setVisible(true);
				try { Thread.sleep(1000); } catch (InterruptedException e) {}
				for (int i = 3; i > 0; i--) {
					round_label.setText("       " + i);
					try { Thread.sleep(1000); } catch (InterruptedException e) {}
				}
				round_label.setVisible(false);
				
				Thread round_create =  new Thread(create_clay = () -> {
					
					while(true) {
						try {
							int ran = (int) (Math.random() * 3) + 5;
							Thread.sleep(100 * ran);
						} catch (InterruptedException e) {
							break;
						}
					
						new Thread(claies_group, () -> {
							
							int height = (int) (Math.random() * 300);
							
							Plate plate = new Plate(height, Plate.PLATE_PNG);
							claies.add(plate);
							add(plate);
							
							int ran = (int) (Math.random() * 2);
							// 1 라운드 속도 1.5
							// 10 라운드 속도 3
							
							float speed = (float) 1.5 + (float) 0.15 * round;
							
							if(ran == 0) {
								for (float i = -100; i < 1200; i+=speed) {
									plate.setLocation((int) i, getHeight(i, height));
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
				round_create.start();
				
				try {
					Thread.sleep(10000);
					
					claies_group.interrupt();
					round_create.interrupt();
					repaint();
				} catch (InterruptedException e) {
					
				}
			}
			repaint();
			showMenu();
			
			frame.dialog = new ReloadDialog(frame, "Reload Mode Rank", score + "");
			
			score = 0;
		};
	}
	
	@Override
	public void startGame() {
		game_score.setText("Score 0");
		game_start = new Thread(time_start, "Time Start");
		game_start.start();
		frame.setCursor(frame.blankCursor);
	}
}
