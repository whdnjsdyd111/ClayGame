package main.single;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JLabel;
import javax.swing.border.LineBorder;

import main.MainFrame;

public class InfinityMode extends InGame {
	
	Thread start_infinity = null;
	Thread start_time = null;

	public InfinityMode(MainFrame frame) {
		super(frame);
		
		score = 10;
		
		this.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				removeClay(e.getX(), e.getY(), claies, game_score, score);
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
			int i = 0;
			
			while(true) {
				int sec = i % 60;
				game_time.setText("0" + (i / 60) + ":" + (sec < 10 ? "0" + sec : sec));
				
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					break;
				}
				
				i++;
			}
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

					if(clay_label.isVisible() && score != 0) {
						score--;
						changeText(score);
						
						if(score == 0)
							start_infinity.interrupt();
					}
					
					claies.remove(clay_label);
					remove(clay_label);
					
				}).start();
			}
		};
	}
	
	@Override
	public void startGame() {
		frame.setCursor(frame.blankCursor);
		game_score.setText("Score 10");
		start_time = new Thread(time_start, "Time Start");
		start_infinity = new Thread(create_clay, "Create Clay");
		
		start_time.start();
		start_infinity.start();
		score = 10;
	}
	
	private void changeText(int score) {
		game_score.setText("Score " + score);
	}
	
	private void stopInfinity() {
		claies_group.interrupt();
		
		repaint();
		showMenu();
	}
}
