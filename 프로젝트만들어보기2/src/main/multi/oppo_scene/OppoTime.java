package main.multi.oppo_scene;

import main.MainFrame;
import main.common.Plate;

public class OppoTime extends MultiOppoGame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public OppoTime(MainFrame frame) {
		super(frame);
		
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
		};
		
		startGame();
		setVisible(true);
	}
	
	@Override
	public void create_clay(int height, int ran) {
		new Thread(claies_group, () -> {
			
			Plate plate = new Plate(height);
			claies.add(plate);
			add(plate);
			
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
	public void reload() {}
}
