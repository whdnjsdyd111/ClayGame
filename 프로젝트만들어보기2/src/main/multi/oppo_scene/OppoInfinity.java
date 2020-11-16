package main.multi.oppo_scene;

import main.MainFrame;
import main.common.Plate;

public class OppoInfinity extends MultiOppoGame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	Thread start_infinity = null;
	Thread start_time = null;
	int minu = 0;
	
	public OppoInfinity(MainFrame frame) {
		super(frame);
		
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
			
			// frame.dialog = new InfinityDialog(frame, "infinity mode rank", game_time.getText());
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
			
			float speed = (float) (minu > 0 ? minu * 1.5 : 0);
			
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
			claies.remove(plate);
			remove(plate);
			
		}).start();
	}
	@Override
	public void startGame() {
		start_time = new Thread(time_start, "Time Start");
		
		start_time.start();
		minu = 0;
	}
	
	@Override
	public void endGame(int score) {
		System.out.println("상대방 시간 점수 : " + score);
		int sec = score / 100;
		int minu = sec / 60;
		int mili = score % 100;
		
		start_time.interrupt();
		game_score.setText((minu < 10 ? "0" + minu : minu) + ":" + (sec < 10 ? "0" + sec : sec) + ":" + (mili < 10 ? "0" + mili : mili));
		game_score.setVisible(true);
		game_time.setVisible(false);
	}
	
	@Override
	public void reload() {}
}
