package main.multi.oppo_scene;

import java.awt.Font;

import javax.swing.JLabel;

import main.MainFrame;
import main.common.Plate;
import main.single.Bullet;

public class OppoReload extends MultiOppoGame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	int round = 1;
	JLabel round_label = null;
	Bullet bullet[] = new Bullet[5];	// ÃÑ¾Ë 5¹ß
	int bullet_count = 4;	// ÀÎµ¦½º¿¡ ¸ÂÃß±â
	
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
	
	public OppoReload(MainFrame frame) {
		super(frame);
		
		for (int i = 0; i < bullet.length; i++) {
			bullet[i] = new Bullet(800, 725 - i * 80);
			add(bullet[i]);
		}
		
		round_label = new JLabel("1 Round Start");
		round_label.setFont(new Font("Consolas", Font.BOLD, 40));
		round_label.setBounds(275, 250, 350, 60);
		add(round_label);
		round_label.setVisible(false);
		
		time_start = () -> {
			for (round = 1; round <= 10; round++) {
				repaint();
				
				game_time.setText(round + " Round");
				
				round_label.setText(round + " Round Start!");
				round_label.setVisible(true);
				try { Thread.sleep(1000); } catch (InterruptedException e) {}
				for (int i = 3; i > 0; i--) {
					round_label.setText("       " + i);
					try { Thread.sleep(1000); } catch (InterruptedException e) {}
				}
				round_label.setVisible(false);
				
				try { Thread.sleep(10000); } catch (InterruptedException e) {}
			}
		};
		
		startGame();
		setVisible(true);
	}
	
	@Override
	public void create_clay(int height, int ran) {
		new Thread(() -> {
			
			Plate plate = new Plate(height);
			claies.add(plate);
			add(plate);
			
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
	
	@Override
	public void startGame() {
		new Thread(time_start, "Time Start").start();
	}
	
	@Override
	public void endGame(int score) {
		game_score.setText("Score " + score);
		game_score.setVisible(true);
		game_time.setVisible(false);
	}
	
	@Override
	public void receiveMousePoint(int x, int y) {
		removeClay(x, y, claies, game_score);
		bullet[bullet_count--].setVisible(false);
	}
	
	@Override
	public void reload() {
		new Thread(reload_run).start();
	}
	
}
