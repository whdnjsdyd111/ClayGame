package main.single;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JLabel;
import javax.swing.border.LineBorder;

import main.MainFrame;

public class TimeMode extends InGame {
	
	public TimeMode(MainFrame frame) {
		super(frame);
		
		this.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				removeClay(e.getX(), e.getY(), claies, game_score, ++score);
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
			for (int i = 10; i >= 0; i--) {
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
					
					int height = (int) (Math.random() * 300);
					
					JLabel clay_label = new JLabel("clay");
					clay_label.setBounds(0, height, 100, 50);
					clay_label.setBorder(new LineBorder(Color.BLUE, 3));
					claies.add(clay_label);
					add(clay_label);
					
					int ran = (int) (Math.random() * 2);
					
					if(ran == 0) {
						for (int i = -100; i < 1200; i+=2) {
							clay_label.setLocation(i, getHeight(i, height));
							try {
								Thread.sleep(1);
							} catch (InterruptedException e) {
								claies.remove(clay_label);
								remove(clay_label);
							}
						}
					} else {
						for (int i = 1200; i > -100; i-=2) {
							clay_label.setLocation(i, getHeight(i, height));
							try {
								Thread.sleep(1);
							} catch (InterruptedException e) {
								claies.remove(clay_label);
								remove(clay_label);
							}
						}
					}
					claies.remove(clay_label);
					remove(clay_label);
					
				}).start();
			}
			claies_group.interrupt();
			
			repaint();
			showMenu();
			isEnd = false;
			score = 0;
		};
	}
	
	@Override
	public void startGame() {
		game_score.setText("Score 0");
		new Thread(time_start, "Time Start").start();
		new Thread(create_clay, "Create Clay").start();
		frame.setCursor(frame.blankCursor);
	}
	
}
