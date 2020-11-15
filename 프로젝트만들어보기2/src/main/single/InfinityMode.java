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
	
	Thread start_infinity = null;
	Thread start_time = null;
	int minu = 0;

	public InfinityMode(MainFrame frame) {
		super(frame);
		
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
			
			frame.dialog = new InfinityDialog(frame, "Infinity Mode Rank", game_time.getText());
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
					
					Plate plate = new Plate(height);
					claies.add(plate);
					add(plate);
					
					int ran = (int) (Math.random() * 2);
					float speed = (float) (minu > 0 ? minu * 1.5 : 0);
					
					if(ran == 0) {
						for (float i = -100; i < 1200; i+=(2 + speed)) {
							plate.setLocation((int) i, getHeight(i, height));
							try {
								Thread.sleep(1);
							} catch (InterruptedException e) {
								claies.remove(plate);
								remove(plate);
							}
						}
					} else {
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
		minu = 0;
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
