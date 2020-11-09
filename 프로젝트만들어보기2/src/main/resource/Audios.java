package main.resource;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Audios {
	
	public static final String SHOOT = "shoot.wav";
	public static final String PERCUSSION = "percussion.wav";
	public static final String RELOAD = "reload.wav";
	

	public static void audio(String fileName) {
		new Thread() {
			public void run() {
				Clip clip = null;
				try {
					clip = AudioSystem.getClip();
					AudioInputStream inputStream = AudioSystem.getAudioInputStream(getClass().getResource(fileName));
					clip.open(inputStream);
					clip.start();
				} catch (Exception e) {
					e.printStackTrace();
				}

				while (true) {
					try { Thread.sleep(10); } catch (InterruptedException ie2) {}
					if (clip != null && !clip.isRunning()) {
						clip.close();
						break;
					}
				}
			}
		}.start();
	}

}
