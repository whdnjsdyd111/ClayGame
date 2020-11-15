package main.multi.my_scene;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.channels.SocketChannel;

import javax.swing.JLabel;
import javax.swing.border.LineBorder;

import main.MainFrame;
import main.common.Plate;
import main.multi.AlertDialog;
import main.resource.Audios;

public class MyInfinity extends MultiMyGame {
	
	Thread start_infinity = null;
	Thread start_time = null;
	int minu = 0;
	int i = 0;
	
	public MyInfinity(MainFrame frame, SocketChannel socketChannel) {
		super(frame, socketChannel);
		score = 10;
		game_time.setSize(200, 50);
		
		this.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				Audios.audio(Audios.SHOOT);
				removeClay(e.getX(), e.getY(), claies, game_score);
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
			i = 0;
			
			while(true) {
				int sec = i / 100;
				minu = sec / 60;
				int mili = i % 100;
				game_time.setText((minu < 10 ? "0" + minu : minu) + ":" + (sec < 10 ? "0" + sec : sec) + ":" + (mili < 10 ? "0" + mili : mili));
				
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					break;
				}
				
				i++;
			}
			
			// frame.dialog = new InfinityDialog(frame, "infinity mode rank", game_time.getText());
		};

		create_clay = () -> {
			claies_group = new ThreadGroup("clay group");
			claies_group.setDaemon(true);
			
			while(true) {
				
				try {
					int ran = (int) (Math.random() * 3) + 2;
					Thread.sleep(200 * ran);
				} catch (InterruptedException e) {
					stopInfinity();
					start_time.interrupt();
					break;
				}
				
				new Thread(claies_group, () -> {
					
					int height = (int) (Math.random() * 200) + 100;
					
					Plate plate = new Plate(height, Plate.PLATE_PNG);
					claies.add(plate);
					add(plate);
					
					int ran = (int) (Math.random() * 2);
					float speed = (float) (minu > 0 ? minu * 1.5 : 0);
					sendClayPoint(height, ran);
					
					if(ran == 0) {
						for (float i = -100; i < 900; i+=(2 + speed)) {
							plate.setLocation((int) i, getHeight(i, height));
							try {
								Thread.sleep(1);
							} catch (InterruptedException e) {
								claies.remove(plate);
								remove(plate);
							}
						}
					} else {
						for (float i = 900; i > -100; i-=(2 + speed)) {
							plate.setLocation((int) i, getHeight(i, height));
							try {
								Thread.sleep(1);
							} catch (InterruptedException e) {
								claies.remove(plate);
								remove(plate);
							}
						}
					}

					if(plate.isVisible() && score != 0) {
						changeText(--score);
						
						if(score == 0)
							start_infinity.interrupt();
					}
					
					claies.remove(plate);
					remove(plate);
					
				}).start();
			}
		};
		startGame();
		setVisible(true);
	}
	
	@Override
	public void startGame() {
		score = 10;
		minu = 0;
		frame.setCursor(frame.blankCursor);
		game_score.setText("Score 10");
		start_time = new Thread(time_start, "Time Start");
		start_infinity = new Thread(create_clay, "Create Clay");
		
		start_time.start();
		start_infinity.start();
	}
	
	private void changeText(int score) {
		game_score.setText("Score " + score);
	}
	
	private void stopInfinity() {
		claies_group.interrupt();
		
		endGame();
		repaint();
		showMenu();
	}
	
	@Override
	public void endGame() {
		try {
			IntBuffer intBuffer = IntBuffer.wrap(new int[] { -1, i });
			ByteBuffer byteBuffer = ByteBuffer.allocate(intBuffer.capacity() * 4);
			for (int i = 0; i < intBuffer.capacity(); i++) {
				byteBuffer.putInt(intBuffer.get(i));
			}
			byteBuffer.flip();
			socketChannel.write(byteBuffer);
			
			System.out.println("[게임 끝남 전송, 무한 모드 점수 전송]");
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
