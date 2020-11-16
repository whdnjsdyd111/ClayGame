package main.multi.my_scene;

import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.channels.SocketChannel;

import javax.swing.JLabel;

import main.MainFrame;
import main.common.Plate;
import main.multi.AlertDialog;
import main.multi.oppo_scene.MultiOppoGame;
import main.resource.Audios;

public class MyTime extends MultiMyGame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private JLabel endScore_label;
	
	public MyTime(MainFrame frame, SocketChannel socketChannel, MultiOppoGame oppo) {
		super(frame, socketChannel, oppo);
		
		this.endScore_label = new JLabel();
		endScore_label.setBounds(300, 300, 400, 50);
		endScore_label.setVisible(true);
		endScore_label.setFont(new Font("Consolas", Font.BOLD, 40));
		add(endScore_label);
		
		this.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				Audios.audio(Audios.SHOOT);
				score = removeClay(e.getX(), e.getY(), claies, game_score, score);
				sendMousePoint(e.getX(), e.getY());
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

		time_start = () -> {
			for (int i = 60; i >= 0; i--) {
				int sec = i % 60;
				game_time.setText("0" + (i / 60) + ":" + (sec < 10 ? "0" + sec : sec));
				
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
			isEnd = true;
		};
		
		create_clay = () -> {
			claies_group = new ThreadGroup("clay group");
			claies_group.setDaemon(true);
			
			while(!isEnd) {
				
				try {
					int ran = (int) (Math.random() * 4) + 1;
					Thread.sleep(100 * ran);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				new Thread(claies_group, () -> {
					
					int height = (int) (Math.random() * 200) + 100;
					
					Plate plate = new Plate(height);
					claies.add(plate);
					add(plate);
					
					int ran = (int) (Math.random() * 2);
					sendClayPoint(height, ran);
					
					if(ran == 0) {
						for (int i = -100; i < 900; i+=2) {
							plate.setLocation(i, getHeight(i, height));
							try {
								Thread.sleep(1);
							} catch (InterruptedException e) {
								claies.remove(plate);
								remove(plate);
							}
						}
					} else {
						for (int i = 900; i > -100; i-=2) {
							plate.setLocation(i, getHeight(i, height));
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
			endGame();
			repaint();
			showMenu();
			// frame.dialog = new TimeDialog(frame, "time mode rank", score + "");
		};

		startGame();
		setVisible(true);
	}
	
	@Override
	public void startGame() {
		game_score.setText("Score 0");
		new Thread(time_start, "Time Start").start();
		new Thread(create_clay, "Create Clay").start();
		frame.setCursor(frame.blankCursor);
	}
	
	@Override
	public void endGame() {
		endScore_label.setText("Score : " + score);
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
}
