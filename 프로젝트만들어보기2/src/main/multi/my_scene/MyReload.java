package main.multi.my_scene;

import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.channels.SocketChannel;

import javax.swing.JLabel;

import main.MainFrame;
import main.common.Plate;
import main.multi.AlertDialog;
import main.resource.Audios;
import main.single.Bullet;

public class MyReload extends MultiMyGame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	int round = 1;
	JLabel round_label = null;
	Bullet bullet[] = new Bullet[5];	// 총알 5발
	int bullet_count = 4;	// 인덱스에 맞추기
	Thread game_start = null;
	
	private JLabel endScore_label = null;
	
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
	
	public MyReload(MainFrame frame, SocketChannel socketChannel) {
		super(frame, socketChannel);
		
		this.endScore_label = new JLabel();
		endScore_label.setBounds(250, 300, 400, 50);
		endScore_label.setVisible(true);
		endScore_label.setFont(new Font("Consolas", Font.BOLD, 40));
		add(endScore_label);
		
		for (int i = 0; i < bullet.length; i++) {
			bullet[i] = new Bullet(800, 725 - i * 80);
			add(bullet[i]);
		}
		
		round_label = new JLabel("1 Round Start!");
		round_label.setFont(new Font("Consolas", Font.BOLD, 40));
		round_label.setBounds(275, 250, 350, 60);
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
					sendMousePoint(e.getX(), e.getY());
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
					sendReload();
				}
				if(e.getKeyChar() == 'r' && reload.getState() == Thread.State.TERMINATED) {
					reload = new Thread(reload_run);
					reload.start();
					Audios.audio(Audios.RELOAD);
					sendReload();
				}
			}
		});
		
		game_time.setBounds(200, 35, 300, 50);
		game_score.setBounds(500, 35, 300, 50);
		
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
							
							int height = (int) (Math.random() * 200) + 100;
							
							Plate plate = new Plate(height);
							claies.add(plate);
							add(plate);
							
							int ran = (int) (Math.random() * 2);
							// 1 라운드 속도 1.5
							// 10 라운드 속도 3
							sendClayPoint(height, ran);
							
							float speed = (float) 1.5 + (float) 0.15 * round;
							
							if(ran == 0) {
								for (float i = -100; i < 900; i+=speed) {
									plate.setLocation((int) i, getHeight(i, height));
									try {
										Thread.sleep(1);
									} catch (InterruptedException e) {
										claies.remove(plate);
										remove(plate);
									}
								}
							} else {
								for (float i = 900; i > -100; i-=speed) {
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
					
					round_create.interrupt();
					repaint();
				} catch (InterruptedException e) {
					
				}
			}
			repaint();
			showMenu();
			
			// frame.dialog = new ReloadDialog(frame, "reload mode rank", score + "");
			
			endGame();
		};
		
		startGame();
		setVisible(true);
	}
	
	@Override
	public void startGame() {
		game_score.setText("Score 0");
		game_start = new Thread(time_start, "Time Start");
		game_start.start();
		frame.setCursor(frame.blankCursor);
	}
	
	@Override
	public void endGame() {
		endScore_label.setText("Final Score : " + score);
		endScore_label.setVisible(true);
		
		try {
			IntBuffer intBuffer = IntBuffer.wrap(new int[] { -1, score });
			ByteBuffer byteBuffer = ByteBuffer.allocate(intBuffer.capacity() * 4);
			for (int i = 0; i < intBuffer.capacity(); i++) {
				byteBuffer.putInt(intBuffer.get(i));
			}
			byteBuffer.flip();
			socketChannel.write(byteBuffer);
			
			System.out.println("[게임 끝남 전송, 시간 제한 모드 점수 전송]");
		} catch (Exception e) {
			e.printStackTrace();
			try {
				System.out.println("[상대방과 통신 두절 : " + socketChannel.getRemoteAddress() + 
						" : " + Thread.currentThread().getName() + "]");				
				socketChannel.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
			isEnd = true;
			frame.setCursor(Cursor.getDefaultCursor());
			frame.remove(this);
			new AlertDialog(frame, AlertDialog.MSG_NET1);
		}
	}
	
	private void sendReload() {
		try {
			IntBuffer intBuffer = IntBuffer.wrap(new int[] { -2 });
			ByteBuffer byteBuffer = ByteBuffer.allocate(intBuffer.capacity() * 4);
			
			byteBuffer.putInt(intBuffer.get(0));
			byteBuffer.flip();
			socketChannel.write(byteBuffer);
			
			System.out.println("[재장전 전송]");
		} catch (Exception e) {
			e.printStackTrace();
			try {
				System.out.println("[상대방과 통신 두절 : " + socketChannel.getRemoteAddress() + 
						" : " + Thread.currentThread().getName() + "]");				
				socketChannel.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
			isEnd = true;
			frame.setCursor(Cursor.getDefaultCursor());
			frame.remove(this);
			new AlertDialog(frame, AlertDialog.MSG_NET1);
		}
	}
}
