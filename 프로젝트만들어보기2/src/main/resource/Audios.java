package main.resource;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Audios {
	
	public static final String SHOOT = "shoot.wav";	// 총쏘는 소리 wav
	public static final String PERCUSSION = "percussion.wav";	// 격발 소리 wav
	public static final String RELOAD = "reload.wav";	// 재장전 소리 wav
	

	public static void audio(String fileName) {	// 파일 이름을 얻어 소리내는 메소드
		Thread thread = new Thread() {
			public void run() {
				Clip clip = null;
				try {
					clip = AudioSystem.getClip();	// 클립을 초기화하고 
					AudioInputStream inputStream = AudioSystem.getAudioInputStream(getClass().getResource(fileName));	// 오디오 스트림으로 파일 이름으로 얻기
					clip.open(inputStream);	// 해당 클립에 오디오 스트림을 넣어서 시작시기기
					clip.start();
				} catch (Exception e) {
					e.printStackTrace();
				}

				while (true) {
					try { Thread.sleep(10); } catch (InterruptedException ie2) {}	// 매순간 체크해서 클립이 끝났다면 
					if (clip != null && !clip.isRunning()) {	// 클립 close 해주기
						clip.close();
						break;
					}
				}
			}
		};
		thread.setPriority(Thread.MAX_PRIORITY);	// 해당 쓰레드의 우선 순위 맥스로 지정 (소리가 늦게나면 안되기 때문에)
		thread.start();	// 스레드 시작
	}

}
